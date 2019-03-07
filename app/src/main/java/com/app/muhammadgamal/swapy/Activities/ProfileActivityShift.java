package com.app.muhammadgamal.swapy.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Adapters.ShiftProfileAdapter;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.app.muhammadgamal.swapy.SwapData.User;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityShift extends AppCompatActivity {

    private final static String LOG_TAG = ProfileActivityShift.class.getSimpleName();

    private CircleImageView profileUserImg;
    private ImageView imgCloseShiftProfileDialog, imgCloseShiftProfileChooseDialog;
    private ListView listView;
    private ShiftProfileAdapter shiftProfileAdapter;
    private Dialog shiftProfileDialog, chooseShiftProfileDialog;
    private TextView userProfileName, companyBranch, account, currentShift, preferredShift, userEmail, userPhone, textSentOrAcceptedRequest, textWaitingForAcceptance, textDisplayContactInfo, textAcceptedRequest, you_accepted_request, user_sent_you_request;
    private Button buttonSwapRequest, buttonCancelShiftProfileDialog, buttonCreateShiftProfileDialog;
    private ProgressBar progressBar, progressBarProfileActivityImage, progressBar_ShiftProfileChooseDialog;
    private FirebaseAuth mAuth;
    private String requestMessage;
    private TextView swapDone;
    private FirebaseFirestore mFireStore;
    //The Database that will contain the map of the notifications for each user with his ID
    private DatabaseReference notificationDB;
    private DatabaseReference databaseReference;
    private DatabaseReference shiftSwapRequestsDb;
    private SwapRequestShift swapRequestShift;
    private String toID, toLoginID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromLoginID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredShift;
    private int accepted, approved; //true = 1, false = 0
    private String swapperID, currentUserId, swapperLoginID, currentUserLoginID, swapperPreferredShift, swapperTeamLeader, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private String userName;
    private LinearLayout userContactInfo, phoneShiftProfile, emailShiftProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shift);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mFireStore = FirebaseFirestore.getInstance();
        notificationDB = FirebaseDatabase.getInstance().getReference().child("Notifications");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        Intent intent = getIntent();
        SwapDetails swapDetails = intent.getParcelableExtra("swapper info");
        swapperID = swapDetails.getSwapperID();
        swapperLoginID = swapDetails.getSwapperLoginID();
        swapperName = swapDetails.getSwapperName();
        swapperEmail = swapDetails.getSwapperEmail();
        swapperPhone = swapDetails.getSwapperPhone();
        swapperCompanyBranch = swapDetails.getSwapperCompanyBranch();
        swapperAccount = swapDetails.getSwapperAccount();
        swapperImageUrl = swapDetails.getSwapperImageUrl();
        swapperShiftDay = swapDetails.getSwapperShiftDay();
        swapShiftDate = swapDetails.getSwapShiftDate();
        swapperShiftTime = swapDetails.getSwapperShiftTime();
        swapperTeamLeader = swapDetails.getSwapperTeamLeader();
        swapperPreferredShift = swapDetails.getSwapperPreferredShift();

        toID = swapperID;
        toLoginID = swapperLoginID;
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

        //set the request message
        requestMessage = swapperName + "" + (R.string.notification_message);

        progressBarProfileActivityImage = (ProgressBar) findViewById(R.id.progressBarProfileActivityImage);
        profileUserImg = (CircleImageView) findViewById(R.id.profileUserImg);
        if (swapperImageUrl != null) {
            progressBarProfileActivityImage.setVisibility(View.VISIBLE);
            Glide.with(ProfileActivityShift.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileActivityImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(profileUserImg);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileUserImg.setImageDrawable(photoUrl);
        }


        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userProfileName = (TextView) findViewById(R.id.userProfileName);
        userProfileName.setText(swapperName);
        companyBranch = (TextView) findViewById(R.id.profileCompanyBranch);
        companyBranch.setText(swapperCompanyBranch);
        account = (TextView) findViewById(R.id.account);
        account.setText(swapperAccount);
        currentShift = (TextView) findViewById(R.id.currentShift);
        currentShift.setText(swapperShiftTime);
        preferredShift = (TextView) findViewById(R.id.preferredShift);
        preferredShift.setText(swapperPreferredShift);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userEmail.setText(swapperEmail);
        userPhone = (TextView) findViewById(R.id.userPhone);
        userPhone.setText(swapperPhone);
        textSentOrAcceptedRequest = (TextView) findViewById(R.id.textSentOrAcceptedRequest);
        textWaitingForAcceptance = (TextView) findViewById(R.id.textWaitingForAcceptance);
        textDisplayContactInfo = (TextView) findViewById(R.id.textDisplayContactInfo);
        textAcceptedRequest = (TextView) findViewById(R.id.textAcceptedRequest);
        you_accepted_request = (TextView) findViewById(R.id.you_accepted_request);
        user_sent_you_request = (TextView) findViewById(R.id.user_sent_you_request);

        buttonSwapRequest = (Button) findViewById(R.id.buttonSwapRequest);
        buttonSwapRequest.bringToFront();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_profile);

        userContactInfo = (LinearLayout) findViewById(R.id.userContactInfo);
        phoneShiftProfile = (LinearLayout) findViewById(R.id.phoneShiftProfile);
        emailShiftProfile = (LinearLayout) findViewById(R.id.emailShiftProfile);

        shiftProfileDialog = new Dialog(ProfileActivityShift.this);
        shiftProfileDialog.setContentView(R.layout.shift_profile_dialog);
        shiftProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgCloseShiftProfileDialog = shiftProfileDialog.findViewById(R.id.imgCloseShiftProfileDialog);
        imgCloseShiftProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftProfileDialog.dismiss();
            }
        });

        chooseShiftProfileDialog = new Dialog(ProfileActivityShift.this);
        chooseShiftProfileDialog.setContentView(R.layout.shift_profile_choose_dialog);
        chooseShiftProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseShiftProfileDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                shiftProfileDialog.dismiss();
            }
        });

        imgCloseShiftProfileChooseDialog = chooseShiftProfileDialog.findViewById(R.id.imgCloseShiftProfileChooseDialog);
        imgCloseShiftProfileChooseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseShiftProfileDialog.dismiss();
                shiftProfileDialog.dismiss();
            }
        });
        buttonCancelShiftProfileDialog = (Button) shiftProfileDialog.findViewById(R.id.buttonCancelShiftProfileDialog);
        buttonCancelShiftProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftProfileDialog.dismiss();
            }
        });
        buttonCreateShiftProfileDialog = (Button) shiftProfileDialog.findViewById(R.id.buttonCreateShiftProfileDialog);
        buttonCreateShiftProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityShift.this, SwapCreationActivity.class);
                startActivity(intent);
            }
        });

        progressBar_ShiftProfileChooseDialog = (ProgressBar) chooseShiftProfileDialog.findViewById(R.id.progressBar_ShiftProfileChooseDialog);
        //if the user opens his swap the swap request button view will be gone
        if (swapperID.equals(currentUserId)) {
            buttonSwapRequest.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            textDisplayContactInfo.setVisibility(View.GONE);
            userContactInfo.setVisibility(View.VISIBLE);
            textAcceptedRequest.setVisibility(View.GONE);
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName = user.getmUsername();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        buttonSwapRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonSwapRequest.setVisibility(View.INVISIBLE);
//                progressBar.setVisibility(View.VISIBLE);
//                //set the request message
//                //requestMessage = swapperName + "" +(R.string.notification_message);
//                requestMessage = userName + "" + " wants to swap his shift with your shift";
//
//                Map <String, Object> notificationMessage = new HashMap<>();
//                notificationMessage.put("message", requestMessage);
//                notificationMessage.put("from", currentUserId);
//
//                mFireStore.collection("Users/"+swapperID+ "/Notifications").add(notificationMessage)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(ProfileActivityShift.this, "Notification sent", Toast.LENGTH_LONG).show();
//                            progressBar.setVisibility(View.INVISIBLE);
//                           swapDone.setVisibility(View.VISIBLE);
//                }
//                });
//            }
//        });


        buttonSwapRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSwapRequest.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                DatabaseReference shiftSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");
                shiftSwapDb.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                        if (dataSnapshot.exists()) {
                            if (swapDetails.getSwapperID().equals(fromID)) {
                                chooseShiftProfileDialog.show();
                            } else {
                                shiftProfileDialog.show();
                                buttonSwapRequest.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
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
        });

        phoneShiftProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String message = "tel:" + swapperPhone;
                intent.setData(Uri.parse(message));
                startActivity(intent);
            }
        });

        emailShiftProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String message = "mailto:" + swapperEmail;
                emailIntent.setData(Uri.parse(message));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        showBtnSwapRequest();
    }

    private void showBtnSwapRequest() {

        DatabaseReference shiftSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");
        shiftSwapDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                if (dataSnapshot.exists()) {
                    if (swapDetails.getSwapperID().equals(fromID)) {
                        fromLoginID = swapDetails.getSwapperLoginID();
                        fromImageUrl = swapDetails.getSwapperImageUrl();
                        fromName = swapDetails.getSwapperName();
                        fromPhone = swapDetails.getSwapperPhone();
                        fromEmail = swapDetails.getSwapperEmail();
                        fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
                        fromAccount = swapDetails.getSwapperAccount();
                        fromShiftDate = swapDetails.getSwapShiftDate();
                        fromShiftDay = swapDetails.getSwapperShiftDay();
                        fromShiftTime = swapDetails.getSwapperShiftTime();
                        fromPreferredShift = swapDetails.getSwapperPreferredShift();

                        String child = fromID + fromShiftDay + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftTime + toPreferredShift;
                        //last child (fromID + fromShiftDay + fromShiftDate + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftDate + toShiftTime + toPreferredShift)
                        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                                .child(child);
                        shiftSwapRequestsDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                SwapRequestShift swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);

                                if (dataSnapshot.exists()) {

                                    if (swapRequestShift.getAccepted() == 1) {

                                        buttonSwapRequest.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        textDisplayContactInfo.setVisibility(View.GONE);
                                        userContactInfo.setVisibility(View.VISIBLE);
                                        textAcceptedRequest.setVisibility(View.VISIBLE);

                                    } else {

                                        buttonSwapRequest.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        textWaitingForAcceptance.setVisibility(View.VISIBLE);
                                        textDisplayContactInfo.setVisibility(View.VISIBLE);
                                        userContactInfo.setVisibility(View.GONE);
                                        textAcceptedRequest.setVisibility(View.GONE);

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String child2 = toID + toShiftDay + toShiftTime + toPreferredShift + fromID + fromShiftDay + fromShiftTime + fromPreferredShift;
                        //last child (fromID + fromShiftDay + fromShiftDate + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftDate + toShiftTime + toPreferredShift)
                        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                                .child(child2);
                        shiftSwapRequestsDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                SwapRequestShift swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);

                                if (dataSnapshot.exists()) {

                                    if (swapRequestShift.getAccepted() == 1) {

                                        buttonSwapRequest.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        textDisplayContactInfo.setVisibility(View.GONE);
                                        userContactInfo.setVisibility(View.VISIBLE);
                                        you_accepted_request.setVisibility(View.VISIBLE);

                                    } else {

                                        buttonSwapRequest.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        user_sent_you_request.setVisibility(View.VISIBLE);
                                        textDisplayContactInfo.setVisibility(View.VISIBLE);
                                        userContactInfo.setVisibility(View.GONE);
                                        textAcceptedRequest.setVisibility(View.GONE);

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

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

//    private void swapShiftRequest() {
//
//        DatabaseReference shiftSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");
//        shiftSwapDb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
//                if (dataSnapshot.exists()) {
//                    if (swapDetails.getSwapperID().equals(fromID)) {
//                        fromLoginID = swapDetails.getSwapperLoginID();
//                        fromImageUrl = swapDetails.getSwapperImageUrl();
//                        fromName = swapDetails.getSwapperName();
//                        fromPhone = swapDetails.getSwapperPhone();
//                        fromEmail = swapDetails.getSwapperEmail();
//                        fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
//                        fromAccount = swapDetails.getSwapperAccount();
//                        fromShiftDate = swapDetails.getSwapShiftDate();
//                        fromShiftDay = swapDetails.getSwapperShiftDay();
//                        fromShiftTime = swapDetails.getSwapperShiftTime();
//                        fromPreferredShift = swapDetails.getSwapperPreferredShift();
//                        String child = fromID + fromShiftDay + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftTime + toPreferredShift;
//                        shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
//                                .child(child);
//                        swapRequestShift = new SwapRequestShift(toID,
//                                toLoginID,
//                                toImageUrl,
//                                toName,
//                                toPhone,
//                                toEmail,
//                                toCompanyBranch,
//                                toAccount,
//                                toShiftDate,
//                                toShiftDay,
//                                toShiftTime,
//                                toPreferredShift,
//                                fromID,
//                                fromLoginID,
//                                fromImageUrl,
//                                fromName,
//                                fromPhone,
//                                fromEmail,
//                                fromCompanyBranch,
//                                fromAccount,
//                                fromShiftDate,
//                                fromShiftDay,
//                                fromShiftTime,
//                                fromPreferredShift,
//                                -1,
//                                -1);
//                        shiftSwapRequestsDb.setValue(swapRequestShift).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(ProfileActivityShift.this, "Notification sent", Toast.LENGTH_LONG).show();
//                                progressBar.setVisibility(View.INVISIBLE);
//                                textSentOrAcceptedRequest.setVisibility(View.VISIBLE);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ProfileActivityShift.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                                progressBar.setVisibility(View.INVISIBLE);
//                                buttonSwapRequest.setVisibility(View.VISIBLE);
//                            }
//                        });
//
//                    }
////                    else {
////                        Toast.makeText(ProfileActivityShift.this, " You have to create a swap to be able to send a request", Toast.LENGTH_LONG).show();
////                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void fetchChooseList() {

        DatabaseReference shiftSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");
        shiftSwapDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                    if (swapDetails.getSwapperID().equals(fromID)) {
                        shiftProfileAdapter.add(swapDetails);
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

        final List<SwapDetails> swapBodyList = new ArrayList<>();
        Collections.reverse(swapBodyList);
        shiftProfileAdapter = new ShiftProfileAdapter(ProfileActivityShift.this, R.layout.shift_profile_list_item, swapBodyList);
        listView = chooseShiftProfileDialog.findViewById(R.id.listShiftProfileChooseDialog);
        listView.setAdapter(shiftProfileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar_ShiftProfileChooseDialog.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                SwapDetails swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                fromLoginID = swapDetails.getSwapperLoginID();
                fromImageUrl = swapDetails.getSwapperImageUrl();
                fromName = swapDetails.getSwapperName();
                fromPhone = swapDetails.getSwapperPhone();
                fromEmail = swapDetails.getSwapperEmail();
                fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
                fromAccount = swapDetails.getSwapperAccount();
                fromShiftDate = swapDetails.getSwapShiftDate();
                fromShiftDay = swapDetails.getSwapperShiftDay();
                fromShiftTime = swapDetails.getSwapperShiftTime();
                fromPreferredShift = swapDetails.getSwapperPreferredShift();
                String child = fromID + fromShiftDay + fromShiftTime + fromPreferredShift + toID + toShiftDay + toShiftTime + toPreferredShift;
                shiftSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request")
                        .child(child);
                swapRequestShift = new SwapRequestShift(toID,
                        toLoginID,
                        toImageUrl,
                        toName,
                        toPhone,
                        toEmail,
                        toCompanyBranch,
                        toAccount,
                        toShiftDate,
                        toShiftDay,
                        toShiftTime,
                        toPreferredShift,
                        fromID,
                        fromLoginID,
                        fromImageUrl,
                        fromName,
                        fromPhone,
                        fromEmail,
                        fromCompanyBranch,
                        fromAccount,
                        fromShiftDate,
                        fromShiftDay,
                        fromShiftTime,
                        fromPreferredShift,
                        -1,
                        -1);
                shiftSwapRequestsDb.setValue(swapRequestShift)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //set the request message
                                requestMessage = userName + "" + " wants to swap with your shift";

                                Map<String, Object> notificationMessage = new HashMap<>();
                                notificationMessage.put("message", requestMessage);
                                notificationMessage.put("from", currentUserId);

                                notificationDB.child(swapperID).push()
                                        .setValue(notificationMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            chooseShiftProfileDialog.show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            fetchChooseList();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileActivityShift.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                        Log.e(LOG_TAG, "Failed to insert row for " + currentUserId);
                                    }
                                });
                                Toast.makeText(ProfileActivityShift.this, "Notification sent", Toast.LENGTH_LONG).show();
                                progressBar_ShiftProfileChooseDialog.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                chooseShiftProfileDialog.dismiss();
                                shiftProfileDialog.dismiss();
                                progressBar.setVisibility(View.INVISIBLE);
                                textSentOrAcceptedRequest.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivityShift.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar_ShiftProfileChooseDialog.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        chooseShiftProfileDialog.dismiss();
                        shiftProfileDialog.dismiss();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonSwapRequest.setVisibility(View.VISIBLE);
                    }
                });
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
