package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;

import org.w3c.dom.Text;

public class AccountSittings extends AppCompatActivity {

    private TextView changeUserName;
    private TextView changeUserEmail;
    private TextView changeUserPassword;
    private TextView switchToOtherCompany;
    private Intent sittingBodyIntent;

    // Indicators will be sent through intent to SittingsActivityBody so that it knows which data to show
    private String changeNameIndicator = "name";
    private String changePasswordIndicator = "password";
    private String changeEmailIndicator = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sittings);

        sittingBodyIntent = new Intent(AccountSittings.this,SittingActivityBody.class);

        changeUserName = findViewById(R.id.sittings_change_account_user_name);
        changeUserEmail = findViewById(R.id.sittings_change_account_user_email);
        changeUserPassword = findViewById(R.id.sittings_change_account_user_password);

        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sittingBodyIntent.putExtra("Change name",changeNameIndicator);
                startActivity(sittingBodyIntent);
            }
        });
        changeUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sittingBodyIntent.putExtra("Change password", changePasswordIndicator);
                startActivity(sittingBodyIntent);
            }
        });

        changeUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sittingBodyIntent.putExtra("Change email", changeEmailIndicator);
                startActivity(sittingBodyIntent);
            }
        });
    }

}
