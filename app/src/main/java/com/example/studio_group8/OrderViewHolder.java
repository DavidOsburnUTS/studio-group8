package com.example.studio_group8;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mShow;
    public ItemClickListener itemClickListener;




    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        mShow = itemView.findViewById(R.id.show);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);





    }

    public void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
