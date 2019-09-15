package com.example.studio_group8;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginMain extends AppCompatActivity {

    Button loginBtn;

    private EditText musername, mpassword;



    static final int requestcode = 1;
    Animation rightleft;
    LinearLayout L1;
    Animation bottomup;
    Animation topdown;
    RelativeLayout R1;
    RelativeLayout R2;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
        loginBtn =(Button) findViewById(R.id.login);

        R1 = (RelativeLayout) findViewById(R.id.RelativeLayout);
        R2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        L1 = (LinearLayout) findViewById(R.id.Layout1);

        rightleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        bottomup = AnimationUtils.loadAnimation(this,R.anim.bottomup);
        topdown = AnimationUtils.loadAnimation(this,R.anim.topdown);

        R1.setAnimation(bottomup);
        R2.setAnimation(topdown);
        L1.setAnimation(rightleft);

        checkPermission();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });


}


    public void register_page(View view) {
        Intent register = new Intent(LoginMain.this, RegisterActivity.class);
        startActivity(register);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void forgot_pass(View view) {
        Intent forget = new Intent(LoginMain.this, ForgotPassActivity.class);
        startActivity(forget);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void main_page (View view) {
        Intent main = new Intent(LoginMain.this, MainActivity.class);
        startActivity(main);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);

    }






    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //PERMISSION GRANTED
            } else {
                ActivityCompat.requestPermissions(LoginMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LoginMain.requestcode) {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            showDetails();

        }
    }

    public void showDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginMain.this);
        builder.setTitle("Storage  Write Permission")
                .setMessage("This permission is necessary to access storage to be able to save data")
                .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(LoginMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create();
        builder.show();


    }


    // Firebase login code




    private void loginUserAccount() {


        String email, password;
        email = musername.getText().toString();
        password = mpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();


                            Intent intent = new Intent(LoginMain.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }





}
