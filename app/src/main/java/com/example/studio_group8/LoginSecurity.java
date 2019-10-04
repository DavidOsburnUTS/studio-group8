package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSecurity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_security);

    }



    public void back( View view) {
        finish();
    }

    public void login_security_password(View view) {
        Intent logSecPass = new Intent(LoginSecurity.this, LoginSecurityPassword.class);
        startActivity(logSecPass);
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }

}
