package com.example.codea2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Preguntas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        getSupportActionBar().setTitle("PLAY TIME");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

    } // Fin del Oncreate de la Actividad 02

    public void Mensaje(String msg){ Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); };

} // [3:47:42 PM] Fin de la Clase Actividad 02