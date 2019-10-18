package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

public class TvCategory extends AppCompatActivity {
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
        setContentView(R.layout.activity_tv);


        FirebaseStorage storage  = FirebaseStorage.getInstance();

        mStorageRefrence = storage.getReference();

        mRecyclerView = findViewById(R.id.tv_recyclerview);


        query = FirebaseDatabase.getInstance().getReference("Product").orderByChild("category").equalTo("TV");


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                        holder.productPrice.setText( "$" + String.format("%.2f",model.getprice()));

                        GlideApp.
                                with(TvCategory.this)
                                .load(model.getImage())
                                .into(holder.productImage);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TvCategory.this, ProductDetails.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("pid", model.getPid());
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



    public void cancelHome(View view) {
        finish();
    }

    public void back( View view) {
        finish();
    }


}