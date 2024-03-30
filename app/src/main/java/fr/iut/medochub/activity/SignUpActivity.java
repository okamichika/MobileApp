package fr.iut.medochub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.iut.medochub.DBAccess;
import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, firstName, lastName, email, password, confirmPassword;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Récupération de tous les champs de saisie et du texte d'erreur
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        error = findViewById(R.id.infoconexion);

        // Configuration des boutons
        Button buttonConfirm = findViewById(R.id.confirm_button);
        TextView buttonSignIn = findViewById(R.id.haveaccount);

        buttonSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });

        buttonConfirm.setOnClickListener(view -> signUp());
    }

    private void signUp() {
        // Récupération et vérification des données saisies par l'utilisateur
        String strUsername = username.getText().toString().trim();
        String strFirstName = firstName.getText().toString().trim();
        String strLastName = lastName.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        String strConfirmPassword = confirmPassword.getText().toString().trim();

        if (strUsername.isEmpty() || strFirstName.isEmpty() || strLastName.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strConfirmPassword.isEmpty()) {
            error.setText("Vérifiez les champs vides.");
        } else if (!strEmail.contains("@")) {
            error.setText("Vérifiez votre adresse e-mail.");
        } else if (!strPassword.equals(strConfirmPassword)) {
            error.setText("Les mots de passe ne correspondent pas.");
        } else {
            // Appel asynchrone pour l'inscription
            DBAccess.signUp(strUsername, strFirstName, strLastName, strEmail, strPassword, result -> {
                if ("success".equals(result)) {
                    error.setText("");
                    // Inscription réussie, on créer notre utilisateur
                    User user = new User(strUsername, strFirstName, strLastName, strEmail);

                    //on modifie les données pour la prochaine connection
                    SharedPreferences sharedPreferences = getSharedPreferences("fr.iut.medochub.userConnexion", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("isLogged", true);

                    editor.putString("currentUserUsername", strUsername);
                    editor.putString("currentUserFirstName", strFirstName);
                    editor.putString("currentUserLastName", strLastName);
                    editor.putString("currentUserEmail", strEmail);
                    editor.apply();

                    //on lance la nouvelle activité
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("currentUser", user);
                    startActivity(intent);
                    finish();
                } else {
                    error.setText(result);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        // Retour à l'écran de démarrage si le bouton retour est utilisé
        Intent intent = new Intent(getApplicationContext(), StartingPageActivity.class);
        startActivity(intent);
    }
}
