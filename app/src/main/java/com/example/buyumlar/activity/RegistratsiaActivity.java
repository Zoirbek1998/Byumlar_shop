package com.example.buyumlar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.buyumlar.R;
import com.example.buyumlar.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class RegistratsiaActivity extends AppCompatActivity {

    MaterialButton signUp;
    TextInputEditText name, email, password;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registratsia);

        initView();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatUser();
            }
        });

//        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
//        boolean isFristTime = sharedPreferences.getBoolean("fristTime",true);

//        if (isFristTime) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("fristTime",false);
//            editor.commit();
//
//            Intent intent = new Intent(RegistratsiaActivity.this,onBoardingActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    private void creatUser() {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password Length must be greater then 6 lettet!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creat User

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            UserModel userModel = new UserModel(userName,userEmail,userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);


                            Toast.makeText(RegistratsiaActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(RegistratsiaActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        signUp = findViewById(R.id.signUp);
        name = findViewById(R.id.reg_Name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pass);

    }

    public void SignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}