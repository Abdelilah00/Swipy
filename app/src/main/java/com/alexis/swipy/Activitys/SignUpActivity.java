package com.alexis.swipy.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexis.swipy.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void getSignin(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void clk_signup(View view) {
    }
}
