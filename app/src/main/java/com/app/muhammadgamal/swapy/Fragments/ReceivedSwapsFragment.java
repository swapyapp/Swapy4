package com.app.muhammadgamal.swapy.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedSwapsFragment extends Fragment {

    private View rootView;

    public ReceivedSwapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_received_swaps, container, false);

        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //((NavDrawerActivity) getActivity()).updateStatusBarColor("#0081cb");

        getActivity().setTitle("Received swaps");

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.received_swaps_viewpager);
        ReceivedSwapsPagerAdapter adapterViewPager = new ReceivedSwapsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        return rootView;
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
