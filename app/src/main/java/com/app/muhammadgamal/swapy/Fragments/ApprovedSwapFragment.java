package com.app.muhammadgamal.swapy.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.Adapters.ApprovedSwapAdapter;
import com.app.muhammadgamal.swapy.Adapters.ReceivedSwapAdapter;
import com.app.muhammadgamal.swapy.Adapters.SentSwapAdapter;
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

public class ApprovedSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout approvedSwipeRefresh;
    private String userId;
    private FirebaseAuth mAuth;
    private User user;
    private ProgressBar progressBar_approved;
    private TextView empty_view_approved, empty_view2_approved;
    private ListView approvedList;
    private SwapRequestShift swapRequestShift;
    private SwapDetails swapDetails;
    private SentSwapAdapter sentSwapAdapter;
    private ReceivedSwapAdapter receivedSwapAdapter;
    private ApprovedSwapAdapter approvedSwapAdapter;
    private ImageView imgNoConnectionApproved;

    private ImageView navigationDrawerBtn;
    private DrawerLayout drawer;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(false);
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_approved_swap, container, false);

//        Toolbar toolbar = rootView.findViewById(R.id.acceptedSwaps_fragment_toolBar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar_approved = rootView.findViewById(R.id.progressBar_approved);
        empty_view_approved = rootView.findViewById(R.id.empty_view_approved);
        empty_view2_approved = rootView.findViewById(R.id.empty_view2_approved);
        imgNoConnectionApproved = rootView.findViewById(R.id.imgNoConnectionApproved);
        imgNoConnectionApproved.setVisibility(View.GONE);
        progressBar_approved.setVisibility(View.VISIBLE);
        empty_view2_approved.setVisibility(View.GONE);

//        navigationDrawerBtn = (ImageView)rootView.findViewById(R.id.toolbar_navigation_icon_accepted_swaps);
//        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//        navigationDrawerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.openDrawer(GravityCompat.START);
//            }
//        });


        //handle the SwipeRefreshLayout
        approvedSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.approvedSwipeRefresh);
        approvedSwipeRefresh.setOnRefreshListener(this);
        approvedSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {
            imgNoConnectionApproved.setVisibility(View.GONE);
            progressBar_approved.setVisibility(View.GONE);
            empty_view_approved.setVisibility(View.VISIBLE);
            empty_view_approved.setText(R.string.no_approved_swaps);
            empty_view2_approved.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();

                        if (swapRequestShift.getApproved() == 1) {
                            approvedSwapAdapter.add(swapRequestShift);
                        }

                        progressBar_approved.setVisibility(View.GONE);
                        empty_view_approved.setVisibility(View.GONE);
                        empty_view2_approved.setVisibility(View.GONE);

                        if (approvedSwapAdapter.isEmpty()) {
                            progressBar_approved.setVisibility(View.GONE);
                            empty_view_approved.setVisibility(View.VISIBLE);
                            empty_view_approved.setText(R.string.no_approved_swaps);
                            empty_view2_approved.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_approved.setVisibility(View.GONE);
                        empty_view_approved.setVisibility(View.VISIBLE);
                        empty_view_approved.setText(R.string.no_approved_swaps);
                        empty_view2_approved.setVisibility(View.VISIBLE);
                    }

                    progressBar_approved.setVisibility(View.GONE);
                    empty_view_approved.setVisibility(View.GONE);
                    empty_view2_approved.setVisibility(View.GONE);

                    if (approvedSwapAdapter.isEmpty()) {
                        progressBar_approved.setVisibility(View.GONE);
                        empty_view_approved.setVisibility(View.VISIBLE);
                        empty_view_approved.setText(R.string.no_approved_swaps);
                        empty_view2_approved.setVisibility(View.VISIBLE);
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
            approvedSwapAdapter = new ApprovedSwapAdapter(getContext(), R.layout.approved_list_item, swapBodyList);
            approvedList = rootView.findViewById(R.id.approvedList);
            approvedList.setVisibility(View.VISIBLE);
            approvedList.setAdapter(approvedSwapAdapter);

        } else {
            progressBar_approved.setVisibility(View.GONE);
            if (approvedList != null) {
                approvedList.setVisibility(View.INVISIBLE);
            }
            imgNoConnectionApproved.setVisibility(View.VISIBLE);
            empty_view_approved.setVisibility(View.VISIBLE);
            empty_view_approved.setText(R.string.no_internet_connection);
            empty_view2_approved.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                approvedSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }

    @Override
    public void onStart() {
        super.onStart();


    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}
