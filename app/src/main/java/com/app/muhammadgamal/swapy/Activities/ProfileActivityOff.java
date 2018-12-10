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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOff extends AppCompatActivity {

    private ImageView img_back_off_profile;
    private CircleImageView offProfileUserImg;
    private TextView userOffProfileName, offProfileCompanyBranch, offProfileAccount, offProfileCurrent, offProfilePreferred,  offProfileTextDisplayContactInfo,  userEmailOffProfile,  userPhoneOffProfile,
            textSentOrAcceptedRequestOffProfile,  textWaitingForAcceptanceOffProfile,  textAcceptedRequestOffProfile,  you_accepted_requestOffProfile,  user_sent_you_request_off_profile;
    private Button buttonSwapRequestOffProfile;
    private ProgressBar progressBar_off_profile, progressBarOffProfileActivityImage;
    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperName, swapperEmail, swapperPhone, swapperCompanyBranch, swapperAccount,
            swapperImageUrl, offDay, swapOffDate, preferredOff;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        progressBar_off_profile = (ProgressBar) findViewById(R.id.progressBar_off_profile);
        progressBarOffProfileActivityImage = (ProgressBar) findViewById(R.id.progressBarOffProfileActivityImage);

        img_back_off_profile = (ImageView) findViewById(R.id.img_back_off_profile);
        offProfileUserImg = (CircleImageView) findViewById(R.id.offProfileUserImg);

        Intent intent = getIntent();
        SwapOff swapDetails = intent.getParcelableExtra("off swapper info");

        swapperID = swapDetails.getSwapperID();
        swapperName = swapDetails.getSwapperName();
        swapperEmail = swapDetails.getSwapperEmail();
        swapperPhone = swapDetails.getSwapperPhone();
        swapperCompanyBranch = swapDetails.getSwapperCompanyBranch();
        swapperAccount = swapDetails.getSwapperAccount();
        swapperImageUrl = swapDetails.getSwapperImageUrl();
        offDay = swapDetails.getOffDay();
        swapOffDate = swapDetails.getSwapOffDate();
        preferredOff = swapDetails.getPreferedOff();

        if (swapperImageUrl != null){
            progressBarOffProfileActivityImage.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityOff.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarOffProfileActivityImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(offProfileUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            offProfileUserImg.setImageDrawable(photoUrl);
        }

        userOffProfileName = (TextView) findViewById(R.id.userOffProfileName);
        userOffProfileName.setText(swapperName);
        offProfileCompanyBranch = (TextView) findViewById(R.id.offProfileCompanyBranch);
        offProfileCompanyBranch.setText(swapperCompanyBranch);
        offProfileAccount = (TextView) findViewById(R.id.offProfileAccount);
        offProfileAccount.setText(swapperAccount);
        offProfileCurrent = (TextView) findViewById(R.id.offProfileCurrent);
        offProfileCurrent.setText(offDay);
        offProfilePreferred = (TextView) findViewById(R.id.offProfilePreferred);
        offProfilePreferred.setText(preferredOff);
        offProfileTextDisplayContactInfo = (TextView) findViewById(R.id.offProfileTextDisplayContactInfo);
        userEmailOffProfile = (TextView) findViewById(R.id.userEmailOffProfile);
        userEmailOffProfile.setText(swapperEmail);
        userPhoneOffProfile = (TextView) findViewById(R.id.userPhoneOffProfile);
        userPhoneOffProfile.setText(swapperPhone);
        textSentOrAcceptedRequestOffProfile = (TextView) findViewById(R.id.textSentOrAcceptedRequestOffProfile);
        textWaitingForAcceptanceOffProfile = (TextView) findViewById(R.id.textWaitingForAcceptanceOffProfile);
        textAcceptedRequestOffProfile = (TextView) findViewById(R.id.textAcceptedRequestOffProfile);
        you_accepted_requestOffProfile = (TextView) findViewById(R.id.you_accepted_requestOffProfile);
        user_sent_you_request_off_profile = (TextView) findViewById(R.id.user_sent_you_request_off_profile);

        buttonSwapRequestOffProfile = (Button) findViewById(R.id.buttonSwapRequestOffProfile);



    }
}
