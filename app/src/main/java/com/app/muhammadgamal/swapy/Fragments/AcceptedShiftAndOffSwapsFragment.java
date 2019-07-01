package com.app.muhammadgamal.swapy.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.muhammadgamal.swapy.R;

public class AcceptedShiftAndOffSwapsFragment extends Fragment {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.accepted_shift_swaps:
                    getFragmentManager().
                            beginTransaction().
                            replace(R.id.accepted_swaps_frame,
                                    new AcceptedShiftSwapFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.accepted_off_swaps:
                    getFragmentManager().
                            beginTransaction().
                            replace(R.id.accepted_swaps_frame,
                                    new AcceptedOffSwapFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }
    };


    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_accepted_shift_and_off_swaps, container, false);

        BottomNavigationView navView = rootView.findViewById(R.id.nav_view_bottom);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getFragmentManager().
                beginTransaction().
                replace(R.id.accepted_swaps_frame,
                        new AcceptedShiftSwapFragment())
                .addToBackStack(null)
                .commit();

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

