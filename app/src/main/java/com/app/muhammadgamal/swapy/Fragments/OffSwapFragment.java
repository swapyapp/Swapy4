package com.app.muhammadgamal.swapy.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.SwapOffCreationActivity;
import com.app.muhammadgamal.swapy.Adapters.SwapOffAdapter;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.PreferredShiftSpinnerListener;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffSwapFragment extends Fragment {
    private View rootView;
    // Store instance variables
    private String title;
    private int page;

//    // newInstance constructor for creating fragment with arguments
//    public static OffSwapFragment newInstance(int page, String title) {
//        OffSwapFragment offSwapFragment = new OffSwapFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        offSwapFragment.setArguments(args);
//        return offSwapFragment;
//    }

//    // Store instance variables based on arguments passed
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
//    }

    @Nullable
    private static int PREFERRED_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    private Dialog filterDialog;
    private ImageView imgCloseFilterDialog;
    // List view that represent teh swap data
    private ListView swapList;
    private TextView empty_view_off, empty_view2_off, filterPreferredTimePMText, filterPreferredTimeAMText;
    private SwipeRefreshLayout OffSwipeRefresh;
    private FloatingActionButton fab_add_off_swap_shift;
    private NetworkInfo networkInfo;
    private ConnectivityManager cm;
    private DatabaseReference mSwapDataBaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private Button homeSwapButton, buttonApplyFilter;
    private ListView listView;
    private Spinner homeFilterSpinner;
    private String userId, preferredShift, preferredAMorPM = null, currentUserAccount, currentUserCompanyBranch;
    private RelativeLayout filterPreferredTimeAM, filterPreferredTimePM;
    private SwapOffAdapter swapOffAdapter;
    private ProgressBar progressBar_home_off;
    private FirebaseAuth mAuth;
    private User user;
    private RelativeLayout imgFilter;
    private ImageView imgOffNoConnectionHome;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_off_swap, container, false);
        getActivity().setTitle("Home");
        getActivity().setTitle("Off");
        //Add to Activity
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps").child("off_swaps");

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();

        fab_add_off_swap_shift = rootView.findViewById(R.id.fab_add_off_swap_shift);
        fab_add_off_swap_shift.bringToFront();

        progressBar_home_off = rootView.findViewById(R.id.progressBar_home_off);
        empty_view_off = rootView.findViewById(R.id.empty_view_off);
        empty_view2_off = rootView.findViewById(R.id.empty_view2_off);
        imgOffNoConnectionHome = rootView.findViewById(R.id.imgOffNoConnectionHome);
        progressBar_home_off.setVisibility(View.VISIBLE);
        empty_view2_off.setVisibility(View.GONE);
        fab_add_off_swap_shift.setVisibility(View.GONE);
        imgOffNoConnectionHome.setVisibility(View.GONE);

        fab_add_off_swap_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SwapOffCreationActivity.class);
                startActivity(intent);
            }
        });
        fetchData();
        return rootView;
    }

    //show swaps in home fragment
    @SuppressLint("RestrictedApi")
    private void fetchData() {

        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {

            imgOffNoConnectionHome.setVisibility(View.GONE);
            DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    currentUserAccount = user.getmAccount();
                    currentUserCompanyBranch = user.getmBranch();

                    ChildEventListener mChildEventListener = new ChildEventListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
                            if (swapDetails.getSwapperAccount().equals(currentUserAccount) && swapDetails.getSwapperCompanyBranch().equals(currentUserCompanyBranch)) {
                                if (preferredAMorPM == null) {
                                    swapOffAdapter.add(swapDetails);
                                }
                            }
                            progressBar_home_off.setVisibility(View.GONE);
                            fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                            empty_view_off.setVisibility(View.GONE);
                            empty_view2_off.setVisibility(View.GONE);
                            if (swapOffAdapter.isEmpty()) {
                                empty_view_off.setVisibility(View.VISIBLE);
                                empty_view_off.setText(R.string.no_swaps_found);
                                fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                                empty_view2_off.setVisibility(View.VISIBLE);
                                String time = "any time";
//                        if (homeFilterSpinner.getSelectedItem().toString() != null) {
//                            time = homeFilterSpinner.getSelectedItem().toString() + preferredAMorPM;
//                        }
//                                selectedPreferredTime.setText(homeFilterSpinner.getSelectedItem().toString() + preferredAMorPM);
                            }
//                    }


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
                    };

                    if (swapOffAdapter.isEmpty()) {
                        empty_view_off.setVisibility(View.VISIBLE);
                        empty_view_off.setText(R.string.no_swaps_found);
                        empty_view2_off.setVisibility(View.VISIBLE);
                        progressBar_home_off.setVisibility(View.GONE);
                        fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                        String time = "any time";
//                        if (homeFilterSpinner.getSelectedItem().toString() != null) {
//                            time = homeFilterSpinner.getSelectedItem().toString() + preferredAMorPM;
//                        }
//                        selectedPreferredTime.setText(homeFilterSpinner.getSelectedItem().toString() + preferredAMorPM);
                    }

                    mSwapDataBaseReference.addChildEventListener(mChildEventListener);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final List<SwapOff> swapBodyList = new ArrayList<>();
            Collections.reverse(swapBodyList);
            swapOffAdapter = new SwapOffAdapter(getContext(), R.layout.swap_off_list_item, swapBodyList);
            listView = rootView.findViewById(R.id.homeList);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(swapOffAdapter);


//        if (homeSwapButton != null) {
//            homeSwapButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = (Integer) view.getTag();
//                    Intent intent = new Intent(rootView.getContext(), ProfileActivity.class);
//                    intent.putExtra("swapper info", swapBodyList.get(position));
//                    startActivity(intent);
//                }
//            });
//
//        }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                int position = (Integer) view.getTag();

                    //image and card views transitions
                    String imageTransitionName = getString(R.string.image_transition_name);
                    String listItemTransitionName = getString(R.string.list_item_transition_name);
                    View swapper_image = view.findViewById(R.id.swapper_image);
                    View cardView = view.findViewById(R.id.listItemCardView);
                    Pair<View, String> p1 = Pair.create(swapper_image, imageTransitionName);
                    Pair<View, String> p2 = Pair.create(cardView, listItemTransitionName);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), swapper_image, ViewCompat.getTransitionName(swapper_image));


//                    SwapDetails swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
//                    Intent intent = new Intent(getContext(), ProfileActivity.class);
//                    intent.putExtra("swapper info", swapDetails);
//                    startActivity(intent, options.toBundle());
                }
            });
        } else {
            progressBar_home_off.setVisibility(View.GONE);
            if (listView != null) {
                listView.setVisibility(View.INVISIBLE);
            }
            imgOffNoConnectionHome.setVisibility(View.VISIBLE);
            empty_view_off.setVisibility(View.VISIBLE);
            empty_view_off.setText(R.string.no_internet_connection);
            empty_view2_off.setVisibility(View.VISIBLE);
            fab_add_off_swap_shift.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        filterDialog = new Dialog(getContext());
        imgFilter = getView().findViewById(R.id.imgFilter);
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
    }

    public void showFilterDialog() {

        Resources res = getResources();
        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        filterDialog.setContentView(R.layout.shift_dialog_filter);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgCloseFilterDialog = filterDialog.findViewById(R.id.imgCloseFilterDialog);
        imgCloseFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.dismiss();
            }
        });

        //time spinner
        homeFilterSpinner = filterDialog.findViewById(R.id.preferredTimeSpinner);
        preferredShiftHomeSpinner();

        filterPreferredTimeAMText = filterDialog.findViewById(R.id.filterPreferredTimeAMText);
        filterPreferredTimePMText = filterDialog.findViewById(R.id.filterPreferredTimePMText);

        filterPreferredTimeAM = filterDialog.findViewById(R.id.filterPreferredTimeAM);
        filterPreferredTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 0;
                filterPreferredTimeAM.setBackground(SelectedBackground);
                filterPreferredTimePM.setBackground(notSelectedBackground);
                filterPreferredTimeAMText.setTextColor(getResources().getColor(R.color.white));
                filterPreferredTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        filterPreferredTimePM = filterDialog.findViewById(R.id.filterPreferredTimePM);
        filterPreferredTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 1;
                filterPreferredTimePM.setBackground(SelectedBackground);
                filterPreferredTimeAM.setBackground(notSelectedBackground);
                filterPreferredTimePMText.setTextColor(getResources().getColor(R.color.white));
                filterPreferredTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });


        buttonApplyFilter = filterDialog.findViewById(R.id.buttonApplyFilter);
        buttonApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilter();
                PREFERRED_TIME_SELECTED = 0;
            }
        });

        filterDialog.show();
    }

    private void applyFilter() {

        if (PREFERRED_TIME_SELECTED == 0) {
            preferredAMorPM = " AM";
        }
        if (PREFERRED_TIME_SELECTED == 1) {
            preferredAMorPM = " PM";
        }
        if (homeFilterSpinner.getSelectedItem().toString().equals("any time")) {
            preferredAMorPM = null;
        } else {
            preferredShift = homeFilterSpinner.getSelectedItem().toString() + preferredAMorPM;
        }
        fetchData();
        filterDialog.dismiss();

    }

    private void preferredShiftHomeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.Home_Preferred_Shift, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homeFilterSpinner.setAdapter(adapter);
        homeFilterSpinner.setOnItemSelectedListener(new PreferredShiftSpinnerListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((NavDrawerActivity)getActivity()).updateStatusBarColor("#007c91");

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}