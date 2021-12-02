package com.example.buyumlar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.buyumlar.R;
import com.example.buyumlar.model.NewProductsModel;
import com.example.buyumlar.model.PopularProductModel;
import com.example.buyumlar.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    TextView rating,description,price,name,number;
    ImageView imageView,addItems,removeItems;
    MaterialButton addCart,buyNow;
    Toolbar toolbar;

    int totalQuentity = 1;
    int totalPrice = 0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    //New Products
    NewProductsModel newProductsModel = null;
    //Popular Product
    PopularProductModel popularProductModel = null;
    //Show All
    ShowAllModel showAllModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final Object obj = getIntent().getSerializableExtra("detaile");
        if (obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof PopularProductModel){
            popularProductModel = (PopularProductModel)obj;
        }else if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }


        //New Products
        if (newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(imageView);
            name.setText(newProductsModel.getName());
            description.setText(newProductsModel.getDescription());
            rating.setText(newProductsModel.getRating());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() + totalQuentity;
        }
        //Populiar Product
        if (popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(imageView);
            name.setText(popularProductModel.getName());
            description.setText(popularProductModel.getDescription());
            rating.setText(popularProductModel.getRating());
            price.setText(String.valueOf(popularProductModel.getPrice()));

            totalPrice = popularProductModel.getPrice() + totalQuentity;
        }
        //Show All
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(imageView);
            name.setText(showAllModel.getName());
            description.setText(showAllModel.getDescription());
            rating.setText(showAllModel.getRating());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() + totalQuentity;
        }

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuentity < 10){
                    totalQuentity++;
                    number.setText(String.valueOf(totalQuentity));

                    if (newProductsModel != null){
                        totalPrice = newProductsModel.getPrice() + totalQuentity;
                    }
                    if (popularProductModel != null){
                        totalPrice = popularProductModel.getPrice() + totalQuentity;
                    }
                    if (showAllModel != null){
                        totalPrice = showAllModel.getPrice() + totalQuentity;
                    }
                }
            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuentity > 1){
                    totalQuentity--;
                    number.setText(String.valueOf(totalQuentity));
                }
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(DetailActivity.this,AddressActivity.class);

               if (newProductsModel != null){
                   intent.putExtra("item",newProductsModel);
               }
               if (popularProductModel != null){
                   intent.putExtra("item",popularProductModel);
               }
               if (showAllModel != null){
                   intent.putExtra("item",showAllModel);
               }

               startActivity(intent);
            }
        });

    }

    private void addtoCart() {

        String saveCurentTime,saveCurrentData;

        Calendar calForData = Calendar.getInstance();

        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentData = currentData.format(calForData.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime = currentTime.format(calForData.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurentTime);
        cartMap.put("currentData",saveCurrentData);
        cartMap.put("totalQuantity",number.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void initViews() {

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        rating = findViewById(R.id.rating_detaile);
        description = findViewById(R.id.detail_discription);
        price = findViewById(R.id.detail_price);
        name = findViewById(R.id.detaile_name);
        number = findViewById(R.id.number);
        imageView = findViewById(R.id.imageView);
        addItems = findViewById(R.id.detaile_add);
        removeItems = findViewById(R.id.detaile_remove);
        addCart = findViewById(R.id.detaile_add_cart);
        buyNow = findViewById(R.id.detaile_buy_now);
        toolbar = findViewById(R.id.detaile_tolbar);

    }
}