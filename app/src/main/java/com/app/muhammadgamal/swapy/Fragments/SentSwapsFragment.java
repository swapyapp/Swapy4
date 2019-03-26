package com.app.muhammadgamal.swapy.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import com.app.muhammadgamal.swapy.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class SentSwapsFragment extends Fragment {

    private View rootView;
    private ImageView navigationDrawerBtn;
    private DrawerLayout drawer;
    public SentSwapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sent_swaps, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.sentSwaps_fragment_toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.sent_swaps_viewpager);
        SentSwapsPagerAdapter pagerAdapter = new SentSwapsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = rootView.findViewById(R.id.setswaps_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        navigationDrawerBtn = (ImageView)rootView.findViewById(R.id.toolbar_navigation_icon_sent_swaps);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        navigationDrawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        return rootView;
    }

    public static class SentSwapsPagerAdapter extends FragmentPagerAdapter {

        private String [] tabTitle = new String[] {"Shift requests", "Off requests"};
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
