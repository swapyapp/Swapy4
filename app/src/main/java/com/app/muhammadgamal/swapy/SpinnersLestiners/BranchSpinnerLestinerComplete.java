package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.ResgistrationActivities.CompleteSignUpData;

public class BranchSpinnerLestinerComplete implements AdapterView.OnItemSelectedListener {

    public static String Branch;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose branch")){
            //do nothing
            CompleteSignUpData.BRANCH_CHOSEN = 1;
            Branch = parent.getItemAtPosition(position).toString();
            CompleteSignUpData.spinnerAccountEtisalat.setVisibility(View.GONE);
            CompleteSignUpData.spinnerAccountArabicAccount.setVisibility(View.GONE);
            CompleteSignUpData.spinnerAccountVodafoneUK.setVisibility(View.GONE);
            CompleteSignUpData.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
        } else {
            CompleteSignUpData.BRANCH_CHOSEN = 0;
            Branch = parent.getItemAtPosition(position).toString();
            if (Branch.equals("اتصالات الامارات")){
                CompleteSignUpData.spinnerAccountEtisalat.setVisibility(View.VISIBLE);
                CompleteSignUpData.spinnerAccountArabicAccount.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            } else if (Branch.equals("Vodafone uk")){
                CompleteSignUpData.spinnerAccountEtisalat.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountArabicAccount.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneUK.setVisibility(View.VISIBLE);
                CompleteSignUpData.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            } else if (Branch.equals("Vodafone Arabic")){
                CompleteSignUpData.spinnerAccountEtisalat.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountArabicAccount.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneArabic.setVisibility(View.VISIBLE);
            } else if (Branch.equals("Arabic Account")){
                CompleteSignUpData.spinnerAccountEtisalat.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountArabicAccount.setVisibility(View.VISIBLE);
                CompleteSignUpData.spinnerAccountVodafoneUK.setVisibility(View.GONE);
                CompleteSignUpData.spinnerAccountVodafoneArabic.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}