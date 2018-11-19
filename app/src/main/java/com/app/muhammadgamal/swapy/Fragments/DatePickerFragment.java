package com.app.muhammadgamal.swapy.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }

    @Override
    public void onStart() {
        super.onStart();
<<<<<<< HEAD

=======
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
>>>>>>> 5e2194696766723651f3f57bbbcf3571e7b9db5e
    }
}
