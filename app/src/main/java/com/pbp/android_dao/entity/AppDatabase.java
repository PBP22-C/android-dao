package com.pbp.android_dao.entity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Ruangan.class, Gedung.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RuanganDAO ruanganDAO();
    public abstract GedungDAO gedungDAO();
}
