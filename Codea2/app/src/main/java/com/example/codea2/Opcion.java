package com.example.codea2;

public class Opcion {
    String id;
    String valor;

    Opcion(){}

    Opcion(String id, String valor){
        this.id = id;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "-"+valor+" ";
    }
}
