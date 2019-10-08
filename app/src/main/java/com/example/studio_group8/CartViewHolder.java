package com.example.studio_group8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productCartName, productCartPrice, productCartQuantity;
    public ImageView productCartImage;
    public ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        productCartName = itemView.findViewById(R.id.cart_product_name);
        productCartPrice = itemView.findViewById(R.id.cart_product_price);
        productCartQuantity = itemView.findViewById(R.id.product_cart_quantity);
        productCartImage = itemView.findViewById(R.id.cart_product_image);

    }

    @Override
    public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);


    }

    public void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
