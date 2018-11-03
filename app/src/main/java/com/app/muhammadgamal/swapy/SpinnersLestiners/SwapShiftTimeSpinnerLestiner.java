package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

public class SwapShiftTimeSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    private String SwapShiftTime;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("Shift's time")){
            //do nothing
        } else {
            SwapShiftTime = parent.getItemAtPosition(position).toString();
        }

    }

    public String getSwapShiftTime() {
        return SwapShiftTime;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}