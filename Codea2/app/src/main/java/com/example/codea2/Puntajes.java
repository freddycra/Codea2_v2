package com.example.codea2;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MicrophoneInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Puntajes extends AppCompatActivity {
    ArrayList<String> pAlto = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        // A continuación mi código para OnCreate
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));
        getSupportActionBar().setTitle("*** MI HISTORIAL DE PUNTAJES ***");

        DesplegarPuntajes();

        /*Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //pAlto.clear();
            }

        });*/


        Button MiButton = (Button) findViewById(R.id.navegador);

        //Programamos el evento onclick

        MiButton.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                Uri uri = Uri.parse("http://www.gefcasino.com/Codea2/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }

        });
    }

    public void MensajeOK(String msg){
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder1 = new AlertDialog.Builder( v1.getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {} });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        ;};

    public void Mensaje(String msg){ Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    private void DesplegarPuntajes() {
        VariablesGlobales.getInstance().getMyRef().child("puntajes").orderByChild("puntaje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ArrayList<String> pAlto = new ArrayList<String>();
                int contador = VariablesGlobales.getInstance().getContScore();
                pAlto.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    try {
                        final Puntaje puntaje = postSnapshot.getValue(Puntaje.class);
                        if(puntaje.getEmail_Usuario().equals(VariablesGlobales.getUsuarioGlobal().getCorreo())) {
                            pAlto.add(String.valueOf(contador) + ". " + puntaje.getEmail_Usuario() + " - " + String.valueOf(puntaje.getPuntaje()));
                            contador = contador - 1;
                        }
                    }catch (Exception ex){
                        // System.out.println(ex.getMessage());
                        // Mensaje("Error: "+ex.getMessage());
                        continue;
                    }
                }
                llenar();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    void llenar(){
        Collections.reverse(pAlto);
        ArrayAdapter<String> adaptador =new ArrayAdapter(this, android.R.layout.simple_list_item_1, pAlto);
        ListView milistview = (ListView) findViewById(R.id.dynamic);
        milistview.setAdapter(adaptador);
        // pAlto.clear();
    }

} // [4:42:01 PM] Fin de la Clase Actividad 03