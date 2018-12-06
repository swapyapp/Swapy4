package com.app.muhammadgamal.swapy.Activities;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Fragments.DatePickerFragment;
import com.app.muhammadgamal.swapy.SpinnersLestiners.OffDaySpinnerListener;
import com.app.muhammadgamal.swapy.R;
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

public class SwapOffCreationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static String currentUserCompanyBranch, currentUserAccount;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ImageView img_back_creation_body, img_save_creation_body;
    Spinner current_off_day_spinner, preferred_off_day_spinner;
    EditText edit_text_off_date;
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

        edit_text_off_date = (EditText) findViewById(R.id.edit_text_off_date);
        edit_text_off_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        creation_body_progress_bar = (ProgressBar) findViewById(R.id.creation_body_off_progress_bar);

        current_off_day_spinner = (Spinner) findViewById(R.id.current_off_day_spinner);
        preferred_off_day_spinner = (Spinner) findViewById(R.id.preferred_off_day_spinner);


        swapOffDaySpinner();
        swapPreferredOffSpinner();

        img_back_creation_body = (ImageView) findViewById(R.id.img_back_off_creation_body);
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
    }

    private void swapOffDaySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Home_Preferred_Off2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_off_day_spinner.setAdapter(adapter);
        current_off_day_spinner.setOnItemSelectedListener(new OffDaySpinnerListener());
    }


    private void swapPreferredOffSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Home_Preferred_Off2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferred_off_day_spinner.setAdapter(adapter);
        preferred_off_day_spinner.setOnItemSelectedListener(new OffDaySpinnerListener());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String chosenDateString = dateFormat.format(c.getTime());
        edit_text_off_date.setText(chosenDateString);
        edit_text_off_date.setError(null);
    }

    //add the swap to FireBase RealTime database
    private void addOffToDatabase() {

        final String OffDay = current_off_day_spinner.getSelectedItem().toString();
        final String OffDate = edit_text_off_date.getText().toString().trim();
        final String preferredOffDay = preferred_off_day_spinner.getSelectedItem().toString();
        if (current_off_day_spinner.getSelectedItem().toString().equals("Day")) {
            Toast.makeText(getApplicationContext(), "choose a day", Toast.LENGTH_SHORT).show();
            return;
        }
        if (OffDate.isEmpty()) {
            edit_text_off_date.setError("Pick a date");
            edit_text_off_date.requestFocus();
            return;
        }
        if (preferred_off_day_spinner.getSelectedItem().toString().equals("Day")) {
            Toast.makeText(getApplicationContext(), "choose your preferred off day", Toast.LENGTH_SHORT).show();
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
                SwapOff SwapDetails = new SwapOff(preferredOffDay, OffDay, OffDate, userId, swapperName, swapperEmail, swapperPhone,
                        currentUserCompanyBranch, currentUserAccount, swapperImageUrl);
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
