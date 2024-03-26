package com.example.medochub_tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Medicament {
    private String name;
    private String CIP;

    public Medicament(String name, String CIP) {
        this.name = name;
        this.CIP = CIP;
    }

    public String getName() {
        return name;
    }

    public String getCIP() {
        return CIP;
    }

}