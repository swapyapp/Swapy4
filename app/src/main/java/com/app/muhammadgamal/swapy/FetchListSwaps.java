package com.app.muhammadgamal.swapy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Activities.ProfileActivityShift;
import com.app.muhammadgamal.swapy.Adapters.ShiftProfileAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchListSwaps extends Activity {

    private ImageView imgCloseFetchListSwaps;
    private ListView listView;
    private ProgressBar progressBar_FetchListSwaps;
    private DatabaseReference shiftSwapRequestsDb;
    private SwapRequestShift swapRequestShift;
    private String requestMessage;
    private String userName;
    private DatabaseReference notificationDB;
    private String toID, toLoginID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromLoginID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredShift;
    private String swapperID, currentUserId, swapperLoginID, currentUserLoginID, swapperPreferredShift, swapperTeamLeader, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount, swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;
    private FirebaseAuth mAuth;
    private ShiftProfileAdapter shiftProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_list_swaps);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (hight * .8));

        Intent intent = getIntent();
        swapperID = intent.getExtras().getString("swapperID");
        swapperName = intent.getExtras().getString("swapperName");
        swapperEmail = intent.getExtras().getString("swapperEmail");
        swapperPhone = intent.getExtras().getString("swapperPhone");
        swapperCompanyBranch = intent.getExtras().getString("swapperCompanyBranch");
        swapperAccount = intent.getExtras().getString("swapperAccount");
        swapperImageUrl = intent.getExtras().getString("swapperImageUrl");
        swapperShiftDay = intent.getExtras().getString("swapperShiftDay");
        swapShiftDate = intent.getExtras().getString("swapShiftDate");
        swapperShiftTime = intent.getExtras().getString("swapperShiftTime");
        swapperTeamLeader = intent.getExtras().getString("swapperTeamLeader");
        swapperPreferredShift = intent.getExtras().getString("swapperPreferredShift");
        toID = intent.getExtras().getString("toID");
        toImageUrl = intent.getExtras().getString("toImageUrl");
        toName = intent.getExtras().getString("toName");
        toPhone = intent.getExtras().getString("toPhone");
        toEmail = intent.getExtras().getString("toEmail");
        toCompanyBranch = intent.getExtras().getString("toCompanyBranch");
        toAccount = intent.getExtras().getString("toAccount");
        toShiftDate = intent.getExtras().getString("toShiftDate");
        toShiftDay = intent.getExtras().getString("toShiftDay");
        toShiftTime = intent.getExtras().getString("toShiftTime");
        toPreferredShift = intent.getExtras().getString("toPreferredShift");
        fromID = intent.getExtras().getString("fromID");

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


        notificationDB = FirebaseDatabase.getInstance().getReference().child("Notifications");

        imgCloseFetchListSwaps = findViewById(R.id.imgCloseFetchListSwaps);
        imgCloseFetchListSwaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar_FetchListSwaps = findViewById(R.id.progressBar_FetchListSwaps);

        listView = findViewById(R.id.listFetchListSwaps);

        DatabaseReference shiftSwapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");

        final List<SwapDetails> swapBodyList = new ArrayList<>();
        Collections.reverse(swapBodyList);
        shiftProfileAdapter = new ShiftProfileAdapter(FetchListSwaps.this, R.layout.shift_profile_list_item, swapBodyList);
        listView.setAdapter(shiftProfileAdapter);

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar_FetchListSwaps.setVisibility(View.VISIBLE);
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
                                        .setValue(notificationMessage);
                                Toast.makeText(FetchListSwaps.this, "Notification sent", Toast.LENGTH_LONG).show();
                                progressBar_FetchListSwaps.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FetchListSwaps.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar_FetchListSwaps.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


    }
}
