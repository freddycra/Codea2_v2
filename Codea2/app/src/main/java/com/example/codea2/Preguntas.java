package com.example.codea2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Preguntas extends AppCompatActivity {

    Pregunta pregunta;
    int puntos = 0;
    //TODO agregar un array con las preguntas que ya se han contestado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        getSupportActionBar().setTitle("PLAY TIME");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

        OnclickDelButton(R.id.r1);
        OnclickDelButton(R.id.r2);
        OnclickDelButton(R.id.r3);
        OnclickDelButton(R.id.r4);

        leerPregunta();

    } // Fin del Oncreate de la Actividad 02

    public void leerPregunta(){
        Random rand = new Random();
        int n = rand.nextInt(VariablesGlobales.getInstance().getContador())+1;
        Log.i("Valor aleatorio",String.valueOf(n));
        LeeObjetoEnFirebase(String.valueOf(n));
    }

    public void LeeObjetoEnFirebase(String nombreobj) {
        VariablesGlobales.getInstance().getMyRef().child(nombreobj).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pregunta = dataSnapshot.getValue(Pregunta.class);
                TextView Mi_textview = (TextView) findViewById(R.id.textViewPregunta);
                Mi_textview.setText(pregunta.getPregunta());
                Button Mi_button = (Button) findViewById(R.id.r1);
                Mi_button.setText(pregunta.getOpciones().get(0).getValor());
                Mi_button = (Button) findViewById(R.id.r2);
                Mi_button.setText(pregunta.getOpciones().get(1).getValor());
                if(pregunta.getOpciones().size()>2) {
                    findViewById(R.id.r3).setVisibility(View.VISIBLE);
                    findViewById(R.id.r4).setVisibility(View.VISIBLE);
                    Mi_button = (Button) findViewById(R.id.r3);
                    Mi_button.setText(pregunta.getOpciones().get(2).getValor());
                    Mi_button = (Button) findViewById(R.id.r4);
                    Mi_button.setText(pregunta.getOpciones().get(3).getValor());
                }else{
                    findViewById(R.id.r3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.r4).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void OnclickDelButton(int ref) {

        View view =findViewById(ref);
        Button miButton = (Button) view;
        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.r1:
                        if("1".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        }else{
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }

                        break;

                    case R.id.r2:
                        if("2".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        }else{
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;

                    case R.id.r3:
                        if("3".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        }else{
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;

                    case R.id.r4:
                        if("4".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        }else{
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;
                    default:break; }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

    public void verificaRespuesta(boolean correcta){
        if(correcta){
            TextView tv = (TextView) findViewById(R.id.puntos);
            tv.setText(String.valueOf(++puntos));
            leerPregunta();
        }else{
            finish();
        }
    }




    public void Mensaje(String msg){ Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); };

} // [3:47:42 PM] Fin de la Clase Actividad 02