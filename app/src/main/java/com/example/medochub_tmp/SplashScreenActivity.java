package com.example.medochub_tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // redirection vers mainActivity apres 3s
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //démarrer page
                Intent intent = new Intent(getApplicationContext(), connection.class);
                startActivity(intent);
                finish();
            }
        };
        //handler post delayed
        new Handler().postDelayed(runnable, 3000);

    }
}