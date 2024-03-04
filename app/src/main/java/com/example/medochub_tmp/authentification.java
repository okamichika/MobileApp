package com.example.medochub_tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class authentification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        TextView createaccount = (TextView) findViewById(R.id.nothaveaccount);

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "onClick: Bouton connection_id cliqué!!!");

                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
            }
        });
    }
}