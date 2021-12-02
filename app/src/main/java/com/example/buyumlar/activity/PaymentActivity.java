package com.example.buyumlar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyumlar.R;
import com.google.android.material.button.MaterialButton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    double amount = 0.0;
    Toolbar toolbar;
    TextView subTotal,discount,shipping,total;
    MaterialButton btnChesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initView();
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        amount = getIntent().getDoubleExtra("amount",0.0);

        subTotal.setText(amount+"$");

        btnChesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod();
            }
        });

    }

    private void paymentMethod() {

        Checkout checkout = new Checkout();

        final Activity activity = PaymentActivity.this;

        try {
            JSONObject options = new JSONObject();
            // Set Compane Name
            options.put("name", "My Buyumlar App");
            //Ref no
            options.put("description", "Reference No. #123456");
            // Image to be display
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
            //from response of step 3.
            options.put("currency", "USD");

            options.put("prefill.contact","9988776655");
            amount = amount*100;
            options.put("amount", amount);
            JSONObject retryObj = new JSONObject();
            //email
            options.put("email", "mzoirbek98gmail.com");
            //contact
            options.put("contact","+998916119878");

            options.put("prefill",retryObj);

            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);


            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
        }




    private void initView() {

        toolbar = findViewById(R.id.payment_toolbar);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shipping);
        total = findViewById(R.id.total);
        btnChesk = findViewById(R.id.chesk_out);

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Cancel", Toast.LENGTH_SHORT).show();
    }
}