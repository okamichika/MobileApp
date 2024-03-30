package fr.iut.medochub.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView username, lastname, firstname, email;
    private Button buttonModify, buttonBack;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //on récupère les informations et on rajoute les textes
        username = (TextView) findViewById(R.id.username);
        lastname = (TextView) findViewById(R.id.lastname);
        firstname = (TextView) findViewById(R.id.firstname);
        email = (TextView) findViewById(R.id.email);

        user = (User) getIntent().getParcelableExtra("currentUser");

        username.setText(user.getUsername());
        lastname.setText(user.getLastName());
        firstname.setText(user.getFirstName());
        email.setText(user.getEmail());

        buttonModify = (Button) findViewById(R.id.modify_information);
        buttonBack = (Button) findViewById(R.id.back);

        buttonModify.setOnClickListener(view -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setMessage("Fonctionnalité désactivée pour le moment.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}