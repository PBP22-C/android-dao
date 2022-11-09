package com.pbp.android_dao.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GedungWithRuangans {
    @Embedded
    public Gedung gedung;
    @Relation(
            parentColumn = "kodeGedung", // Kolom PK tabel Gedung
            entityColumn = "kodeGedung"  // Kolom FK di tabel Ruangan
    )
    public List<Ruangan> ruangans;
}
