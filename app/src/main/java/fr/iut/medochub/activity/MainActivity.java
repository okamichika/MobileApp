package fr.iut.medochub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class MainActivity extends AppCompatActivity {

    private ImageView profile, settings;
    private Button buttonAddMedicine, buttonSeeMedicine;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //création des boutons
        profile = (ImageView) findViewById(R.id.profile);
        settings = (ImageView) findViewById(R.id.settings);

        buttonAddMedicine = (Button) findViewById(R.id.addMedicine);
        buttonSeeMedicine = (Button) findViewById(R.id.seeMedicine);

        //on récupère l'utilisateur courant de notre intent
        user = (User) getIntent().getParcelableExtra("currentUser");

        //on créer les événements et les redirections de nos boutons
        buttonAddMedicine.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddMedicineActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        buttonSeeMedicine.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SeeMedicineActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        profile.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        settings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}