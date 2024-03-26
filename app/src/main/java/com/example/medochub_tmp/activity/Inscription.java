package com.example.medochub_tmp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medochub_tmp.R;
import com.example.medochub_tmp.activity.AuthentificationActivity;
import com.example.medochub_tmp.activity.ConsultationMedocActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);


        TextView connexion = (TextView) findViewById(R.id.haveaccount);
        EditText email = findViewById(R.id.email);
        TextInputEditText passwd1 = findViewById(R.id.passwd1);
        TextInputEditText passwd2 = findViewById(R.id.passwd2);

        Button BvaliderInsc = findViewById(R.id.BvaliderInsc);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "onClick: Bouton connection_id cliqué!!!");

                Intent intent = new Intent(getApplicationContext(), AuthentificationActivity.class);
                startActivity(intent);
            }
        });

        BvaliderInsc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(verifAccount(email.toString(), passwd1.getText().toString(), passwd2.getText().toString())){
                    Log.i("MainActivity", "ok, soit on co directment soit on demande une authentification");
                    //a retirer après :
                    startActivity(new Intent(getApplicationContext(), ConsultationMedocActivity.class));
                }else{
                    Log.i("MainActivity", "Nok");
                }
            }
        });
    }

    private boolean verifAccount(String email, String Tpasswd1, String Tpasswd2) {
        TextView infopasswd = findViewById(R.id.infopasswd);

        //if(email deja dans base) text qui dit mail deja utilisé return false

        if(!Tpasswd1.equals(Tpasswd2)){
            Log.i("", "Nok"+Tpasswd1+" et "+Tpasswd2);
            infopasswd.setText("Les mots de passe ne correspondent pas !");
            infopasswd.setTextColor(Color.RED);
            infopasswd.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            return false;
        }
        Log.i("", "ok");

        return true;
    }
}