package fr.iut.medochub.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.iut.medochub.DBAccess;
import fr.iut.medochub.R;

public class SignInActivity extends AppCompatActivity {

    private EditText username, password;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Récupération de tous les champs de saisie et du texte d'erreur
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        error = findViewById(R.id.infoconexion);

        // Configuration des boutons
        Button buttonConfirm = findViewById(R.id.confirm_button);
        TextView buttonForgotPassword = findViewById(R.id.forgotPassword);
        TextView buttonSignUp = findViewById(R.id.nothaveaccount);

        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        buttonForgotPassword.setOnClickListener(view -> {
            new AlertDialog.Builder(SignInActivity.this)
                    .setMessage("Fonctionnalité désactivée pour le moment.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        buttonConfirm.setOnClickListener(view -> signIn());
    }

    private void signIn() {
        String strUsername = username.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        if (strUsername.isEmpty() || strPassword.isEmpty()) {
            error.setText("Vérifiez les champs vides.");
        } else {
            DBAccess.signIn(strUsername, strPassword, (result, user) -> {
                if ("success".equals(result) && (user != null)) { // Ajout d'une vérification de nullité pour user
                    error.setText("");

                    SharedPreferences sharedPreferences = getSharedPreferences("fr.iut.medochub.userConnexion", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // on stocke notre user pour la reconnection automatique
                    editor.putBoolean("isLogged", true);
                    editor.putString("currentUserUsername", user.getUsername());
                    editor.putString("currentUserFirstName", user.getFirstName());
                    editor.putString("currentUserLastName", user.getLastName());
                    editor.putString("currentUserEmail", user.getEmail());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("currentUser", user); // Assurez-vous que User implémente Serializable ou Parcelable
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