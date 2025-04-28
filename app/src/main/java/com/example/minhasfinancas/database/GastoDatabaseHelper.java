package com.example.minhasfinancas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GastoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gastos.db";
    private static final int DATABASE_VERSION = 1;

    public GastoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE gastos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "descricao TEXT," +
                        "valor REAL," +
                        "categoria TEXT," +
                        "data TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS gastos");
        onCreate(db);
    }
}
