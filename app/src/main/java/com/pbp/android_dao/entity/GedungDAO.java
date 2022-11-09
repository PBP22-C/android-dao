package com.pbp.android_dao.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GedungDAO {
    @Query("SELECT * FROM gedung")
    List<Gedung> getAll();

    @Transaction
    @Query("SELECT * FROM gedung")
    List<GedungWithRuangans> getAllGedungWithRuangan();

    @Transaction
    @Query("SELECT * FROM gedung WHERE kodeGedung IN (:kodeGedung)")
    List<GedungWithRuangans> getGedungWithRuangan(String kodeGedung);

//    @Query("SELECT * FROM gedung WHERE kodeGedung IN (:gedungIds)")
//    List<Gedung> loadAllByIds(int[] gedungIds);

    @Transaction
    @Query("SELECT * FROM gedung WHERE kodeGedung IN (:kodeGedung)")
    List<GedungWithRuangans> getGedungWithRuangan(String kodeGedung);

//    @Query("SELECT * FROM gedung WHERE kodeGedung IN (:gedungIds)")
//    List<Gedung> loadAllByIds(int[] gedungIds);

    @Query("SELECT * FROM gedung WHERE namaGedung LIKE :nama LIMIT 1")
    Gedung findByName(String nama);

    @Insert
    void insertAll(Gedung... gedungs);

    @Query("INSERT INTO gedung (kodeGedung, namaGedung) VALUES (:kodeGedung, :namaGedung)")
    void insertOne(String kodeGedung, String namaGedung);

    @Delete
    void delete(Gedung gedung);
}
