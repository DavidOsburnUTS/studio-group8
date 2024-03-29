package com.example.studio_group8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productPrice, productQuantity, productDesc;
    public ImageView productImage;
    public ItemClickListener listener;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);



        productImage = (ImageView) itemView.findViewById(R.id.transaction_image);
        productName = (TextView) itemView.findViewById(R.id.product_text);
        productPrice = (TextView) itemView.findViewById(R.id.textView_order_id_label);
        productQuantity = (TextView) itemView.findViewById(R.id.textView_order_quantity_label);
        productDesc = (TextView) itemView.findViewById(R.id.product_desc);

    }

    public void setItemClickListener (ItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
