package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.ProfileActivityOffReceivedRequest;
import com.app.muhammadgamal.swapy.Adapters.OffReceivedSwapAdapter;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceivedOffSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View rootView;
    private SwipeRefreshLayout offReceivedSwipeRefresh;
    private ProgressBar progressBar_received_off;
    private TextView empty_view_received_off, empty_view2_received_off;
    private ListView offReceivedList;
    private String userId;
    private FirebaseAuth mAuth;
    private OffReceivedSwapAdapter offReceivedSwapAdapter;
    private ImageView offImgNoConnectionReceived;
    private SwapRequestOff swapRequestOff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_off_received_swap, container, false);

        progressBar_received_off = rootView.findViewById(R.id.progressBar_received_off);
        empty_view_received_off = rootView.findViewById(R.id.empty_view_received_off);
        empty_view2_received_off = rootView.findViewById(R.id.empty_view2_received_off);
        offImgNoConnectionReceived = rootView.findViewById(R.id.offImgNoConnectionReceived);
        progressBar_received_off.setVisibility(View.VISIBLE);
        empty_view2_received_off.setVisibility(View.GONE);
        offImgNoConnectionReceived.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        offReceivedSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.offReceivedSwipeRefresh);
        offReceivedSwipeRefresh.setOnRefreshListener(this);
        offReceivedSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())){

            offImgNoConnectionReceived.setVisibility(View.GONE);
            progressBar_received_off.setVisibility(View.GONE);
            empty_view_received_off.setVisibility(View.VISIBLE);
            empty_view_received_off.setText(R.string.no_received_swaps);
            empty_view2_received_off.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestOff = dataSnapshot.getValue(SwapRequestOff.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();

                        if (swapRequestOff.getToID().equals(userId) && swapRequestOff.getAccepted() == -1) {
                            offReceivedSwapAdapter.add(swapRequestOff);
                        }

                        progressBar_received_off.setVisibility(View.GONE);
                        empty_view_received_off.setVisibility(View.GONE);
                        empty_view2_received_off.setVisibility(View.GONE);
                        if (offReceivedSwapAdapter.isEmpty()) {
                            progressBar_received_off.setVisibility(View.GONE);
                            empty_view_received_off.setVisibility(View.VISIBLE);
                            empty_view_received_off.setText(R.string.no_received_swaps);
                            empty_view2_received_off.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_received_off.setVisibility(View.GONE);
                        empty_view_received_off.setVisibility(View.VISIBLE);
                        empty_view_received_off.setText(R.string.no_received_swaps);
                        empty_view2_received_off.setVisibility(View.VISIBLE);
                    }
                    progressBar_received_off.setVisibility(View.GONE);
                    empty_view_received_off.setVisibility(View.GONE);
                    empty_view2_received_off.setVisibility(View.GONE);
                    if (offReceivedSwapAdapter.isEmpty()) {
                        progressBar_received_off.setVisibility(View.GONE);
                        empty_view_received_off.setVisibility(View.VISIBLE);
                        empty_view_received_off.setText(R.string.no_received_swaps);
                        empty_view2_received_off.setVisibility(View.VISIBLE);
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

            final List<SwapRequestOff> swapBodyList = new ArrayList<>();
            Collections.reverse(swapBodyList);
            offReceivedSwapAdapter = new OffReceivedSwapAdapter(getContext(), R.layout.off_sent_list_item, swapBodyList);
            offReceivedList = rootView.findViewById(R.id.offReceivedList);
            offReceivedList.setVisibility(View.VISIBLE);
            offReceivedList.setAdapter(offReceivedSwapAdapter);

            offReceivedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SwapRequestOff swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityOffReceivedRequest.class);
                    intent.putExtra("Received Off SRA swapper info", swapDetails);
                    startActivity(intent);
                }
            });

        } else {
            progressBar_received_off.setVisibility(View.GONE);
            if (offReceivedList != null) {
                offReceivedList.setVisibility(View.INVISIBLE);
            }
            offImgNoConnectionReceived.setVisibility(View.VISIBLE);
            empty_view_received_off.setVisibility(View.VISIBLE);
            empty_view_received_off.setText(R.string.no_internet_connection);
            empty_view2_received_off.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offReceivedSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
