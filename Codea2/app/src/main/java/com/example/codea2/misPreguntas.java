package com.example.codea2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;

public class misPreguntas extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_preguntas);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(misPreguntas.this, singin_activity.class));
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));
        getSupportActionBar().setTitle("Mis Preguntas - " + mAuth.getCurrentUser().getEmail());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), crearPregunta.class));
            }
        });
        //TODO mostrar las preguntas que ha hecho el usuario

        // alambramos el Button
        Button MiButton = (Button) findViewById(R.id.buttonMP);

        //Programamos el evento onclick
        MiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                DesplegarMisPreguntas();
            }

        });

    }

    private void DesplegarMisPreguntas() {
        VariablesGlobales.getInstance().getMyRef().child("preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                View linearLayout;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    try {
                        final Pregunta pregunta = postSnapshot.getValue(Pregunta.class);
                        System.out.println(pregunta.getPregunta());
                        linearLayout =  findViewById(R.id.layoutMP);
                        TextView valueTV = new TextView(getApplicationContext());
                        valueTV.setText(pregunta.toString());
                        valueTV.setId(Integer.parseInt(pregunta.getId()));
                        valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                        valueTV.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View arg0) {
                                DialogSiNO_01(pregunta);
                            }
                        });
                        ((LinearLayout) linearLayout).addView(valueTV);
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                        continue;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void DialogSiNO_01(Pregunta p){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Quieres editar la pregunta?");
        VariablesGlobales.getInstance().setAuxPregunta(p);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {startActivity(new Intent(getApplicationContext(), EditarPregunta.class)); } });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {VariablesGlobales.getInstance().setAuxPregunta(null); } });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void Mensaje(String msg){Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}
