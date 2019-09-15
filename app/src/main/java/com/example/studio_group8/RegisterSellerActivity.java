package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterSellerActivity extends Activity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.activity_register_business};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private int dotscount;
    private EditText inputEmail, inputPassword, inputConfirmPassword, inputFirstName, inputUsername;
    private Button btnSignUp;
    static final int requestcode = 1;
    private FirebaseAuth mAuth;
    private Button BtnNext, BtnBack;


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
//                    BtnBack.setVisibility(View.VISIBLE);
                    BtnBack.setVisibility(View.INVISIBLE);
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



    public void CreateNewSeller(final FirebaseAuth mAuth, final String email, final String password, final String name, final String phone) {
        final User newUser = new User(email, name, phone);
        if (email.trim().equals("") || password.trim().equals("") || name.trim().equals("") || phone.trim().equals("")) {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
        else {
            //Changed to object system can use it for images now
            Toast.makeText(this, "Creating...", Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterSellerActivity.this, "Successful Creation, Please Verify your Email", Toast.LENGTH_SHORT).show();
                        String id1 = mAuth.getCurrentUser().getUid();
                        final FirebaseUser user = mAuth.getCurrentUser();
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("User").child(id1).setValue(newUser);
                        user.sendEmailVerification();
                        finish();

                    } else
                    {
                        Toast.makeText(RegisterSellerActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    public void registerSeller(View view) {

        EditText name = (EditText) findViewById(R.id.sellerName);
        EditText email = (EditText) findViewById(R.id.sellerEmail);
        EditText phone = (EditText) findViewById(R.id.phonenumber);
        EditText password = (EditText) findViewById(R.id.sellerpass);
        EditText confirmpassword = (EditText) findViewById(R.id.sellerconfirmpass);


        CreateNewSeller(mAuth, email.getText().toString(), password.getText().toString(), name.getText().toString(), phone.getText().toString());

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
                        Toast.makeText(RegisterSellerActivity.this, "Success", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterSellerActivity.this, "Failure", Toast.LENGTH_LONG).show();

                    }
                });
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.next:
                loadNext();
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
