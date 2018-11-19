package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.Activities.SignUpActivity;

public class CurrentShiftSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    public static String CurrentShift;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Current Shift")){
            //do nothing
            SignUpActivity.CURRENT_SHIFT_CHOSEN = 1;
        } else {
            SignUpActivity.CURRENT_SHIFT_CHOSEN = 0;
            CurrentShift = parent.getItemAtPosition(position).toString();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}