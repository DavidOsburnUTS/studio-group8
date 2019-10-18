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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

public class Recommendation extends AppCompatActivity {
    private Context context;

    RecyclerView mRecyclerView;
    StorageReference mStorageRefrence;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    public int startIndex;
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

    public void cancelHome(View view) {
        finish();
    }

    public void back( View view) {
        finish();
    }











    @Override
    public void onStart() {
        super.onStart();




            startIndex = (int) (Math.random() * 12 + 1);
            System.out.println(startIndex);
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(mDatabase.orderByChild("index").startAt(startIndex).endAt(startIndex+7).limitToFirst(3),
//                                orderByChild("category").equalTo("Console").limitToFirst(3),
                                Product.class)
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
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recommendations_list, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }




}



