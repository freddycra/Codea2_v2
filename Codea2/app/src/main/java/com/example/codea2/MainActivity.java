package com.example.codea2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private Usuario recibido;
    private Usuario u;
    String nickname;

    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasWriteContactsPermission1 = checkSelfPermission(Manifest.permission.CAMERA);
        if (hasWriteContactsPermission1 != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }

        // A continuación mi código para OnCreate
        Mensaje("Codea2 te da la bienvenida.");
        recibido = new Usuario();
        u = new Usuario();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

        Button Jugar = (Button) findViewById(R.id.jugar);
        Button Puntajes = (Button) findViewById(R.id.puntajes);
        Button Cuenta = (Button) findViewById(R.id.cuenta);
        Button mis_preguntas = (Button) findViewById(R.id.mispreguntas);
        Button video = (Button) findViewById(R.id.video);

        Jugar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //TODO validacion si el numero de preguntas es 0
             startActivity(new Intent(getApplicationContext(), Preguntas.class));
            }
        });

        Puntajes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
             Intent intentN = new Intent(getApplicationContext(), Puntajes.class);
             startActivity(intentN);
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

            //ImageView singup = (ImageView) findViewById(R.id.logout);
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

        getSupportActionBar().setTitle("Inicio - " + mAuth.getCurrentUser().getEmail());

        //Actualizar la parte de usuarios
        String email2 = mAuth.getCurrentUser().getEmail().toString();
        String correo = email2.replace(".", "");
        String [] aux = correo.split("@");
        nickname = aux[0];
        u.setNickname(nickname);
        u.setNombre("indefinido");
        u.setApellido("indefinido");
        u.setEdad(0);
        u.setUbicacion("indefinido");

        VariablesGlobales.getInstance().getMyRef().child("usuarios").child(nickname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recibido = dataSnapshot.getValue(Usuario.class);
                if(recibido==null){
                    VariablesGlobales.getInstance().getMyRef().child("usuarios").child(nickname).setValue(u);
                    VariablesGlobales.getInstance().setUsuarioGlobal(u);//La variable global es el usuario nuevo
                }else{
                    VariablesGlobales.getInstance().setUsuarioGlobal(recibido);//La variable global es el usuario recibido
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    } // Fin del Oncreate de la Actividad 01

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
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
                mAuth.signOut();

                break;
            default:  Mensaje("No clasificado"); break;
        }
        return super.onOptionsItemSelected(item);
    }


} // [11:00:42 PM] Fin de la Clase Actividad 01