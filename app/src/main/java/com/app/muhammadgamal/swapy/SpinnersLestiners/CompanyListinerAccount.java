package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.UserSittings.ChangeCompany;
import com.app.muhammadgamal.swapy.UserSittings.ChangeCompany;

public class CompanyListinerAccount implements AdapterView.OnItemSelectedListener {

    public static String company;
    public static int VODAFONE;
    public static int RAYA;
    public static int ORANGE;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose company")) {
            company = parent.getItemAtPosition(position).toString();
        } else {
            ChangeCompany.COMPANY_CHOSEN = 0;
            company = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
