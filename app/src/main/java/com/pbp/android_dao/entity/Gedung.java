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
    private String nama;

    public Gedung(String kodeGedung, String nama) {
        this.kodeGedung = kodeGedung;
        this.nama = nama;
    }

    public String getKodeGedung() {
        return kodeGedung;
    }

    public void setKodeGedung(String kodeGedung) {
        this.kodeGedung = kodeGedung;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String toString() {
        return nama;
    }
}