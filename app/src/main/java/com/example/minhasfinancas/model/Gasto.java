package com.example.minhasfinancas.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "gastos")
public class Gasto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String descricao;
    private double valor;
    private String categoria;
    private String data;

    // Construtor vazio (obrigatório para o Room)
    public Gasto() {}

    // Construtor cheio (opcional para usar no app)
    public Gasto(String descricao, double valor, String categoria, String data) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
