package com.app.muhammadgamal.swapy.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AccountFragment extends Fragment {
    private View rootView;
    private DatabaseReference userDatabaseRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private TextView accountUsername, accountLoginID, accountMail, accountPhone;
    private ImageView accountImage;
    private String userName, userMail, userLoginId, userImage, userPhone;
    private User user;
    private final String LOG_TAG = "AccountFragment";
    ProgressBar progressBarNav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Account");
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        accountImage = rootView.findViewById(R.id.user_account_image);
        accountUsername = rootView.findViewById(R.id.user_account_name);
        accountLoginID = rootView.findViewById(R.id.account_user_loginid);
        accountMail = rootView.findViewById(R.id.account_user_mail);
        accountPhone = rootView.findViewById(R.id.account_user_phone);

        progressBarNav = rootView.findViewById(R.id.progressBarAcF);

        getUserData();


        return inflater.inflate(R.layout.fragment_account, container, false);
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

    private void getUserData() {
        final String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        currentUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (dataSnapshot.exists()) {
                    if (user.getmProfilePhotoURL() != null) {
                        progressBarNav.setVisibility(View.VISIBLE);
                        Glide.with(AccountFragment.this)
                                .load(user.getmProfilePhotoURL())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBarNav.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(accountImage);
                    }
                    accountUsername.setText(user.getmUsername());
                    accountMail.setText(user.getmEmail());
                    accountPhone.setText(user.getmPhoneNumber());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

