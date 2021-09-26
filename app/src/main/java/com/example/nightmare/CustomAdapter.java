package com.example.adddevices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList mobile_id, mobile_brand, mobile_model, mobile_condition, mobile_price, mobile_description;

    CustomAdapter( Activity activity, Context context, ArrayList mobile_id, ArrayList mobile_brand, ArrayList mobile_model, ArrayList mobile_condition, ArrayList mobile_price,
                   ArrayList mobile_description) {
           this.activity = activity;
           this.context = context;
           this.mobile_id = mobile_id;
           this.mobile_brand = mobile_brand;
           this.mobile_model = mobile_model;
           this.mobile_condition = mobile_condition;
           this.mobile_price = mobile_price;
           this.mobile_description = mobile_description;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mobile_id_txt.setText(String.valueOf(mobile_id.get(position)));
        holder.mobile_brand_txt.setText(String.valueOf(mobile_brand.get(position)));
        holder.mobile_model_txt.setText(String.valueOf(mobile_model.get(position)));
        holder.mobile_condition_txt.setText(String.valueOf(mobile_condition.get(position)));
        holder.mobile_price_txt.setText(String.valueOf(mobile_price.get(position)));
        holder.mobile_description_txt.setText(String.valueOf(mobile_description.get(position)));
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra( "id", String.valueOf(mobile_id.get(position)));
            intent.putExtra( "brand", String.valueOf(mobile_brand.get(position)));
            intent.putExtra( "model", String.valueOf(mobile_model.get(position)));
            intent.putExtra( "condition", String.valueOf(mobile_condition.get(position)));
            intent.putExtra( "price", String.valueOf(mobile_price.get(position)));
            intent.putExtra( "description", String.valueOf(mobile_description.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }
    @Override
    public int getItemCount() {
        return mobile_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mobile_id_txt, mobile_brand_txt, mobile_model_txt, mobile_condition_txt, mobile_price_txt, mobile_description_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile_id_txt = itemView.findViewById(R.id.mobile_id_txt);
            mobile_brand_txt = itemView.findViewById(R.id.mobile_brand_txt);
            mobile_model_txt = itemView.findViewById(R.id.mobile_model_txt);
            mobile_condition_txt = itemView.findViewById(R.id.mobile_condition_txt);
            mobile_price_txt = itemView.findViewById(R.id.mobile_price_txt);
            mobile_description_txt = itemView.findViewById(R.id.mobile_description_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
