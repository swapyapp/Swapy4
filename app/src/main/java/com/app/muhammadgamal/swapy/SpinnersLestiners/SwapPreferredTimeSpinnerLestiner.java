package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

public class SwapPreferredTimeSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    private String SwapPreferredTime;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("Preferred shift")){
            //do nothing
        } else {
            SwapPreferredTime = parent.getItemAtPosition(position).toString();
        }

    }

    public String getSwapPreferredTime() {
        return SwapPreferredTime;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
