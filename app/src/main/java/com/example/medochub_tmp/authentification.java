package com.example.medochub_tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class authentification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        TextView createaccount = (TextView) findViewById(R.id.nothaveaccount);
        TextInputEditText login = findViewById(R.id.login);
        TextInputEditText mdp = findViewById(R.id.mdp);
        Button bvaliderCo = findViewById(R.id.BvaliderCo);
        TextView infoconexion = findViewById(R.id.infoconexion);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "onClick: Bouton connection_id cliqué!!!");

                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
            }
        });

        bvaliderCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(veriflogin(Objects.requireNonNull(login.getText()).toString(), Objects.requireNonNull(mdp.getText()).toString())) {
                    Log.i("MainActivity", "ok");
                    startActivity(new Intent(getApplicationContext(), Medicaments.class));

                }else{
                    //login.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    infoconexion.setText("Identifiant ou mot de passe incorrect !");
                    Log.i("MainActivity", "Nok");
                }
            }
        });
    }
    public boolean veriflogin(String id, String mdp) {
        //verif dans bdd

        return id.equals(mdp);
    }
}