package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BuyerSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_buyer);

    }

    public void back( View view) {
        finish();
    }

    public void cancelHome(View view) {
        finish();
    }



    public void login_security(View view) {
        Intent login = new Intent(BuyerSettingActivity.this, LoginSecurity.class);
        startActivity(login);
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void manage_perso_details(View view) {
        Intent manage = new Intent(BuyerSettingActivity.this, ManagePersoDetails.class);
        startActivity(manage);
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }



}
