package com.example.minhasfinancas.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.minhasfinancas.model.Gasto;
import com.example.minhasfinancas.dao.GastoDao;

@Database(entities = {Gasto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GastoDao gastoDao();
}
