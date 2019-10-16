package com.example.studio_group8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView accountName, accountType, accountEmail;
    public ImageView accountImage;
    public ItemClickListener listener;
    public ImageView remove;

    private FirebaseAuth mAuth;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);



        accountImage = (ImageView) itemView.findViewById(R.id.admin_acc_image);
        accountName = (TextView) itemView.findViewById(R.id.admin_acc_name);
        accountType = (TextView) itemView.findViewById(R.id.admin_acc_type);
        accountEmail = (TextView) itemView.findViewById(R.id.admin_acc_email);


        mAuth = FirebaseAuth.getInstance();

        final String id = mAuth.getCurrentUser().getUid();
//        remove = itemView.findViewById(R.id.admin_remove_account);
    }




    public void setItemClickListener (ItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }



}
