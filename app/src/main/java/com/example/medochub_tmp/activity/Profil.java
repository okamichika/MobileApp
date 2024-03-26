package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medochub_tmp.R;
import com.example.medochub_tmp.activity.Medicaments;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button baccueil = findViewById(R.id.Baccueil);
        baccueil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medicaments.class);
                startActivity(intent);
            }
        });

        Button bmodif = findViewById(R.id.Bmodif);
        bmodif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //rendre les input actif
            }
        });
    }
}