package com.app.muhammadgamal.swapy.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.muhammadgamal.swapy.R;

public class HomeFragment extends Fragment {

    private FragmentPagerAdapter adapterViewPager;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //getActivity().setTitle("Home");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

//        BottomNavigationView homeBottomNavigationView = rootView.findViewById(R.id.homeBottomNavigationView);

        ViewPager vpPager = (ViewPager) rootView.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setTextColor(Color.WHITE);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.home_fragment_container,
                            new ShiftSwapFragment())
                    .commit();
        }

//        homeBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                switch (menuItem.getItemId()) {
//
//                    case R.id.nav_bar_shift:
//                        getActivity().getSupportFragmentManager().
//                                beginTransaction().
//                                replace(R.id.home_fragment_container,
//                                        new ShiftSwapFragment())
//                                .commit();
//                        return true;
//                    case R.id.nav_bar_off:
//                        getActivity().getSupportFragmentManager().
//                                beginTransaction().
//                                replace(R.id.home_fragment_container,
//                                        new OffSwapFragment())
//                                .commit();
//                        return true;
//                    default:
//                        return false;
//
//                }
//            }
//        });

        return rootView;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"Shift Swaps", "Off Swaps"};

        private int NUM_ITEMS = 2;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}