package com.alexis.swipy.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexis.swipy.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void getSignup(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }

    public void clk_Signin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
