package com.momo.a1strun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity2 extends AppCompatActivity {
    TextInputLayout regpassword, regemail;
    Button regLoginbtn;
    Button regForgBtn;
    FirebaseAuth auth;
    private ProgressDialog mload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mload = new ProgressDialog(LoginActivity2.this);
        regpassword = findViewById(R.id.password);
        regemail = findViewById(R.id.email1);
        regLoginbtn = findViewById(R.id.regLogin);
        regForgBtn = findViewById(R.id.regForg);
        auth = FirebaseAuth.getInstance();

        regLoginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //rootNodeL = FirebaseDatabase.getInstance();
                String email = regemail.getEditText().getText().toString();
                String pass = regpassword.getEditText().getText().toString();
                if(TextUtils.isEmpty(email)){
                    regemail.setError("Please enter your Username");
                    regemail.requestFocus();
                }
                if(TextUtils.isEmpty(pass)){
                    regpassword.setError("Please enter your password");
                    regpassword.requestFocus();
                }
                mload.setTitle("Login");
                mload.setMessage("Please wait");
                mload.setCanceledOnTouchOutside(false);
                mload.show();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity2.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Main_screenActivity.class));
                            mload.dismiss();
                        }
                        else{
                            Toast.makeText(LoginActivity2.this, "Error!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });//Login Button method end
        regForgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setMessage("Enter your email for Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity2.this, "Reset Sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity2.this, "Error ! Reset Link is Not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        passwordResetDialog.create().show();
                    }
                });
            }
        });
    }
}