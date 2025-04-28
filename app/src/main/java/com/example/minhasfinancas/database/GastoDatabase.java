package com.example.minhasfinancas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.minhasfinancas.dao.GastoDao;
import com.example.minhasfinancas.model.Gasto;

@Database(entities = {Gasto.class}, version = 1)
public abstract class GastoDatabase extends RoomDatabase {

    private static GastoDatabase instance;

    public abstract GastoDao gastoDao();

    public static synchronized GastoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            GastoDatabase.class, "gastos_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
