package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import com.app.muhammadgamal.swapy.ResgistrationActivities.SignUpActivity;

public class BranchSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    public static String Branch;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose branch")){
            //do nothing
            SignUpActivity.BRANCH_CHOSEN = 1;
            Branch = parent.getItemAtPosition(position).toString();
            SignUpActivity.spinnerAccountEtisalat.setVisibility(View.GONE);
            SignUpActivity.spinnerAccountArabicAccount.setVisibility(View.GONE);
            SignUpActivity.spinnerAccountVodafoneUK.setVisibility(View.GONE);
            SignUpActivity.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
        } else {
            SignUpActivity.BRANCH_CHOSEN = 0;
            Branch = parent.getItemAtPosition(position).toString();
            if (Branch.equals("اتصالات الامارات")){
                SignUpActivity.spinnerAccountEtisalat.setVisibility(View.VISIBLE);
                SignUpActivity.spinnerAccountArabicAccount.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            } else if (Branch.equals("Vodafone uk")){
                SignUpActivity.spinnerAccountEtisalat.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountArabicAccount.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneUK.setVisibility(View.VISIBLE);
                SignUpActivity.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            } else if (Branch.equals("Vodafone Arabic")){
                SignUpActivity.spinnerAccountEtisalat.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountArabicAccount.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneArabic.setVisibility(View.VISIBLE);
            } else if (Branch.equals("Arabic Account")){
                SignUpActivity.spinnerAccountEtisalat.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountArabicAccount.setVisibility(View.VISIBLE);
                SignUpActivity.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                SignUpActivity.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}