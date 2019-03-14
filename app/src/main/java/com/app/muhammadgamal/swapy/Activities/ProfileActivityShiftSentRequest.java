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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Fragments.SentShiftSwapFragment;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
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

public class ProfileActivityShiftSentRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb;
    private int swapAccepted;
    private ProgressBar progressBarProfileShiftSentRequestImg1, progressBarProfileShiftSentRequestImg2, progressBar_WithdrawProfileShiftSentRequest;
    private CircleImageView profileShiftSentRequestUserImg, userProfileShiftSentRequestUserImg;
    private TextView NameProfileShiftSentRequest, ShiftTimeProfileShiftSentRequest, shiftDayProfileShiftSentRequest, shiftDateProfileShiftSentRequest;
    private TextView UserNameProfileShiftSentRequest, userShiftTimeProfileShiftSentRequest, userShiftDayProfileShiftSentRequest, userShiftDateProfileShiftSentRequest;
    private TextView textDisplayContactInfoProfileShiftSentRequest;
    private Button buttonWithdrawProfileShiftSentRequest;
    private SwapRequestShift swapDetails;
    private String toID, toLoginID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromLoginID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_sent_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        fromImageUrl = swapDetails.getFromImageUrl();
        fromName = swapDetails.getFromName();
        fromPhone = swapDetails.getFromPhone();
        fromEmail = swapDetails.getFromEmail();
        fromCompanyBranch = swapDetails.getFromCompanyBranch();
        fromAccount = swapDetails.getFromAccount();
        fromShiftDate = swapDetails.getFromShiftDate();
        fromShiftDay = swapDetails.getFromShiftDay();
        fromShiftTime = swapDetails.getFromShiftTime();
        fromPreferredShift = swapDetails.getFromPreferredShift();

        toID = swapperID;
        toImageUrl = swapperImageUrl;
        toName = swapperName;
        toPhone = swapperPhone;
        toEmail = swapperEmail;
        toCompanyBranch = swapperCompanyBranch;
        toAccount = swapperAccount;
        toShiftDate = swapShiftDate;
        toShiftDay = swapperShiftDay;
        toShiftTime = swapperShiftTime;
        toPreferredShift = swapperPreferredShift;
        fromID = currentUserId;

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

        buttonWithdrawProfileShiftSentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonWithdrawProfileShiftSentRequest.setVisibility(View.INVISIBLE);
                withdraw();
            }
        });

    }

    private void withdraw() {

        String child = fromID + fromShiftDay + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftTime + toPreferredShift;
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                .child(child);
        shiftSwapRequestsDb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityShiftSentRequest.this, "Done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivityShiftSentRequest.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                buttonWithdrawProfileShiftSentRequest.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivityShiftSentRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
