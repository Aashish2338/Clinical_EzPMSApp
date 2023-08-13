package com.ezpms.ezpmsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ezpms.ezpmsapp.Activities.HomeActivity;
import com.ezpms.ezpmsapp.Activities.LoginActivity;
import com.ezpms.ezpmsapp.Utilities.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private Context mContext;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        sessionManager = new SessionManager(mContext);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (sessionManager.getLogInDirection().equals("inside")) {
                    intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (sessionManager.getLogInDirection().equals("start")) {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}