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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.app.muhammadgamal.swapy.UserSittings.AccountSittings;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private ProgressBar progressBarAccount, progressBarCoverImage;
    private DatabaseReference userRef;
    private ImageView sittingsActivity, navDrawerImage, coverProfile;
    static int PReqCode = 1;
    static int REQUESTCODE = 1, REQUESTCODE2 = 2;
    private String profileImageUrl;
    private Uri pickedImageUri,pickedImageCoverUri ;
    private DrawerLayout drawer;
    private ImageView changeEmail, ChangeAccount;
    private  UploadTask uploadTask, uploadTaskCover;

    private static final String TAG = AccountFragment.class.getName();



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
        navDrawerImage = rootView.findViewById(R.id.navDrawerImage);
        userName = rootView.findViewById(R.id.fragment_account_user_name);
        userPhone = rootView.findViewById(R.id.fragment_account_phone);
        userMail = rootView.findViewById(R.id.fragment_account_email);
        coverProfile = rootView.findViewById(R.id.cover_image);
        progressBarCoverImage = rootView.findViewById(R.id.cover_image_progress_bar);



        sittingsActivity = rootView.findViewById(R.id.fragment_account_sittings);
        sittingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountSittings.class);
                startActivity(intent);
            }
        });

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        navDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
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

//        coverProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    openGalleryForeCover();
//                }
//        });

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
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUESTCODE);
    }

    private void openGalleryForeCover() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUESTCODE2);
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
        }else if (resultCode == RESULT_OK && requestCode == REQUESTCODE2 && data != null && data.getData() != null){
            //user choose the image
            //replace the image in the UI
            pickedImageCoverUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImageCoverUri);
                coverProfile.setVisibility(View.INVISIBLE);
                progressBarCoverImage.setVisibility(View.VISIBLE);
                uploadProfileCoverToFirebase();
                coverProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadProfileImageToFirebase() {
        String fileName = UUID.randomUUID().toString();
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + fileName + ".jpg");
        if (pickedImageUri != null) {

             uploadTask = profileImageRef.putFile(pickedImageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
//                    userImage.setVisibility(View.VISIBLE);
//                    progressBarAccount.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
//                    userImage.setVisibility(View.VISIBLE);
//                    progressBarAccount.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();


                }

            });

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return profileImageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        profileImageUrl = downloadUri.toString();
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
                                Toast.makeText(getContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }

    }

    private void uploadProfileCoverToFirebase() {
        String fileName = UUID.randomUUID().toString();
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profileCovers/" + fileName + ".jpg");
        if (pickedImageCoverUri != null) {

            uploadTaskCover = profileImageRef.putFile(pickedImageCoverUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTaskCover.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
//                    userImage.setVisibility(View.VISIBLE);
//                    progressBarAccount.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
//                    userImage.setVisibility(View.VISIBLE);
//                    progressBarAccount.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();


                }

            });

            Task<Uri> urlTask = uploadTaskCover.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return profileImageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        profileImageUrl = downloadUri.toString();
                        userRef.child("mProfileCoverURL").setValue(profileImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBarCoverImage.setVisibility(View.INVISIBLE);
                                coverProfile.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBarCoverImage.setVisibility(View.INVISIBLE);
                                coverProfile.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        // Handle failures
                        // ...
                    }
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
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
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

                    if(isAdded()) {
                        if (user.getmProfilePhotoURL() != null) {
                            Glide.with(getContext())
                                    .load(user.getmProfilePhotoURL())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            progressBarAccount.setVisibility(View.GONE);
                                            Log.e(TAG, "Load Image from fireBase failed");
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            progressBarAccount.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(userImage);
                        }else {
                            progressBarAccount.setVisibility(View.GONE);
                        }
                    }
                        if (user.getmCoverPhotoURL() != null) {
                            Glide.with(getContext())
                                    .load(user.getmCoverPhotoURL())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            progressBarCoverImage.setVisibility(View.GONE);
                                            Log.e(TAG, "Load Image from fireBase failed");
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            progressBarCoverImage.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(coverProfile);
                        }else {
                            progressBarCoverImage.setVisibility(View.GONE);
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



