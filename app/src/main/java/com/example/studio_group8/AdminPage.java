package com.example.studio_group8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }



    public void account_page(View view) {
        Intent adminacc = new Intent(AdminPage.this, AdminAccPage.class);
        startActivity(adminacc);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }


    public void admin_add_product (View view) {
        Intent add_product = new Intent(AdminPage.this, SellProduct.class);
        startActivity(add_product);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void admin_transactions (View view) {
        Intent transactions = new Intent(AdminPage.this, AdminTransPage.class);
        startActivity(transactions);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void admin_product (View view) {
        Intent product = new Intent(AdminPage.this, AdminProdPage.class);
        startActivity(product);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }



    @Override
    public void onBackPressed() {

        alertsignout();

    }

    public void logout(View view) {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                AdminPage.this);

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
                        Intent i = new Intent(AdminPage.this,
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

    public void alertsignout()
    {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                AdminPage.this);

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
                        Intent i = new Intent(AdminPage.this,
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

}