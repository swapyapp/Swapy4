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
            ChangeCompany.arrayBranch = R.array.branch;
            //do nothing
            ChangeCompany.COMPANY_CHOSEN = 1;
            ChangeCompany.spinnerCompanyBranchRaya.setVisibility(View.GONE);
            ChangeCompany.spinnerCompanyBranchOrange.setVisibility(View.GONE);
            ChangeCompany.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
        } else {
            ChangeCompany.COMPANY_CHOSEN = 0;
            company = parent.getItemAtPosition(position).toString();
            if (company.equals("Raya")) {
                ChangeCompany.spinnerCompanyBranchRaya.setVisibility(View.VISIBLE);
                ChangeCompany.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            } else if (company.equals("Vodafone")) {
                ChangeCompany.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchVodafone.setVisibility(View.VISIBLE);
            } else if (company.equals("Orange")) {
                ChangeCompany.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchOrange.setVisibility(View.VISIBLE);
                ChangeCompany.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }else {
                ChangeCompany.spinnerCompanyBranchRaya.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchOrange.setVisibility(View.GONE);
                ChangeCompany.spinnerCompanyBranchVodafone.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
