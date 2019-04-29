package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.ResgistrationActivities.CompleteSignUpData;

public class CompanySpinnerLestinerCompleteData implements AdapterView.OnItemSelectedListener {

    public static String company;
    public static int VODAFONE;
    public static int RAYA;
    public static int ORANGE;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose company")) {
            company = parent.getItemAtPosition(position).toString();
            CompleteSignUpData.arrayBranch = R.array.branch;
            //do nothing
            CompleteSignUpData.COMPANY_CHOSEN_COMPLETEDATA = 1;
            CompleteSignUpData.spinnerCompanyBranchRaya.setVisibility(View.GONE);
            CompleteSignUpData.spinnerCompanyBranchOrange.setVisibility(View.GONE);
            CompleteSignUpData.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
        } else {
            CompleteSignUpData.COMPANY_CHOSEN_COMPLETEDATA = 0;
            company = parent.getItemAtPosition(position).toString();
            if (company.equals("Raya")) {
                CompleteSignUpData.spinnerCompanyBranchRaya.setVisibility(View.VISIBLE);
                CompleteSignUpData.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            } else if (company.equals("Vodafone")) {
                CompleteSignUpData.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchVodafone.setVisibility(View.VISIBLE);
            } else if (company.equals("Orange")) {
                CompleteSignUpData.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchOrange.setVisibility(View.VISIBLE);
                CompleteSignUpData.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }else {
                CompleteSignUpData.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                CompleteSignUpData.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
