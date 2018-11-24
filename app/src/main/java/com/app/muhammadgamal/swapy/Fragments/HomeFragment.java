package com.app.muhammadgamal.swapy.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.app.muhammadgamal.swapy.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        BottomNavigationView homeBottomNavigationView = rootView.findViewById(R.id.homeBottomNavigationView);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.home_fragment_container,
                            new ShiftSwapFragment())
                    .commit();
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
}