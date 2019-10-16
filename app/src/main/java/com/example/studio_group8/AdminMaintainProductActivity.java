package com.example.studio_group8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private Button submit;
    private EditText name,price, description;

    private String pname;
    private String productID = "";

    private Button remove;



    final DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product");
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_edit_product);

        submit = findViewById(R.id.product_admin_submit);
        name = findViewById(R.id.product_admin_title);
        price = findViewById(R.id.product_admin_price);
        description = findViewById(R.id.product_admin_desc);


        productID = getIntent().getStringExtra("productid");


        displaySpecificProductInfo(productID);




        remove = findViewById(R.id.product_admin_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productRef
                        .child(productID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(AdminMaintainProductActivity.this, "Item removed from permanently", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }

    });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct(productID, name.getText().toString().toUpperCase(), description.getText().toString(), price.getText().toString());


            }
        });
    }


    public void AddProduct(String productID, String name, String description, String price){
        double d = Double.parseDouble(price);
        final adminProduct addProduct = new adminProduct(productID, name, description, d);
        if (name.trim().equals("") || description.trim().equals("") ) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }

        else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Product").child(addProduct.productid).setValue(addProduct);
            finish();
        }

    }



    private void displaySpecificProductInfo(String productID)
    {
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Product products = dataSnapshot.getValue(Product.class);
                    name.setText(products.getName());
                    price.setText(String.valueOf( products.getprice()));
                    description.setText(products.getDesc());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    public void cancelHome( View view) {
        finish();
    }




}
