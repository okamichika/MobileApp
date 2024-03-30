package fr.iut.medochub.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import fr.iut.medochub.DBAccess;
import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonLanguage, buttonPassword, buttonDisconnect, buttonDelete, buttonSupport, buttonBack;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //on récupère les différents bouton de la page
        buttonLanguage = (Button) findViewById(R.id.language);
        buttonPassword = (Button) findViewById(R.id.password);
        buttonDisconnect = (Button) findViewById(R.id.disconnect);
        buttonDelete = (Button) findViewById(R.id.delete);
        buttonSupport = (Button) findViewById(R.id.contact);
        buttonBack = (Button) findViewById(R.id.back);

        //on récupère l'utilisateur
        user = (User) getIntent().getParcelableExtra("currentUser");

        //on active les événements de click
        buttonLanguage.setOnClickListener(view -> {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setMessage("Seulement le français est disponible pour le moment.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        buttonPassword.setOnClickListener(view -> {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setMessage("Fonctionnalité désactivée pour le moment.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        buttonDisconnect.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("fr.iut.medochub.userConnexion", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putBoolean("isLogged", false);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), StartingPageActivity.class);
            startActivity(intent);
        });

        buttonDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Êtes-vous sûr ?")
                    .setPositiveButton("NON", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("OUI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DBAccess.deleteAccount(user.getUsername(), new DBAccess.DeleteAccountCallBack() {
                                @Override
                                public void onResultDeleteAccount(Boolean result) {
                                    if (result) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("nom_du_fichier_pref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.putBoolean("isLogged", false);
                                        editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), StartingPageActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        runOnUiThread(() -> {
                                            dialog.dismiss();
                                            new AlertDialog.Builder(SettingsActivity.this)
                                                    .setMessage("Une erreur est survenue, veuillez réessayer plus tard.")
                                                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                                                    .show();
                                        });
                                    }
                                }
                            });
                        }
                    });
            // Créer et afficher la boîte de dialogue
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        buttonSupport.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Informations de support");
            builder.setMessage("Pour toute assistance, veuillez contacter medochub@support.com.");

            builder.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
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