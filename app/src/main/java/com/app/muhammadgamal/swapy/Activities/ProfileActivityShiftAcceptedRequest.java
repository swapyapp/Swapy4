package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
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
    private ImageView img_back_profile_shift_Accepted_request;
    private TextView NameProfileShiftAcceptedRequest, ShiftTimeProfileShiftAcceptedRequest, shiftDayProfileShiftAcceptedRequest, shiftDateProfileShiftAcceptedRequest;
    private TextView UserNameProfileShiftAcceptedRequest, userShiftTimeProfileShiftAcceptedRequest, userShiftDayProfileShiftAcceptedRequest, userShiftDateProfileShiftAcceptedRequest;
    private TextView userEmailProfileShiftAcceptedRequest, userPhoneProfileShiftAcceptedRequest;
    private LinearLayout emailShiftProfileProfileShiftAcceptedRequest, phoneShiftProfileShiftAcceptedRequest;
    private SwapRequestShift swapDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift_accepted_request);

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
        img_back_profile_shift_Accepted_request = (ImageView) findViewById(R.id.img_back_profile_shift_Accepted_request);

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

    }
}
