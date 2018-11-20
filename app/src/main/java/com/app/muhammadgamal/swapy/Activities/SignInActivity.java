package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignInActivity extends AppCompatActivity {

    TextView signUpText, pls_verify_email, resend_verify_email;
    Button signInButton, retryBtnSignIn;
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private FirebaseFirestore mFireStore;
    private FirebaseUser user;
    private LinearLayout mainView, splashScreen, noConnectionViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mFireStore = FirebaseFirestore.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        pls_verify_email = (TextView) findViewById(R.id.pls_verify_email);

        mainView = (LinearLayout) findViewById(R.id.mainView);
        splashScreen = (LinearLayout) findViewById(R.id.splashScreen);
        noConnectionViewSignIn = (LinearLayout) findViewById(R.id.noConnectionViewSignIn);

        signUpText = (TextView) findViewById(R.id.signUpText);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        signInButton = (Button) findViewById(R.id.buttonSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

//        resend_verify_email = (TextView) findViewById(R.id.resend_verify_email);
//        resend_verify_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(SignInActivity.this, "Verification email sent", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });

        retryBtnSignIn = (Button) findViewById(R.id.retryBtnSignIn);
        retryBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(SignInActivity.this) || Common.isWifiAvailable(SignInActivity.this)) {
                    splashScreen.setVisibility(View.VISIBLE);
                    mainView.setVisibility(View.GONE);
                    noConnectionViewSignIn.setVisibility(View.GONE);
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        Task<Void> userTask = user.reload();
                        userTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                    if (user != null) {
                                boolean isEmailVerified = user.isEmailVerified();
                                if (isEmailVerified) {
                                    Intent intent = new Intent(SignInActivity.this, NavDrawerActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(SignInActivity.this, VerifyActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
//                    }
                            }
                        });
                    } else {
                        splashScreen.setVisibility(View.GONE);
                        mainView.setVisibility(View.VISIBLE);
                    }
                } else {
                    mainView.setVisibility(View.GONE);
                    noConnectionViewSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignInActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn() {
        logIn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if user is already logged in then HomeFragment will open instead of SignInActivity

        if (Common.isNetworkAvailable(SignInActivity.this) || Common.isWifiAvailable(SignInActivity.this)) {
            splashScreen.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            noConnectionViewSignIn.setVisibility(View.GONE);
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Task<Void> userTask = user.reload();
                userTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                    if (user != null) {
                        boolean isEmailVerified = user.isEmailVerified();
                        if (isEmailVerified) {
                            Intent intent = new Intent(SignInActivity.this, NavDrawerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SignInActivity.this, VerifyActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
//                    }
                    }
                });
            } else {
                splashScreen.setVisibility(View.GONE);
                mainView.setVisibility(View.VISIBLE);
            }
        } else {
            mainView.setVisibility(View.GONE);
            noConnectionViewSignIn.setVisibility(View.VISIBLE);
        }

//        if (user != null) {
//            boolean isEmailVerified = user.isEmailVerified();
//            if (isEmailVerified) {
//                Intent intent = new Intent(SignInActivity.this, NavDrawerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            } else {
//                Intent intent = new Intent(SignInActivity.this, VerifyActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        }

    }


    private void logIn() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password is 6");
            editTextPassword.requestFocus();
            return;
        }

        signInButton.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signInButton.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {

//                    mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
//                        @Override
//                        public void onSuccess(GetTokenResult getTokenResult) {
//                            String token_id = getTokenResult.getToken();
//                            String current_id = mAuth.getCurrentUser().getUid();
//
//                            Map <String, Object> tokenMap = new HashMap<>();
//                            tokenMap.put("device_token", token_id);
//
//                            userRef.child(current_id).child("device_token").setValue(token_id).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    logIn();
//                                }
//                            });
//                        }
//                    });


//                    String currentUserID = mAuth.getCurrentUser().getUid();
//                    user = mAuth.getCurrentUser();
//                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
//
//                    userRef.child(currentUserID).child("device_token")
//                            .setValue(deviceToken)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//
//                                        logIn();
//                                    }
//                                }
//                            });
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//                    if (user != null) {
                    Task<Void> userTask = user.reload();
                    userTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            boolean isEmailVerified = user.isEmailVerified();
                            if (isEmailVerified) {
                                Intent intent = new Intent(SignInActivity.this, NavDrawerActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                editTextEmail.setError("Your email is not verified.");
                                editTextEmail.requestFocus();
                                pls_verify_email.setVisibility(View.VISIBLE);
//                                    resend_verify_email.setVisibility(View.VISIBLE);
                                signInButton.setVisibility(View.VISIBLE);
                                FirebaseAuth.getInstance().signOut();


                            }

                            String currentUserID = mAuth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                            userRef.child(currentUserID).child("device_token")
                                    .setValue(deviceToken)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                        }



                    });
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}