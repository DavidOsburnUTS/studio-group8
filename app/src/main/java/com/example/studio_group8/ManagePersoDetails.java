package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ManagePersoDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_perso_details);

    }

    public void back( View view) {
        finish();
    }

    public void manage_perso_details_address(View view) {
        Intent logSecPass = new Intent(ManagePersoDetails.this, ManagePersoDetailsAddress.class);
        startActivity(logSecPass);
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }


}
