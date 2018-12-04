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
<<<<<<< HEAD
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapShiftTimeSpinnerLestiner;
=======
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
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

public class SwapOffCreationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static String currentUserCompanyBranch, currentUserAccount;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ImageView img_back_creation_body, img_save_creation_body;
<<<<<<< HEAD
    Spinner shifts_day_spinner, shifts_time_spinner, preferred_off_day_spinner;
    RelativeLayout creationBodyShiftTimeAM, creationBodyShiftTimePM, creationBodyPreferredTimeAM, creationBodyPreferredTimePM;
    EditText edit_text_current_off_date;
    TextView creationBodyShiftTimeAMText, creationBodyShiftTimePMText, creationBodyPreferredTimeAMText, creationBodyPreferredTimePMText;
=======
    Spinner shifts_day_spinner, preferred_off_day_spinner;
    EditText edit_text_off_date;
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
    ProgressBar creation_body_progress_bar;
    String userId, swapperImageUrl, swapperName, swapperEmail, swapperPhone, swapperLoginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_off_creation);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("swaps").child("off_swaps");

<<<<<<< HEAD
        edit_text_current_off_date = (EditText) findViewById(R.id.edit_text_current_off_date);
        edit_text_current_off_date.setOnClickListener(new View.OnClickListener() {
=======
        edit_text_off_date = (EditText) findViewById(R.id.edit_text_off_date);
        edit_text_off_date.setOnClickListener(new View.OnClickListener() {
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        creation_body_progress_bar = (ProgressBar) findViewById(R.id.creation_body_off_progress_bar);

        shifts_day_spinner = (Spinner) findViewById(R.id.current_off_day_spinner);
        preferred_off_day_spinner = (Spinner) findViewById(R.id.preferred_off_day_spinner);


<<<<<<< HEAD
        img_back_creation_body = (ImageView) findViewById(R.id.img_back_creation_body);
=======
        swapOfftDaySpinner();
        swapPreferredOffSpinner();

        img_back_creation_body = (ImageView) findViewById(R.id.img_back_off_creation_body);
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
        img_back_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_save_creation_body = (ImageView) findViewById(R.id.img_save_off_creation_body);
        img_save_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOffToDatabase();
            }
        });

        swapOfftDaySpinner();
        swapOffTimeSpinner();
        swapPreferredOffSpinner();
    }

    private void swapOfftDaySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shift_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts_day_spinner.setAdapter(adapter);
        shifts_day_spinner.setOnItemSelectedListener(new SwapShiftDaySpinnerLestiner());
    }


    private void swapPreferredOffSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.preferred_shift_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferred_off_day_spinner.setAdapter(adapter);
        preferred_off_day_spinner.setOnItemSelectedListener(new SwapPreferredTimeSpinnerLestiner());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String chosenDateString = dateFormat.format(c.getTime());
<<<<<<< HEAD
        edit_text_current_off_date.setText(chosenDateString);
        edit_text_current_off_date.setError(null);
=======
        edit_text_off_date.setText(chosenDateString);
        edit_text_off_date.setError(null);
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
    }

    //add the swap to FireBase RealTime database
    private void addOffToDatabase() {

        final String OffDay = shifts_day_spinner.getSelectedItem().toString();
<<<<<<< HEAD
        final String OffDate = edit_text_current_off_date.getText().toString().trim();
=======
        final String OffDate = edit_text_off_date.getText().toString().trim();
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
        final String preferredOffDay = preferred_off_day_spinner.getSelectedItem().toString();
        if (shifts_day_spinner.getSelectedItem().toString().equals("Day")) {
            Toast.makeText(getApplicationContext(), "choose a day", Toast.LENGTH_SHORT).show();
            return;
        }
        if (OffDate.isEmpty()) {
<<<<<<< HEAD
            edit_text_current_off_date.setError("Enter your Shift's date");
            edit_text_current_off_date.requestFocus();
            return;
        }
        if (shifts_time_spinner.getSelectedItem().toString().equals("Shift")) {
            Toast.makeText(getApplicationContext(), "choose your shift's time", Toast.LENGTH_SHORT).show();
=======
            edit_text_off_date.setError("Enter your Shift's date");
            edit_text_off_date.requestFocus();
>>>>>>> 5f4aebfe6d373eb1d8d8bc281a5b4b8c1003272e
            return;
        }
        if (preferred_off_day_spinner.getSelectedItem().toString().equals("Preferred shift")) {
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
                swapperLoginID = user.getmLoginID();

                swapperEmail = mAuth.getCurrentUser().getEmail();
                SwapOff SwapDetails = new SwapOff(userId, swapperName, swapperEmail, swapperPhone,
                        currentUserCompanyBranch, currentUserAccount, swapperImageUrl, OffDay, OffDate,
                         preferredOffDay, swapperLoginID);
                creation_body_progress_bar.setVisibility(View.VISIBLE);
                img_save_creation_body.setVisibility(View.GONE);
                databaseReference.push().setValue(SwapDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        img_save_creation_body.setVisibility(View.VISIBLE);
                        creation_body_progress_bar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Swap added successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SwapOffCreationActivity.this, NavDrawerActivity.class));
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


