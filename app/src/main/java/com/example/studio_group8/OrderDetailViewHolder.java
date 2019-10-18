package com.example.studio_group8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderDetailViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, qorderid, productQuantity, producttotal;
    public ImageView productImage;
    public ItemClickListener itemClickListener;
    public ImageView remove;

    public OrderDetailViewHolder(@NonNull View itemView) {
        super(itemView);


        productName = itemView.findViewById(R.id.cart_product_name);
        qorderid = itemView.findViewById(R.id.product_desc);
        productQuantity = itemView.findViewById(R.id.product_cart_quantity);
        productImage = itemView.findViewById(R.id.transaction_image);
        producttotal = itemView.findViewById(R.id.cart_product_price);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);





    }

    public void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}

