package com.pbp.android_dao.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ruangan {
    @PrimaryKey
    private String kode_ruangan;
    private String nama;
    private Gedung gedung;

    public Ruangan(int id, String nama, String lokasi) {
        this.kode_ruangan = kode_ruangan;
        this.nama = nama;
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

    public Gedung getGedung() {
        return gedung;
    }

    public void setGedung(Gedung gedung) {
        this.gedung = gedung;
    }

}
