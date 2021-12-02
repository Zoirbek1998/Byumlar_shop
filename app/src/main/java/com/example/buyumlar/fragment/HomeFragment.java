package com.example.buyumlar.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.buyumlar.R;
import com.example.buyumlar.activity.DetailActivity;
import com.example.buyumlar.activity.ShowAllActivity;
import com.example.buyumlar.adapter.CategoryAdapter;
import com.example.buyumlar.adapter.NewProductAdapter;
import com.example.buyumlar.adapter.PopularProductAdapter;
import com.example.buyumlar.model.CategoryModel;
import com.example.buyumlar.model.NewProductsModel;
import com.example.buyumlar.model.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView categoryRecycler,newProductRecycler,populyarRecycler;
    TextView catShowAll,populyarShowAll,newProductShowAll;

    // Category recycler
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Products
    NewProductAdapter newProductAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Praduct
    PopularProductAdapter productAdapter;
    List<PopularProductModel> productModelList;

    ProgressDialog progressDialog;
    LinearLayout linearLayout;

    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        catShowAll = root.findViewById(R.id.category_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);
        populyarShowAll = root.findViewById(R.id.popular_see_all);

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount On Perfumes Item", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount On Shoes Item", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"Discount On Hours Item", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner4,"Discount On Clothes Item", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome To My Buyumlar App");
        progressDialog.setMessage("please wait....");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        //Category
        categoryRecycler = root.findViewById(R.id.cat_recycler);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        categoryRecycler.setAdapter(categoryAdapter);

        // New Products
        newProductRecycler = root.findViewById(R.id.new_product_rec);
        newProductRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(getContext(),newProductsModelList);
        newProductRecycler.setAdapter(newProductAdapter);

        //Popular Praduct
        populyarRecycler = root.findViewById(R.id.popular_rec);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        populyarRecycler.setLayoutManager(manager);
        productModelList = new ArrayList<>();
        productAdapter = new PopularProductAdapter(getContext(),productModelList);
        populyarRecycler.setAdapter(productAdapter);


        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModel model = document.toObject(CategoryModel.class);
                                categoryModelList.add(model);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            }
                        } else {

                        }
                    }
                });

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewProductsModel model = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(model);
                                newProductAdapter.notifyDataSetChanged();

                            }
                        } else {

                        }
                    }
                });

        db.collection("Popular Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularProductModel model = document.toObject(PopularProductModel.class);
                                productModelList.add(model);
                                productAdapter.notifyDataSetChanged();

                            }
                        } else {

                        }
                    }
                });


        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
               startActivity(intent);
            }
        });
        populyarShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ShowAllActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}