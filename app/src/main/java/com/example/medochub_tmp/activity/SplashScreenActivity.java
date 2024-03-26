package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.medochub_tmp.R;
import com.example.medochub_tmp.activity.ConnectionActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ConnectionActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Handler().postDelayed(runnable, 3000);

    }
}