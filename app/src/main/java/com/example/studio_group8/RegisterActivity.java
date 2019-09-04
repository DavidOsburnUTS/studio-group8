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





/*        Dots_Layout = (LinearLayout) findViewById(R.id.SliderDots);
        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void createDots (int current_position) {
        if(Dots_Layout!=null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[layouts.length];
        for(int i =0; i<layouts.length;i++) {
            dots[i] = new ImageView(this);
            if(i==current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            }
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonactive_dot));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i], params);
        }
    }*/



    public void cancel(View view) {
        finish();
    }


//    public void check(View  view) {
//        SafetyNet.getClient(this).verifyWithRecaptcha("6LeJXrYUAAAAAHRsW-8Qbzqp4igKRdZYgwfXCSBa")
//            .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                @Override
//                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
//                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
//
//                }
//            })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }


}



