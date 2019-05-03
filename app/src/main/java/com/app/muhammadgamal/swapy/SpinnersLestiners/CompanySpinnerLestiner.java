package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.ResgistrationActivities.SignUpActivity;
import com.app.muhammadgamal.swapy.UserSittings.ChangeCompany;

public class CompanySpinnerLestiner implements OnItemSelectedListener {

    public static String company;
    public static int VODAFONE;
    public static int RAYA;
    public static int ORANGE;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose company")) {
            company = parent.getItemAtPosition(position).toString();
            SignUpActivity.arrayBranch = R.array.branch;
            //do nothing
            SignUpActivity.COMPANY_CHOSEN = 1;
            SignUpActivity.spinnerCompanyBranchRaya.setVisibility(View.GONE);
            SignUpActivity.spinnerCompanyBranchOrange.setVisibility(View.GONE);
            SignUpActivity.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
        } else {
            SignUpActivity.COMPANY_CHOSEN = 0;
            company = parent.getItemAtPosition(position).toString();
            if (company.equals("Raya")) {
                SignUpActivity.spinnerCompanyBranchRaya.setVisibility(View.VISIBLE);
                SignUpActivity.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            } else if (company.equals("Vodafone")) {
                SignUpActivity.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchVodafone.setVisibility(View.VISIBLE);
            } else if (company.equals("Orange")) {
                SignUpActivity.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchOrange.setVisibility(View.VISIBLE);
                SignUpActivity.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }else {
                SignUpActivity.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                SignUpActivity.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
