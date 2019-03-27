package com.app.muhammadgamal.swapy.SpinnersLestiners;


import android.view.View;
import android.widget.AdapterView;

public class YourPreferredDaySpinnerLestiner implements AdapterView.OnItemSelectedListener {

    private String YourPreferredDay;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("Any day")){
            //do nothing
        } else {
            YourPreferredDay = parent.getItemAtPosition(position).toString();
        }

    }

    public String getYourPreferredDay() {
        return YourPreferredDay;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

