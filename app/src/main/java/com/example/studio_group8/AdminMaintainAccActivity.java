package com.example.studio_group8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminMaintainAccActivity extends AppCompatActivity {
    private Button submit;
    public EditText accountname, type, accountemail;

    private String accountID = "";

    private Button remove;


 DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference().child("User");







    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_account_page_preview);

        accountname = findViewById(R.id.account_admin_name);
        accountemail = findViewById(R.id.acc_admin_email);



        accountID = getIntent().getStringExtra("name");


        getAccountDetails(accountID);


        remove = findViewById(R.id.acc_remove);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountRef
                        .child(accountID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Account removed from permanently", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });

    }



    private void getAccountDetails(String accountID) {

        accountRef.child(accountID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    accountname.setText(user.getName());
                    accountemail.setText(String.valueOf(user.getEmail()));

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
