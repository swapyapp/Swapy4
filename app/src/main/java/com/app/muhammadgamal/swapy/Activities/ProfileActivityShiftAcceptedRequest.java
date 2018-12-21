package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityShiftAcceptedRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb;
    private int swapAccepted;
    private ProgressBar progressBarProfileShiftAcceptedRequestImg1, progressBarProfileShiftAcceptedRequestImg2;
    private CircleImageView profileShiftAcceptedRequestUserImg, userProfileShiftAcceptedRequestUserImg;
    private TextView NameProfileShiftAcceptedRequest, ShiftTimeProfileShiftAcceptedRequest, shiftDayProfileShiftAcceptedRequest, shiftDateProfileShiftAcceptedRequest;
    private TextView UserNameProfileShiftAcceptedRequest, userShiftTimeProfileShiftAcceptedRequest, userShiftDayProfileShiftAcceptedRequest, userShiftDateProfileShiftAcceptedRequest;
    private TextView userEmailProfileShiftAcceptedRequest, userPhoneProfileShiftAcceptedRequest;
    private LinearLayout emailShiftProfileProfileShiftAcceptedRequest, phoneShiftProfileShiftAcceptedRequest;
    private SwapRequestShift swapDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_accepted_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request");

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Accepted Shift SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileShiftAcceptedRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileShiftAcceptedRequestImg1);
        progressBarProfileShiftAcceptedRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileShiftAcceptedRequestImg2);

        profileShiftAcceptedRequestUserImg = (CircleImageView) findViewById(R.id.profileShiftAcceptedRequestUserImg);
        userProfileShiftAcceptedRequestUserImg = (CircleImageView) findViewById(R.id.userProfileShiftAcceptedRequestUserImg);

        NameProfileShiftAcceptedRequest = (TextView) findViewById(R.id.NameProfileShiftAcceptedRequest);
        ShiftTimeProfileShiftAcceptedRequest = (TextView) findViewById(R.id.ShiftTimeProfileShiftAcceptedRequest);
        shiftDayProfileShiftAcceptedRequest = (TextView) findViewById(R.id.shiftDayProfileShiftAcceptedRequest);
        shiftDateProfileShiftAcceptedRequest = (TextView) findViewById(R.id.shiftDateProfileShiftAcceptedRequest);

        UserNameProfileShiftAcceptedRequest = (TextView) findViewById(R.id.UserNameProfileShiftAcceptedRequest);
        userShiftTimeProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userShiftTimeProfileShiftAcceptedRequest);
        userShiftDayProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userShiftDayProfileShiftAcceptedRequest);
        userShiftDateProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userShiftDateProfileShiftAcceptedRequest);

        userEmailProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userEmailProfileShiftAcceptedRequest);
        userPhoneProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userPhoneProfileShiftAcceptedRequest);

        emailShiftProfileProfileShiftAcceptedRequest = (LinearLayout) findViewById(R.id.emailShiftProfileProfileShiftAcceptedRequest);
        phoneShiftProfileShiftAcceptedRequest = (LinearLayout) findViewById(R.id.phoneShiftProfileShiftAcceptedRequest);

        //if current user is the sender then show receiver data
        if (swapDetails.getFromID().equals(currentUserId)) {

            NameProfileShiftAcceptedRequest.setText(swapDetails.getToName());
            ShiftTimeProfileShiftAcceptedRequest.setText(swapDetails.getToShiftTime());
            shiftDayProfileShiftAcceptedRequest.setText(swapDetails.getToShiftDay());
            shiftDateProfileShiftAcceptedRequest.setText(swapDetails.getToShiftDate());
            userEmailProfileShiftAcceptedRequest.setText(swapDetails.getToEmail());
            userPhoneProfileShiftAcceptedRequest.setText(swapDetails.getToPhone());
            if (swapDetails.getToImageUrl() != null) {
                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityShiftAcceptedRequest.this)
                        .load(swapDetails.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(profileShiftAcceptedRequestUserImg);
            } else {
                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                profileShiftAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            UserNameProfileShiftAcceptedRequest.setText(swapDetails.getFromName());
            userShiftTimeProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftTime());
            userShiftDayProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftDay());
            userShiftDateProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftDate());
            if (swapDetails.getFromImageUrl() != null) {
                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityShiftAcceptedRequest.this)
                        .load(swapDetails.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userProfileShiftAcceptedRequestUserImg);
            } else {
                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userProfileShiftAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            phoneShiftProfileShiftAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String message = "tel:" + swapDetails.getToPhone();
                    intent.setData(Uri.parse(message));
                    startActivity(intent);
                }
            });

            emailShiftProfileProfileShiftAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    String message = "mailto:" + swapDetails.getToEmail();
                    emailIntent.setData(Uri.parse(message));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

        }

        //if current user is the receiver then show sender data
        if (swapDetails.getToID().equals(currentUserId)) {

            NameProfileShiftAcceptedRequest.setText(swapDetails.getFromName());
            ShiftTimeProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftTime());
            shiftDayProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftDay());
            shiftDateProfileShiftAcceptedRequest.setText(swapDetails.getFromShiftDate());
            userEmailProfileShiftAcceptedRequest.setText(swapDetails.getFromEmail());
            userPhoneProfileShiftAcceptedRequest.setText(swapDetails.getFromPhone());
            if (swapDetails.getFromImageUrl() != null) {
                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityShiftAcceptedRequest.this)
                        .load(swapDetails.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(profileShiftAcceptedRequestUserImg);
            } else {
                progressBarProfileShiftAcceptedRequestImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                profileShiftAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            UserNameProfileShiftAcceptedRequest.setText(swapDetails.getToName());
            userShiftTimeProfileShiftAcceptedRequest.setText(swapDetails.getToShiftTime());
            userShiftDayProfileShiftAcceptedRequest.setText(swapDetails.getToShiftDay());
            userShiftDateProfileShiftAcceptedRequest.setText(swapDetails.getToShiftDate());
            if (swapDetails.getToImageUrl() != null) {
                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityShiftAcceptedRequest.this)
                        .load(swapDetails.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userProfileShiftAcceptedRequestUserImg);
            } else {
                progressBarProfileShiftAcceptedRequestImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userProfileShiftAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            phoneShiftProfileShiftAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String message = "tel:" + swapDetails.getFromPhone();
                    intent.setData(Uri.parse(message));
                    startActivity(intent);
                }
            });

            emailShiftProfileProfileShiftAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    String message = "mailto:" + swapDetails.getFromEmail();
                    emailIntent.setData(Uri.parse(message));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //To support reverse transition when user clicks the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
