package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityShiftReceivedRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb;
    private int swapAccepted;
    private ProgressBar progressBarProfileShiftRequestImg1, progressBarProfileShiftRequestImg2, progressBar_acceptProfileShiftRequest, progressBar_rejectProfileShiftRequest;
    private CircleImageView profileShiftRequestUserImg, userProfileShiftRequestUserImg;
    private ImageView img_back_profile_shift_request;
    private TextView NameProfileShiftRequest, ShiftTimeProfileShiftRequest, shiftDayProfileShiftRequest, shiftDateProfileShiftRequest;
    private TextView UserNameProfileShiftRequest, userShiftTimeProfileShiftRequest, userShiftDayProfileShiftRequest, userShiftDateProfileShiftRequest;
    private TextView textDisplayContactInfoProfileShiftRequest, userEmailProfileShiftRequest, userPhoneProfileShiftRequest;
    private TextView you_accepted_requestShiftProfile;
    private LinearLayout emailShiftProfileProfileShiftRequest, phoneShiftProfileShiftRequest, userContactInfoProfileShiftReceivedRequest;
    private Button buttonAcceptProfileShiftRequest, buttonRejectProfileShiftRequest;
    private SwapRequestShift swapDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_received_request);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request");

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Received Shift SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileShiftRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileShiftReceivedRequestImg1);
        progressBarProfileShiftRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileShiftReceivedRequestImg2);
        progressBar_acceptProfileShiftRequest = (ProgressBar) findViewById(R.id.progressBar_acceptProfileShiftReceivedRequest);
        progressBar_rejectProfileShiftRequest = (ProgressBar) findViewById(R.id.progressBar_rejectProfileShiftReceivedRequest);

        profileShiftRequestUserImg = (CircleImageView) findViewById(R.id.profileShiftReceivedRequestUserImg);
        userProfileShiftRequestUserImg = (CircleImageView) findViewById(R.id.userProfileShiftReceivedRequestUserImg);
        img_back_profile_shift_request = (ImageView) findViewById(R.id.img_back_profile_shift_received_request);

        NameProfileShiftRequest = (TextView) findViewById(R.id.NameProfileShiftReceivedRequest);
        ShiftTimeProfileShiftRequest = (TextView) findViewById(R.id.ShiftTimeProfileShiftReceivedRequest);
        shiftDayProfileShiftRequest = (TextView) findViewById(R.id.shiftDayProfileShiftReceivedRequest);
        shiftDateProfileShiftRequest = (TextView) findViewById(R.id.shiftDateProfileShiftReceivedRequest);

        UserNameProfileShiftRequest = (TextView) findViewById(R.id.UserNameProfileShiftReceivedRequest);
        userShiftTimeProfileShiftRequest = (TextView) findViewById(R.id.userShiftTimeProfileShiftReceivedRequest);
        userShiftDayProfileShiftRequest = (TextView) findViewById(R.id.userShiftDayProfileShiftReceivedRequest);
        userShiftDateProfileShiftRequest = (TextView) findViewById(R.id.userShiftDateProfileShiftReceivedRequest);

        textDisplayContactInfoProfileShiftRequest = (TextView) findViewById(R.id.textDisplayContactInfoProfileShiftReceivedRequest);
        userEmailProfileShiftRequest = (TextView) findViewById(R.id.userEmailProfileShiftReceivedRequest);
        userPhoneProfileShiftRequest = (TextView) findViewById(R.id.userPhoneProfileShiftReceivedRequest);

        you_accepted_requestShiftProfile = (TextView) findViewById(R.id.you_accepted_requestShiftProfile);

        emailShiftProfileProfileShiftRequest = (LinearLayout) findViewById(R.id.emailShiftProfileProfileShiftReceivedRequest);
        phoneShiftProfileShiftRequest = (LinearLayout) findViewById(R.id.phoneShiftProfileShiftReceivedRequest);
        userContactInfoProfileShiftReceivedRequest = (LinearLayout) findViewById(R.id.userContactInfoProfileShiftReceivedRequest);

        buttonAcceptProfileShiftRequest = (Button) findViewById(R.id.buttonAcceptProfileShiftReceivedRequest);
        buttonRejectProfileShiftRequest = (Button) findViewById(R.id.buttonRejectProfileShiftReceivedRequest);


        swapperID = swapDetails.getFromID();
        swapperName = swapDetails.getFromName();
        swapperEmail = swapDetails.getFromEmail();
        swapperPhone = swapDetails.getFromPhone();
        swapperCompanyBranch = swapDetails.getFromCompanyBranch();
        swapperAccount = swapDetails.getFromAccount();
        swapperImageUrl = swapDetails.getFromImageUrl();
        swapperShiftDay = swapDetails.getFromShiftDay();
        swapShiftDate = swapDetails.getFromShiftDate();
        swapperShiftTime = swapDetails.getFromShiftTime();
        swapperPreferredShift = swapDetails.getFromPreferredShift();

        //swapper's data
        NameProfileShiftRequest.setText(swapperName);
        ShiftTimeProfileShiftRequest.setText(swapperShiftTime);
        shiftDayProfileShiftRequest.setText(swapperShiftDay);
        shiftDateProfileShiftRequest.setText(swapShiftDate);
        userEmailProfileShiftRequest.setText(swapperEmail);
        userPhoneProfileShiftRequest.setText(swapperPhone);
        if (swapperImageUrl != null) {
            progressBarProfileShiftRequestImg1.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityShiftReceivedRequest.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileShiftRequestImg1.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(profileShiftRequestUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileShiftRequestUserImg.setImageDrawable(photoUrl);
        }

        //current user's data
        UserNameProfileShiftRequest.setText(swapDetails.getToName());
        userShiftTimeProfileShiftRequest.setText(swapDetails.getToShiftTime());
        userShiftDayProfileShiftRequest.setText(swapDetails.getToShiftDay());
        userShiftDateProfileShiftRequest.setText(swapDetails.getToShiftDate());
        if (swapDetails.getToImageUrl() != null) {
            progressBarProfileShiftRequestImg2.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityShiftReceivedRequest.this)
                    .load(swapDetails.getToImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileShiftRequestImg2.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userProfileShiftRequestUserImg);

        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            userProfileShiftRequestUserImg.setImageDrawable(photoUrl);
        }

        phoneShiftProfileShiftRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String message = "tel:" + swapperPhone;
                intent.setData(Uri.parse(message));
                startActivity(intent);
            }
        });

        emailShiftProfileProfileShiftRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String message = "mailto:" + swapperEmail;
                emailIntent.setData(Uri.parse(message));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        buttonAcceptProfileShiftRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAcceptProfileShiftRequest.setVisibility(View.INVISIBLE);
                progressBar_acceptProfileShiftRequest.setVisibility(View.VISIBLE);
                accept();
            }
        });

        buttonRejectProfileShiftRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonRejectProfileShiftRequest.setVisibility(View.INVISIBLE);
                progressBar_rejectProfileShiftRequest.setVisibility(View.VISIBLE);
                reject();
            }
        });

    }

    private void accept(){

        shiftSwapRequestsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    SwapRequestShift swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);
                    if (swapRequestShift.getFromID().equals(currentUserId)
                            && swapRequestShift.getFromShiftTime().equals(swapDetails.getToShiftTime())
                            && swapRequestShift.getFromShiftDay().equals(swapDetails.getToShiftDay())
                            && swapRequestShift.getFromPreferredShift().equals(swapDetails.getToPreferredShift())
                            && swapRequestShift.getToID().equals(swapperID)
                            && swapRequestShift.getToShiftTime().equals(swapperShiftTime)
                            && swapRequestShift.getToShiftDay().equals(swapperShiftDay)
                            && swapRequestShift.getToPreferredShift().equals(swapperPreferredShift)) {


                        shiftSwapRequestsDb.child("accepted").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar_acceptProfileShiftRequest.setVisibility(View.INVISIBLE);
                                userContactInfoProfileShiftReceivedRequest.setVisibility(View.VISIBLE);
                                textDisplayContactInfoProfileShiftRequest.setVisibility(View.GONE);
                            }
                        });

                    }

                    if (swapRequestShift.getToID().equals(currentUserId)
                            && swapRequestShift.getToShiftTime().equals(swapDetails.getToShiftTime())
                            && swapRequestShift.getToShiftDay().equals(swapDetails.getToShiftDay())
                            && swapRequestShift.getToPreferredShift().equals(swapDetails.getToPreferredShift())
                            && swapRequestShift.getFromID().equals(swapperID)
                            && swapRequestShift.getFromShiftTime().equals(swapperShiftTime)
                            && swapRequestShift.getFromShiftDay().equals(swapperShiftDay)
                            && swapRequestShift.getFromPreferredShift().equals(swapperPreferredShift)) {

                        shiftSwapRequestsDb.child("accepted").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar_acceptProfileShiftRequest.setVisibility(View.INVISIBLE);
                                userContactInfoProfileShiftReceivedRequest.setVisibility(View.VISIBLE);
                                textDisplayContactInfoProfileShiftRequest.setVisibility(View.GONE);
                            }
                        });

                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void reject(){



    }
}
