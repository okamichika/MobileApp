package fr.iut.medochub;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBAccess {
    private static final String urlDB = "http://192.168.1.14/medochub";

    public static void signUp(String username, String firstName, String lastName, String email, String password, SignUpCallback callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                // Création de la connexion avec le fichier PHP
                URL url = new URL(urlDB + "/signup.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Création des données en mode POST
                String urldata = Uri.encode("username", "UTF-8") + "=" + Uri.encode(username, "UTF-8") +
                        "&" + Uri.encode("firstName", "UTF-8") + "=" + Uri.encode(firstName, "UTF-8") +
                        "&" + Uri.encode("lastName", "UTF-8") + "=" + Uri.encode(lastName, "UTF-8") +
                        "&" + Uri.encode("email", "UTF-8") + "=" + Uri.encode(email, "UTF-8") +
                        "&" + Uri.encode("password", "UTF-8") + "=" + Uri.encode(password, "UTF-8");

                // Envoi des données au PHP
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(urldata);
                writer.flush();
                writer.close();
                os.close();

                // Vérification que la connexion a bien été effectuée
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Récupération de la réponse
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    // Appel du callback avec la réponse
                    handler.post(() -> callback.onResultSignUp(response.toString().trim()));
                } else {
                    throw new Exception("HTTP error code: " + responseCode);
                }
            } catch (Exception e) {
                handler.post(() -> callback.onResultSignUp(e.toString()));
            }
        });
    }

    public static void signIn(String username, String password, SignInCallback signInCallback) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(urlDB + "/signin.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String urldata = Uri.encode("username", "UTF-8") + "=" + Uri.encode(username, "UTF-8") +
                        "&" + Uri.encode("password", "UTF-8") + "=" + Uri.encode(password, "UTF-8");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(urldata);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString().trim());
                    try {
                        if (jsonResponse.getString("status").equals("success")) {
                            JSONObject jsonUser = jsonResponse.getJSONObject("user");
                            User user = new User(
                                    jsonUser.getString("username"),
                                    jsonUser.getString("prenom"),
                                    jsonUser.getString("nom"),
                                    jsonUser.getString("adressemail")
                            );
                            handler.post(() -> {
                                try {
                                    signInCallback.onResultSignIn(jsonResponse.getString("status"), user);
                                } catch (JSONException e) {
                                    signInCallback.onResultSignIn("error", null);
                                }
                            });
                        } else {
                            handler.post(() -> {
                                try {
                                    signInCallback.onResultSignIn(jsonResponse.getString("message"), null);
                                } catch (JSONException e) {
                                    signInCallback.onResultSignIn("error", null);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        // Gérer l'exception JSONException ici
                        handler.post(() -> signInCallback.onResultSignIn("error", null));
                    }
                } else {
                    throw new Exception("HTTP error code: " + responseCode);
                }
            } catch (Exception e) {
                handler.post(() -> signInCallback.onResultSignIn("error", null));
            }
        });
    }

    public static void deleteAccount(String username, DeleteAccountCallBack callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(urlDB + "/deleteAccount.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String urldata = Uri.encode("username", "UTF-8") + "=" + Uri.encode(username, "UTF-8");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(urldata);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    boolean result = Boolean.parseBoolean(response.toString().trim());
                    handler.post(() -> callback.onResultDeleteAccount(result));
                } else {
                    throw new Exception("HTTP error code: " + responseCode);
                }
            } catch (Exception e) {
                handler.post(() -> callback.onResultDeleteAccount(false));
            }
        });
    }

    // Interface pour le callback
    public interface SignUpCallback {
        void onResultSignUp(String result);
    }

    public interface SignInCallback {
        void onResultSignIn(String result, User user);
    }

    public interface DeleteAccountCallBack {
        void onResultDeleteAccount(Boolean result);
    }

}
