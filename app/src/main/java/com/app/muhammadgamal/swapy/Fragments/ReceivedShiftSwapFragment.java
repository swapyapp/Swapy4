package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.ProfileActivityShiftReceivedRequest;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.Adapters.ReceivedSwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceivedShiftSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout receivedSwipeRefresh;
    private String userId;
    private FirebaseAuth mAuth;
    private User user;
    private DatabaseReference swapDb;
    private DatabaseReference userDb;
    private ProgressBar progressBar_received;
    private TextView empty_view_received, empty_view2_received;
    private ListView receivedList;
    private ReceivedSwapAdapter ReceivedSwapAdapter;
    private SwapRequestShift swapRequestShift;
    private String toID, toLoginID, currentUserLoginID;
    private SwapDetails swapDetails;
    private ImageView imgNoConnectionReceived;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shift_received_swap, container, false);

        progressBar_received = rootView.findViewById(R.id.progressBar_received);
        empty_view_received = rootView.findViewById(R.id.empty_view_received);
        empty_view2_received = rootView.findViewById(R.id.empty_view2_received);
        imgNoConnectionReceived = rootView.findViewById(R.id.imgNoConnectionReceived);
        progressBar_received.setVisibility(View.VISIBLE);
        empty_view2_received.setVisibility(View.GONE);
        imgNoConnectionReceived.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        receivedSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.receivedSwipeRefresh);
        receivedSwipeRefresh.setOnRefreshListener(this);
        receivedSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            imgNoConnectionReceived.setVisibility(View.GONE);
            progressBar_received.setVisibility(View.GONE);
            empty_view_received.setVisibility(View.VISIBLE);
            empty_view_received.setText(R.string.no_received_swaps);
            empty_view2_received.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();

                        if (swapRequestShift.getToID().equals(userId) && swapRequestShift.getAccepted() == -1) {
                            ReceivedSwapAdapter.add(swapRequestShift);
                        }

                        progressBar_received.setVisibility(View.GONE);
                        empty_view_received.setVisibility(View.GONE);
                        empty_view2_received.setVisibility(View.GONE);
                        if (ReceivedSwapAdapter.isEmpty()) {
                            progressBar_received.setVisibility(View.GONE);
                            empty_view_received.setVisibility(View.VISIBLE);
                            empty_view_received.setText(R.string.no_received_swaps);
                            empty_view2_received.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_received.setVisibility(View.GONE);
                        empty_view_received.setVisibility(View.VISIBLE);
                        empty_view_received.setText(R.string.no_received_swaps);
                        empty_view2_received.setVisibility(View.VISIBLE);
                    }
                    progressBar_received.setVisibility(View.GONE);
                    empty_view_received.setVisibility(View.GONE);
                    empty_view2_received.setVisibility(View.GONE);
                    if (ReceivedSwapAdapter.isEmpty()) {
                        progressBar_received.setVisibility(View.GONE);
                        empty_view_received.setVisibility(View.VISIBLE);
                        empty_view_received.setText(R.string.no_received_swaps);
                        empty_view2_received.setVisibility(View.VISIBLE);
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

            final List<SwapRequestShift> swapBodyList = new ArrayList<>();
            Collections.reverse(swapBodyList);
            ReceivedSwapAdapter = new ReceivedSwapAdapter(getContext(), R.layout.sent_list_item, swapBodyList);
            receivedList = rootView.findViewById(R.id.receivedList);
            receivedList.setVisibility(View.VISIBLE);
            receivedList.setAdapter(ReceivedSwapAdapter);

            receivedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SwapRequestShift swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityShiftReceivedRequest.class);
                    intent.putExtra("Received Shift SRA swapper info", swapDetails);
                    startActivity(intent);
                }
            });

        } else {
            progressBar_received.setVisibility(View.GONE);
            if (receivedList != null) {
                receivedList.setVisibility(View.INVISIBLE);
            }
            imgNoConnectionReceived.setVisibility(View.VISIBLE);
            empty_view_received.setVisibility(View.VISIBLE);
            empty_view_received.setText(R.string.no_internet_connection);
            empty_view2_received.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                receivedSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((NavDrawerActivity) getActivity()).updateStatusBarColor("#0081cb");

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}
