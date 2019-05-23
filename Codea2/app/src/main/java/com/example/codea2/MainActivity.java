package com.example.codea2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        // A continuación mi código para OnCreate
        Mensaje("Codea2 te da la bienvenida.");

        getSupportActionBar().setTitle("Inicio");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

        Button Jugar = (Button) findViewById(R.id.jugar);
        Button Cuenta = (Button) findViewById(R.id.cuenta);
        Button video = (Button) findViewById(R.id.video);
        Button mis_preguntas = (Button) findViewById(R.id.mispreguntas);

        Jugar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //TODO validacion si el numero de preguntas es 0
             startActivity(new Intent(getApplicationContext(), Preguntas.class));
            }
        });
        Cuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
             startActivity(new Intent(getApplicationContext(), Act02.class));
            }
        });
        video.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=LOuSxeO2kro&t=6s")));
            }
        });
        mis_preguntas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), misPreguntas.class));
            }
        });


            ImageView singup = (ImageView) findViewById(R.id.logout);
            mAuth = FirebaseAuth.getInstance();
            mAuthListner = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser()==null)
                    {
                        startActivity(new Intent(MainActivity.this, singin_activity.class));
                    }
                }
            };
            singup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                }
            });


    } // Fin del Oncreate de la Actividad 01

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

} // [11:00:42 PM] Fin de la Clase Actividad 01