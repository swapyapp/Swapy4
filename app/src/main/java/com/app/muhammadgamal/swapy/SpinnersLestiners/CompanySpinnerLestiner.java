package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.app.muhammadgamal.swapy.Activities.SignUpActivity;

public class CompanySpinnerLestiner implements OnItemSelectedListener {

    public static String company;
    public static int VODAFONE ;
    public static int RAYA ;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose company")){
            //do nothing
            SignUpActivity.COMPANY_CHOSEN = 1;
        } else {
            SignUpActivity.COMPANY_CHOSEN = 0;
            company = parent.getItemAtPosition(position).toString();
            if (parent.getItemAtPosition(position).equals("Vodafone")){
                VODAFONE = 1;
            } if (parent.getItemAtPosition(position).equals("Raya")){
                RAYA = 1;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
