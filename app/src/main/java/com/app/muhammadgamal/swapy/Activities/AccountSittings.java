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
    private TextView deleteAccount;
    private TextView switchToOtherCompany;
    private TextView generalSittings;
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
        final Bundle bundle = new Bundle();

        changeUserName = findViewById(R.id.sittings_change_account_user_name);
        changeUserEmail = findViewById(R.id.sittings_change_account_user_email);
        changeUserPassword = findViewById(R.id.sittings_change_account_user_password);
        deleteAccount = findViewById(R.id.sittings_delete_account);

        generalSittings = (TextView)findViewById(R.id.sittings_general_sittings);

        generalSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generalSittings = new Intent(AccountSittings.this, SettingsActivity.class);
                startActivity(generalSittings);
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
