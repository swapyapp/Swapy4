package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

public class PreferredOffDaySpinnerListener implements AdapterView.OnItemSelectedListener{


    public static String PreferredOffDay;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("any day")){
            //do nothing
            PreferredOffDay = null;
        } else {
            PreferredOffDay = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
