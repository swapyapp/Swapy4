package com.app.muhammadgamal.swapy.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.ReceivedSwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SentSwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequest;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SentSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout sentSwipeRefresh;
    private ProgressBar progressBar_sent;
    private TextView empty_view_sent, empty_view2_sent;
    private ListView sentList;
    private SwapRequest swapRequest;
    private SwapDetails swapDetails;
    private String userId;
    private FirebaseAuth mAuth;
    private User user;
    private SentSwapAdapter sentSwapAdapter;
    private DatabaseReference swapDb;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sent_swap, container, false);
        getActivity().setTitle("Sent swap requests");

        progressBar_sent = rootView.findViewById(R.id.progressBar_sent);
        empty_view_sent = rootView.findViewById(R.id.empty_view_sent);
        empty_view2_sent = rootView.findViewById(R.id.empty_view2_sent);
        progressBar_sent.setVisibility(View.VISIBLE);
        empty_view2_sent.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        sentSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.sentSwipeRefresh);
        sentSwipeRefresh.setOnRefreshListener(this);
        sentSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequest = dataSnapshot.getValue(SwapRequest.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();

                        if (swapRequest.getFromID().equals(userId)) {
                            sentSwapAdapter.add(swapRequest);
                        }

                        progressBar_sent.setVisibility(View.GONE);
                        empty_view_sent.setVisibility(View.GONE);
                        empty_view2_sent.setVisibility(View.GONE);
                        if (sentSwapAdapter.isEmpty()) {
                            progressBar_sent.setVisibility(View.GONE);
                            empty_view_sent.setVisibility(View.VISIBLE);
                            empty_view_sent.setText(R.string.no_sent_swaps);
                            empty_view2_sent.setVisibility(View.VISIBLE);
                        }

                    }
                    progressBar_sent.setVisibility(View.GONE);
                    empty_view_sent.setVisibility(View.GONE);
                    empty_view2_sent.setVisibility(View.GONE);
                    if (sentSwapAdapter.isEmpty()) {
                        progressBar_sent.setVisibility(View.GONE);
                        empty_view_sent.setVisibility(View.VISIBLE);
                        empty_view_sent.setText(R.string.no_sent_swaps);
                        empty_view2_sent.setVisibility(View.VISIBLE);
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


            final List<SwapRequest> swapBodyList = new ArrayList<>();
            Collections.reverse(swapBodyList);
            sentSwapAdapter = new SentSwapAdapter(getContext(), R.layout.sent_list_item, swapBodyList);
            sentList = rootView.findViewById(R.id.sentList);
            sentList.setVisibility(View.VISIBLE);
            sentList.setAdapter(sentSwapAdapter);

        } else {

            progressBar_sent.setVisibility(View.GONE);
            if (sentList != null) {
                sentList.setVisibility(View.INVISIBLE);
            }
            empty_view_sent.setVisibility(View.VISIBLE);
            empty_view_sent.setText(R.string.no_internet_connection);
            empty_view2_sent.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sentSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
