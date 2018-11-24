package com.app.muhammadgamal.swapy.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
=======
>>>>>>> f430401c2f9e432e75a91652785211f2d8373acc
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.Adapters.SwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
=======

import com.app.muhammadgamal.swapy.R;
>>>>>>> f430401c2f9e432e75a91652785211f2d8373acc

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
<<<<<<< HEAD
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static int PREFERRED_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    private Dialog filterDialog;
    private ImageView imgCloseFilterDialog;
    // List view that represent teh swap data
    private ListView swapList;
    private TextView empty_view, empty_view2, filterPreferredTimePMText, filterPreferredTimeAMText, selectedPreferredTime;
    private SwipeRefreshLayout homeSwipeRefresh;
    private FloatingActionButton fab_add_swap;
    private NetworkInfo networkInfo;
    private ConnectivityManager cm;
    private DatabaseReference mSwapDataBaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private Button homeSwapButton, buttonApplyFilter;
    private View rootView;
    private ListView listView;
    private Spinner homeFilterSpinner;
    private String userId, preferredShift, preferredAMorPM = null, currentUserAccount, currentUserCompanyBranch;
    private RelativeLayout filterPreferredTimeAM, filterPreferredTimePM;
    private SwapAdapter swapAdapter;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private User user;
    private RelativeLayout imgFilter;
    private ImageView imgNoConnectionHome;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");
        //Add to Activity
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        homeSwapButton = rootView.findViewById(R.id.btnHomeSwapList);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps");

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();

        fab_add_swap = rootView.findViewById(R.id.fab_add_swap);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_home_fragment);


        progressBar = rootView.findViewById(R.id.progressBar_home);
        empty_view = rootView.findViewById(R.id.empty_view);
        empty_view2 = rootView.findViewById(R.id.empty_view2);
        selectedPreferredTime = rootView.findViewById(R.id.selectedPreferredTime);
        imgNoConnectionHome = rootView.findViewById(R.id.imgNoConnectionHome);
        progressBar.setVisibility(View.VISIBLE);
        empty_view2.setVisibility(View.GONE);
        fab_add_swap.setVisibility(View.GONE);
        imgNoConnectionHome.setVisibility(View.GONE);

        //handle the SwipeRefreshLayout
        homeSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.homeSwipeRefresh);
        homeSwipeRefresh.setOnRefreshListener(this);
        homeSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fab_add_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SwapCreationActivity.class);
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

            imgNoConnectionHome.setVisibility(View.GONE);
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
                            SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
//                    if (swapDetails.getSwapperAccount() != null && swapDetails.getSwapperCompanyBranch() != null) {
                            if (swapDetails.getSwapperAccount().equals(currentUserAccount)
                                    && swapDetails.getSwapperCompanyBranch().equals(currentUserCompanyBranch)) {
                                if (preferredAMorPM == null) {
//                                if (swapDetails.getSwapperAccount().equals(currentUserAccount) && swapDetails.getSwapperCompanyBranch().equals(currentUserCompanyBranch)) {
                                    swapAdapter.add(swapDetails);
                                    selectedPreferredTime.setText(R.string.any_time);
//                                }
                                } else if (preferredAMorPM.equals(" AM")) {
                                    if (swapDetails.getSwapperShiftTime().equals(homeFilterSpinner.getSelectedItem()
                                            .toString() + preferredAMorPM)) {
                                        swapAdapter.add(swapDetails);
                                        selectedPreferredTime.setText(homeFilterSpinner.getSelectedItem()
                                                .toString() + preferredAMorPM);
                                    }
                                } else if (preferredAMorPM.equals(" PM")) {
                                    if (swapDetails.getSwapperShiftTime().equals(homeFilterSpinner.getSelectedItem()
                                            .toString() + preferredAMorPM)) {
                                        swapAdapter.add(swapDetails);
                                        selectedPreferredTime.setText(homeFilterSpinner.getSelectedItem().toString()
                                                + preferredAMorPM);
                                    }
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            fab_add_swap.setVisibility(View.VISIBLE);
                            empty_view.setVisibility(View.GONE);
                            empty_view2.setVisibility(View.GONE);
                            if (swapAdapter.isEmpty()) {
                                empty_view.setVisibility(View.VISIBLE);
                                empty_view.setText(R.string.no_swaps_found);
                                fab_add_swap.setVisibility(View.VISIBLE);
                                empty_view2.setVisibility(View.VISIBLE);
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
=======
>>>>>>> f430401c2f9e432e75a91652785211f2d8373acc

        BottomNavigationView homeBottomNavigationView = rootView.findViewById(R.id.homeBottomNavigationView);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.home_fragment_container,
                            new ShiftSwapFragment())
                    .commit();
<<<<<<< HEAD
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

                    if (swapAdapter.isEmpty()) {
                        empty_view.setVisibility(View.VISIBLE);
                        empty_view.setText(R.string.no_swaps_found);
                        empty_view2.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        fab_add_swap.setVisibility(View.VISIBLE);
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

            final List<SwapDetails> swapBodyList = new ArrayList<>();
            Collections.reverse(swapBodyList);
            swapAdapter = new SwapAdapter(getContext(), R.layout.home_list_item, swapBodyList);
            listView = rootView.findViewById(R.id.homeList);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(swapAdapter);


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
                    View swapper_image = view.findViewById(R.id.swapper_off_image);
                    View cardView = view.findViewById(R.id.listItemCardView);
                    Pair<View, String> p1 = Pair.create(swapper_image, imageTransitionName);
                    Pair<View, String> p2 = Pair.create(cardView, listItemTransitionName);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), swapper_image, ViewCompat.getTransitionName(swapper_image));


                    SwapDetails swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("swapper info", swapDetails);
                    startActivity(intent, options.toBundle());
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            if (listView != null) {
                listView.setVisibility(View.INVISIBLE);
            }
            imgNoConnectionHome.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.VISIBLE);
            empty_view.setText(R.string.no_internet_connection);
            empty_view2.setVisibility(View.VISIBLE);
            fab_add_swap.setVisibility(View.GONE);
=======
>>>>>>> f430401c2f9e432e75a91652785211f2d8373acc
        }

        homeBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_bar_shift:
                        getActivity().getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.home_fragment_container,
                                        new ShiftSwapFragment())
                                .commit();
                        return true;
                    case R.id.nav_bar_off:
                        getActivity().getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.home_fragment_container,
                                        new OffSwapFragment())
                                .commit();
                        return true;
                    default:
                        return false;

                }
            }
        });

        return rootView;
    }
<<<<<<< HEAD
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
        // ...

        public static class MyPagerAdapter extends FragmentPagerAdapter {
            private static int NUM_ITEMS = 2;

            public MyPagerAdapter(FragmentManager fragmentManager) {
                super(fragmentManager);
            }

            // Returns total number of pages
            @Override
            public int getCount() {
                return NUM_ITEMS;
            }

            // Returns the fragment to display for that page
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: // Fragment # 0 - This will show FirstFragment
                        return OffSwapFragment.newInstance(0, "Swap Shift");
                    case 1: // Fragment # 0 - This will show FirstFragment different title
                        return OffSwapFragment.newInstance(1, "Swap Off");
                    default:
                        return null;
                }
            }

            // Returns the page title for the top indicator
            @Override
            public CharSequence getPageTitle(int position) {
                return "Page " + position;
            }

        }


}
=======
}
>>>>>>> f430401c2f9e432e75a91652785211f2d8373acc
