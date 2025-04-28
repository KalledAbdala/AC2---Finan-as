package com.example.minhasfinancas.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.minhasfinancas.model.Gasto;

import java.util.List;

@Dao
public interface GastoDao {

    @Insert
    void inserir(Gasto gasto);

    @Transaction
    default void inserirGastoComTransacao(Gasto gasto) {
        inserir(gasto);
    }

    @Query("SELECT * FROM gastos")
    List<Gasto> buscarTodos();
}
