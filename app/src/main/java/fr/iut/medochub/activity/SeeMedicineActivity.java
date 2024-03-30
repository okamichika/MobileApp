package fr.iut.medochub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fr.iut.medochub.MedicineListFragment;
import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class SeeMedicineActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_medicine);

        //on récupère l'utilisateur

        user = (User) getIntent().getParcelableExtra("currentUser");

        //on récupère les buttons et on active les événements
        Button buttonBack = findViewById(R.id.back);
        ImageView buttonSettings = findViewById(R.id.settings);
        ImageView buttonProfile = findViewById(R.id.profile);


        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("currentUser", user);
                startActivity(intent);
            }
        });


        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("currentUser", user);
                startActivity(intent);
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("currentUser", user);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MedicineListFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}