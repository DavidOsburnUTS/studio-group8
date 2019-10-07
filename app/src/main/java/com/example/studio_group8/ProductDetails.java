package com.example.studio_group8;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {
    private ImageView prodImage;
    private Button addCart;
    private TextView  prodPrice, prodDescription, prodName;
    private String productID = "";


    private EditText mtitle, mdesc, mquantity;
    private int mquint =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

//        productID = getIntent().getStringExtra("pid");

        productID = getIntent().getStringExtra("name");

        addCart = (Button) findViewById(R.id.addtoCart);
        prodImage = (ImageView) findViewById(R.id.prod_image);
        prodPrice = (TextView) findViewById(R.id.prod_price);
        prodDescription = (TextView) findViewById(R.id.prod_desc);
        prodName = (TextView) findViewById(R.id.prod_text);


        prodName.setSelected(true);
        getProductDetails(productID);
    }




    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Product");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Product products = dataSnapshot.getValue(Product.class);
                    prodName.setText(products.getName());
                    prodPrice.setText(String.valueOf("$"+ String.format("%.2f", products.getprice())));
                    prodDescription.setText(products.getDesc());
                    GlideApp.
                            with(ProductDetails.this)
                            .load(products.getImage())
                            .into(prodImage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void increment (View view) {
        mquint = mquint +1;
        display(mquint);

    }

    public void back( View view) {
        finish();
    }


    public void decrement(View view) {
        if(mquint>1) {
            mquint = mquint - 1;
            display(mquint);
        }
    }


    private void display(int number) {
        EditText quantityText = (EditText) findViewById(R.id.quantity_edit_text);
        String quantityValue = quantityText.getText().toString();
        int intQuantityValue = Integer.parseInt(quantityValue);
        quantityText.setText("" +number);

    }





}

