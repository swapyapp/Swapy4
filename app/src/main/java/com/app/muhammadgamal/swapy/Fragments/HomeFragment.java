package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.FragmentWithViewPager;
import com.app.muhammadgamal.swapy.Notifications.ReceivedSwapsActivity;
import com.app.muhammadgamal.swapy.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private FragmentPagerAdapter adapterViewPager;
    private View rootView;
    private TabLayout tabLayout;
    private ImageView navigationDrawerBtn;
    private  ViewPager vpPager;
    private DrawerLayout drawer;
    public ImageView searchIcon;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mSwapRequestsShift;
    private DatabaseReference mSwapRequestsOff;
    private static int NEW_REQUEST =0;
    private FirebaseAuth mAuth;
    private String userId;
    private TextView notificationCounter;
    private ImageView notificationImage;
    private static final String TAG = HomeFragment.class.getName();



    private int[] tabIcons = {
//            R.drawable.ic_shift_whit,
//            R.drawable.ic_off_whit,
            R.drawable.ic_check_black_24dp
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

//        BottomNavigationView homeBottomNavigationView = rootView.findViewById(R.id.homeBottomNavigationView);
        Toolbar mToolbar = rootView.findViewById(R.id.home_fragment_toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.main_title);

        //notificationCounter = rootView.findViewById(R.id.notification_counter);
        notificationImage = rootView.findViewById(R.id.toolbar_notification_img);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapRequestsShift = mFirebaseDatabase.getReference().child("Swap Requests").child("Off Request");
        mSwapRequestsOff = mFirebaseDatabase.getReference().child("Swap Requests").child("Shift Request");

        DatabaseReference userShiftRequest = mSwapRequestsShift.child(userId);

//        ValueEventListener requestListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    NEW_REQUEST = 1;
//                    if (NEW_REQUEST == 1) {
//                        notificationImage.setImageResource(R.drawable.ic_notification_red);
//                    } else {
//                        notificationImage.setImageResource(R.drawable.ic_notification);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//
//            }
//        };
//        userShiftRequest.addValueEventListener(requestListener);




        vpPager = (ViewPager) rootView.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);


        invalidateOptionsMenu(0);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_home);
        setupTabIcons();
        tabLayout.setupWithViewPager(vpPager);


        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.home_fragment_container,
                            new ShiftSwapFragment())
                    .commit();
        }
        navigationDrawerBtn = (ImageView)rootView.findViewById(R.id.toolbar_navigation_icon);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        navigationDrawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        notificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NEW_REQUEST = 0;
                //notificationCounter.setTextColor(Color.WHITE);
//                notificationImage.setImageResource(R.drawable.ic_notification);
                startActivity(new Intent(getContext(), ReceivedSwapsActivity.class));

            }
        });

        return rootView;
    }

    private void invalidateOptionsMenu(int position) {
        for(int i = 0; i < adapterViewPager.getCount(); i++) {
            Fragment fragment = adapterViewPager.getItem(i);
            fragment.setHasOptionsMenu(i == position);
        }
    }

    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_check_black_24dp);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"Shift", "Off","Accepted Swaps"};

        private int NUM_ITEMS = 3;

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
                    return new ShiftSwapFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new OffSwapFragment();
                case 2:
                     return new AcceptedShiftAndOffSwapsFragment();
                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    @Override
    public void onStart() {
        super.onStart();
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.nav_bar_items, menu);
        if (vpPager.getCurrentItem()==0){
            menu.findItem(R.id.search_icon).setVisible(true);
        } else if(vpPager.getCurrentItem()==1){
            menu.findItem(R.id.search_icon).setVisible(true);
        } else if(vpPager.getCurrentItem()==2){
            menu.findItem(R.id.search_icon).setVisible(false);
        }
        super.onCreateOptionsMenu(menu,inflater);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.search_icon) {
//            Toast.makeText(getContext(), "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}