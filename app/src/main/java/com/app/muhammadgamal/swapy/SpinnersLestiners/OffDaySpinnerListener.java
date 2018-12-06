package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

public class OffDaySpinnerListener implements AdapterView.OnItemSelectedListener {

    public static String offDay;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getItemAtPosition(position).equals("Day")) {
            //do nothing
            offDay = null;
        } else {
            offDay = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
