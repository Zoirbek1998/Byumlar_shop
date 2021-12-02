package com.example.buyumlar.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.buyumlar.R;
import com.example.buyumlar.adapter.ShowAllAdapter;
import com.example.buyumlar.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModel;
    Toolbar toolbar;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.show_all_toolbar);
        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.show_all_rec);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        showAllModel = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModel);
        recyclerView.setAdapter(showAllAdapter);



        if (type != null && type.isEmpty()) {
            firestore.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });


        }

        if (type != null && type.equalsIgnoreCase("wristwatch") ){
            firestore.collection("ShowAll").whereEqualTo("type","wristwatch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("man") ){
            firestore.collection("ShowAll").whereEqualTo("type","man")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("perfume") ){
            firestore.collection("ShowAll").whereEqualTo("type","perfume")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("woman") ){
            firestore.collection("ShowAll").whereEqualTo("type","woman")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("kids") ){
            firestore.collection("ShowAll").whereEqualTo("type","kids")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                    ShowAllModel model = snapshot.toObject(ShowAllModel.class);
                                    showAllModel.add(model);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }


    }
}