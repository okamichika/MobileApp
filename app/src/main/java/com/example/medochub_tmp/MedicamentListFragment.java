package com.example.medochub_tmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

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
            TextView MedocName = medicamentView.findViewById(R.id.MedocName);
            TextView CIP = medicamentView.findViewById(R.id.CIP);
            TextView Jour = medicamentView.findViewById(R.id.Jour);
            TextView NumJour = medicamentView.findViewById(R.id.NumJour);
            TextView Mois = medicamentView.findViewById(R.id.Mois);
            TextView Annee = medicamentView.findViewById(R.id.Annee);

            MedocName.setText(medicament.getName());
            CIP.setText(medicament.getCIP());
            /*Jour.setText(medicament.getJour());
            NumJour.setText(medicament.getNumJ());
            Mois.setText(medicament.getMois());
            Annee.setText(medicament.getAnnee());*/

            layoutMedicamentList.addView(medicamentView);
        }
    }
}
