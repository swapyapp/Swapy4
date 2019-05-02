package com.app.muhammadgamal.swapy.Notifications;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.muhammadgamal.swapy.Fragments.ReceivedOffSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.ReceivedShiftSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.ReceivedSwapsFragment;
import com.app.muhammadgamal.swapy.R;

public class ReceivedSwapsActivity extends AppCompatActivity {
    private ImageView backDrawerBtn;
    private DrawerLayout drawer;

    public ReceivedSwapsActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_swaps);
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.receivedSwaps_toolBar_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Received swaps");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_white_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        ViewPager viewPager = (ViewPager)findViewById(R.id.received_swaps_viewpager_activity);
        ReceivedSwapsFragment.ReceivedSwapsPagerAdapter adapterViewPager = new ReceivedSwapsFragment.
                ReceivedSwapsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapterViewPager);
        TabLayout tabLayout =findViewById(R.id.tablayout_receivedSwaps_activity);
        tabLayout.setupWithViewPager(viewPager);

//        backDrawerBtn = (ImageView)rootView.findViewById(R.id.toolbar_back_icon_received_swaps);
//        backDrawerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getActivity().finish();
//            }
//        });

    }

    public class ReceivedSwapsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"Shift requests", "Off requests"};

        private int NUM_ITEMS = 2;

        public ReceivedSwapsPagerAdapter(FragmentManager fragmentManager) {
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
                    return new ReceivedShiftSwapFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new ReceivedOffSwapFragment();
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
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//        ((NavDrawerActivity) getActivity()).updateStatusBarColor("#0081cb");

    }
}
