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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOffAcceptedRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private SwapRequestOff swapDetails;
    private int swapAccepted;
    private ProgressBar progressBarProfileOffAcceptedRequestImg1, progressBarProfileOffAcceptedRequestImg2;
    private CircleImageView profileOffAcceptedRequestUserImg, userProfileOffAcceptedRequestUserImg;
    private TextView NameProfileOffAcceptedRequest, offDayProfileShiftAcceptedRequest, OffDateProfileShiftAcceptedRequest;
    private TextView UserNameProfileOffAcceptedRequest, userOffDayProfileShiftAcceptedRequest, userOffDateProfileShiftAcceptedRequest;
    private TextView userEmailProfileOffAcceptedRequest, userPhoneProfileOffAcceptedRequest;
    private LinearLayout emailShiftProfileProfileOffAcceptedRequest, phoneShiftProfileOffAcceptedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off_accepted_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Accepted Off SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileOffAcceptedRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileOffAcceptedRequestImg1);
        progressBarProfileOffAcceptedRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileOffAcceptedRequestImg2);

        profileOffAcceptedRequestUserImg = (CircleImageView) findViewById(R.id.profileOffAcceptedRequestUserImg);
        userProfileOffAcceptedRequestUserImg = (CircleImageView) findViewById(R.id.userProfileOffAcceptedRequestUserImg);

        NameProfileOffAcceptedRequest = (TextView) findViewById(R.id.NameProfileOffAcceptedRequest);
        offDayProfileShiftAcceptedRequest = (TextView) findViewById(R.id.offDayProfileShiftAcceptedRequest);
        OffDateProfileShiftAcceptedRequest = (TextView) findViewById(R.id.OffDateProfileShiftAcceptedRequest);

        UserNameProfileOffAcceptedRequest = (TextView) findViewById(R.id.UserNameProfileOffAcceptedRequest);
        userOffDayProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userOffDayProfileShiftAcceptedRequest);
        userOffDateProfileShiftAcceptedRequest = (TextView) findViewById(R.id.userOffDateProfileShiftAcceptedRequest);

        userEmailProfileOffAcceptedRequest = (TextView) findViewById(R.id.userEmailProfileOffAcceptedRequest);
        userPhoneProfileOffAcceptedRequest = (TextView) findViewById(R.id.userPhoneProfileOffAcceptedRequest);

        emailShiftProfileProfileOffAcceptedRequest = (LinearLayout) findViewById(R.id.emailShiftProfileProfileOffAcceptedRequest);
        phoneShiftProfileOffAcceptedRequest = (LinearLayout) findViewById(R.id.phoneShiftProfileOffAcceptedRequest);

        //if current user is the sender then show receiver data
        if (swapDetails.getFromID().equals(currentUserId)) {

            NameProfileOffAcceptedRequest.setText(swapDetails.getToName());
            offDayProfileShiftAcceptedRequest.setText(swapDetails.getToOffDay());
            OffDateProfileShiftAcceptedRequest.setText(swapDetails.getToOffDate());
            userEmailProfileOffAcceptedRequest.setText(swapDetails.getToEmail());
            userPhoneProfileOffAcceptedRequest.setText(swapDetails.getToPhone());
            if (swapDetails.getToImageUrl() != null) {
                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityOffAcceptedRequest.this)
                        .load(swapDetails.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(profileOffAcceptedRequestUserImg);
            } else {
                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                profileOffAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            UserNameProfileOffAcceptedRequest.setText(swapDetails.getFromName());
            userOffDayProfileShiftAcceptedRequest.setText(swapDetails.getFromOffDay());
            userOffDateProfileShiftAcceptedRequest.setText(swapDetails.getFromOffDate());
            if (swapDetails.getFromImageUrl() != null) {
                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityOffAcceptedRequest.this)
                        .load(swapDetails.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userProfileOffAcceptedRequestUserImg);
            } else {
                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userProfileOffAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            phoneShiftProfileOffAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String message = "tel:" + swapDetails.getToPhone();
                    intent.setData(Uri.parse(message));
                    startActivity(intent);
                }
            });

            emailShiftProfileProfileOffAcceptedRequest.setOnClickListener(new View.OnClickListener() {
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

            NameProfileOffAcceptedRequest.setText(swapDetails.getFromName());
            offDayProfileShiftAcceptedRequest.setText(swapDetails.getFromOffDay());
            OffDateProfileShiftAcceptedRequest.setText(swapDetails.getFromOffDate());
            userEmailProfileOffAcceptedRequest.setText(swapDetails.getFromEmail());
            userPhoneProfileOffAcceptedRequest.setText(swapDetails.getFromPhone());
            if (swapDetails.getFromImageUrl() != null) {
                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityOffAcceptedRequest.this)
                        .load(swapDetails.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(profileOffAcceptedRequestUserImg);
            } else {
                progressBarProfileOffAcceptedRequestImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                profileOffAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            UserNameProfileOffAcceptedRequest.setText(swapDetails.getToName());
            userOffDayProfileShiftAcceptedRequest.setText(swapDetails.getToOffDay());
            userOffDateProfileShiftAcceptedRequest.setText(swapDetails.getToOffDate());
            if (swapDetails.getToImageUrl() != null) {
                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivityOffAcceptedRequest.this)
                        .load(swapDetails.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userProfileOffAcceptedRequestUserImg);
            } else {
                progressBarProfileOffAcceptedRequestImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = getApplicationContext().getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userProfileOffAcceptedRequestUserImg.setImageDrawable(photoUrl);
            }

            phoneShiftProfileOffAcceptedRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String message = "tel:" + swapDetails.getFromPhone();
                    intent.setData(Uri.parse(message));
                    startActivity(intent);
                }
            });

            emailShiftProfileProfileOffAcceptedRequest.setOnClickListener(new View.OnClickListener() {
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
