package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOffSentRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredOff, swapOffDate, swapperOffDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private String toID, toName, toOffDate, toOffDay, toPhone, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredOff;
    private String fromID, fromName, fromOffDate, fromOffDay, fromPhone, fromOffTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredOff;
    private DatabaseReference databaseReference;
    private DatabaseReference offSwapRequestsDb;
    private SwapRequestOff swapDetails;
    private int swapAccepted;
    private CircleImageView profileOffSentRequestUserImg, userProfileOffSentRequestUserImg;
    private TextView NameProfileOffSentRequest, offDayProfileOffSentRequest, OffDateProfileOffSentRequest;
    private TextView UserNameProfileOffSentRequest, userOffDayProfileOffSentRequest, userOffDateProfileOffSentRequest;
    private TextView textDisplayContactInfoProfileOffSentRequest;
    private Button buttonWithdrawProfileOffSentRequest;
    private ProgressBar progressBarProfileOffSentRequestImg1, progressBarProfileOffSentRequestImg2, progressBar_WithdrawProfileOffSentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off_sent_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Sent Off SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileOffSentRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileOffSentRequestImg1);
        progressBarProfileOffSentRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileOffSentRequestImg2);
        progressBar_WithdrawProfileOffSentRequest = (ProgressBar) findViewById(R.id.progressBar_WithdrawProfileOffSentRequest);

        profileOffSentRequestUserImg = (CircleImageView) findViewById(R.id.profileOffSentRequestUserImg);
        userProfileOffSentRequestUserImg = (CircleImageView) findViewById(R.id.userProfileOffSentRequestUserImg);

        NameProfileOffSentRequest = (TextView) findViewById(R.id.NameProfileOffSentRequest);
        offDayProfileOffSentRequest = (TextView) findViewById(R.id.offDayProfileOffSentRequest);
        OffDateProfileOffSentRequest = (TextView) findViewById(R.id.OffDateProfileOffSentRequest);

        UserNameProfileOffSentRequest = (TextView) findViewById(R.id.UserNameProfileOffSentRequest);
        userOffDayProfileOffSentRequest = (TextView) findViewById(R.id.userOffDayProfileOffSentRequest);
        userOffDateProfileOffSentRequest = (TextView) findViewById(R.id.userOffDateProfileOffSentRequest);

        textDisplayContactInfoProfileOffSentRequest = (TextView) findViewById(R.id.textDisplayContactInfoProfileOffSentRequest);

        buttonWithdrawProfileOffSentRequest = (Button) findViewById(R.id.buttonWithdrawProfileOffSentRequest);

        swapperID = swapDetails.getToID();
        swapperName = swapDetails.getToName();
        swapperEmail = swapDetails.getToEmail();
        swapperPhone = swapDetails.getToPhone();
        swapperCompanyBranch = swapDetails.getToCompanyBranch();
        swapperAccount = swapDetails.getToAccount();
        swapperImageUrl = swapDetails.getToImageUrl();
        swapperOffDay = swapDetails.getToOffDay();
        swapOffDate = swapDetails.getToOffDate();
        swapperPreferredOff = swapDetails.getToPreferredOff();

        fromImageUrl = swapDetails.getFromImageUrl();
        fromName = swapDetails.getToName();
        fromPhone = swapDetails.getFromPhone();
        fromEmail = swapDetails.getFromEmail();
        fromCompanyBranch = swapDetails.getFromCompanyBranch();
        fromAccount = swapDetails.getFromAccount();
        fromOffDate = swapDetails.getFromOffDate();
        fromOffDay = swapDetails.getFromOffDay();
        fromPreferredOff = swapDetails.getFromPreferredOff();

        toID = swapperID;
        toImageUrl = swapperImageUrl;
        toName = swapperName;
        toPhone = swapperPhone;
        toEmail = swapperEmail;
        toCompanyBranch = swapperCompanyBranch;
        toAccount = swapperAccount;
        toOffDate = swapOffDate;
        toOffDay = swapperOffDay;
        toPreferredOff = swapperPreferredOff;
        fromID = currentUserId;

        //swapper's data
        NameProfileOffSentRequest.setText(swapperName);
        offDayProfileOffSentRequest.setText(swapperOffDay);
        OffDateProfileOffSentRequest.setText(swapOffDate);
        if (swapperImageUrl != null) {
            progressBarProfileOffSentRequestImg1.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityOffSentRequest.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileOffSentRequestImg1.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(profileOffSentRequestUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileOffSentRequestUserImg.setImageDrawable(photoUrl);
        }

        //current user's data
        UserNameProfileOffSentRequest.setText(swapDetails.getFromName());
        userOffDayProfileOffSentRequest.setText(swapDetails.getFromOffDay());
        userOffDateProfileOffSentRequest.setText(swapDetails.getFromOffDate());
        if (swapDetails.getToImageUrl() != null) {
            progressBarProfileOffSentRequestImg2.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityOffSentRequest.this)
                    .load(swapDetails.getFromImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileOffSentRequestImg2.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userProfileOffSentRequestUserImg);

        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            userProfileOffSentRequestUserImg.setImageDrawable(photoUrl);
        }

        buttonWithdrawProfileOffSentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonWithdrawProfileOffSentRequest.setVisibility(View.INVISIBLE);
                withdraw();
            }
        });

    }

    private void withdraw() {

        String child = fromID + fromOffDay + fromPreferredOff + toID + toOffDay + toPreferredOff;
        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request")
                .child(child);
        offSwapRequestsDb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityOffSentRequest.this, "Done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivityOffSentRequest.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                buttonWithdrawProfileOffSentRequest.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivityOffSentRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
