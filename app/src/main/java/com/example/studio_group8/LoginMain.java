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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
//import com.google.firebase.auth.FirebaseAuth;

public class LoginMain extends AppCompatActivity {

    Button loginBtn;


    static final int requestcode = 1;
    Animation rightleft;
    LinearLayout L1;
    Animation bottomup;
    Animation topdown;
    RelativeLayout R1;
    RelativeLayout R2;
//    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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





//        mAuth = FirebaseAuth.getInstance();

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


}
