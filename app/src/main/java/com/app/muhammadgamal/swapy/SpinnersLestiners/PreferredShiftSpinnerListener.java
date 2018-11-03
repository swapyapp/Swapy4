package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class PreferredShiftSpinnerListener implements AdapterView.OnItemSelectedListener {

    public static String PreferredShift;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Preferred Shift")){
            //do nothing
            PreferredShift = null;
        } else {
            PreferredShift = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}