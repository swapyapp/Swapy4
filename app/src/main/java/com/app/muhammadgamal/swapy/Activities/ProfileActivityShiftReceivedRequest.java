package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityShiftReceivedRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private String swapperID, currentUserId, swapperPreferredShift, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb, shiftSwapsDatabaseReference, shiftSwapsDatabaseReference2;
    private RelativeLayout rejectShift, acceptShift, shiftReceivedButtons;
    private int swapAccepted;
    private ProgressBar progressBarProfileShiftRequestImg1, progressBarProfileShiftRequestImg2, progressBar_acceptProfileShiftRequest, progressBar_rejectProfileShiftRequest;
    private CircleImageView profileShiftRequestUserImg, userProfileShiftRequestUserImg;
    private TextView NameProfileShiftRequest, ShiftTimeProfileShiftRequest, shiftDayProfileShiftRequest, shiftDateProfileShiftRequest;
    private TextView UserNameProfileShiftRequest, userShiftTimeProfileShiftRequest, userShiftDayProfileShiftRequest, userShiftDateProfileShiftRequest;
    private TextView textDisplayContactInfoProfileShiftRequest, userEmailProfileShiftRequest, userPhoneProfileShiftRequest;
    private TextView you_accepted_requestShiftProfile;
    private String toID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredShift;
    private LinearLayout emailShiftProfileProfileShiftRequest, phoneShiftProfileShiftRequest, userContactInfoProfileShiftReceivedRequest;
    private Button buttonAcceptProfileShiftRequest, buttonRejectProfileShiftRequest;
    private SwapRequestShift swapDetails;
    private final static String LOG_TAG = ProfileActivityShiftReceivedRequest.class.getSimpleName();
    private String requestMessage;
    private DatabaseReference notificationDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_received_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        notificationDB = FirebaseDatabase.getInstance().getReference().child("Notifications").child("Accepted Swaps");

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Received Shift SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileShiftRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileShiftReceivedRequestImg1);
        progressBarProfileShiftRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileShiftReceivedRequestImg2);
        progressBar_acceptProfileShiftRequest = (ProgressBar) findViewById(R.id.progressBar_acceptProfileShiftReceivedRequest);
        progressBar_rejectProfileShiftRequest = (ProgressBar) findViewById(R.id.progressBar_rejectProfileShiftReceivedRequest);

        profileShiftRequestUserImg = (CircleImageView) findViewById(R.id.profileShiftReceivedRequestUserImg);
        userProfileShiftRequestUserImg = (CircleImageView) findViewById(R.id.userProfileShiftReceivedRequestUserImg);

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

        rejectShift = (RelativeLayout) findViewById(R.id.rejectShift);
        acceptShift = (RelativeLayout) findViewById(R.id.acceptShift);
        shiftReceivedButtons = (RelativeLayout) findViewById(R.id.shiftReceivedButtons);

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

        fromImageUrl = swapDetails.getToImageUrl();
        fromName = swapDetails.getToName();
        fromPhone = swapDetails.getToPhone();
        fromEmail = swapDetails.getToEmail();
        fromCompanyBranch = swapDetails.getToCompanyBranch();
        fromAccount = swapDetails.getToAccount();
        fromShiftDate = swapDetails.getToShiftDate();
        fromShiftDay = swapDetails.getToShiftDay();
        fromShiftTime = swapDetails.getToShiftTime();
        fromPreferredShift = swapDetails.getToPreferredShift();

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
                rejectShift.setVisibility(View.INVISIBLE);
                accept();
            }
        });

        buttonRejectProfileShiftRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonRejectProfileShiftRequest.setVisibility(View.INVISIBLE);
                progressBar_rejectProfileShiftRequest.setVisibility(View.VISIBLE);
                acceptShift.setVisibility(View.INVISIBLE);
                reject();
            }
        });

    }

    private void accept() {

        String child = toID + toShiftDay + toShiftTime + toPreferredShift + fromID + fromShiftDay + fromShiftTime + fromPreferredShift;
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                .child(child);
        shiftSwapRequestsDb.child("accepted").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityShiftReceivedRequest.this, "Successfully accepted", Toast.LENGTH_SHORT).show();
                shiftSwapsDatabaseReference = firebaseDatabase.getReference().child("swaps").child("shift_swaps").child(toID + toShiftDay + toShiftTime + toPreferredShift);
                shiftSwapsDatabaseReference.removeValue();
                shiftSwapsDatabaseReference2 = firebaseDatabase.getReference().child("swaps").child("shift_swaps").child(fromID + fromShiftDay + fromShiftTime + fromPreferredShift);
                shiftSwapsDatabaseReference2.removeValue();
                shiftReceivedButtons.setVisibility(View.INVISIBLE);
                you_accepted_requestShiftProfile.setVisibility(View.VISIBLE);
                textDisplayContactInfoProfileShiftRequest.setVisibility(View.GONE);
                userContactInfoProfileShiftReceivedRequest.setVisibility(View.VISIBLE);

                //set the request message
                requestMessage = toName + "" + " Accepted to swap with your shift";

                Map<String, Object> notificationMessage = new HashMap<>();
                notificationMessage.put("message", requestMessage);
                notificationMessage.put("from", toID);

                notificationDB.child(fromID).push()
                        .setValue(notificationMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivityShiftReceivedRequest.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        Log.e(LOG_TAG, "Failed to insert row for " + currentUserId);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                buttonAcceptProfileShiftRequest.setVisibility(View.VISIBLE);
                rejectShift.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivityShiftReceivedRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void reject() {

        String child = toID + toShiftDay + toShiftTime + toPreferredShift + fromID + fromShiftDay + fromShiftTime + fromPreferredShift;
        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                .child(child);
        shiftSwapRequestsDb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityShiftReceivedRequest.this, "Rejected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivityShiftReceivedRequest.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivityShiftReceivedRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
