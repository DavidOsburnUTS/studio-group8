package com.example.studio_group8;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Orderdate {
    public String date;

   private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Order");

    public Orderdate() {

    }

    public Orderdate(String date){
        this.date = date;

    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getdate() {



        return date = mDatabase.push().getKey();
    }
}
