package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class RegisterActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_register);



    }
    public void register_buyer (View view) {
        Intent registerbuy = new Intent(RegisterActivity.this, RegisterBuyerActivity.class);
        startActivity(registerbuy);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void register_seller (View view) {
        Intent registersell = new Intent(RegisterActivity.this, RegisterSellerActivity.class);
        startActivity(registersell);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }






    public void cancel(View view) {
        finish();
    }




}



