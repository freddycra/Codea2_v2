package com.example.codea2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class crearPregunta extends AppCompatActivity {
    //TODO poner estas variables en una clase VariablesGlobales
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int contador = 1;
    int correcta = 1; //Marca la opcion que es correcta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta);

        numeroOpciones();
        opcionCorrecta();
        botonCrear();
        myRef.child("cantidad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contador = dataSnapshot.getValue(Integer.class);
                Mensaje("Este es el contador"+contador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void botonCrear(){
        Button MiButton = (Button) findViewById(R.id.btnCrear);
        MiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //Se crean las opciones
                ArrayList<Opcion> ops = new ArrayList<>();
                RadioButton r1 = (RadioButton) findViewById(R.id.radioButton);

                TextView op = (TextView) findViewById(R.id.op1);
                ops.add(new Opcion("1",op.getText().toString()));
                op = (TextView) findViewById(R.id.op2);
                ops.add(new Opcion("2",op.getText().toString()));

                //Se crean 3 y 4 solo si son 4 opciones
                if(r1.isChecked()) {
                    op = (TextView) findViewById(R.id.op3);
                    ops.add(new Opcion("3", op.getText().toString()));
                    op = (TextView) findViewById(R.id.op4);
                    ops.add(new Opcion("4", op.getText().toString()));
                }

                //Se aumenta el ID de la pregunta
                //TODO cambiar el contador por el numero de docuemntos de la base
                //Se puede hacer poniendo un documento la cantidad de preguntas
                contador = contador+1;
                //Se crea la pregunta
                EditText etPregunta = (EditText) findViewById(R.id.editTextPregunta);
                escribirPregunta(String.valueOf(contador), etPregunta.getText().toString(),String.valueOf(correcta),ops);
                Mensaje("Se ha agregado la pregunta");
                finish();

            }
        });
    }

    private void escribirPregunta(String id, String pregunta, String respuesta, List<Opcion> opciones) {
        myRef.child(String.valueOf(contador)).setValue(new Pregunta(id,pregunta,respuesta,opciones));
    }

    private void opcionCorrecta(){
        final Switch s1 = (Switch) findViewById(R.id.switch1);
        final Switch s2 = (Switch) findViewById(R.id.switch2);
        final Switch s3 = (Switch) findViewById(R.id.switch3);
        final Switch s4 = (Switch) findViewById(R.id.switch4);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s2.setChecked(false);
                    s3.setChecked(false);
                    s4.setChecked(false);
                    correcta = 1;
                    Mensaje("La opcion 1 es la correcta");
                }
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s1.setChecked(false);
                    s3.setChecked(false);
                    s4.setChecked(false);
                    correcta = 2;
                    Mensaje("La opcion 2 es la correcta");
                }
            }
        });
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s1.setChecked(false);
                    s2.setChecked(false);
                    s4.setChecked(false);
                    correcta = 3;
                    Mensaje("La opcion 3 es la correcta");
                }
            }
        });
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    s1.setChecked(false);
                    s2.setChecked(false);
                    s3.setChecked(false);
                    correcta = 4;
                    Mensaje("La opcion 4 es la correcta");
                }
            }
        });
    }

    private void numeroOpciones(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
                if (rb1.isChecked()) {
                    findViewById(R.id.op3).setVisibility(View.VISIBLE);
                    findViewById(R.id.switch3).setVisibility(View.VISIBLE);
                    findViewById(R.id.op4).setVisibility(View.VISIBLE);
                    findViewById(R.id.switch4).setVisibility(View.VISIBLE);
                }
                if (rb2.isChecked()) {
                    //TODO revisar que el switch funcione
                    findViewById(R.id.op3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.switch3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.op4).setVisibility(View.INVISIBLE);
                    findViewById(R.id.switch4).setVisibility(View.INVISIBLE);
                    Switch s1 = (Switch) findViewById(R.id.switch1);
                    s1.setChecked(true);
                    opcionCorrecta();
                }
            }
        });
    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}
