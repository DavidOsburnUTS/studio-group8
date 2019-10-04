package com.example.studio_group8;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ManagePersoDetailsAddress extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_perso_details_address);

    }

    public void back( View view) {
        finish();
    }

}
