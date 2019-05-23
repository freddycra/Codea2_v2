package com.example.codea2;

public class Puntaje {
    String id;
    int puntaje;
    String email_Usuario;

    public Puntaje(String id, int puntaje, String email_Usuario) {
        this.id = id;
        this.puntaje = puntaje;
        this.email_Usuario = email_Usuario;
    }

    public String getId() {
        return id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getEmail_Usuario() {
        return email_Usuario;
    }

    public void setEmail_Usuario(String email_Usuario) {
        this.email_Usuario = email_Usuario;
    }
}
