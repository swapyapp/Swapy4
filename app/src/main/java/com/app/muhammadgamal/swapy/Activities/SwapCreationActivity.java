package com.app.muhammadgamal.swapy.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Fragments.DatePickerFragment;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapPreferredTimeSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapShiftDaySpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapShiftTimeSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SwapCreationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static String currentUserCompanyBranch, currentUserAccount;
    static int SHIFT_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    static int PREFERRED_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    // instance of the FireBase and reference to the database
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ImageView img_back_creation_body, img_save_creation_body;
    Spinner shifts_day_spinner, shifts_time_spinner, preferred_time_spinner;
    RelativeLayout creationBodyShiftTimeAM, creationBodyShiftTimePM, creationBodyPreferredTimeAM, creationBodyPreferredTimePM;
    EditText edit_text_shift_date;
    TextView creationBodyShiftTimeAMText, creationBodyShiftTimePMText, creationBodyPreferredTimeAMText, creationBodyPreferredTimePMText;
    ProgressBar creation_body_progress_bar;
    String userId, swapperImageUrl, swapperName, swapperEmail, swapperPhone, swapperLoginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_creation_body);

        Resources res = getResources();
        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("swaps").child("shift_swaps");

        edit_text_shift_date = (EditText) findViewById(R.id.edit_text_shift_date);
        edit_text_shift_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        creation_body_progress_bar = (ProgressBar) findViewById(R.id.creation_body_progress_bar);

        shifts_day_spinner = (Spinner) findViewById(R.id.shifts_day_spinner);
        shifts_time_spinner = (Spinner) findViewById(R.id.shifts_time_spinner);
        preferred_time_spinner = (Spinner) findViewById(R.id.preferred_time_spinner);

        creationBodyShiftTimeAMText = (TextView) findViewById(R.id.creationBodyShiftTimeAMText);
        creationBodyShiftTimePMText = (TextView) findViewById(R.id.creationBodyShiftTimePMText);
        creationBodyPreferredTimeAMText = (TextView) findViewById(R.id.creationBodyPreferredTimeAMText);
        creationBodyPreferredTimePMText = (TextView) findViewById(R.id.creationBodyPreferredTimePMText);

        creationBodyShiftTimeAM = (RelativeLayout) findViewById(R.id.creationBodyShiftTimeAM);
        creationBodyShiftTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHIFT_TIME_SELECTED = 0;
                creationBodyShiftTimeAM.setBackground(SelectedBackground);
                creationBodyShiftTimePM.setBackground(notSelectedBackground);
                creationBodyShiftTimeAMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyShiftTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyShiftTimePM = (RelativeLayout) findViewById(R.id.creationBodyShiftTimePM);
        creationBodyShiftTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHIFT_TIME_SELECTED = 1;
                creationBodyShiftTimePM.setBackground(SelectedBackground);
                creationBodyShiftTimeAM.setBackground(notSelectedBackground);
                creationBodyShiftTimePMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyShiftTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyPreferredTimeAM = (RelativeLayout) findViewById(R.id.creationBodyPreferredTimeAM);
        creationBodyPreferredTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 0;
                creationBodyPreferredTimeAM.setBackground(SelectedBackground);
                creationBodyPreferredTimePM.setBackground(notSelectedBackground);
                creationBodyPreferredTimeAMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyPreferredTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyPreferredTimePM = (RelativeLayout) findViewById(R.id.creationBodyPreferredTimePM);
        creationBodyPreferredTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 1;
                creationBodyPreferredTimePM.setBackground(SelectedBackground);
                creationBodyPreferredTimeAM.setBackground(notSelectedBackground);
                creationBodyPreferredTimePMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyPreferredTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        swapShiftDaySpinner();
        swapShiftTimeSpinner();
        swapPreferredTimeSpinner();

        img_back_creation_body = (ImageView) findViewById(R.id.img_back_creation_body);
        img_back_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_save_creation_body = (ImageView) findViewById(R.id.img_save_creation_body);
        img_save_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSwapToDatabase();
            }
        });
    }

    private void swapShiftDaySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shift_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts_day_spinner.setAdapter(adapter);
        shifts_day_spinner.setOnItemSelectedListener(new SwapShiftDaySpinnerLestiner());
    }

    private void swapShiftTimeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shift_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts_time_spinner.setAdapter(adapter);
        shifts_time_spinner.setOnItemSelectedListener(new SwapShiftTimeSpinnerLestiner());
    }

    private void swapPreferredTimeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.preferred_shift_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferred_time_spinner.setAdapter(adapter);
        preferred_time_spinner.setOnItemSelectedListener(new SwapPreferredTimeSpinnerLestiner());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String chosenDateString = dateFormat.format(c.getTime());
        edit_text_shift_date.setText(chosenDateString);
        edit_text_shift_date.setError(null);
    }

    //add the swap to FireBase RealTime database
    private void addSwapToDatabase() {
        String shiftAMorPM, preferredAMorPM;
        if (SHIFT_TIME_SELECTED == 0) {
            shiftAMorPM = " AM";
        } else {
            shiftAMorPM = " PM";
        }
        if (PREFERRED_TIME_SELECTED == 0) {
            preferredAMorPM = " AM";
        } else {
            preferredAMorPM = " PM";
        }
        final String shiftDay = shifts_day_spinner.getSelectedItem().toString();
        final String shiftDate = edit_text_shift_date.getText().toString().trim();
        final String shiftTime = shifts_time_spinner.getSelectedItem().toString() + shiftAMorPM;
        final String preferredShift = preferred_time_spinner.getSelectedItem().toString() + preferredAMorPM;
        if (shifts_day_spinner.getSelectedItem().toString().equals("Day")) {
            Toast.makeText(getApplicationContext(), "choose a day", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shiftDate.isEmpty()) {
            edit_text_shift_date.setError("Enter your Shift's date");
            edit_text_shift_date.requestFocus();
            return;
        }
        if (shifts_time_spinner.getSelectedItem().toString().equals("Shift")) {
            Toast.makeText(getApplicationContext(), "choose your shift's time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (preferred_time_spinner.getSelectedItem().toString().equals("Preferred shift")) {
            Toast.makeText(getApplicationContext(), "choose your preferred shift", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getmProfilePhotoURL() != null) {
                    swapperImageUrl = user.getmProfilePhotoURL();
                } else {
                    swapperImageUrl = null;
                }
                swapperName = user.getmUsername();
                swapperPhone = user.getmPhoneNumber();
                currentUserCompanyBranch = user.getmBranch();
                currentUserAccount = user.getmAccount();

                swapperEmail = mAuth.getCurrentUser().getEmail();
                SwapDetails SwapDetails = new SwapDetails(userId, swapperName, swapperEmail, swapperPhone, currentUserCompanyBranch, currentUserAccount, swapperImageUrl, shiftDay, shiftDate, shiftTime, preferredShift, swapperLoginID);
                creation_body_progress_bar.setVisibility(View.VISIBLE);
                img_save_creation_body.setVisibility(View.GONE);
                databaseReference.push().setValue(SwapDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        img_save_creation_body.setVisibility(View.VISIBLE);
                        creation_body_progress_bar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Swap added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}