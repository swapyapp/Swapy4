package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class ProfileActivityOffReceivedRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperPreferredOff, swapOffDate, swapperOffDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private DatabaseReference databaseReference;
    private SwapRequestOff swapDetails;
    private DatabaseReference offSwapRequestsDb;
    private int swapAccepted;
    private CircleImageView profileOffRequestUserImg, userProfileOffRequestUserImg;
    private TextView NameProfileOffRequest, offDayProfileShiftRequest, OffDateProfileShiftRequest;
    private TextView UserNameProfileOffRequest, userOffDayProfileShiftRequest, userOffDateProfileShiftRequest;
    private TextView textDisplayContactInfoProfileOffRequest, userEmailProfileOffRequest, userPhoneProfileOffRequest;
    private TextView you_accepted_requestOffProfile;
    private String toID, toName, toOffDate, toOffDay, toPhone, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredOff;
    private String fromID, fromName, fromOffDate, fromOffDay, fromPhone, fromOffTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredOff;
    private Button buttonAcceptProfileOffRequest, buttonRejectProfileOffRequest;
    private RelativeLayout rejectOff, acceptOff, OffReceivedButtons;
    private LinearLayout emailShiftProfileProfileOffRequest, phoneShiftProfileOffRequest, userContactInfoProfileOffRequest;
    private ProgressBar progressBarProfileOffRequestImg1, progressBarProfileOffRequestImg2, progressBar_acceptProfileOffRequest, progressBar_rejectProfileOffRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off_received_request);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        Intent intent = getIntent();
        swapDetails = intent.getParcelableExtra("Received Off SRA swapper info");

        swapAccepted = swapDetails.getAccepted();

        progressBarProfileOffRequestImg1 = (ProgressBar) findViewById(R.id.progressBarProfileOffRequestImg1);
        progressBarProfileOffRequestImg2 = (ProgressBar) findViewById(R.id.progressBarProfileOffRequestImg2);
        progressBar_acceptProfileOffRequest = (ProgressBar) findViewById(R.id.progressBar_acceptProfileOffRequest);
        progressBar_rejectProfileOffRequest = (ProgressBar) findViewById(R.id.progressBar_rejectProfileOffRequest);

        profileOffRequestUserImg = (CircleImageView) findViewById(R.id.profileOffRequestUserImg);
        userProfileOffRequestUserImg = (CircleImageView) findViewById(R.id.userProfileOffRequestUserImg);

        NameProfileOffRequest = (TextView) findViewById(R.id.NameProfileOffRequest);
        offDayProfileShiftRequest = (TextView) findViewById(R.id.offDayProfileShiftRequest);
        OffDateProfileShiftRequest = (TextView) findViewById(R.id.OffDateProfileShiftRequest);

        UserNameProfileOffRequest = (TextView) findViewById(R.id.UserNameProfileOffRequest);
        userOffDayProfileShiftRequest = (TextView) findViewById(R.id.userOffDayProfileShiftRequest);
        userOffDateProfileShiftRequest = (TextView) findViewById(R.id.userOffDateProfileShiftRequest);

        textDisplayContactInfoProfileOffRequest = (TextView) findViewById(R.id.textDisplayContactInfoProfileOffRequest);
        userEmailProfileOffRequest = (TextView) findViewById(R.id.userEmailProfileOffRequest);
        userPhoneProfileOffRequest = (TextView) findViewById(R.id.userPhoneProfileOffRequest);

        you_accepted_requestOffProfile = (TextView) findViewById(R.id.you_accepted_requestOffProfile);

        emailShiftProfileProfileOffRequest = (LinearLayout) findViewById(R.id.emailShiftProfileProfileOffRequest);
        phoneShiftProfileOffRequest = (LinearLayout) findViewById(R.id.phoneShiftProfileOffRequest);
        userContactInfoProfileOffRequest = (LinearLayout) findViewById(R.id.userContactInfoProfileOffRequest);

        rejectOff = (RelativeLayout) findViewById(R.id.rejectOff);
        acceptOff = (RelativeLayout) findViewById(R.id.acceptOff);
        OffReceivedButtons = (RelativeLayout) findViewById(R.id.OffReceivedButtons);

        buttonAcceptProfileOffRequest = (Button) findViewById(R.id.buttonAcceptProfileOffRequest);
        buttonRejectProfileOffRequest = (Button) findViewById(R.id.buttonRejectProfileOffRequest);

        swapperID = swapDetails.getFromID();
        swapperName = swapDetails.getFromName();
        swapperEmail = swapDetails.getFromEmail();
        swapperPhone = swapDetails.getFromPhone();
        swapperCompanyBranch = swapDetails.getFromCompanyBranch();
        swapperAccount = swapDetails.getFromAccount();
        swapperImageUrl = swapDetails.getFromImageUrl();
        swapperOffDay = swapDetails.getFromOffDay();
        swapOffDate = swapDetails.getFromOffDate();
        swapperPreferredOff = swapDetails.getFromPreferredOff();

        fromImageUrl = swapDetails.getToImageUrl();
        fromName = swapDetails.getToName();
        fromPhone = swapDetails.getToPhone();
        fromEmail = swapDetails.getToEmail();
        fromCompanyBranch = swapDetails.getToCompanyBranch();
        fromAccount = swapDetails.getToAccount();
        fromOffDate = swapDetails.getToOffDate();
        fromOffDay = swapDetails.getToOffDay();
        fromPreferredOff = swapDetails.getToPreferredOff();

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
        NameProfileOffRequest.setText(swapperName);
        offDayProfileShiftRequest.setText(swapperOffDay);
        OffDateProfileShiftRequest.setText(swapOffDate);
        userEmailProfileOffRequest.setText(swapperEmail);
        userPhoneProfileOffRequest.setText(swapperPhone);
        if (swapperImageUrl != null) {
            progressBarProfileOffRequestImg1.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityOffReceivedRequest.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileOffRequestImg1.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(profileOffRequestUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileOffRequestUserImg.setImageDrawable(photoUrl);
        }

        //current user's data
        UserNameProfileOffRequest.setText(swapDetails.getToName());
        userOffDayProfileShiftRequest.setText(swapDetails.getToOffDay());
        userOffDateProfileShiftRequest.setText(swapDetails.getToOffDate());
        if (swapDetails.getToImageUrl() != null) {
            progressBarProfileOffRequestImg2.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityOffReceivedRequest.this)
                    .load(swapDetails.getToImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileOffRequestImg2.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userProfileOffRequestUserImg);

        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            userProfileOffRequestUserImg.setImageDrawable(photoUrl);
        }

        phoneShiftProfileOffRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String message = "tel:" + swapperPhone;
                intent.setData(Uri.parse(message));
                startActivity(intent);
            }
        });

        emailShiftProfileProfileOffRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String message = "mailto:" + swapperEmail;
                emailIntent.setData(Uri.parse(message));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        buttonAcceptProfileOffRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAcceptProfileOffRequest.setVisibility(View.INVISIBLE);
                progressBar_acceptProfileOffRequest.setVisibility(View.VISIBLE);
                rejectOff.setVisibility(View.INVISIBLE);
                accept();
            }
        });

        buttonRejectProfileOffRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonRejectProfileOffRequest.setVisibility(View.INVISIBLE);
                progressBar_rejectProfileOffRequest.setVisibility(View.VISIBLE);
                acceptOff.setVisibility(View.INVISIBLE);
                reject();
            }
        });

    }

    private void accept() {

        String child = toID + toOffDay + toPreferredOff + fromID + fromOffDay + fromPreferredOff;
        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request")
                .child(child);
        offSwapRequestsDb.child("accepted").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityOffReceivedRequest.this, "Successfully accepted", Toast.LENGTH_SHORT).show();
                OffReceivedButtons.setVisibility(View.INVISIBLE);
                you_accepted_requestOffProfile.setVisibility(View.VISIBLE);
                textDisplayContactInfoProfileOffRequest.setVisibility(View.GONE);
                userContactInfoProfileOffRequest.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                buttonAcceptProfileOffRequest.setVisibility(View.VISIBLE);
                rejectOff.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivityOffReceivedRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void reject() {

        String child = toID + toOffDay + toPreferredOff + fromID + fromOffDay + fromPreferredOff;
        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request")
                .child(child);
        offSwapRequestsDb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivityOffReceivedRequest.this, "Rejected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivityOffReceivedRequest.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivityOffReceivedRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
