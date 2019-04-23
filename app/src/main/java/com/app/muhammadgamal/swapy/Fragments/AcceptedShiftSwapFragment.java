package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.ProfileActivityShiftAcceptedRequest;
import com.app.muhammadgamal.swapy.Activities.ProfileActivityShiftReceivedRequest;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.Adapters.AcceptedSwapAdapter;
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

public class AcceptedShiftSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout acceptedSwipeRefresh;
    private String userId;
    private FirebaseAuth mAuth;
    private User user;
    private ProgressBar progressBar_accepted;
    private TextView empty_view_accepted, empty_view2_accepted;
    private ListView acceptedList;
    private SwapRequestShift swapRequestShift;
    private SwapDetails swapDetails;
    private AcceptedSwapAdapter acceptedSwapAdapter;
    private ReceivedSwapAdapter receivedSwapAdapter;
    private ImageView imgNoConnectionAccepted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.nav_bar_items, menu);
        MenuItem item = menu.findItem(R.id.search_icon);
        item.setIcon(null);
        item.setTitle("");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shift_accepted_swap, container, false);
        getActivity().setTitle("Accepted swaps");

        progressBar_accepted = rootView.findViewById(R.id.progressBar_accepted);
        empty_view_accepted = rootView.findViewById(R.id.empty_view_accepted);
        empty_view2_accepted = rootView.findViewById(R.id.empty_view2_accepted);
        imgNoConnectionAccepted = rootView.findViewById(R.id.imgNoConnectionAccepted);
        progressBar_accepted.setVisibility(View.VISIBLE);
        empty_view2_accepted.setVisibility(View.GONE);
        imgNoConnectionAccepted.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        acceptedSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.acceptedSwipeRefresh);
        acceptedSwipeRefresh.setOnRefreshListener(this);
        acceptedSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }


    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            imgNoConnectionAccepted.setVisibility(View.GONE);
            progressBar_accepted.setVisibility(View.GONE);
            empty_view_accepted.setVisibility(View.VISIBLE);
            empty_view_accepted.setText(R.string.no_accepted_swaps);
            empty_view2_accepted.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Shift Request");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestShift = dataSnapshot.getValue(SwapRequestShift.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();
                        if (swapRequestShift.getToID().equals(userId) || swapRequestShift.getFromID().equals(userId)) {
                            if (swapRequestShift.getAccepted() == 1) {
                                acceptedSwapAdapter.add(swapRequestShift);
                            }
                        }

                        progressBar_accepted.setVisibility(View.GONE);
                        empty_view_accepted.setVisibility(View.GONE);
                        empty_view2_accepted.setVisibility(View.GONE);
                        if (acceptedSwapAdapter.isEmpty()) {
                            progressBar_accepted.setVisibility(View.GONE);
                            empty_view_accepted.setVisibility(View.VISIBLE);
                            empty_view_accepted.setText(R.string.no_accepted_swaps);
                            empty_view2_accepted.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_accepted.setVisibility(View.GONE);
                        empty_view_accepted.setVisibility(View.VISIBLE);
                        empty_view_accepted.setText(R.string.no_accepted_swaps);
                        empty_view2_accepted.setVisibility(View.VISIBLE);
                    }
                    progressBar_accepted.setVisibility(View.GONE);
                    empty_view_accepted.setVisibility(View.GONE);
                    empty_view2_accepted.setVisibility(View.GONE);
                    if (acceptedSwapAdapter.isEmpty()) {
                        progressBar_accepted.setVisibility(View.GONE);
                        empty_view_accepted.setVisibility(View.VISIBLE);
                        empty_view_accepted.setText(R.string.no_accepted_swaps);
                        empty_view2_accepted.setVisibility(View.VISIBLE);
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
            acceptedSwapAdapter = new AcceptedSwapAdapter(getContext(), R.layout.accepted_list_item, swapBodyList);
            acceptedList = rootView.findViewById(R.id.acceptedList);
            acceptedList.setVisibility(View.VISIBLE);
            acceptedList.setNestedScrollingEnabled(true);
            acceptedList.setAdapter(acceptedSwapAdapter);

            acceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SwapRequestShift swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityShiftAcceptedRequest.class);
                    intent.putExtra("Accepted Shift SRA swapper info", swapDetails);
                    startActivity(intent);
                }
            });

        } else {
            progressBar_accepted.setVisibility(View.GONE);
            if (acceptedList != null) {
                acceptedList.setVisibility(View.INVISIBLE);
            }
            imgNoConnectionAccepted.setVisibility(View.VISIBLE);
            empty_view_accepted.setVisibility(View.VISIBLE);
            empty_view_accepted.setText(R.string.no_internet_connection);
            empty_view2_accepted.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                acceptedSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }

    @Override
    public void onStart() {
        super.onStart();
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
      //  ((NavDrawerActivity) getActivity()).updateStatusBarColor("#0081cb");

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }




}
