package com.example.buyumlar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.buyumlar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    TextInputEditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    MaterialButton addAddress;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        //Toolbar
        setSupportActionBar(toolbar);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViews();


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userNumber = phoneNumber.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()){
                    final_address += userName+", ";
                }
                if (!userAddress.isEmpty()){
                    final_address += userAddress+", ";
                }
                if (!userCity.isEmpty()){
                    final_address += userCity+", ";
                }
                if (!userCode.isEmpty()){
                    final_address += userCode+", ";
                }
                if (!userNumber.isEmpty()){
                    final_address += userNumber+". ";
                }

                if (!userName.isEmpty() && !userAddress.isEmpty() && !userCity.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()){

                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                startActivity( new Intent(AddAddressActivity.this,DetailActivity.class));
                                finish();
                            }

                        }
                    });
                }else {
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.address_name);
        city = findViewById(R.id.address_city);
        postalCode = findViewById(R.id.postal_code);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address_lane);
        toolbar = findViewById(R.id.add_address_toolbar);
        name = findViewById(R.id.address_name);
        addAddress = findViewById(R.id.address);
    }
}