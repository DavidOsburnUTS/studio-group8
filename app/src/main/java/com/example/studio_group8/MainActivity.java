package com.example.studio_group8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Homefragment()).commit();


    }








    //Categories
    public void phone_category(View view) {
        Intent phone = new Intent(MainActivity.this, PhoneCategory.class);
        startActivity(phone);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }



    public void laptop_category(View view) {
        Intent laptop = new Intent(MainActivity.this, LaptopCategory.class);
        startActivity(laptop);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void setting_buyer(View view) {
        Intent buyersetting = new Intent(MainActivity.this, BuyerSettingActivity.class);
        startActivity(buyersetting);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void edit_profile(View view) {
        Intent editprofile = new Intent(MainActivity.this, EditProfile.class);
        startActivity(editprofile);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void recommendation(View view) {
        Intent reco = new Intent(MainActivity.this, Recommendation.class);
        startActivity(reco);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void order_history(View view) {
        Intent history = new Intent(MainActivity.this, OrderHistory.class);
        startActivity(history);
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void camera_category(View view) {
        Intent camera = new Intent(MainActivity.this, CameraCategory.class);
        startActivity(camera);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void audio_category(View view) {
        Intent audio = new Intent(MainActivity.this, AudioCategory.class);
        startActivity(audio);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void tv_category(View view) {
        Intent tv = new Intent(MainActivity.this, TvCategory.class);
        startActivity(tv);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }
    public void console_category(View view) {
        Intent console = new Intent(MainActivity.this, ConsoleCategory.class);
        startActivity(console);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void checkout(View view) {
        Intent check = new Intent(MainActivity.this, CartActivity.class);
        startActivity(check);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }


    public void add_product(View view) {
        Intent add = new Intent(MainActivity.this, SellProduct.class);
        startActivity(add);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void cancel( View view) {
        finish();
    }

    public void cancelHome(View view) {
        finish();
    }


    @Override
    public void onBackPressed() {

        alertsignout();

    }

    public void alertsignout()
    {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                MainActivity.this);

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
                        Intent i = new Intent(MainActivity.this,
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



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.Bottombaritemone:
                                selectedFragment = new Homefragment();
                                break;
                            case R.id.Bottombaritemtwo:
                                selectedFragment = new Searchfragment();
                                break;
                            case R.id.Bottombaritemthree:
                                selectedFragment = new Cartfragment();
                                break;
                            case R.id.Bottombaritemfour:
                                selectedFragment = new Notificationfragment();
                                break;
                            case R.id.Bottombaritemfive:
                                selectedFragment = new Accountfragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();

                        return true;

                    }

                };



    }



