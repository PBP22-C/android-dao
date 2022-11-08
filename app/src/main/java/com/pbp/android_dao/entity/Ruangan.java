package com.pbp.android_dao.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ruangan {
    @PrimaryKey
    private String kodeRuangan;
    private String nama;
    private int kapasitas;
    private Gedung gedung;

    public Ruangan(String kodeRuangan, String nama, int kapasitas, Gedung gedung) {
        this.kodeRuangan = kodeRuangan;
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.gedung = gedung;
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

    public Gedung getGedung() {
        return gedung;
    }

    public void setGedung(Gedung gedung) {
        this.gedung = gedung;
    }

}
