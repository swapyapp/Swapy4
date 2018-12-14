package com.app.muhammadgamal.swapy.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.app.muhammadgamal.swapy.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class SentSwapsFragment extends Fragment {

    private View rootView;
    public SentSwapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sent_swaps, container, false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.sent_swaps_viewpager);
        SentSwapsPagerAdapter pagerAdapter = new SentSwapsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        return rootView;
    }

    public static class SentSwapsPagerAdapter extends FragmentPagerAdapter {

        private String [] tabTitle = new String[] {"Sent Shift Swaps", "Sent Off swaps"};
        public SentSwapsPagerAdapter (FragmentManager fragmentManager){
            super(fragmentManager);
        }
        private int NUM_ITEMS = 2;

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SentShiftSwapFragment();
                case 1:
                    return new SentOffSwapFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }

}
