package com.example.studio_group8;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellProduct extends AppCompatActivity {

    int quantity = 0;
    private EditText mtitle, mdesc, mquantity;
    private Button mupload;
    private int mquint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product);

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mtitle   = (EditText) findViewById(R.id.title);
        mdesc   = (EditText) findViewById(R.id.desc);
        mquantity   = (EditText) findViewById(R.id.quantity_edit_text);
        mupload = (Button) findViewById(R.id.upload);
        String value = mquantity.getText().toString();
        mquint =Integer.parseInt(value);
        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProduct(currentuser, mtitle.getText().toString(), mdesc.getText().toString(), mquint);

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SellProduct.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

    }


    public void increment (View view) {
        quantity = quantity +1;
        display(quantity);

    }


    public void decrement(View view) {
        if(quantity>0) {
            quantity = quantity - 1;
            display(quantity);
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

    public void AddProduct(String currentuser, String mtitle, String mdesc, int mquantity){
        final Product addProduct = new Product(currentuser, mtitle, mdesc, mquantity);
        if (mtitle.trim().equals("") || mdesc.trim().equals("") || mquantity== 0) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("product").child(addProduct.name).setValue(addProduct);

        }

    }
}