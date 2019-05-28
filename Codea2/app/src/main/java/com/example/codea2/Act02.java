package com.example.codea2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Act02 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int candado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act02);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        candado = 0;


        bloquearComponentes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                if(candado==0){
                    fab.setImageResource(R.drawable.candadoopen);
                    //Inicia la edición de datos del usuario
                    desbloquearComponentes();
                    //Fin de edición
                    Snackbar.make(view, "Ya puede editar la información.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    candado = 1;
                }else{
                    fab.setImageResource(R.drawable.candadoclose);
                    bloquearComponentes();
                    //Guarda los nuevos datos del usuario (manda los datos a Firebase)
                    EditText correo = (EditText) findViewById(R.id.correoUsuario);
                    String correoNuevo="indefinido";
                    if(!(correo.getText().toString()).equals("")){correoNuevo=correo.getText().toString();}

                    EditText nombre = (EditText) findViewById(R.id.nombreUsuario);
                    String nombreNuevo = "indefinido";
                    if(!(nombre.getText().toString()).equals("")){nombreNuevo=nombre.getText().toString();}

                    EditText apellido = (EditText) findViewById(R.id.apellidoUsuario);
                    String apellidoNuevo = "indefinido";
                    if(!(apellido.getText().toString()).equals("")){apellidoNuevo=apellido.getText().toString();}

                    EditText nickname = (EditText) findViewById(R.id.nickUsuario);
                    String nicknameNuevo = "indenifido";
                    if(!(nickname.getText().toString()).equals("")){nicknameNuevo=nickname.getText().toString();}

                    EditText edad = (EditText) findViewById(R.id.edadUsuario);
                    int edadNueva = 0;
                    if(!(edad.getText().toString()).equals("")){
                        try {
                            edadNueva = Integer.parseInt(edad.getText().toString());
                        }catch (Exception e){
                        }
                    }
                    EditText ubicacion = (EditText) findViewById(R.id.ubicacionCorreo);
                    String ubicacionNueva = "indefinido";
                    if(!(ubicacion.getText().toString()).equals("")){ubicacionNueva=ubicacion.getText().toString();}

                    Usuario us = new Usuario(correoNuevo,nombreNuevo,apellidoNuevo,nicknameNuevo,edadNueva,ubicacionNueva);
                    VariablesGlobales.getInstance().getMyRef().child("usuarios").child(us.getNickname()).setValue(us);
                    VariablesGlobales.getInstance().setUsuarioGlobal(us);//Se edita el usuario actual



                    TextView t = (TextView) findViewById(R.id.correoUsuario);
                    t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getCorreo());

                    t = (TextView) findViewById(R.id.nombreUsuario);
                    t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getNombre());


                    t = (TextView) findViewById(R.id.apellidoUsuario);
                    t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getApellido());

                    t = (TextView) findViewById(R.id.nickUsuario);
                    t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getNickname());

                    t = (TextView) findViewById(R.id.edadUsuario);
                    t.setText(String.valueOf(VariablesGlobales.getInstance().getUsuarioGlobal().getEdad()));

                    t = (TextView) findViewById(R.id.ubicacionCorreo);
                    t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getUbicacion());
                    //Fin de guardar los datos
                    Snackbar.make(view, "Ya está en modo seguro.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    candado = 0;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // A continuación mi código para OnCreate
        Mensaje("Bienvenido Actividad 02");

        TextView t = (TextView) findViewById(R.id.correoUsuario);
        t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getCorreo());

        t = (TextView) findViewById(R.id.nombreUsuario);
        t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getNombre());


        t = (TextView) findViewById(R.id.apellidoUsuario);
        t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getApellido());

        t = (TextView) findViewById(R.id.nickUsuario);
        t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getNickname());

        t = (TextView) findViewById(R.id.edadUsuario);
        t.setText(String.valueOf(VariablesGlobales.getInstance().getUsuarioGlobal().getEdad()));

        t = (TextView) findViewById(R.id.ubicacionCorreo);
        t.setText(VariablesGlobales.getInstance().getUsuarioGlobal().getUbicacion());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));
        getSupportActionBar().setTitle("Información de Usuario");
    } // Fin del Oncreate de la Actividad 02

    public void Mensaje(String msg){Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act02, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.inicio) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else if (id == R.id.jugar) {

        } else if (id == R.id.mi_puntaje) {
            Intent i = new Intent(getApplicationContext(), Puntajes.class);
            startActivity(i);
        } else if (id == R.id.mi_cuenta) {

        } else if (id == R.id.mis_preguntas) {
            Intent i = new Intent(getApplicationContext(), misPreguntas.class);
            startActivity(i);
        } else if (id == R.id.desarrolladores) {
            Intent i = new Intent(getApplicationContext(), Desarrolladores.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bloquearComponentes(){
        EditText correo = (EditText) findViewById(R.id.correoUsuario);
        correo.setFocusable(false);
        correo.setFocusableInTouchMode(false);
        correo.setClickable(false);

        EditText nombre = (EditText) findViewById(R.id.nombreUsuario);
        nombre.setFocusable(false);
        nombre.setFocusableInTouchMode(false);
        nombre.setClickable(false);

        EditText apellido = (EditText) findViewById(R.id.apellidoUsuario);
        apellido.setFocusable(false);
        apellido.setFocusableInTouchMode(false);
        apellido.setClickable(false);

        EditText nickname = (EditText) findViewById(R.id.nickUsuario);
        nickname.setFocusable(false);
        nickname.setFocusableInTouchMode(false);
        nickname.setClickable(false);

        EditText edad = (EditText) findViewById(R.id.edadUsuario);
        edad.setFocusable(false);
        edad.setFocusableInTouchMode(false);
        edad.setClickable(false);

        EditText ubicacion = (EditText) findViewById(R.id.ubicacionCorreo);
        ubicacion.setFocusable(false);
        ubicacion.setFocusableInTouchMode(false);
        ubicacion.setClickable(false);
    }

    public void desbloquearComponentes(){

        EditText nombre = (EditText) findViewById(R.id.nombreUsuario);
        nombre.setFocusable(true);
        nombre.setFocusableInTouchMode(true);
        nombre.setClickable(true);

        EditText apellido = (EditText) findViewById(R.id.apellidoUsuario);
        apellido.setFocusable(true);
        apellido.setFocusableInTouchMode(true);
        apellido.setClickable(true);

        EditText edad = (EditText) findViewById(R.id.edadUsuario);
        edad.setFocusable(true);
        edad.setFocusableInTouchMode(true);
        edad.setClickable(true);

        EditText ubicacion = (EditText) findViewById(R.id.ubicacionCorreo);
        ubicacion.setFocusable(true);
        ubicacion.setFocusableInTouchMode(true);
        ubicacion.setClickable(true);
    }
} // [12:29:16 PM] Fin de la Clase Actividad 02