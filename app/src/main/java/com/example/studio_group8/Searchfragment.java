package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;

public class Searchfragment extends Fragment {


    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private SearchView mSearch;
    private DatabaseReference mProductDatabase;
    private Context context;

    private String searchText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
//        search =  (SearchView) view.findViewById(R.id.search_field);
        mSearchField = (EditText) view.findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) view.findViewById(R.id.search_btn);
        mResultList = (RecyclerView) view.findViewById(R.id.searchList);

//        mProductDatabase = FirebaseDatabase.getInstance().getReference("product");


        mResultList = (RecyclerView) view.findViewById(R.id.searchList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(context));


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchText = mSearchField.getText().toString().toUpperCase();
//                firebaseProductSearch(searchText);

                onStart();
            }
        });
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Product");

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(reference.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff"), Product.class)
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
                                with(getActivity())
                                .load(model.getImage())
                                .into(holder.productImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), ProductDetails.class);
//                                intent.putExtra("name", model.getName());
//                                intent.putExtra("pid", model.getPid());

                                intent.putExtra("productid", model.getproductid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        mResultList.setAdapter(adapter);
        adapter.startListening();
    }


}





/*    private void firebaseProductSearch(String searchText) {

        if(mSearchField.getText().toString().trim().equals("")) {

            Toast.makeText(context, "Please enter a name....", Toast.LENGTH_LONG).show();
        }

        else {
            Query firebaseSearchQuery = mProductDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
            FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                    Product.class,
                    R.layout.activity_list,
                    ProductViewHolder.class,
                    firebaseSearchQuery
            ) {

                @Override
                protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                    viewHolder.setDetails(getActivity().getApplicationContext(), model.getName(), model.getDesc(), model.getImage(), model.getQuantity());

                }


            };
            mResultList.setAdapter(firebaseRecyclerAdapter);

//            Toast.makeText(context, "Found", Toast.LENGTH_LONG).show();


        }
    }*/






   /* public static class ProductViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setDetails(Context ctx, String name, String desc, String productImage, int quantity){
            TextView product_name = (TextView) mview.findViewById(R.id.name_text);
            TextView product_desc = (TextView) mview.findViewById(R.id.notif_text);
            ImageView product_image = (ImageView) mview.findViewById(R.id.notif_image);
            TextView product_quantity = (TextView) mview.findViewById(R.id.quantity);
            product_name.setText(name);
            product_desc.setText(desc);
            Glide.with(ctx).load(productImage).into(product_image);
            product_quantity.setText(quantity);
        }
    }*/










