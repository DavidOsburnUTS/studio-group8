package com.example.studio_group8;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CameraCategory extends AppCompatActivity {



    RecyclerView mRecyclerView;
    StorageReference mStorageRefrence;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;

    private DatabaseReference mDatabase;
    Query query;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        FirebaseStorage storage  = FirebaseStorage.getInstance();

        mStorageRefrence = storage.getReference();

        mRecyclerView = findViewById(R.id.camera_recyclerview);


        query = FirebaseDatabase.getInstance().getReference("Product").orderByChild("category").equalTo("Camera");


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void back( View view) {
        finish();
    }



    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull Product model) {


                        holder.productName.setText(model.getName());
                        holder.productDesc.setText(model.getDesc());
                        holder.productQuantity.setText(String.valueOf(model.getQuantity()));
                        holder.productPrice.setText( "$" + model.getprice());

                        GlideApp.
                                with(CameraCategory.this)
                                .load(model.getImage())
                                .into(holder.productImage);
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