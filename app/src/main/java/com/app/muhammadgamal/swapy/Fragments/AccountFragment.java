package com.app.muhammadgamal.swapy.Fragments;

import android.graphics.Color;
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
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Account");
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        accountImage = rootView.findViewById(R.id.user_account_image);
        accountUsername = rootView.findViewById(R.id.user_account_name);
        accountLoginID = rootView.findViewById(R.id.account_user_loginid);
        accountMail = rootView.findViewById(R.id.account_user_mail);
        accountPhone = rootView.findViewById(R.id.account_user_phone);


         currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
       currentUserDb.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               user = dataSnapshot.getValue(User.class);
               if (user.getmProfilePhotoURL() != null) {
                   Glide.with(AccountFragment.this).load(user.getmProfilePhotoURL()).into(accountImage);
               }
               accountUsername.setText(user.getmUsername());
               accountMail.setText(user.getmEmail());
               accountPhone.setText(user.getmPhoneNumber());
               accountLoginID.setText(user.getmLoginID());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.e(LOG_TAG, "Error with retreaving data from Database");
           }
       });

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//
//        ((NavDrawerActivity)getActivity()).updateStatusBarColor("#FFFFFF");

    }
}
