package com.example.codea2;

import java.util.List;

public class Pregunta {
    private String id;
    private String pregunta;
    private String resputesta;
    private int tipo; // 0-Nada 1-Imagen 2-Audio 3-Video
    private String url;
    private String usuario;
    private int reportes;
    private List<Opcion> opciones;

    Pregunta(){}

    Pregunta(String id, String pregunta, String respuesta, int tipo, String url,String usuario, int reportes, List opciones){
        this.id = id;
        this.pregunta= pregunta;
        this.resputesta = respuesta;
        this.tipo = tipo;
        this.url = url;
        this.usuario = usuario;
        this.reportes = reportes;
        this.opciones = opciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getResputesta() {
        return resputesta;
    }

    public void setResputesta(String resputesta) {
        this.resputesta = resputesta;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getReportes() {
        return reportes;
    }

    public void setReportes(int reportes) {
        this.reportes = reportes;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append("ID: "+id+"\n");
        r.append(pregunta+"\n");
        r.append(opciones.toString());
        r.append("\nRespuesta: "+resputesta+"\n\n");
        return r.toString();
    }
}
