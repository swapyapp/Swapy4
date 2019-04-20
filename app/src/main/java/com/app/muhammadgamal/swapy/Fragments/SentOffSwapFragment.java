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
import com.app.muhammadgamal.swapy.Activities.ProfileActivityOffSentRequest;
import com.app.muhammadgamal.swapy.Adapters.OffSentSwapAdapter;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SentOffSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout offSentSwipeRefresh;
    private ProgressBar progressBar_sent_off;
    private TextView empty_view_sent_off, empty_view2_sent_off;
    private ListView offSentList;
    private String userId;
    private FirebaseAuth mAuth;
    private ImageView offImgNoConnectionSent;
    private OffSentSwapAdapter offSentSwapAdapter;
    private SwapRequestOff swapRequestOff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_off_sent_swap, container, false);

        progressBar_sent_off = rootView.findViewById(R.id.progressBar_sent_off);
        empty_view_sent_off = rootView.findViewById(R.id.empty_view_sent_off);
        empty_view2_sent_off = rootView.findViewById(R.id.empty_view2_sent_off);
        offImgNoConnectionSent = rootView.findViewById(R.id.offImgNoConnectionSent);
        progressBar_sent_off.setVisibility(View.VISIBLE);
        empty_view2_sent_off.setVisibility(View.GONE);
        offImgNoConnectionSent.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        offSentSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.offSentSwipeRefresh);
        offSentSwipeRefresh.setOnRefreshListener(this);
        offSentSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            offImgNoConnectionSent.setVisibility(View.GONE);
            progressBar_sent_off.setVisibility(View.GONE);
            empty_view_sent_off.setVisibility(View.VISIBLE);
            empty_view_sent_off.setText(R.string.no_sent_swaps);
            empty_view2_sent_off.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestOff = dataSnapshot.getValue(SwapRequestOff.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();

                        if (swapRequestOff.getFromID().equals(userId) && swapRequestOff.getAccepted() == -1) {
                            offSentSwapAdapter.add(swapRequestOff);
                        }

                        progressBar_sent_off.setVisibility(View.GONE);
                        empty_view_sent_off.setVisibility(View.GONE);
                        empty_view2_sent_off.setVisibility(View.GONE);
                        if (offSentSwapAdapter.isEmpty()) {
                            progressBar_sent_off.setVisibility(View.GONE);
                            empty_view_sent_off.setVisibility(View.VISIBLE);
                            empty_view_sent_off.setText(R.string.no_sent_swaps);
                            empty_view2_sent_off.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_sent_off.setVisibility(View.GONE);
                        empty_view_sent_off.setVisibility(View.VISIBLE);
                        empty_view_sent_off.setText(R.string.no_sent_swaps);
                        empty_view2_sent_off.setVisibility(View.VISIBLE);
                    }
                    progressBar_sent_off.setVisibility(View.GONE);
                    empty_view_sent_off.setVisibility(View.GONE);
                    empty_view2_sent_off.setVisibility(View.GONE);
                    if (offSentSwapAdapter.isEmpty()) {
                        progressBar_sent_off.setVisibility(View.GONE);
                        empty_view_sent_off.setVisibility(View.VISIBLE);
                        empty_view_sent_off.setText(R.string.no_sent_swaps);
                        empty_view2_sent_off.setVisibility(View.VISIBLE);
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
            offSentSwapAdapter = new OffSentSwapAdapter(getContext(), R.layout.off_sent_list_item, swapBodyList);
            offSentList = rootView.findViewById(R.id.offSentList);
            offSentList.setVisibility(View.VISIBLE);
            offSentList.setNestedScrollingEnabled(true);
            offSentList.setAdapter(offSentSwapAdapter);

            offSentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SwapRequestOff swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityOffSentRequest.class);
                    intent.putExtra("Sent Off SRA swapper info", swapDetails);
                    startActivity(intent);
                }
            });

        } else {

            progressBar_sent_off.setVisibility(View.GONE);
//            if (offSentList != null) {
//                offSentList.setVisibility(View.INVISIBLE);
//            }
            offImgNoConnectionSent.setVisibility(View.VISIBLE);
            empty_view_sent_off.setVisibility(View.VISIBLE);
            empty_view_sent_off.setText(R.string.no_internet_connection);
            empty_view2_sent_off.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offSentSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
