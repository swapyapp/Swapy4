package com.app.muhammadgamal.swapy.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.app.muhammadgamal.swapy.Adapters.OffProfileAdapter;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOff extends AppCompatActivity {

    private CircleImageView offProfileUserImg;
    private ImageView imgCloseOffProfileDialog, imgCloseOffProfileChooseDialog;
    private ListView listView;
    private Dialog offProfileDialog, chooseOffProfileDialog;
    private OffProfileAdapter offProfileAdapter;
    private TextView userOffProfileName, offProfileCompanyBranch, offProfileAccount, offProfileCurrent, offProfilePreferred, offProfileTextDisplayContactInfo, userEmailOffProfile, userPhoneOffProfile,
            textSentOrAcceptedRequestOffProfile, textWaitingForAcceptanceOffProfile, textAcceptedRequestOffProfile, you_accepted_requestOffProfile, user_sent_you_request_off_profile;
    private Button buttonSwapRequestOffProfile, buttonCancelOffProfileDialog, buttonCreateOffProfileDialog;
    private ProgressBar progressBar_off_profile, progressBarOffProfileActivityImage, progressBar_offProfileChooseDialog;
    private FirebaseAuth mAuth;
    private String swapperID, currentUserId, swapperName, swapperEmail, swapperPhone, swapperCompanyBranch, swapperAccount,
            swapperImageUrl, offDay, swapOffDate, preferredOff;
    private String toID, toLoginID, toName, toOffDate, toOffDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredOff;
    private String fromID, fromLoginID, fromName, fromOffDate, fromOffDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredOff;
    private DatabaseReference databaseReference;
    private LinearLayout phoneOffProfile, emailOffProfile, userContactInfoOffProfile;
    private SwapRequestOff swapRequestOff;
    private DatabaseReference offSwapRequestsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off);

        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        progressBar_off_profile = (ProgressBar) findViewById(R.id.progressBar_off_profile);
        progressBarOffProfileActivityImage = (ProgressBar) findViewById(R.id.progressBarOffProfileActivityImage);

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

        toID = swapperID;
        toImageUrl = swapperImageUrl;
        toName = swapperName;
        toPhone = swapperPhone;
        toEmail = swapperEmail;
        toCompanyBranch = swapperCompanyBranch;
        toAccount = swapperAccount;
        toOffDate = swapOffDate;
        toOffDay = offDay;
        toPreferredOff = preferredOff;
        fromID = currentUserId;

        if (swapperImageUrl != null) {
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

        phoneOffProfile = (LinearLayout) findViewById(R.id.phoneOffProfile);
        emailOffProfile = (LinearLayout) findViewById(R.id.emailOffProfile);
        userContactInfoOffProfile = (LinearLayout) findViewById(R.id.userContactInfoOffProfile);

        offProfileDialog = new Dialog(ProfileActivityOff.this);
        offProfileDialog.setContentView(R.layout.off_profile_dialog);
        offProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgCloseOffProfileDialog = offProfileDialog.findViewById(R.id.imgCloseOffProfileDialog);
        imgCloseOffProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offProfileDialog.dismiss();
            }
        });

        chooseOffProfileDialog = new Dialog(ProfileActivityOff.this);
        chooseOffProfileDialog.setContentView(R.layout.off_profile_choose_dialog);
        chooseOffProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgCloseOffProfileChooseDialog = chooseOffProfileDialog.findViewById(R.id.imgCloseOffProfileChooseDialog);
        imgCloseOffProfileChooseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseOffProfileDialog.dismiss();
            }
        });

        progressBar_offProfileChooseDialog = (ProgressBar) chooseOffProfileDialog.findViewById(R.id.progressBar_offProfileChooseDialog);

        buttonCancelOffProfileDialog = (Button) offProfileDialog.findViewById(R.id.buttonCancelOffProfileDialog);
        buttonCancelOffProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offProfileDialog.dismiss();
            }
        });
        buttonCreateOffProfileDialog = (Button) offProfileDialog.findViewById(R.id.buttonCreateOffProfileDialog);
        buttonCreateOffProfileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityOff.this, SwapOffCreationActivity.class);
                startActivity(intent);
            }
        });

        phoneOffProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String message = "tel:" + swapperPhone;
                intent.setData(Uri.parse(message));
                startActivity(intent);
            }
        });

        emailOffProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String message = "mailto:" + swapperEmail;
                emailIntent.setData(Uri.parse(message));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        buttonSwapRequestOffProfile = (Button) findViewById(R.id.buttonSwapRequestOffProfile);
        showBtnSwapRequest();
        if (swapperID.equals(currentUserId)) {
            buttonSwapRequestOffProfile.setVisibility(View.GONE);
            progressBar_off_profile.setVisibility(View.GONE);
            offProfileTextDisplayContactInfo.setVisibility(View.GONE);
            userContactInfoOffProfile.setVisibility(View.VISIBLE);
            textAcceptedRequestOffProfile.setVisibility(View.GONE);
        }
        buttonSwapRequestOffProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSwapRequestOffProfile.setVisibility(View.INVISIBLE);
                progressBar_off_profile.setVisibility(View.VISIBLE);
                DatabaseReference offSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("off_swaps");
                offSwapDb.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
                        if (dataSnapshot.exists()) {
                            if (swapDetails.getSwapperID().equals(fromID)) {
                                chooseOffProfileDialog.show();
                                progressBar_off_profile.setVisibility(View.INVISIBLE);
                                fetchChooseList();
                            } else {
                                offProfileDialog.show();
                                buttonSwapRequestOffProfile.setVisibility(View.VISIBLE);
                                progressBar_off_profile.setVisibility(View.INVISIBLE);
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

    }

    private void showBtnSwapRequest() {

        DatabaseReference offSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("off_swaps");
        offSwapDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
                if (dataSnapshot.exists()) {
                    if (swapDetails.getSwapperID().equals(fromID)) {
                        fromImageUrl = swapDetails.getSwapperImageUrl();
                        fromName = swapDetails.getSwapperName();
                        fromPhone = swapDetails.getSwapperPhone();
                        fromEmail = swapDetails.getSwapperEmail();
                        fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
                        fromAccount = swapDetails.getSwapperAccount();
                        fromOffDate = swapDetails.getSwapOffDate();
                        fromOffDay = swapDetails.getOffDay();
                        fromPreferredOff = swapDetails.getPreferedOff();
                        String child = toID + toOffDay + toPreferredOff + fromID + fromOffDay + fromPreferredOff;
                        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request").child(child);
                        offSwapRequestsDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                SwapRequestOff swapRequestOff = dataSnapshot.getValue(SwapRequestOff.class);
                                if (dataSnapshot.exists()) {

                                    if (swapRequestOff.getAccepted() == 1) {

                                        buttonSwapRequestOffProfile.setVisibility(View.GONE);
                                        progressBar_off_profile.setVisibility(View.GONE);
                                        offProfileTextDisplayContactInfo.setVisibility(View.GONE);
                                        userContactInfoOffProfile.setVisibility(View.VISIBLE);
                                        you_accepted_requestOffProfile.setVisibility(View.VISIBLE);

                                    } else {

                                        buttonSwapRequestOffProfile.setVisibility(View.GONE);
                                        progressBar_off_profile.setVisibility(View.GONE);
                                        user_sent_you_request_off_profile.setVisibility(View.VISIBLE);
                                        offProfileTextDisplayContactInfo.setVisibility(View.VISIBLE);
                                        userContactInfoOffProfile.setVisibility(View.GONE);
                                        textAcceptedRequestOffProfile.setVisibility(View.GONE);

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String child2 = fromID + fromOffDay + fromPreferredOff + toID + toOffDay + toPreferredOff;
                        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request").child(child2);
                        offSwapRequestsDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                SwapRequestOff swapRequestOff = dataSnapshot.getValue(SwapRequestOff.class);
                                if (dataSnapshot.exists()) {

                                    if (swapRequestOff.getAccepted() == 1) {

                                        buttonSwapRequestOffProfile.setVisibility(View.GONE);
                                        progressBar_off_profile.setVisibility(View.GONE);
                                        offProfileTextDisplayContactInfo.setVisibility(View.GONE);
                                        userContactInfoOffProfile.setVisibility(View.VISIBLE);
                                        textAcceptedRequestOffProfile.setVisibility(View.VISIBLE);

                                    } else {

                                        buttonSwapRequestOffProfile.setVisibility(View.GONE);
                                        progressBar_off_profile.setVisibility(View.GONE);
                                        textWaitingForAcceptanceOffProfile.setVisibility(View.VISIBLE);
                                        offProfileTextDisplayContactInfo.setVisibility(View.VISIBLE);
                                        userContactInfoOffProfile.setVisibility(View.GONE);
                                        textAcceptedRequestOffProfile.setVisibility(View.GONE);

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

//    private void swapOffRequest() {
//
//        DatabaseReference offSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("off_swaps");
//        offSwapDb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
//                if (dataSnapshot.exists()) {
//                    if (swapDetails.getSwapperID().equals(fromID)) {
//                        fromImageUrl = swapDetails.getSwapperImageUrl();
//                        fromName = swapDetails.getSwapperName();
//                        fromPhone = swapDetails.getSwapperPhone();
//                        fromEmail = swapDetails.getSwapperEmail();
//                        fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
//                        fromAccount = swapDetails.getSwapperAccount();
//                        fromOffDate = swapDetails.getSwapOffDate();
//                        fromOffDay = swapDetails.getOffDay();
//                        fromPreferredOff = swapDetails.getPreferedOff();
//                        String child = fromID + fromOffDay + fromPreferredOff + toID + toOffDay + toPreferredOff;
//                        offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request").child(child);
//                        swapRequestOff = new SwapRequestOff(toID,
//                                toName,
//                                toOffDate,
//                                toOffDay,
//                                toPhone,
//                                toAccount,
//                                toCompanyBranch,
//                                toEmail,
//                                toImageUrl,
//                                toPreferredOff,
//                                fromID,
//                                fromName,
//                                fromOffDate,
//                                fromOffDay,
//                                fromPhone,
//                                fromAccount,
//                                fromCompanyBranch,
//                                fromEmail,
//                                fromImageUrl,
//                                fromPreferredOff,
//                                -1,
//                                -1);
//                        offSwapRequestsDb.setValue(swapRequestOff).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(ProfileActivityOff.this, "Notification sent", Toast.LENGTH_LONG).show();
//                                progressBar_off_profile.setVisibility(View.INVISIBLE);
//                                textSentOrAcceptedRequestOffProfile.setVisibility(View.VISIBLE);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ProfileActivityOff.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                                progressBar_off_profile.setVisibility(View.INVISIBLE);
//                                textSentOrAcceptedRequestOffProfile.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }
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

        DatabaseReference offSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("off_swaps");
        offSwapDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
                    if (swapDetails.getSwapperID().equals(fromID)) {
                        offProfileAdapter.add(swapDetails);
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

        final List<SwapOff> swapBodyList = new ArrayList<>();
        Collections.reverse(swapBodyList);
        offProfileAdapter = new OffProfileAdapter(ProfileActivityOff.this, R.layout.off_profile_list_item, swapBodyList);
        listView = chooseOffProfileDialog.findViewById(R.id.listOffProfileChooseDialog);
        listView.setAdapter(offProfileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar_offProfileChooseDialog.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                SwapOff swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                fromImageUrl = swapDetails.getSwapperImageUrl();
                fromName = swapDetails.getSwapperName();
                fromPhone = swapDetails.getSwapperPhone();
                fromEmail = swapDetails.getSwapperEmail();
                fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
                fromAccount = swapDetails.getSwapperAccount();
                fromOffDate = swapDetails.getSwapOffDate();
                fromOffDay = swapDetails.getOffDay();
                fromPreferredOff = swapDetails.getPreferedOff();
                String child = fromID + fromOffDay + fromPreferredOff + toID + toOffDay + toPreferredOff;
                offSwapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request").child(child);
                swapRequestOff = new SwapRequestOff(toID,
                        toName,
                        toOffDate,
                        toOffDay,
                        toPhone,
                        toAccount,
                        toCompanyBranch,
                        toEmail,
                        toImageUrl,
                        toPreferredOff,
                        fromID,
                        fromName,
                        fromOffDate,
                        fromOffDay,
                        fromPhone,
                        fromAccount,
                        fromCompanyBranch,
                        fromEmail,
                        fromImageUrl,
                        fromPreferredOff,
                        -1,
                        -1);
                offSwapRequestsDb.setValue(swapRequestOff).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivityOff.this, "Notification sent", Toast.LENGTH_LONG).show();
                        progressBar_offProfileChooseDialog.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        chooseOffProfileDialog.dismiss();
                        offProfileDialog.dismiss();
                        progressBar_off_profile.setVisibility(View.INVISIBLE);
                        textSentOrAcceptedRequestOffProfile.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivityOff.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar_offProfileChooseDialog.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        chooseOffProfileDialog.dismiss();
                        offProfileDialog.dismiss();
                        progressBar_off_profile.setVisibility(View.INVISIBLE);
                        textSentOrAcceptedRequestOffProfile.setVisibility(View.VISIBLE);
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
