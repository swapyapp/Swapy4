package com.app.muhammadgamal.swapy.UserSittings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccount extends AppCompatActivity {

    private final String TAG = DeleteAccount.class.getSimpleName();
    private String reAuthEmail;
    private String reAuthPassword;
    private TextView delete_account_cancel;

    private EditText editTextEmail;
    private EditText editTextPassword;

    String email, password;

    private Button reAuthButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        editTextEmail = findViewById(R.id.delete_account_email);
        editTextPassword= findViewById(R.id.delete_account_password);

        delete_account_cancel = findViewById(R.id.delete_account_cancel);
        delete_account_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reAuthButton = findViewById(R.id.bttn_reauthenticate);

        email = reAuthEmail = editTextEmail.getText().toString().trim();
        password = reAuthPassword = editTextPassword.getText().toString().trim();

        reAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.

                if (reAuthEmail != null && reAuthPassword != null ) {
                    if (!reAuthEmail.isEmpty() && !reAuthPassword.isEmpty()) {

                        AuthCredential credential = EmailAuthProvider
                                .getCredential(reAuthEmail, reAuthPassword);

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(DeleteAccount.this, "reAuthenticate done successfully :)", Toast.LENGTH_LONG).show();
//                                user.delete()
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Log.d(TAG, "User account deleted.");
//                                                    finish();
//                                                }
//                                            }
//                                        });

                                    }
                                });
                    }
            }else {
                    Toast.makeText(DeleteAccount.this, "Please enter your Email and passowred to reAthenticate", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
