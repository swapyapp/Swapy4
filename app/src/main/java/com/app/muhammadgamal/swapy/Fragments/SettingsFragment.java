package com.app.muhammadgamal.swapy.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Activities.AccountSittings;
import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.ProfileActivityShift;
import com.app.muhammadgamal.swapy.Activities.SettingsActivity;
import com.app.muhammadgamal.swapy.Activities.SignInActivity;
import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;

import java.util.UUID;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private LinearLayout rateUs;
    private Dialog rateUsDialog;
    private int ratingLevel;
    private DatabaseReference rateRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Settings");

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUserID = mAuth.getCurrentUser().getUid();
        rateRef = FirebaseDatabase.getInstance().getReference().child("Rates").child(currentUserID);

        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView more_options = rootView.findViewById(R.id.sittings_more_options);
        more_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreOptions = new Intent(getContext(), SettingsActivity.class);
                startActivity(moreOptions);
            }
        });

        ImageView log_out = rootView.findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentUser = mAuth.getCurrentUser().getUid();
                userRef.child(currentUser).child("device_token").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(rootView.getContext(), SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }
        });
        rateUsDialog = new Dialog(rootView.getContext());
        rateUsDialog.setContentView(R.layout.rate_us_dialog);
        rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rateUs = rootView.findViewById(R.id.rateUs);
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUsDialog.show();
            }
        });

        SmileRating smileRating = (SmileRating) rateUsDialog.findViewById(R.id.smile_rating);
        Button buttonRateUsDialog = rateUsDialog.findViewById(R.id.buttonRateUsDialog);
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                ratingLevel = level;
            }
        });
        buttonRateUsDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateRef.setValue(ratingLevel);
                Toast.makeText(rootView.getContext(), "Thank You fo rating us", Toast.LENGTH_SHORT).show();
                rateUsDialog.dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((NavDrawerActivity)getActivity()).updateStatusBarColor("#0081cb");

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}
