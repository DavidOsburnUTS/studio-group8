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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OrderHistory extends AppCompatActivity {

    private DatabaseReference mDatabase;

    StorageReference mStorageRefrence;

    RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);





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

   //     String key = mDatabase.child("posts").push().getKey();


        FirebaseRecyclerOptions<Orderproduct> options =
                new FirebaseRecyclerOptions.Builder<Orderproduct>()
                        .setQuery(mDatabase.orderByChild(currentuser), Orderproduct.class)
                        .build();

        FirebaseRecyclerAdapter<Orderproduct, OrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<Orderproduct, OrderViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int i, @NonNull Orderproduct model) {


                holder.mShow.setText(("Order id: "+model.getorderid()));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//
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
}