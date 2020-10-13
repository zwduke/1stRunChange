package com.momo.a1strun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button regbut;
    private Button regbut2;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Firebase connected", Toast.LENGTH_SHORT).show();
        regbut = findViewById(R.id.btn);
        regbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRegistration();
            }

        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        regbut2 = findViewById(R.id.btn2);
        regbut2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mFirebaseAuth.getCurrentUser() ==null)
                logout();
            }
        });

    }
    public void openRegistration(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    public void logout(){
        mFirebaseAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}