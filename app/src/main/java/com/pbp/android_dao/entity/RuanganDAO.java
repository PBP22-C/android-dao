package com.pbp.android_dao.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RuanganDAO {
    @Query("SELECT * FROM ruangan")
    List<Ruangan> getAll();

    @Query("SELECT * FROM ruangan WHERE kodeRuangan IN (:ruanganIds)")
    List<Ruangan> loadAllByIds(int[] ruanganIds);

    @Query("SELECT * FROM ruangan WHERE nama LIKE :nama LIMIT 1")
    Ruangan findByName(String nama);

    @Insert
    void insertAll(Ruangan... ruangans);

    @Delete
    void delete(Ruangan ruangan);
}
