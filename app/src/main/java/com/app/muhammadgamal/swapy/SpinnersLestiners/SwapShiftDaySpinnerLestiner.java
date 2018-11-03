package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

public class SwapShiftDaySpinnerLestiner implements AdapterView.OnItemSelectedListener {

    private String SwapShiftDay;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("Day")){
            //do nothing
        } else {
            SwapShiftDay = parent.getItemAtPosition(position).toString();
        }

    }

    public String getSwapShiftDay() {
        return SwapShiftDay;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}