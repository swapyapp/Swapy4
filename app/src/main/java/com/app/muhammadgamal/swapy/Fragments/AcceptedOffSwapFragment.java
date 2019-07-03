package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.app.muhammadgamal.swapy.Activities.ProfileActivityOffAcceptedRequest;
import com.app.muhammadgamal.swapy.Activities.ProfileActivityOffReceivedRequest;
import com.app.muhammadgamal.swapy.Adapters.OffAcceptedSwapAdapter;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AcceptedOffSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout offAcceptedSwipeRefresh;
    private ProgressBar progressBar_accepted_off;
    private TextView empty_view_accepted_off, empty_view2_accepted_off;
    private ListView offAcceptedList;
    private String userId;
    private FirebaseAuth mAuth;
    private ImageView offImgNoConnectionAccepted;
    private OffAcceptedSwapAdapter offAcceptedSwapAdapter;
    private SwapRequestOff swapRequestOff;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.nav_bar_items, menu);
        MenuItem item = menu.findItem(R.id.search_icon);
        item.setIcon(null);
        item.setTitle("");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_off_accepted_swap, container, false);

        progressBar_accepted_off = rootView.findViewById(R.id.progressBar_accepted_off);
        empty_view_accepted_off = rootView.findViewById(R.id.empty_view_accepted_off);
        empty_view2_accepted_off = rootView.findViewById(R.id.empty_view2_accepted_off);
        offImgNoConnectionAccepted = rootView.findViewById(R.id.offImgNoConnectionAccepted);
        progressBar_accepted_off.setVisibility(View.VISIBLE);
        empty_view2_accepted_off.setVisibility(View.GONE);
        offImgNoConnectionAccepted.setVisibility(View.GONE);

        shimmerFrameLayout = rootView.findViewById(R.id.shimmer_view_container_accepted_off);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        //handle the SwipeRefreshLayout
        offAcceptedSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.offAcceptedSwipeRefresh);
        offAcceptedSwipeRefresh.setOnRefreshListener(this);
        offAcceptedSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchData();

        return rootView;
    }

    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            offImgNoConnectionAccepted.setVisibility(View.GONE);
            progressBar_accepted_off.setVisibility(View.GONE);
            empty_view_accepted_off.setVisibility(View.VISIBLE);
            empty_view_accepted_off.setText(R.string.no_accepted_swaps);
            empty_view2_accepted_off.setVisibility(View.VISIBLE);
            DatabaseReference swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests").child("Off Request");
            swapRequestsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    swapRequestOff = dataSnapshot.getValue(SwapRequestOff.class);
                    if (dataSnapshot.exists()) {

                        mAuth = FirebaseAuth.getInstance();
                        userId = mAuth.getCurrentUser().getUid();
                        if (swapRequestOff.getToID().equals(userId) || swapRequestOff.getFromID().equals(userId)) {
                            if (swapRequestOff.getAccepted() == 1) {
                                offAcceptedSwapAdapter.add(swapRequestOff);
                            }
                        }

                        progressBar_accepted_off.setVisibility(View.GONE);
                        empty_view_accepted_off.setVisibility(View.GONE);
                        empty_view2_accepted_off.setVisibility(View.GONE);
                        if (offAcceptedSwapAdapter.isEmpty()) {
                            progressBar_accepted_off.setVisibility(View.GONE);
                            empty_view_accepted_off.setVisibility(View.VISIBLE);
                            empty_view_accepted_off.setText(R.string.no_accepted_swaps);
                            empty_view2_accepted_off.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar_accepted_off.setVisibility(View.GONE);
                        empty_view_accepted_off.setVisibility(View.VISIBLE);
                        empty_view_accepted_off.setText(R.string.no_accepted_swaps);
                        empty_view2_accepted_off.setVisibility(View.VISIBLE);
                    }
                    progressBar_accepted_off.setVisibility(View.GONE);
                    empty_view_accepted_off.setVisibility(View.GONE);
                    empty_view2_accepted_off.setVisibility(View.GONE);
                    if (offAcceptedSwapAdapter.isEmpty()) {
                        progressBar_accepted_off.setVisibility(View.GONE);
                        empty_view_accepted_off.setVisibility(View.VISIBLE);
                        empty_view_accepted_off.setText(R.string.no_accepted_swaps);
                        empty_view2_accepted_off.setVisibility(View.VISIBLE);
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
            offAcceptedSwapAdapter = new OffAcceptedSwapAdapter(getContext(), R.layout.off_accepted_list_item, swapBodyList);
            offAcceptedList = rootView.findViewById(R.id.offAcceptedList);
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
            offAcceptedList.setVisibility(View.VISIBLE);
            offAcceptedList.setNestedScrollingEnabled(true);
            offAcceptedList.setAdapter(offAcceptedSwapAdapter);
            offAcceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SwapRequestOff swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityOffAcceptedRequest.class);
                    intent.putExtra("Accepted Off SRA swapper info", swapDetails);
                    startActivity(intent);
                }
            });

        } else {
            progressBar_accepted_off.setVisibility(View.GONE);
            if (offAcceptedList != null) {
                offAcceptedList.setVisibility(View.INVISIBLE);
            }
            offImgNoConnectionAccepted.setVisibility(View.VISIBLE);
            empty_view_accepted_off.setVisibility(View.VISIBLE);
            empty_view_accepted_off.setText(R.string.no_internet_connection);
            empty_view2_accepted_off.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offAcceptedSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
