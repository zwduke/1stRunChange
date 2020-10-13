package com.momo.a1strun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    TextInputLayout regpassword, regemail;
    Button regBtn;
    Button regToLoginBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        //assigning variables from activity
        regemail = findViewById(R.id.email);
        regpassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.regBtn);
        regToLoginBtn = findViewById(R.id.regToLoginBtn);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Main_screenActivity.class));
            finish();
        }

        regBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = regemail.getEditText().getText().toString();
                String password = regpassword.getEditText().getText().toString();
                if (TextUtils.isEmpty(email)) {
                    regemail.setError("Email is Required.");
                    regemail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    regpassword.setError("Email is Required.");
                    regpassword.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Main_screenActivity.class));
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });//Register Button method end
        regToLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openLogin();
            }
        });
    }
    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity2.class);
        startActivity(intent);
    }
}