package com.example.studio_group8;

import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {
    private ImageView prodImage;
    private Button addCart;
    private TextView   prodDescription, prodName;
    private EditText prodPrice,productQuantity;
    private String productID = "";
    private String quantity;
    private int updatedQuantity;

    String currentuser;

    private EditText mtitle, mdesc, mquantity;
    private int mquint =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        productID = getIntent().getStringExtra("productid");




//        productID = getIntent().getStringExtra("name");

        addCart = (Button) findViewById(R.id.addtoCart);
        productQuantity = (EditText) findViewById(R.id.quantity_edit_text);
        prodImage = (ImageView) findViewById(R.id.prod_image);
        prodPrice = (EditText) findViewById(R.id.prod_price);
        prodDescription = (TextView) findViewById(R.id.prod_desc);
        prodName = (TextView) findViewById(R.id.prod_text);



        prodName.setSelected(true);
        getProductDetails(productID);


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList(); 
            }
        });

    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentDate = currentDate.format(calDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentTime = currentDate.format(calDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Product");


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productid", productID);
        cartMap.put("name", prodName.getText().toString());
        cartMap.put("desc", prodDescription.getText().toString());
        cartMap.put("quantity", productQuantity.getText().toString());
        cartMap.put("price", prodPrice.getText().toString());
//        cartMap.put("price", prodPrice.getText().toString());
//        cartMap.put("image", prodImage);
//        cartMap.put("price", prodPrice.);
//        cartMap.put("quantity", n);

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        cartListRef.child(currentuser)
                .child("Products")
                .child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            updatedQuantity = Integer.parseInt(quantity) - mquint;
                            productsRef.child(productID).child("quantity").setValue(updatedQuantity);

                            Toast.makeText(ProductDetails.this, "Added to cart List", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }
                });

    }


    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Product");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Product products = dataSnapshot.getValue(Product.class);
                    prodName.setText(products.getName());
                    prodPrice.setText( String.valueOf( products.getprice()));
                    prodDescription.setText(products.getDesc());
                    GlideApp.
                            with(getApplicationContext())
                            .load(products.getImage())
                            .into(prodImage);

                    quantity = String.valueOf(products.getQuantity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void increment (View view) {

        if (Integer.parseInt(quantity)>mquint) {
            mquint = mquint +1;
        }
        else {
            Toast.makeText(ProductDetails.this, "You have reached the maximum quantity for this product.", Toast.LENGTH_SHORT).show();
        }

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

    public void cancelHome(View view) {
        finish();
    }





}

