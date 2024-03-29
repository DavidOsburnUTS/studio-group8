package com.example.studio_group8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;


import com.braintreepayments.cardform.view.CardForm;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CardPayment extends AppCompatActivity {
// cart number 5234269538465723
String orderid;
private DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart");
    final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String total;
    CardForm cardForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

         cardForm = findViewById(R.id.card_form);
        Button buy = findViewById(R.id.btnBuy);
        total = getIntent().getStringExtra("totalprice");


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(CardPayment.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CardPayment.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            Toast.makeText(CardPayment.this, "Thank you for purchase", Toast.LENGTH_SHORT).show();
                           // CreateOrder();
                            //GetProduct();
                            Finalorder();

                            final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            cartListRef.child(user)
                                    .child("Products")
                                    .removeValue();

                            Intent home = new Intent(CardPayment.this, MainActivity.class);
                            startActivity(home);
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                }else {
                    Toast.makeText(CardPayment.this, "Please complete the form", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void back( View view) {
        finish();
    }

//    public void CreateOrder() {
//        orderid = UUID.randomUUID().toString();
//        Date c = Calendar.getInstance().getTime();
//
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        String formattedDate = df.format(c);
//        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");
//        DatabaseReference fromPath = cartListRef.child(currentuser).child("Products");
//        DatabaseReference ordersave = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference toPath = ordersave.child("Order").child(currentuser).child("Purchase date "+formattedDate).child(orderid);
//        //AddOrder(fromPath, toPath);
//
//
//
//
//
//    }
//
//    private void AddOrder(final DatabaseReference fromPath, final DatabaseReference toPath) {
//        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
//                        if (firebaseError != null) {
//                            System.out.println("Copy failed");
//                        } else {
//                            System.out.println("Success");
//                            final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            cartListRef.child(user)
//                                    .child("Products")
//                                    .removeValue();
//
//                        }
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

//
    public void Finalorder(){
        orderid = UUID.randomUUID().toString();
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);



        FinalOrder finalorder = new  FinalOrder(currentuser,orderid,total,cardForm.getCardNumber(), cardForm.getPostalCode(), cardForm.getMobileNumber(), formattedDate );
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Order").child(orderid).setValue(finalorder);
        CreateOrder();




    }

        public void CreateOrder() {
     //   orderid = UUID.randomUUID().toString();
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        DatabaseReference fromPath = cartListRef.child(currentuser).child("Products");
        DatabaseReference ordersave = FirebaseDatabase.getInstance().getReference();
        DatabaseReference toPath = ordersave.child("OrderProducts").child(orderid);
        AddOrder(fromPath, toPath);





    }

    private void AddOrder(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                            final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            cartListRef.child(user)
                                    .child("Products")
                                    .removeValue();

                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    }
