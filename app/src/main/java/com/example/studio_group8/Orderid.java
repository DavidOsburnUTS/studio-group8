package com.example.studio_group8;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


    public class Orderid {
        public String id;
       private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Order");

        public Orderid() {

        }

        public Orderid(String id){
            this.id = id;
        }

        public void setid(String id) {
            this.id = id;
        }

        public String getid() {

            return id ;
        }


    }

