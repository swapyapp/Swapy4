package com.app.muhammadgamal.swapy.Fragments;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.module.AppGlideModule;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


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

        progressBarAccount = rootView.findViewById(R.id.fragment_account_progressbar);
        progressBarAccount.setVisibility(View.VISIBLE);

<<<<<<< HEAD
        getUserDataFromDataBase();

        return rootView;
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
    private void getUserDataFromDataBase (){
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
=======
// Attach a listener to read the data at our posts reference
        userRef.addValueEventListener(new ValueEventListener() {
>>>>>>> 4c3f71744ad8ae02b830a249fecfcb0056d96b7b
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
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"The read failed: " + databaseError.getCode());
            }
        });
<<<<<<< HEAD
=======
        return rootView;
>>>>>>> 4c3f71744ad8ae02b830a249fecfcb0056d96b7b
    }
}



