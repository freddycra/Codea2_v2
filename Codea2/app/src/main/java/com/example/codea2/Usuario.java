package com.example.codea2;

public class Usuario {

    private String correo;
    private String nombre;
    private String apellido;
    private String nickname;
    private int edad;
    private String ubicacion;


    public Usuario(){
        this.correo = "indefinido";
        this.nombre = "indefinido";
        this.apellido = "indefinido";
        this.nickname = "indefinido";
        this.edad = 0;
        this.ubicacion = "indefinido";
    }

    public Usuario(String correo, String nombre, String apellido, String nickname, int edad, String ubicacion) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.edad = edad;
        this.ubicacion = ubicacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
