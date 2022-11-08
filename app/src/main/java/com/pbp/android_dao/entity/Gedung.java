package com.pbp.android_dao.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Gedung {

    @PrimaryKey
    private String kode_gedung;
    private String nama;
    private List<Ruangan> ruangan;

    public Gedung(String kode_gedung, String nama, List<Ruangan> ruangan) {
        this.kode_gedung = kode_gedung;
        this.nama = nama;
        this.ruangan = ruangan;
    }

    public String getKode_gedung() {
        return kode_gedung;
    }

    public void setKode_gedung(String kode_gedung) {
        this.kode_gedung = kode_gedung;
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
