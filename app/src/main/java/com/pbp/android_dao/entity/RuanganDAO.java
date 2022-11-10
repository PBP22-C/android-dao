package com.pbp.android_dao.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RuanganDAO {
    @Query("SELECT * FROM ruangan")
    List<Ruangan> getAll();

    @Query("SELECT * FROM ruangan WHERE kodeRuangan IN (:ruanganIds)")
    List<Ruangan> loadAllByIds(int[] ruanganIds);

    @Query("SELECT * FROM ruangan WHERE nama LIKE :nama LIMIT 1")
    Ruangan findByName(String nama);

    @Query("SELECT * FROM ruangan WHERE kodeRuangan LIKE :kodeRuangan LIMIT 1")
    List<Ruangan> findByKode(String kodeRuangan);

    @Insert
    void insertAll(Ruangan... ruangans);

    @Query("INSERT INTO ruangan (kodeRuangan, nama, kapasitas, kodeGedung) VALUES (:kodeRuangan, :nama,:kapasitas, :kodeGedung)")
    void insertOne(String kodeRuangan, String nama, int kapasitas, String kodeGedung);

    @Update
    void update(Ruangan ruangan);

    @Delete
    void delete(Ruangan ruangan);
}
