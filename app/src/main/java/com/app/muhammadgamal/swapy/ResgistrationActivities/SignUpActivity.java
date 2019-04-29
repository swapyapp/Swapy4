package com.app.muhammadgamal.swapy.ResgistrationActivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    // 0 => chosen
    // 1 => not chosen
    public static int COMPANY_CHOSEN = 0, BRANCH_CHOSEN = 0, ACCOUNT_CHOSEN = 0, CURRENT_SHIFT_CHOSEN = 0;
    static int TIME_SELECTED = 0; // 0 => AM & 1 => PM
    static final int PReqCode = 1;
    static int REQUESTCODE = 1;
    static int IMG_UPLOADED = 0;
    static int USER_INFO_SAVED = 0;
    TextView signInText;
    Button signUpButton;
    ImageView userImageSignUp;
    Uri pickedImageUri;
    EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName, editTextPhone, editTextLastName;
    String profileImageUrl;
    public static Spinner spinnerCompany,
            spinnerCompanyBranchRaya, spinnerCompanyBranchVodafone, spinnerCompanyBranchOrange,
            spinnerAccountEtisalat, spinnerAccountVodafoneArabic, spinnerAccountVodafoneUK, spinnerAccountArabicAccount;
    ProgressBar progressBarImg;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference deviceTokenRef;
    private String name, email, photoUri, phone;
    private UploadTask uploadTask;
    public static int arrayBranch = R.array.branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Resources res = getResources();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        signInText = (TextView) findViewById(R.id.signInText);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        progressBarImg = (ProgressBar) findViewById(R.id.progressBarImg);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        spinnerCompany = (Spinner) findViewById(R.id.spinnerCompany);
        spinnerCompanyBranchRaya = (Spinner) findViewById(R.id.spinnerCompanyBranchRaya);
        spinnerCompanyBranchVodafone = findViewById(R.id.spinnerCompanyBranchVodafone);
        spinnerCompanyBranchOrange = findViewById(R.id.spinnerCompanyBranchOrange);

        spinnerAccountEtisalat = findViewById(R.id.spinnerAccountEtisalat);
        spinnerAccountVodafoneArabic = findViewById(R.id.spinnerAccountVodafoneArabic);
        spinnerAccountVodafoneUK = findViewById(R.id.spinnerAccountVodafoneUK);
        spinnerAccountArabicAccount = findViewById(R.id.spinnerAccountArabicAccount);

        companySpinner();
        branchSpinnerRaya();
        branchSpinnerVodafone();
        branchSpinnerOrange();
        accountSpinnerEtisalat();
        accountSpinnerVodafoneArabic();
        accountSpinnerVodafoneUK();
        accountSpinnerArabicAccount();

        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        signUpButton = (Button) findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        userImageSignUp = findViewById(R.id.userImageSignUp);
        userImageSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22) {
                    requestPermissionAndOpenGallery();
                } else {
                    openGallery();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PReqCode: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        }
    }


    private void companySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);
        spinnerCompany.setOnItemSelectedListener(new CompanySpinnerLestiner());
    }

    public void branchSpinnerRaya() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_raya, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchRaya.setAdapter(adapter);
        spinnerCompanyBranchRaya.setOnItemSelectedListener(new BranchSpinnerLestiner());
    }
    public void branchSpinnerVodafone() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_vodafone, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchVodafone.setAdapter(adapter);
        spinnerCompanyBranchVodafone.setOnItemSelectedListener(new BranchSpinnerLestiner());
    }
    public void branchSpinnerOrange() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_orange, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchOrange.setAdapter(adapter);
        spinnerCompanyBranchOrange.setOnItemSelectedListener(new BranchSpinnerLestiner());
    }

    private void accountSpinnerEtisalat() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_Etisalat_Emarat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountEtisalat.setAdapter(adapter);
        spinnerAccountEtisalat.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void accountSpinnerVodafoneArabic() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_Vodafone_arabic, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountVodafoneArabic.setAdapter(adapter);
        spinnerAccountVodafoneArabic.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }
    private void accountSpinnerVodafoneUK() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_vodafone_uk, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountVodafoneUK.setAdapter(adapter);
        spinnerAccountVodafoneUK.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }
    private void accountSpinnerArabicAccount() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_arabic_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountArabicAccount.setAdapter(adapter);
        spinnerAccountArabicAccount.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void saveUserInfoToFirebaseDatabase() {
        String AMorPM;
        if (TIME_SELECTED == 0) {
            AMorPM = " AM";
        } else {
            AMorPM = " PM";
        }
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String username = firstName + " " + lastName;
        String phoneNumber = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String userId = mAuth.getCurrentUser().getUid();
        User user;
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        if (profileImageUrl != null) {
            signUpButton.setVisibility(View.GONE);
            user = new User(username, email, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, profileImageUrl, phoneNumber);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification();
                    Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            signUpButton.setVisibility(View.GONE);
            user = new User(username, email, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, null, phoneNumber);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification();
                    Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUESTCODE);
    }

    private void requestPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    SignUpActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null && data.getData() != null) {
            //user choose the image
            //replace the image in the UI
            pickedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImageUri);
                userImageSignUp.setImageBitmap(bitmap);
                uploadProfileImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImageToFirebase() {
        String fileName = UUID.randomUUID().toString();
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + fileName + ".jpg");
        if (pickedImageUri != null) {
            progressBarImg.setVisibility(View.VISIBLE);

            uploadTask = profileImageRef.putFile(pickedImageUri);
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    IMG_UPLOADED = 0;
                    progressBarImg.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    IMG_UPLOADED = 1;
                    progressBarImg.setVisibility(View.GONE);
                    profileImageUrl = profileImageRef.getDownloadUrl().toString();
                    Toast.makeText(SignUpActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                }
            });

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return profileImageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        profileImageUrl = downloadUri.toString();
                        userRef.child("mProfilePhotoURL").setValue(profileImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userImageSignUp.setVisibility(View.VISIBLE);
                                progressBarImg.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUpActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                userImageSignUp.setVisibility(View.VISIBLE);
                                progressBarImg.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUpActivity.this, "Image uploading failed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });



//            profileImageRef.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    IMG_UPLOADED = 1;
//                    progressBarImg.setVisibility(View.GONE);
//                    profileImageUrl = profileImageRef.getDownloadUrl().toString();
//                    Toast.makeText(SignUpActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    IMG_UPLOADED = 0;
//                    progressBarImg.setVisibility(View.GONE);
//                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
        }

    }

    private void signUp() {
        String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        final String phoneNumber = editTextPhone.getText().toString();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("First name is required");
            editTextFirstName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            editTextFirstName.setError("Last name is required");
            editTextFirstName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Valid email  is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!email.endsWith(".com")) {
            editTextEmail.setError("Company email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }
        if (phoneNumber.length() != 11) {
            editTextPhone.setError("Please enter a valid phone number");
            editTextPhone.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password is 6");
            editTextPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Password Not matching");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (COMPANY_CHOSEN == 1) {
            Toast.makeText(this, "choose a Branch", Toast.LENGTH_SHORT).show();
            return;
        }
        if (BRANCH_CHOSEN == 1) {
            Toast.makeText(this, "choose a branch", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ACCOUNT_CHOSEN == 1) {
            Toast.makeText(this, "choose an account", Toast.LENGTH_SHORT).show();
            return;
        }


        if (pickedImageUri != null) {
            if (IMG_UPLOADED == 1) {
                signUpButton.setVisibility(View.GONE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                signUpButton.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    saveUserInfoToFirebaseDatabase();
                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    String currentUserID = mAuth.getCurrentUser().getUid();
                                    deviceTokenRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                    deviceTokenRef.child("device_token").setValue(deviceToken);
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(SignUpActivity.this, "Please wait until Image is uploaded", Toast.LENGTH_LONG).show();
            }
        } else {
            signUpButton.setVisibility(View.GONE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            signUpButton.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                saveUserInfoToFirebaseDatabase();

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                deviceTokenRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                deviceTokenRef.child("device_token").setValue(deviceToken);
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }


    }


}
