package com.example.buyumlar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyumlar.R;
import com.example.buyumlar.model.MyCartModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> myCartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int totalAmmount = 0;

    public MyCartAdapter(Context context, List<MyCartModel> myCartModelList) {
        this.context = context;
        this.myCartModelList = myCartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.productName.setText(myCartModelList.get(position).getProductName());
        holder.productPrice.setText(myCartModelList.get(position).getProductPrice());
        holder.currentData.setText(myCartModelList.get(position).getCurrentData());
        holder.currentTime.setText(myCartModelList.get(position).getCurrentTime());
        holder.totalPrice.setText(String.valueOf(myCartModelList.get(position).getTotalPrice()));
        holder.totalQuantity.setText(myCartModelList.get(position).getTotalQuantity());






        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(myCartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    myCartModelList.remove(myCartModelList.get(position));
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,myCartModelList.size());
//                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice,currentData
                ,currentTime,totalPrice,totalQuantity;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            currentData = itemView.findViewById(R.id.current_data);
            currentTime = itemView.findViewById(R.id.current_time);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            delete = itemView.findViewById(R.id.delet_item);
        }
    }
}
