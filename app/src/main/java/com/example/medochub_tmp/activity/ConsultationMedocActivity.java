package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.medochub_tmp.MedicamentListFragment;
import com.example.medochub_tmp.R;

public class ConsultationMedocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_medoc);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MedicamentListFragment())
                .commit();

        ImageView bsettings = findViewById(R.id.Bsettings);

        bsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });

        ImageView bprofil = findViewById(R.id.Bprofil);

        bprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profil.class);
                startActivity(intent);
            }
        });

        Button baccueil = findViewById(R.id.Baccueil);

        baccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medicaments.class);
                startActivity(intent);
            }
        });


    }
}