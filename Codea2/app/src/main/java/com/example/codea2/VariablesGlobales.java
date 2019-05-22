package com.example.codea2;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VariablesGlobales {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int contador = 1;
    Pregunta auxPregunta;
    private static Usuario usuarioGlobal = new Usuario();

    private static VariablesGlobales instance = null;


    public DatabaseReference getMyRef() {
        return myRef;
    }

    public void setMyRef(DatabaseReference myRef) {
        this.myRef = myRef;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public static Usuario getUsuarioGlobal() {
        return usuarioGlobal;
    }

    public static void setUsuarioGlobal(Usuario usuarioGlobal) {
        VariablesGlobales.usuarioGlobal = usuarioGlobal;
    }
    public Pregunta getAuxPregunta() {
        return auxPregunta;
    }

    public void setAuxPregunta(Pregunta auxPregunta) {
        this.auxPregunta = auxPregunta;
    }

    protected VariablesGlobales() {
        myRef.child("preguntas").child("cantidad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //El contador se obtiene desde la base, el último número
                contador = dataSnapshot.getValue(Integer.class);
                System.out.println("Este es el contador"+contador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static VariablesGlobales getInstance() {
        if(instance == null) {instance = new VariablesGlobales(); }
        return instance;
    }



}
