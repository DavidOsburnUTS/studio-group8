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




        mRecyclerView = findViewById(R.id.orderlistrecycler);



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

        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

   //     String key = mDatabase.child("posts").push().getKey();


        FirebaseRecyclerOptions<FinalOrder> options =
                new FirebaseRecyclerOptions.Builder<FinalOrder>()
                        .setQuery(mDatabase.orderByChild("currentuser").equalTo(user), FinalOrder.class)
                        .build();

        FirebaseRecyclerAdapter<FinalOrder, OrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<FinalOrder, OrderViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int i, @NonNull FinalOrder model) {


                holder.mShow.setText(("Order id: "+model.getorderid()));
                holder.mdate.setText(("Date: "+model.getDate()));
                holder.mcardno.setText(("Card no: "+model.getcardnumber()));
                holder.mmobileno.setText(("Mobile no: "+model.getmobilenumber()));
                holder.mpost.setText(("Postal Code: "+model.getpostalcode()));
                holder.mamount.setText(("Order Total: "+model.gettotal()));





/*
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderHistory.this, OrderDetail.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("desc", model.getDesc());
                        intent.putExtra("orderid", model.getorderid());
                        startActivity(intent);

                    }
                });*/


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
