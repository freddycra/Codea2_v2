package com.example.codea2;

public class Puntaje {
    String id;
    long puntaje;
    String email_Usuario;

    public Puntaje(){
        this.id = "indefinido";
        this.puntaje = 0;
        this.email_Usuario = "indefinido";
    }

    public Puntaje(String id, long puntaje, String email_Usuario) {
        this.id = id;
        this.puntaje = puntaje;
        this.email_Usuario = email_Usuario;
    }

    public String getId() {
        return id;
    }

    public long getPuntaje() {
        return puntaje;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPuntaje(long puntaje) {
        this.puntaje = puntaje;
    }

    public String getEmail_Usuario() {
        return email_Usuario;
    }

    public void setEmail_Usuario(String email_Usuario) {
        this.email_Usuario = email_Usuario;
    }
}
