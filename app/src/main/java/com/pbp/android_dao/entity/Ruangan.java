package com.pbp.android_dao.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ruangan {
    @PrimaryKey
    private String kode_ruangan;
    private String nama;
    private int kapasitas;
    private Gedung gedung;

    public Ruangan(String kode_ruangan, String nama, int kapasitas, Gedung gedung) {
        this.kode_ruangan = kode_ruangan;
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.gedung = gedung;
    }

    public String getKode_ruangan() {
        return kode_ruangan;
    }

    public void setKode_ruangan(String id) {
        this.kode_ruangan = id;
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
