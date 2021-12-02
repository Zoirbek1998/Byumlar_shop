package com.example.buyumlar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.buyumlar.R;
import com.example.buyumlar.adapter.MyCartAdapter;
import com.example.buyumlar.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    MaterialButton buyNow;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> myCartModelList;
    TextView overTotalAmount;
    ProgressBar progressBar;
   int overAllTotalAmmount;
   Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar_my_cart);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.my_cart_toolbar);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buyNow = findViewById(R.id.buy_now);

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);
        overTotalAmount = findViewById(R.id.overTotalAmount);


        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(this,myCartModelList);
        recyclerView.setAdapter(myCartAdapter);


        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()){

                        String documebtId = snapshot.getId();

                        MyCartModel myCartModel = snapshot.toObject(MyCartModel.class);

                        myCartModel.setDocumentId(documebtId);

                        myCartModelList.add(myCartModel);
                        myCartAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    calculateTotalAmount(myCartModelList);

                }

            }
        });


    }

    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {

        int totalAmount = 0;
        for (MyCartModel myCartModel : myCartModelList){
            totalAmount += myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount :"+totalAmount+"$");
    }


}