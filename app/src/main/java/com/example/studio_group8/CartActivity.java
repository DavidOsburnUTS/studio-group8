package com.example.studio_group8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

    }

    public void confirmAndPay (View view) {
        Intent cardpayment = new Intent(CartActivity.this, CardPayment.class);
        startActivity(cardpayment);
        overridePendingTransition( R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void back( View view) {
        finish();
    }
}
