package com.example.buyumlar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyumlar.R;
import com.example.buyumlar.activity.AddressActivity;
import com.example.buyumlar.model.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.HolderView> {

    Context context;
    List<AddressModel> list;
    SelectedAddress selectedAddress;
    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List<AddressModel> list, SelectedAddress selectedAddress) {
        this.context = context;
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(context).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {

        holder.address.setText(list.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel address : list){
                    address.setSelected(false);
                }
                list.get(position).setSelected(true);

                if (selectedRadioBtn != null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAddress(list.get(position).getUserAddress());

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.addres_add);
            radioButton = itemView.findViewById(R.id.address_sellect);
        }
    }

    public interface SelectedAddress{
        void setAddress(String address);
    }
}
