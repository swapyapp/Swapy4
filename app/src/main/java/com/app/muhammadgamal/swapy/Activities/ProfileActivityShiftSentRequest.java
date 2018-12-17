package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ProfileActivityShiftSentRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb;
    private int swapAccepted;
    private ProgressBar progressBarProfileShiftSentRequestImg1, progressBarProfileShiftSentRequestImg2, progressBar_WithdrawProfileShiftSentRequest;
    private CircleImageView profileShiftSentRequestUserImg, userProfileShiftSentRequestUserImg;
    private ImageView img_back_profile_shift_sent_request;
    private TextView NameProfileShiftSentRequest, ShiftTimeProfileShiftSentRequest, shiftDayProfileShiftSentRequest, shiftDateProfileShiftSentRequest;
    private TextView UserNameProfileShiftSentRequest, userShiftTimeProfileShiftSentRequest, userShiftDayProfileShiftSentRequest, userShiftDateProfileShiftSentRequest;
    private TextView textDisplayContactInfoProfileShiftSentRequest;
    private Button buttonWithdrawProfileShiftSentRequest;
    private SwapRequestShift swapDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_sent_request);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request");

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Sent Shift SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileShiftSentRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileShiftSentRequestImg1);
        progressBarProfileShiftSentRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileShiftSentRequestImg2);
        progressBar_WithdrawProfileShiftSentRequest = (ProgressBar) findViewById(R.id.progressBar_WithdrawProfileShiftSentRequest);

        profileShiftSentRequestUserImg = (CircleImageView) findViewById(R.id.profileShiftSentRequestUserImg);
        userProfileShiftSentRequestUserImg = (CircleImageView) findViewById(R.id.userProfileShiftSentRequestUserImg);
        img_back_profile_shift_sent_request = (ImageView) findViewById(R.id.img_back_profile_shift_sent_request);

        NameProfileShiftSentRequest = (TextView) findViewById(R.id.NameProfileShiftSentRequest);
        ShiftTimeProfileShiftSentRequest = (TextView) findViewById(R.id.ShiftTimeProfileShiftSentRequest);
        shiftDayProfileShiftSentRequest = (TextView) findViewById(R.id.shiftDayProfileShiftSentRequest);
        shiftDateProfileShiftSentRequest = (TextView) findViewById(R.id.shiftDateProfileShiftSentRequest);

        UserNameProfileShiftSentRequest = (TextView) findViewById(R.id.UserNameProfileShiftSentRequest);
        userShiftTimeProfileShiftSentRequest = (TextView) findViewById(R.id.userShiftTimeProfileShiftSentRequest);
        userShiftDayProfileShiftSentRequest = (TextView) findViewById(R.id.userShiftDayProfileShiftSentRequest);
        userShiftDateProfileShiftSentRequest = (TextView) findViewById(R.id.userShiftDateProfileShiftSentRequest);

        textDisplayContactInfoProfileShiftSentRequest = (TextView) findViewById(R.id.textDisplayContactInfoProfileShiftSentRequest);

        buttonWithdrawProfileShiftSentRequest = (Button) findViewById(R.id.buttonWithdrawProfileShiftSentRequest);

        swapperID = swapDetails.getToID();
        swapperName = swapDetails.getToName();
        swapperEmail = swapDetails.getToEmail();
        swapperPhone = swapDetails.getToPhone();
        swapperCompanyBranch = swapDetails.getToCompanyBranch();
        swapperAccount = swapDetails.getToAccount();
        swapperImageUrl = swapDetails.getToImageUrl();
        swapperShiftDay = swapDetails.getToShiftDay();
        swapShiftDate = swapDetails.getToShiftDate();
        swapperShiftTime = swapDetails.getToShiftTime();
        swapperPreferredShift = swapDetails.getToPreferredShift();

        //swapper's data
        NameProfileShiftSentRequest.setText(swapperName);
        ShiftTimeProfileShiftSentRequest.setText(swapperShiftTime);
        shiftDayProfileShiftSentRequest.setText(swapperShiftDay);
        shiftDateProfileShiftSentRequest.setText(swapShiftDate);
        if (swapperImageUrl != null) {
            progressBarProfileShiftSentRequestImg1.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityShiftSentRequest.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileShiftSentRequestImg1.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(profileShiftSentRequestUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileShiftSentRequestUserImg.setImageDrawable(photoUrl);
        }

        //current user's data
        UserNameProfileShiftSentRequest.setText(swapDetails.getFromName());
        userShiftTimeProfileShiftSentRequest.setText(swapDetails.getFromShiftTime());
        userShiftDayProfileShiftSentRequest.setText(swapDetails.getFromShiftDay());
        userShiftDateProfileShiftSentRequest.setText(swapDetails.getFromShiftDate());
        if (swapDetails.getToImageUrl() != null) {
            progressBarProfileShiftSentRequestImg2.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityShiftSentRequest.this)
                    .load(swapDetails.getFromImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileShiftSentRequestImg2.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userProfileShiftSentRequestUserImg);

        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            userProfileShiftSentRequestUserImg.setImageDrawable(photoUrl);
        }

    }
}
