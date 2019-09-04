package com.example.studio_group8;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ForgotPassActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_forget_pass);



    }

    public void cancel(View view) {
        finish();
    }

}
