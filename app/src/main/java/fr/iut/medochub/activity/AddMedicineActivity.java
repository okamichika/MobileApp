package fr.iut.medochub.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iut.medochub.CaptureActivityPortrait;
import fr.iut.medochub.R;
import fr.iut.medochub.User;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText medicine;
    private User user;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    // Define a request code for initiating the QR scan, if needed
    private static final int QR_SCAN_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        user = (User) getIntent().getParcelableExtra("currentUser");

        medicine = findViewById(R.id.cip);

        ImageView buttonProfile = findViewById(R.id.profile);
        ImageView buttonSettings = findViewById(R.id.settings);
        Button buttonBack = findViewById(R.id.cancel);
        Button buttonConfirm = findViewById(R.id.confirm_button);
        Button buttonQRCode = findViewById(R.id.qrcode);

        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        buttonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("currentUser", user);
            startActivity(intent);
        });

        buttonConfirm.setOnClickListener(v -> {
            String cip = medicine.getText().toString();
            if (!cip.isEmpty() && isValid(cip)) {
                if (saveMedoc(cip)) {
                    Toast.makeText(getApplicationContext(), "Signalement effectué.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("currentUser", user);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Ce CIP n'existe pas.", Toast.LENGTH_LONG).show();
            }
        });

        buttonQRCode.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                openQRScanner();
            }
        });
    }

    private boolean isValid(String cip) {
        try (InputStream is = getResources().openRawResource(R.raw.cip);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(cip.trim())) {
                    return true;
                }
            }
        } catch (IOException ignored) {
        }

        return false;
    }

    private boolean saveMedoc(String CIP) {
        try {
            File file = new File(getApplicationContext().getFilesDir(), "medicine.txt");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormatted = dateFormat.format(new Date());

                bufferedWriter.write(CIP + ";" + dateFormatted);
                bufferedWriter.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.DATA_MATRIX);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_LONG).show();
            } else {
                String scannedData = result.getContents();

                String dataWithoutPrefix = scannedData.substring(4);

                if (dataWithoutPrefix.startsWith("3400") && dataWithoutPrefix.length() >= 13) {
                    String cip = dataWithoutPrefix.substring(0, 13);
                    Toast.makeText(this, "CIP trouvé : " + cip, Toast.LENGTH_LONG).show();

                    medicine.setText(cip);
                } else {
                    Toast.makeText(this, "QR Code non reconnu comme un médicament valide.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openQRScanner();
            } else {
                Toast.makeText(this, "Camera permission is required to use the QR scanner.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}
