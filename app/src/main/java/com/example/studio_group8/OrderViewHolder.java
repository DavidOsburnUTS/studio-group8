package com.example.studio_group8;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mShow, mdate, mcardno, mmobileno, mpost, mamount;
    public ItemClickListener itemClickListener;




    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        mShow = itemView.findViewById(R.id.show);
        mdate = itemView.findViewById(R.id.date);
        mcardno = itemView.findViewById(R.id.cardnumber);
        mmobileno = itemView.findViewById(R.id.mobilenumber);
        mpost = itemView.findViewById(R.id.postcode);
        mamount = itemView.findViewById(R.id.complete_order_total);






    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);





    }

    public void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
