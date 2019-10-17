package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.telephony.PhoneNumberUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterSellerActivity extends Activity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.activity_register_business};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private int dotscount;
    public EditText inputEmail, inputPassword, inputConfirmPassword, inputFirstName, inputPhone;
    private Button btnSignUp;
    static final int requestcode = 1;
    private FirebaseAuth mAuth;
    private Button BtnNext, BtnBack;

    public Drawable customErrorDrawable;

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

        customErrorDrawable = getResources().getDrawable(R.drawable.error_icon);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());





        inputEmail = (EditText) findViewById(R.id.sellerEmail);
        inputPhone = (EditText) findViewById(R.id.phonenumber);
        inputFirstName = (EditText) findViewById(R.id.sellerName);

        inputPassword = (EditText) findViewById(R.id.sellerpass);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputConfirmPassword = (EditText) findViewById(R.id.sellerconfirmpass);


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

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");


    public boolean validPassword() {
        String passwordInput = inputPassword.getText().toString().trim();
        if(passwordInput.isEmpty()) {
            inputPassword.setError("Field can't be empty", customErrorDrawable);
            return false;
        }  else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            inputPassword.setError("Password too weak", customErrorDrawable);
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = inputEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            inputEmail.setError("Field can't be empty", customErrorDrawable);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            inputEmail.setError("Please enter a valid email address", customErrorDrawable);
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }


    private boolean validatePhone() {
        String phone = inputPhone.getText().toString().trim();


        if (phone.isEmpty()) {
            inputPhone.setError("Field can't be empty", customErrorDrawable);
            return false;
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            inputPhone.setError("Invalid Phone Number", customErrorDrawable);
            return false;
        } else {
            inputPhone.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPassword() {
        String confirmPassword = inputConfirmPassword.getText().toString().trim();
        String passwordInput = inputPassword.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            inputConfirmPassword.setError("Field can't be empty", customErrorDrawable);
            return false;
        } else if (passwordInput.equals(confirmPassword)) {
            inputConfirmPassword.setError(null);
            return true;
        } else {
            inputConfirmPassword.setError("Mismatching Passwords", customErrorDrawable);
            return false;
        }
    }




    private boolean validateFirstName() {
        String firstNameInput = inputFirstName.getText().toString().trim();
        if (firstNameInput.isEmpty()) {
            inputFirstName.setError("Field can't be empty", customErrorDrawable);
            return false;
        }
        else {
            inputFirstName.setError(null);
            return true;
        }
    }




    public void CreateNewSeller(final FirebaseAuth mAuth, final String email, final String password, final String name, final String phone) {
         String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
         String image = "https://firebasestorage.googleapis.com/v0/b/studio-group8.appspot.com/o/elon_musk_royal_society.jpg?alt=media&token=a0cdce02-3c0c-4e0d-8a40-322639bd73a8";
        final Seller newSeller = new Seller(currentuser,email, name, phone, image);

        inputEmail = (EditText) findViewById(R.id.sellerEmail);
        inputPhone = (EditText) findViewById(R.id.phonenumber);
        inputFirstName = (EditText) findViewById(R.id.sellerName);


        if(!validPassword()| !validatePhone()| !validateEmail()| !validateFirstName()| !validateConfirmPassword()) {
            return;
//        if (email.trim().equals("") || password.trim().equals("") || name.trim().equals("") || phone.trim().equals("")) {
//            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//        }
        }
        else {
            SafetyNet.getClient(this).verifyWithRecaptcha("6LeJXrYUAAAAAHRsW-8Qbzqp4igKRdZYgwfXCSBa")
                    .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                            Toast.makeText(RegisterSellerActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Create(email, password, newSeller);

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterSellerActivity.this, "Failure", Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }

    public void Create(final String email, final String password, final Seller newSeller) {
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
                    mDatabase.child("User").child(id1).setValue(newSeller);
                    mDatabase.child("User").child(id1).child("type").setValue("seller");
                    user.sendEmailVerification();
                    finish();

                } else {
                    Toast.makeText(RegisterSellerActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void registerSeller(View view) {

        EditText name = (EditText) findViewById(R.id.sellerName);
        EditText email = (EditText) findViewById(R.id.sellerEmail);
        EditText phone = (EditText) findViewById(R.id.phonenumber);
        EditText password = (EditText) findViewById(R.id.sellerpass);
        EditText confirmpassword = (EditText) findViewById(R.id.sellerconfirmpass);


        inputConfirmPassword = (EditText) findViewById(R.id.sellerconfirmpass);
        inputPassword  = (EditText) findViewById(R.id.sellerpass);

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
