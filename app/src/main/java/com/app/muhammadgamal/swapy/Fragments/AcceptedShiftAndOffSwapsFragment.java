package com.app.muhammadgamal.swapy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.muhammadgamal.swapy.R;

public class AcceptedShiftAndOffSwapsFragment extends Fragment {


    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_accepted_shift_and_off_swaps, container, false);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (rootView.findViewById(R.id.accepted_shift_swaps_container) != null) {

            // Create a new Fragment to be placed in the activity layout
            AcceptedShiftSwapFragment firstFragment = new AcceptedShiftSwapFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.accepted_shift_swaps_container, firstFragment).commit();
        }

//        if (rootView.findViewById(R.id.accepted_off_swaps_container) != null) {
//
//            // Create a new Fragment to be placed in the activity layout
//            AcceptedOffSwapFragment secondFragment = new AcceptedOffSwapFragment();
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getFragmentManager().beginTransaction()
//                    .add(R.id.accepted_off_swaps_container, secondFragment).commit();
//        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.nav_bar_items, menu);
        MenuItem item = menu.findItem(R.id.search_icon);
        item.setIcon(null);
        item.setTitle("");
        super.onCreateOptionsMenu(menu, inflater);
    }
}

