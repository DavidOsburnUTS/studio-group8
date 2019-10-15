package com.example.studio_group8;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OrderHisStepTwo extends AppCompatActivity {

    private DatabaseReference mDatabase;

    StorageReference mStorageRefrence;

    RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    public String orderdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

orderdate = getIntent().getStringExtra("orderdate");


       // getOrderid(orderdate);

        FirebaseStorage storage  = FirebaseStorage.getInstance();


        mStorageRefrence = storage.getReference();




        mRecyclerView = findViewById(R.id.recyclerorderlist);



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Order");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));





    }

    public void cancelHome(View view) {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();



        FirebaseRecyclerOptions<Orderid> options =
                new FirebaseRecyclerOptions.Builder<Orderid>()
                        .setQuery(mDatabase.child(currentuser).child(orderdate), Orderid.class)
                        .build();

        FirebaseRecyclerAdapter<Orderid, OrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<Orderid, OrderViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int i, @NonNull Orderid model) {


                holder.mShow.setText((model.getid()));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderHisStepTwo.this, OrderHisStepTwo.class);
                        intent.putExtra("orderid", model.getid());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_orderstep, parent, false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;


            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

//    private void getOrderid(String orderdate) {
//        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Order");
//        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        FirebaseRecyclerOptions<Orderdate> options =
//                new FirebaseRecyclerOptions.Builder<Orderdate>()
//                        .setQuery(orderRef.child(currentuser).child(orderdate), Orderid.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Orderdate, OrderViewHolder> adapter
//                = new FirebaseRecyclerAdapter<Orderdate, OrderViewHolder>(options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int i, @NonNull Orderdate model) {
//
//
//                holder.mShow.setText((model.getdate()));
//
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(OrderHistStepOne.this, OrderHisStepTwo.class);
//                        intent.putExtra("orderdate", model.getdate());
//                        startActivity(intent);
//                    }
//                });
//
//
//            }
//
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_orderstep, parent, false);
//                OrderViewHolder holder = new OrderViewHolder(view);
//                return holder;
//
//
//            }
//        };
//        mRecyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }


}

