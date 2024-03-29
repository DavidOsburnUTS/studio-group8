package com.example.studio_group8;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Accountfragment extends Fragment{

    Fragment selectedFragment = null;
    private Context context;
    Button loginsec;
    String name;
    private String url;
    ImageView profilepic;
   TextView mtype;

    private FirebaseAuth mAuth;
    DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("User");
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference ImgRef = FirebaseDatabase.getInstance().getReference().child("User");


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_account,container, false);
        TextView musername = (TextView) v.findViewById(R.id.username);
         profilepic = (ImageView) v.findViewById(R.id.profile_image);
         mtype = (TextView) v.findViewById(R.id.type) ;


        ImgRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               url =  dataSnapshot.child(currentuser).child("image").getValue().toString();
                GlideApp.
                        with(Accountfragment.this)
                        .load(url)
                        .into(profilepic);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Button signout = (Button) v.findViewById(R.id.sign_out);
        Button orderhist = (Button) v.findViewById(R.id.order_history);
        Button ownproduct = (Button) v.findViewById(R.id.ownproduct);
        DatabaseReference databaseRef=FirebaseDatabase.getInstance().getReference();



        databaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                  String data = dataSnapshot.child("User").child(currentuser).child("username").getValue(String.class);
                                                  musername.setText(data);
                                              }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String data = dataSnapshot.child("User").child(currentuser).child("type").getValue(String.class);
                mtype.setText(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        orderhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent order = new Intent(getActivity(), OrderHistory.class);
               startActivity(order);


            }
        });


        ownproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent own = new Intent(getActivity(), OwnProduct.class);
                startActivity(own);


            }
        });

        Button editp = (Button) v.findViewById(R.id.edit_profile);

        editp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent order = new Intent(getActivity(), EditProfile.class);
                startActivity(order);


            }
        });




        signout.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                alertsignout();
             /*   mAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(),
                        LoginMain.class);
                startActivity(i);*/
            }
        });

        return v;



    }

    public void alertsignout()
    {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                getActivity());

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm Logout");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to Signout?");

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        mAuth.getInstance().signOut();
                        Intent i = new Intent(getActivity(),
                                LoginMain.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();


    }

    public void edit_profile(View view) {
        Intent editprofile = new Intent(getActivity(), EditProfile.class);
        startActivity(editprofile);
        //overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void order_history(View view) {
        Intent history = new Intent(getActivity(), OrderHistory.class);
        startActivity(history);
        //overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }




    }




















