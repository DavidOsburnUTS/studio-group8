package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterBuyerActivity extends Activity implements View.OnClickListener{

//    Site Code : 6LeJXrYUAAAAAHRsW-8Qbzqp4igKRdZYgwfXCSBa
//    Secret code: 6LeJXrYUAAAAAFm0xEHcoZ__UvXswV8H7uR6JLXe
    private ViewPager mPager;
    private int[] layouts = {R.layout.activity_register_buyer};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private int dotscount;
    private Button BtnNext, BtnBack;
    EditText emailAddress;
    EditText firstName;
    EditText Username;
    EditText Password;
    EditText confirmPassword;

    private FirebaseAuth mAuth;


    private ImageView[] dots;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


        if(new PreferenceManager(this).checkPreferences()) {
            loadHome();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        setContentView(R.layout.activity_main_register);

        mPager = (ViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts, this);
        mPager.setAdapter(mpagerAdapter);


        emailAddress = findViewById(R.id.email_address);
        firstName = findViewById(R.id.first_name);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);



        Dots_Layout = (LinearLayout) findViewById(R.id.SliderDots);
        BtnNext = (Button) findViewById(R.id.next);
        BtnBack = (Button) findViewById(R.id.back);
        BtnBack.setOnClickListener(this);
        BtnNext.setOnClickListener(this);
        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1 && position !=layouts.length-2) {
                    BtnBack.setVisibility(View.INVISIBLE);
//                    BtnBack.setVisibility(View.VISIBLE);
                    BtnNext.setVisibility(View.INVISIBLE);
                }
                else {
                    BtnBack.setVisibility(View.INVISIBLE);
                    BtnNext.setVisibility(View.INVISIBLE);
//                    BtnNext.setVisibility(View.VISIBLE);

                }

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
    }

    public void back(View view) {
        finish();
    }

    public void check(View  view) {
        SafetyNet.getClient(this).verifyWithRecaptcha("6LeJXrYUAAAAAHRsW-8Qbzqp4igKRdZYgwfXCSBa")
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                        Toast.makeText(RegisterBuyerActivity.this, "Success", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterBuyerActivity.this, "Failure", Toast.LENGTH_LONG).show();

                    }
                });
    }

    // CODE FOR REGISTER FUNCTION - REQUIRES SOME FIREBASE SETUP
//    private void registerBuyer(){
//        String email = emailAddress.getText().toString().trim();
//        String password = Password.getText().toString().trim();
//        String check = confirmPassword.getText().toString().trim();
//        //String name = editText6.getText().toString().trim();
//
//        if(email.isEmpty()){
//            emailAddress.setError("Email is required");
//            emailAddress.requestFocus();
//            return;
//        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){   // THIS METHOD CHECKS IF ITS A REAL EMAIL
//            emailAddress.setError("Please enter a valid email");
//            emailAddress.requestFocus();
//            return;
//        }
//        if(password.isEmpty()){
//            Password.setError("Password is required");
//            Password.requestFocus();
//            return;
//        }
//        if(password.length()< 6){
//            Password.setError("Minimum characters for a password is 6");
//            Password.requestFocus();
//            return;
//        }
//        if(!password.equals(check)){
//            confirmPassword.setError("Passwords don't match");
//            confirmPassword.requestFocus();
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),"You've Successfully Registered", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(RegisterBuyerActivity.this, LoginMain.class));
//                            finish();
//                        } else if(task.getException() instanceof FirebaseAuthUserCollisionException){
//                            Toast.makeText(getApplicationContext(), "You've already registered mate", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.next:
                loadNext();
//                registerBuyer();
                    break;
            case R.id.back:
                loadBack();
                    break;


        }

    }

    public void loadHome() {
        startActivity(new Intent(this, RegisterBuyerActivity.class));
        finish();
    }

    public void loadBack() {
        int back_slide = mPager.getCurrentItem()-1;
        if(back_slide<layouts.length) {
            mPager.setCurrentItem(back_slide);
        }
        else {
            loadHome();
            new PreferenceManager(this).writePreferences();
        }
    }

    private void loadNext() {
        int next_slide = mPager.getCurrentItem()+1;

        if(next_slide<layouts.length) {
            mPager.setCurrentItem(next_slide);
        }
        else {
            loadHome();
            new PreferenceManager(this).writePreferences();
        }
    }


}
