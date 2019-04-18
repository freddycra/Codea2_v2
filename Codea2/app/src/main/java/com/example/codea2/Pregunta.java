package com.example.codea2;

import java.util.List;

public class Pregunta {
    private String id;
    private String pregunta;
    private String resputesta;
    private List<Opcion> opciones;

    Pregunta(){}

    Pregunta(String id, String pregunta, String respuesta,List opciones){
        this.id = id;
        this.pregunta= pregunta;
        this.resputesta = respuesta;
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
}
