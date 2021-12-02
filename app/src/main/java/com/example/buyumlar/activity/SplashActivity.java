package com.example.buyumlar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.buyumlar.MainActivity;
import com.example.buyumlar.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    MaterialButton login,registratsion;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_btn);
        registratsion = findViewById(R.id.registratsion_btn);

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            Toast.makeText(this, "Please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }



    }

    public void SplashUp(View view) {
        startActivity(new Intent(this, RegistratsiaActivity.class));
    }

    public void SplashIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));

    }
}