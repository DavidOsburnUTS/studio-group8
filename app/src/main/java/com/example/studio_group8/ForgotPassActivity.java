package com.example.studio_group8;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends Activity {

    private FirebaseAuth mAuth;
    static final int requestcode = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_forget_pass);
    }

    public void reset_password(final FirebaseAuth mAuth, final String email) {
        if (email.trim().equals("")) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }

        else {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@Nullable Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassActivity.this, "Password Reset Request Sent, Check Your Email", Toast.LENGTH_SHORT).show();
                                setContentView(R.layout.activity_login);

                            } else {
                                Toast.makeText(ForgotPassActivity.this, "Email Address Does Not Exist",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

    public void forget_password(View view) {

        EditText email = (EditText) findViewById(R.id.forgot_email);

        reset_password(mAuth, email.getText().toString());
    }








    public void cancel(View view) {
        finish();
    }

}
