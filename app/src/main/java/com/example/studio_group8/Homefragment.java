package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;



public class Homefragment extends Fragment implements View.OnClickListener {




    RecyclerView mRecyclerView;
    StorageReference mStorageRefrence;
    private RecyclerView.LayoutManager layoutManager;


    private FirebaseDatabase database;

    private DatabaseReference mDatabase;
    Query query;


    private Uri image_uri;



    FloatingActionButton fabmain, fabone, fabtwo;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();


    public ImageView prodImage;
    private static String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    private Context context;





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_seller,container, false);
        fabmain = (FloatingActionButton) v.findViewById(R.id.fabmain);
        fabone = (FloatingActionButton) v.findViewById(R.id.fab1);
        FirebaseStorage storage  = FirebaseStorage.getInstance();


        mStorageRefrence = storage.getReference();

        mRecyclerView = v.findViewById(R.id.recyclerproducts);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        fabone.setAlpha(0f);

        fabone.setTranslationY(translationY);

        fabmain.setOnClickListener(this);

        return v;


    }





    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(mDatabase, Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull Product model) {


                        holder.productName.setText(model.getName());
                        holder.productDesc.setText(model.getDesc());
                        holder.productQuantity.setText(String.valueOf(model.getQuantity()));

                        holder.productPrice.setText( "$" + String.format("%.2f",model.getprice()));
                        GlideApp.
                                with(getActivity())
                                .load(model.getImage())
                                .into(holder.productImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), ProductDetails.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("desc", model.getDesc());
                                intent.putExtra("productid", model.getproductid());
                                startActivity(intent);
                            }
                        });



//                        Picasso.get().load(model.getImage()).into(holder.productImage);
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_product, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }










    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabmain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabone.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabmain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabone.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }





    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fabmain:
                Log.i(TAG, "onClick: fab main");
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
        }
    }




}


