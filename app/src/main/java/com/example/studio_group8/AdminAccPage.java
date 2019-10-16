package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminAccPage extends AppCompatActivity {



    private DatabaseReference mDatabase;

    StorageReference mStorageRefrence;

    RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    private ImageView remove;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        FirebaseStorage storage  = FirebaseStorage.getInstance();


        mStorageRefrence = storage.getReference();


        mRecyclerView = findViewById(R.id.adminaccrecycler);





        final String user = FirebaseAuth.getInstance().getUid();



        mDatabase = FirebaseDatabase.getInstance().getReference("User");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


//        remove = findViewById(R.id.admin_remove_account);

    }

    public void cancelHome(View view) {
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mDatabase, User.class)
                        .build();


        FirebaseRecyclerAdapter<User, AccountViewHolder> adapter =
                new FirebaseRecyclerAdapter<User, AccountViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AccountViewHolder holder, int i, @NonNull User model) {


                        holder.accountName.setText(model.getName());
                        holder.accountEmail.setText(model.getEmail());








                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent account = new Intent(AdminAccPage.this, AdminMaintainAccActivity.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("desc", model.getDesc());
                                account.putExtra("name", model.getName());
                                startActivity(account);

                            }
                        });






//                        Picasso.get().load(model.getImage()).into(holder.productImage);
                    }



                    @NonNull
                    @Override
                    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_admin_account, parent, false);
                        AccountViewHolder holder = new AccountViewHolder(view);
                        return holder;
                    }
                };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }






}

