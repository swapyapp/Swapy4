package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class AccountSittings extends AppCompatActivity {

    private TextView accountName;
    private TextView changeUserName;
    private TextView changeUserEmail;
    private TextView changeUserPassword;
    private TextView deleteAccount;
    private TextView switchToOtherCompany;
    private Intent sittingBodyIntent;
    private FirebaseAuth mAuth;
    DatabaseReference ref;

    // Indicators will be sent through intent to SittingsActivityBody so that it knows which data to show

    private String changeNameIndicator = "name";
    private String changePasswordIndicator = "password";
    private String changeEmailIndicator = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sittings);

        mAuth = FirebaseAuth.getInstance();
        final String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        sittingBodyIntent = new Intent(AccountSittings.this,SittingActivityBody.class);
        final Bundle bundle = new Bundle();

        accountName = findViewById(R.id.account_name);
        changeUserName = findViewById(R.id.sittings_change_account_user_name);
        changeUserEmail = findViewById(R.id.sittings_change_account_user_email);
        changeUserPassword = findViewById(R.id.sittings_change_account_user_password);
        deleteAccount = findViewById(R.id.sittings_delete_account);


        ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                accountName.setText(user.getmUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("name",1);
                sittingBodyIntent.putExtras(bundle);
                startActivity(sittingBodyIntent);
            }
        });
        changeUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("password",2);
                sittingBodyIntent.putExtras(bundle);
                startActivity(sittingBodyIntent);
            }
        });

        changeUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("email", 3);
                sittingBodyIntent.putExtras(bundle);
                startActivity(sittingBodyIntent);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSittings.this, DeleteAccount.class);
                startActivity(intent);
            }
        });

    }

}
