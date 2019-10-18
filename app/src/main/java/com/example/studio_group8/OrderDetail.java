package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OrderDetail extends AppCompatActivity {
    private DatabaseReference mDatabase;

    StorageReference mStorageRefrence;
    String orderid;

    RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_hist_detail);

        orderid = getIntent().getStringExtra("orderid");
        FirebaseStorage storage  = FirebaseStorage.getInstance();


        mStorageRefrence = storage.getReference();




        mRecyclerView = findViewById(R.id.orderDetaillistrecycler);



        mDatabase = FirebaseDatabase.getInstance().getReference().child("OrderProducts");

       mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void cancelHome(View view) {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //     String key = mDatabase.child("posts").push().getKey();


        FirebaseRecyclerOptions<OrderProduct> options =
                new FirebaseRecyclerOptions.Builder<OrderProduct>()
                        .setQuery(mDatabase.child(orderid), OrderProduct.class)
                        .build();

        FirebaseRecyclerAdapter<OrderProduct, OrderDetailViewHolder> adapter
                = new FirebaseRecyclerAdapter<OrderProduct, OrderDetailViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int i, @NonNull OrderProduct model) {


                Double quantity = new Double(model.getQuantity());
                holder.productName.setText(model.getName());
              //  holder.qorderid.setText("Orderid"+orderid);
                String number = String.valueOf(model.getQuantity());
                holder.productQuantity.setText(""+quantity);



                Double value = new Double(model.getprice());
                double total = value*quantity;

                holder.producttotal.setText(""+value);
//
//                GlideApp.
//                        with(getApplicationContext())
//                        .load(model.getImage())
//                        .into(productImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetail.this, ProductDetails.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("desc", model.getDesc());
                        intent.putExtra("productid", model.getproductid());
                        startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actitivity_list_orderdetail, parent, false);
                OrderDetailViewHolder holder = new OrderDetailViewHolder(view);
                return holder;


            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
