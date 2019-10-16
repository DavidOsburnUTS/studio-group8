package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Recommendation extends AppCompatActivity {
    private Context context;

    RecyclerView mRecyclerView;
    StorageReference mStorageRefrence;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
//        search =  (SearchView) view.findViewById(R.id.search_field);
//        mSearchField = (EditText) findViewById(R.id.search_field);
//        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
//        mResultList = (RecyclerView) findViewById(R.id.searchList);

//        mProductDatabase = FirebaseDatabase.getInstance().getReference("product");


        mRecyclerView = (RecyclerView) findViewById(R.id.recommendationrecycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");

        FirebaseStorage storage  = FirebaseStorage.getInstance();

        mStorageRefrence = storage.getReference();


    }

    public void back( View view) {
        finish();
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

                        holder.productPrice.setText( "$ " + String.format("%.2f",model.getprice()));
                        GlideApp.
                                with(Recommendation.this)
                                .load(model.getImage())
                                .into(holder.productImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
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




}



