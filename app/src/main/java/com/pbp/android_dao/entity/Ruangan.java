package com.pbp.android_dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Ruangan {
    @PrimaryKey
    @NonNull
    private String kodeRuangan;
    private String nama;
    private int kapasitas;
    private String kodeGedung;

    public Ruangan(String kodeRuangan, String nama, int kapasitas, String kodeGedung) {
        this.kodeRuangan = kodeRuangan;
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.kodeGedung = kodeGedung;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    public void setKodeRuangan(String id) {
        this.kodeRuangan = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getKodeGedung() {
        return kodeGedung;
    }

    public void setKodeGedung(String kodeGedung) {
        this.kodeGedung = kodeGedung;
    }
}
