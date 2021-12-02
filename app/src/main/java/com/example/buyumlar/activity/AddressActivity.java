package com.example.buyumlar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyumlar.R;
import com.example.buyumlar.adapter.AddressAdapter;
import com.example.buyumlar.model.AddressModel;
import com.example.buyumlar.model.NewProductsModel;
import com.example.buyumlar.model.PopularProductModel;
import com.example.buyumlar.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

    MaterialButton addAddress, peymentBtn;
    RecyclerView recyclerView;
    List<AddressModel> list;
    AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Toolbar toolbar;
    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initVars();

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //get Data from detailed activity
        Object obj = getIntent().getSerializableExtra("item");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),list,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        AddressModel model = documentSnapshot.toObject(AddressModel.class);
                        list.add(model);
                        addressAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        peymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = 0.0;
              if (obj instanceof NewProductsModel){
                  NewProductsModel newProductsModel = (NewProductsModel)obj;
                  amount = newProductsModel.getPrice();
              }
                if (obj instanceof PopularProductModel){
                    PopularProductModel popularProductModel = (PopularProductModel)obj;
                    amount = popularProductModel.getPrice();
                }
                if (obj instanceof ShowAllModel){
                    ShowAllModel showAllModel = (ShowAllModel)obj;
                    amount = showAllModel.getPrice();
                }

                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    private void initVars() {

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        addAddress = findViewById(R.id.add_address);
        toolbar = findViewById(R.id.address_toolbar);
        recyclerView = findViewById(R.id.address_recycler);
        peymentBtn = findViewById(R.id.continue_to_payment);

    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}