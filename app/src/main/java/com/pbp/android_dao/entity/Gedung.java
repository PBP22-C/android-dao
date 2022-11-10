package com.pbp.android_dao.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class Gedung {
    @PrimaryKey
    @NonNull
    private String kodeGedung;
    private String namaGedung;

    public Gedung(String kodeGedung, String namaGedung) {
        this.kodeGedung = kodeGedung;
        this.namaGedung = namaGedung;
    }

    public String getKodeGedung() {
        return kodeGedung;
    }

    public void setKodeGedung(String kodeGedung) {
        this.kodeGedung = kodeGedung;
    }

    public String getNamaGedung() {
        return namaGedung;
    }

    public void setNamaGedung(String nama) {
        this.namaGedung = nama;
    }

    @Override
    public String toString() {
        return namaGedung;
    }
}