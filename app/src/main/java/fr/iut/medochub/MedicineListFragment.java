package fr.iut.medochub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MedicineListFragment extends Fragment {

    private LinearLayout layoutMedicamentList;

    public MedicineListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicine_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            layoutMedicamentList = view.findViewById(R.id.layoutMedicationList);

            File file = new File(getContext().getFilesDir(), "medicine.txt");

            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                View medicamentView = LayoutInflater.from(requireContext()).inflate(R.layout.medicine, null);
                TextView CIP = medicamentView.findViewById(R.id.CIP);
                TextView Date = medicamentView.findViewById(R.id.Date);

                String[] parties = line.split(";");
                StringBuilder cip = new StringBuilder(CIP.getText()).append(parties[0]);
                CIP.setText(cip);
                StringBuilder date = new StringBuilder(Date.getText()).append(parties[1]);

                Date.setText(date);
                System.out.println(line);

                layoutMedicamentList.addView(medicamentView);

            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}