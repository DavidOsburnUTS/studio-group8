package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;

public class Cartfragment extends Fragment{



    RecyclerView mRecyclerView;
    StorageReference mStorageRefrence;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    Query query;
    private TextView totalPrice;
    private double  overTotalPrice = 0.00;
    public Button Confirm;
     String currentuser;


    private Context context;
    String numberAsString;





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_cart,container, false);

        FirebaseStorage storage  = FirebaseStorage.getInstance();

        mStorageRefrence = storage.getReference();


        Confirm = v.findViewById(R.id.confirm);
        mRecyclerView = v.findViewById(R.id.recyclercartlist);



        totalPrice = (TextView) v.findViewById(R.id.total_price);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference totalamount = FirebaseDatabase.getInstance().getReference();
                totalamount.child("Total").child(currentuser).child("totalamount").setValue(overTotalPrice);
                Intent cardpayment = new Intent(getActivity(), CardPayment.class);
                cardpayment.putExtra("totalprice", numberAsString);
                startActivity(cardpayment);
            }
        });


        return v;
    }



    public void onStop() {
        super.onStop();
        overTotalPrice = 0.00;
    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child(currentuser)
                .child("Products"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull Cart model) {

                Double value = new Double(model.getprice());
                Integer quantity = new Integer(model.getQuantity());

                double eachProductTotalPrice = value * quantity;

                DecimalFormat df = new DecimalFormat(" #.00");
                holder.productCartQuantity.setText((model.getQuantity()));
                holder.productCartPrice.setText(" " + eachProductTotalPrice);
                holder.productCartName.setText(model.getName());
//                GlideApp.
//                        with(getActivity())
//                        .load(model.getImage())
//                        .into(holder.productCartImage);








//                double eachProductTotalPrice = Double.parseDouble(model.getprice()) * Integer.parseInt(model.getQuantity());

                overTotalPrice = overTotalPrice + eachProductTotalPrice;

                 numberAsString = new Double(overTotalPrice).toString();

                totalPrice.setText(" " + df.format(overTotalPrice));

//                GlideApp.
//                        with(getActivity())
//                        .load(model.getProductimage())
//                        .into(holder.productCartImage);

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


                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        overTotalPrice = overTotalPrice - eachProductTotalPrice;
                        totalPrice.setText(" " + df.format(overTotalPrice));
                        cartListRef.child(currentuser)
                                .child("Products")
                                .child(model.getproductid())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Item removed from cart List", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                });



            }






            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_cart, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;


            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }




}
