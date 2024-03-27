package com.example.medochub_tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Medicament {
    private String CIP;

    public Medicament(String CIP) {
        this.CIP = CIP;
    }

    public String getCIP() {
        return CIP;
    }

}