package com.example.medochub_tmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicamentListFragment extends Fragment {

    private LinearLayout layoutMedicamentList;

    public MedicamentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicament_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutMedicamentList = view.findViewById(R.id.layoutMedicationList);

        // Récupérer la liste de médicaments depuis la source de données
        List<Medicament> medicamentList = data.generateMedications();

        // Ajouter chaque médicament à la liste
        for (Medicament medicament : medicamentList) {
            View medicamentView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_medicament, null);
            TextView CIP = medicamentView.findViewById(R.id.CIP);
            TextView Date = medicamentView.findViewById(R.id.Date);

            StringBuilder sb = new StringBuilder(CIP.getText());
            sb.append(" ").append(medicament.getCIP());
            CIP.setText(sb);

            Date dateDuJour = new Date();
            SimpleDateFormat formateur = new SimpleDateFormat("dd/MM/yyyy");
            String dateFormatee = formateur.format(dateDuJour);
            StringBuilder sb1 = new StringBuilder(Date.getText());
            sb1.append(" ").append(dateFormatee);
            Date.setText(sb1);

            layoutMedicamentList.addView(medicamentView);
        }
    }
}
