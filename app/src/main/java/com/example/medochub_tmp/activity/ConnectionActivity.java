package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.medochub_tmp.R;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        Button BConnection = (Button) findViewById(R.id.se_connecter);

        BConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "onClick: Bouton connection_id cliqué!!!");

                Intent intent = new Intent(getApplicationContext(), AuthentificationActivity.class);
                startActivity(intent);
            }
        });

        Button BInscription = (Button) findViewById(R.id.inscription);

        BInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "onClick: Bouton connection_id cliqué!!!");

                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
            }
        });
    }
}