package com.example.studio_group8;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Searchfragment extends Fragment {


    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private SearchView mSearch;

    private DatabaseReference mProductDatabase;
    private Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
//        search =  (SearchView) view.findViewById(R.id.search_field);
        mSearchField = (EditText) view.findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) view.findViewById(R.id.search_btn);
        mResultList = (RecyclerView) view.findViewById(R.id.searchList);

        mProductDatabase = FirebaseDatabase.getInstance().getReference("product");



        mResultList = (RecyclerView) view.findViewById(R.id.searchList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(context));



        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseProductSearch(searchText);

            }
        });



        return view;


    }

    private void firebaseProductSearch(String searchText) {

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


                    viewHolder.setDetails(getActivity().getApplicationContext(), model.getName(), model.getDesc(), model.getImage());

                }


            };
            mResultList.setAdapter(firebaseRecyclerAdapter);

            Toast.makeText(context, "Found", Toast.LENGTH_LONG).show();


        }
    }






    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

        }

        public void setDetails(Context ctx, String name, String desc, String productImage){

            TextView product_name = (TextView) mview.findViewById(R.id.name_text);
            TextView product_desc = (TextView) mview.findViewById(R.id.desc_text);
            ImageView product_image = (ImageView) mview.findViewById(R.id.profile_image);


            product_name.setText(name);
            product_desc.setText(desc);

            Glide.with(ctx).load(productImage).into(product_image);


        }
    }
}









