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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.app.muhammadgamal.swapy.Activities.ProfileActivityOff;
import com.app.muhammadgamal.swapy.Activities.SwapOffCreationActivity;
import com.app.muhammadgamal.swapy.Adapters.SwapOffAdapter;
import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.Notifications.ReceivedSwapsActivity;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.PreferredOffDaySpinnerListener;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.facebook.shimmer.ShimmerFrameLayout;
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

public class OffSwapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
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
    private Dialog offFilterDialog;
    private ImageView offImgCloseFilterDialog;
    // List view that represent teh swap data
    private ListView swapList;
    private TextView empty_view_off, empty_view2_off, filterPreferredTimePMText, filterPreferredTimeAMText, selectedPreferredOff;
    private SwipeRefreshLayout OffSwipeRefresh;
    private FloatingActionButton fab_add_off_swap_shift;
    private NetworkInfo networkInfo;
    private ConnectivityManager cm;
    private DatabaseReference mSwapDataBaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private Button homeSwapButton, buttonApplyFilter;
    private ListView listView;
    private Spinner preferredOffDaySpinner, swappersPreferredOffDaySpinner;
    private String userId, preferredShift, preferredAMorPM = null, currentUserAccount, currentUserCompanyBranch, filterSelectedYourOfffDay = "any day", filterSelectedSwapperOffDay = "any day";
    private RelativeLayout filterPreferredTimeAM, filterPreferredTimePM;
    private SwapOffAdapter swapOffAdapter;
    private ProgressBar progressBar_home_off;
    private FirebaseAuth mAuth;
    private User user;
    private RelativeLayout imgOffFilter;
    private ImageView imgOffNoConnectionHome, navigDrawerBtn;
    private ShimmerFrameLayout shimmerFrameLayout;
    private DrawerLayout drawer;
    private SwipeRefreshLayout offSwipeRefresh;

    private FloatingActionButton fab_reset_filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_off_swap, container, false);

        //Add to Activity
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps").child("off_swaps");

        fab_reset_filter= rootView.findViewById(R.id.fab_add_swap_off_reset);
        fab_reset_filter.hide();

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();

//        navigDrawerBtn = (ImageView)rootView.findViewById(R.id.imgNavigDrawerOff);
//        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//        navigDrawerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.openDrawer(GravityCompat.START);
//            }
//        });

        fab_add_off_swap_shift = rootView.findViewById(R.id.fab_add_off_swap_shift);
        fab_add_off_swap_shift.bringToFront();

        //handle the SwipeRefreshLayout
        offSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.offSwipeRefresh);
        offSwipeRefresh.setOnRefreshListener(this);
        offSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        progressBar_home_off = rootView.findViewById(R.id.progressBar_home_off);
        shimmerFrameLayout = (ShimmerFrameLayout) rootView.findViewById(R.id.shimmer_view_container_off);
        empty_view_off = rootView.findViewById(R.id.empty_view_off);
        empty_view2_off = rootView.findViewById(R.id.empty_view2_off);
        imgOffNoConnectionHome = rootView.findViewById(R.id.imgOffNoConnectionHome);
       // selectedPreferredOff = rootView.findViewById(R.id.selectedPreferredOff);
//        selectedPreferredOff.setText(filterSelectedYourOfffDay);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
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
                                if (filterSelectedYourOfffDay.equals("any day") && filterSelectedSwapperOffDay.equals("any day")) {
                                    fab_reset_filter.hide();
                                    swapOffAdapter.add(swapDetails);
                                    swapOffAdapter.notifyDataSetChanged();
                                }
                                if (!filterSelectedYourOfffDay.equals("any day") && filterSelectedSwapperOffDay.equals("any day")) {
                                    fab_reset_filter.show();
                                    if (swapDetails.getOffDay().equals(filterSelectedYourOfffDay)) {
                                        swapOffAdapter.add(swapDetails);
                                        swapOffAdapter.notifyDataSetChanged();
                                    }
                                }
                                if (!filterSelectedSwapperOffDay.equals("any day") && filterSelectedYourOfffDay.equals("any day")) {
                                    fab_reset_filter.show();
                                    if (swapDetails.getPreferedOff().equals(filterSelectedSwapperOffDay)) {
                                        swapOffAdapter.add(swapDetails);
                                        swapOffAdapter.notifyDataSetChanged();
                                    }
                                }
                                if (!filterSelectedSwapperOffDay.equals("any day") && !filterSelectedYourOfffDay.equals("any day")) {
                                    fab_reset_filter.show();
                                    if (swapDetails.getPreferedOff().equals(filterSelectedSwapperOffDay) && swapDetails.getOffDay().equals(filterSelectedYourOfffDay)) {
                                        swapOffAdapter.add(swapDetails);
                                        swapOffAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            progressBar_home_off.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmer();
                            fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                            empty_view_off.setVisibility(View.GONE);
                            empty_view2_off.setVisibility(View.GONE);
                            if (swapOffAdapter.isEmpty()) {
                                empty_view_off.setVisibility(View.VISIBLE);
                                empty_view_off.setText(R.string.no_swaps_found);
                                fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                                empty_view2_off.setVisibility(View.VISIBLE);
                                String time = "any time";
//                        if (preferredOffDaySpinner.getSelectedItem().toString() != null) {
//                            time = preferredOffDaySpinner.getSelectedItem().toString() + preferredAMorPM;
//                        }
//                                selectedPreferredTime.setText(preferredOffDaySpinner.getSelectedItem().toString() + preferredAMorPM);
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
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        fab_add_off_swap_shift.setVisibility(View.VISIBLE);
                        String time = "any time";
//                        if (preferredOffDaySpinner.getSelectedItem().toString() != null) {
//                            time = preferredOffDaySpinner.getSelectedItem().toString() + preferredAMorPM;
//                        }
//                        selectedPreferredTime.setText(preferredOffDaySpinner.getSelectedItem().toString() + preferredAMorPM);
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
            listView = rootView.findViewById(R.id.offList);
            listView.setNestedScrollingEnabled(true);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(swapOffAdapter);
            swapOffAdapter.notifyDataSetChanged();


//        if (homeSwapButton != null) {
//            homeSwapButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = (Integer) view.getTag();
//                    Intent intent = new Intent(rootView.getContext(), ProfileActivityShift.class);
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
                    View swapper_image = view.findViewById(R.id.swapper_off_image);
                    Pair<View, String> p1 = Pair.create(swapper_image, imageTransitionName);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), swapper_image, ViewCompat.getTransitionName(swapper_image));


                    SwapOff swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivityOff.class);
                    intent.putExtra("off swapper info", swapDetails);
                    startActivity(intent, options.toBundle());
                }
            });
        } else {
            progressBar_home_off.setVisibility(View.GONE);
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
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
        offFilterDialog = new Dialog(getContext());
//        imgOffFilter = getView().findViewById(R.id.imgOffFilter);
//        imgOffFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showFilterDialog();
//            }
//        });
    }

    public void showFilterDialog() {

        Resources res = getResources();
        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        offFilterDialog.setContentView(R.layout.off_dialog_filter);
        offFilterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        offImgCloseFilterDialog = offFilterDialog.findViewById(R.id.offImgCloseFilterDialog);
        offImgCloseFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offFilterDialog.dismiss();
            }
        });

        //Day spinner
        preferredOffDaySpinner = offFilterDialog.findViewById(R.id.preferredOffDaySpinner);
        preferredOffHomeSpinner();

        //Day spinner
        swappersPreferredOffDaySpinner = offFilterDialog.findViewById(R.id.swappersPreferredOffDaySpinner);
        swapperPreferredOffHomeSpinner();

        buttonApplyFilter = offFilterDialog.findViewById(R.id.buttonApplyFilter);
        buttonApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilter();
            }
        });

        offFilterDialog.show();
    }

    private void applyFilter() {

        filterSelectedYourOfffDay = preferredOffDaySpinner.getSelectedItem().toString();
        filterSelectedSwapperOffDay = swappersPreferredOffDaySpinner.getSelectedItem().toString();
        fetchData();
        offFilterDialog.dismiss();

        fab_reset_filter.show();
        fab_reset_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSelectedYourOfffDay = "any day";
                filterSelectedSwapperOffDay = "any day";
                fetchData();
                fab_reset_filter.hide();
            }
        });

    }

    private void preferredOffHomeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.Home_Preferred_Off, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferredOffDaySpinner.setAdapter(adapter);
        preferredOffDaySpinner.setOnItemSelectedListener(new PreferredOffDaySpinnerListener());
    }

    private void swapperPreferredOffHomeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.Home_Preferred_Off, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        swappersPreferredOffDaySpinner.setAdapter(adapter);
        swappersPreferredOffDaySpinner.setOnItemSelectedListener(new PreferredOffDaySpinnerListener());
    }

    @Override
    public void onStart() {
        super.onStart();
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        //((NavDrawerActivity) getActivity()).updateStatusBarColor("#0081cb");

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_icon:
                showFilterDialog();
                return true;

            case R.id.notification_icon:
                Intent intent = new Intent(getContext(), ReceivedSwapsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}