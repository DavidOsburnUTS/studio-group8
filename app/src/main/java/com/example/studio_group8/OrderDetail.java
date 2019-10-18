package com.example.studio_group8;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetail extends AppCompatActivity {
    private TextView  mtotal,  mpostcode, mmobilenumber;
    private EditText morderid,mcardnumber ;
    String currentuser;
    String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_hist_detail);

        orderid = getIntent().getStringExtra("orderid");
//
//        morderid = (EditText) findViewById(R.id.orderid);
      //  mtotal = (TextView) findViewById(R.id.total);
//        mcardnumber = (EditText) findViewById(R.id.cardnumber);
        mpostcode = (TextView) findViewById(R.id.postcode);
        mmobilenumber = (TextView) findViewById(R.id.mobilenumber);
        getOrderDetails(orderid);
    }

    private void getOrderDetails(String orderid) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Order");

        productsRef.orderByChild("orderid").equalTo(orderid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    FinalOrder products = dataSnapshot.getValue(FinalOrder.class);
                    morderid.setText(products.getorderid());

                  //  mtotal.setText( String.valueOf( products.gettotal()));
                    mcardnumber.setText(products.getcardnumber());
                    mpostcode.setText(products.getpostalcode());
                    mmobilenumber.setText(products.getmobilenumber());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
