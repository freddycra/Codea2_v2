package com.example.codea2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Preguntas extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int contador = 0;

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

        LeeObjetoEnFirebase("1");

    } // Fin del Oncreate de la Actividad 02

    private void escribirPregunta(String id, String pregunta, String respuesta, List<Opcion> opciones) {
        Pregunta p = new Pregunta(id,pregunta,respuesta,opciones);
        myRef.child(String.valueOf(contador)).setValue(p);
    }
    public void LeeObjetoEnFirebase(String nombreobj) {
//        myRef = database.getReference();
        myRef.child(nombreobj).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Pregunta p = dataSnapshot.getValue(Pregunta.class);
                TextView Mi_textview = (TextView) findViewById(R.id.textViewPregunta);
                Button Mi_button = (Button) findViewById(R.id.r1);
                Mi_button.setText(p.getOpciones().get(0).getValor());
                Mi_button = (Button) findViewById(R.id.r2);
                Mi_button.setText(p.getOpciones().get(1).getValor());
                Mi_button = (Button) findViewById(R.id.r3);
                Mi_button.setText(p.getOpciones().get(2).getValor());
                Mi_button = (Button) findViewById(R.id.r4);
                Mi_button.setText(p.getOpciones().get(3).getValor());
                Mi_textview.setText(p.getPregunta());
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
                        // Write a message to the database


                        ArrayList<Opcion> ops = new ArrayList<>();
                        ops.add(new Opcion("1","opcion1"));
                        ops.add(new Opcion("2","opcion2"));
                        ops.add(new Opcion("3","opcion3"));
                        ops.add(new Opcion("4","opcion4"));
                        contador = contador+1;
                        escribirPregunta(String.valueOf(contador), "pregunta "+contador,"cualquiera",ops);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //MensajeOK(dataSnapshot.toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Mensaje("Writing in DB");

                        break;

                    case R.id.r2:
                        Mensaje("Implementar Button2");

                        break;

                    case R.id.r3:
                        Mensaje("Implementar Button3");

                        break;

                    case R.id.r4:
                        Mensaje("Implementar Button4");

                        break;
                    default:break; }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

    public void Mensaje(String msg){ Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); };

} // [3:47:42 PM] Fin de la Clase Actividad 02