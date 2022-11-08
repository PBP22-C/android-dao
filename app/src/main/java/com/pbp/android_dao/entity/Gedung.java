package com.pbp.android_dao.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Gedung {

    @PrimaryKey
    private String kodeGedung;
    private String nama;
    private List<Ruangan> ruangan;

    public Gedung(String kode_gedung, String nama) {
        this.kodeGedung = kode_gedung;
        this.nama = nama;
    }

    public Gedung(String kode_gedung, String nama, List<Ruangan> ruangan) {
        this.kodeGedung = kode_gedung;
        this.nama = nama;
        this.ruangan = ruangan;
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

    public List<Ruangan> getRuangan() {
        return ruangan;
    }

    public void addRuangan(Ruangan ruangan) {
        this.ruangan.add(ruangan);
    }
}
