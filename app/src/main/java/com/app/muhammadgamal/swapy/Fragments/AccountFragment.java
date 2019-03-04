package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.app.muhammadgamal.swapy.Activities.AccountSittings;
import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.SignUpActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.module.AppGlideModule;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {
    private View rootView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference ref;
    private CircleImageView userImage;
    private TextView userName;
    private TextView userMail;
    private TextView userPhone;
    private TextView userSwapNumber;
    private ProgressBar progressBarAccount;
    private DatabaseReference userRef;
    private ImageView sittingsActivity;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    private String profileImageUrl;
    private Uri pickedImageUri;

    private static final String TAG = "AccountFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Account");
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        mAuth = FirebaseAuth.getInstance();
        // get current user ID
        final String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        // Get a reference to our posts
        database = FirebaseDatabase.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        userImage = rootView.findViewById(R.id.fragment_account_user_image);
        userName = rootView.findViewById(R.id.fragment_account_user_name);
        userPhone = rootView.findViewById(R.id.fragment_account_phone);
        userMail = rootView.findViewById(R.id.fragment_account_email);

        sittingsActivity = rootView.findViewById(R.id.fragment_account_sittings);
        sittingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountSittings.class);
                startActivity(intent);
            }
        });

        progressBarAccount = rootView.findViewById(R.id.fragment_account_progressbar);
        progressBarAccount.setVisibility(View.VISIBLE);

        getUserDataFromDataBase();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {
                    requestPermissionAndOpenGallery();
                } else {
                    openGallery();
                }
            }
        });

        return rootView;
    }

    private void requestPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null && data.getData() != null) {
            //user choose the image
            //replace the image in the UI
            pickedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImageUri);
                userImage.setVisibility(View.INVISIBLE);
                progressBarAccount.setVisibility(View.VISIBLE);
                uploadProfileImageToFirebase();
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImageToFirebase() {
        String fileName = UUID.randomUUID().toString();
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + fileName + ".jpg");
        if (pickedImageUri != null) {
            profileImageRef.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImageUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();
                    userRef.child("mProfilePhotoURL").setValue(profileImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            userImage.setVisibility(View.VISIBLE);
                            progressBarAccount.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            userImage.setVisibility(View.VISIBLE);
                            progressBarAccount.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//
//        ((NavDrawerActivity)getActivity()).updateStatusBarColor("#FFFFFF");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void getUserDataFromDataBase() {
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            // Attach a listener to read the data at our posts reference
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if ((dataSnapshot.exists())) {

                    userName.setText(user.getmUsername());
                    userPhone.setText(user.getmPhoneNumber());
                    userMail.setText(user.getmEmail());

                    if (user.getmProfilePhotoURL() != null) {
                        Glide.with(getContext())
                                .load(user.getmProfilePhotoURL())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        progressBarAccount.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBarAccount.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(userImage);
                    } else {
                        progressBarAccount.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "The read failed: " + databaseError.getCode());
            }
        });
    }
}



