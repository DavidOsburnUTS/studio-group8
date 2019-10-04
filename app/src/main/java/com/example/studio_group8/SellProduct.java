package com.example.studio_group8;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


    private EditText mtitle, mdesc, mquantity;
    private Button mupload;
    private int mquint =1;
    private String selectedItemText;
//    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product);

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mtitle   = (EditText) findViewById(R.id.title);
        mdesc   = (EditText) findViewById(R.id.desc);
        mquantity   = (EditText) findViewById(R.id.quantity_edit_text);
        mupload = (Button) findViewById(R.id.upload);

        /*String value = mquantity.getText().toString();
        mquint =Integer.parseInt(value);*/
        final int mquint = Integer.parseInt(mquantity.getText().toString().trim());
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
    });
    }



    public void increment (View view) {
        mquint = mquint +1;
        display(mquint);

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

    public void AddProduct(String currentuser, String mtitle, String mdesc, int mquantity){
        final Product addProduct = new Product(currentuser, mtitle, mdesc, mquantity);
        if (mtitle.trim().equals("") || mdesc.trim().equals("") ) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }

        else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("product").child(addProduct.name).setValue(addProduct);

            Toast.makeText(this, "Number" + mquint + selectedItemText , Toast.LENGTH_SHORT).show();
        }

    }
}