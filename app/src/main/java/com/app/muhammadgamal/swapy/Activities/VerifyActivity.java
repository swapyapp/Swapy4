package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout verifySwipeRefresh;
    private TextView verifyTryDiffEmail, verifyResendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //handle the SwipeRefreshLayout
        verifySwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.verifySwipeRefresh);
        verifySwipeRefresh.setOnRefreshListener(this);
        verifySwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        verifyTryDiffEmail = (TextView) findViewById(R.id.verifyTryDiffEmail);
        verifyTryDiffEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(VerifyActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        verifyResendEmail = (TextView) findViewById(R.id.verifyResendEmail);
        verifyResendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyActivity.this, "Verification email sent", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private void isUserVerified() {

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            if (user.isEmailVerified()) {
//                Intent intent = new Intent(VerifyActivity.this, NavDrawerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        }

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Task<Void> userTask = user.reload();
            userTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    boolean isEmailVerified = user.isEmailVerified();
                    if (isEmailVerified) {
                        Intent intent = new Intent(VerifyActivity.this, NavDrawerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        isUserVerified();
    }

    @Override
    public void onRefresh() {
        isUserVerified();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verifySwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
