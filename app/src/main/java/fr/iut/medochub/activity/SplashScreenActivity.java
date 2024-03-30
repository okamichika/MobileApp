package fr.iut.medochub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import fr.iut.medochub.DBAccess;
import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 3000);
    }

    private void checkLoginStatus() {
        //on récupère l'état de la connection de l'utilisateur à la dernière session
        SharedPreferences sharedPreferences = getSharedPreferences("fr.iut.medochub.userConnexion", MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);

        Intent intent;
        if (isLogged) {
            String username = sharedPreferences.getString("currentUserUsername", null);
            String firstName = sharedPreferences.getString("currentUserFirstName", null);
            String lastName = sharedPreferences.getString("currentUserLastName", null);
            String email = sharedPreferences.getString("currentUserEmail", null);
            User currentUser = new User(username, firstName, lastName, email);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("currentUser", currentUser);
        }
        else {
            intent = new Intent(getApplicationContext(), StartingPageActivity.class);
        }
        startActivity(intent);
        finish();
    }
}