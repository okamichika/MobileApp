package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medochub_tmp.R;
import com.example.medochub_tmp.Validation;

import java.util.Objects;

public class AuthentificationActivity extends AppCompatActivity {
    EditText login, mdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        TextView createaccount = (TextView) findViewById(R.id.nothaveaccount);
        login = (EditText)findViewById(R.id.login);
        mdp = (EditText)findViewById(R.id.mdp);
        Button bvaliderCo = findViewById(R.id.BvaliderCo);
        TextView infoconexion = findViewById(R.id.infoconexion);
        infoconexion.setTextColor(Color.rgb(255, 0, 0));
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void onLogin(View view) {
        String username = login.getText().toString();
        String password = mdp.getText().toString();
        String type = "login";
        Validation validation = new Validation(this);
        validation.execute(type, username, password);
    }
}