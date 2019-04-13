package com.example.codea2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Puntajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        // A continuación mi código para OnCreate
        Mensaje("PUNTAJES GLOBALES");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

        getSupportActionBar().setTitle("Puntajes más altos");

    } // Fin del Oncreate de la Actividad 03

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

} // [4:42:01 PM] Fin de la Clase Actividad 03