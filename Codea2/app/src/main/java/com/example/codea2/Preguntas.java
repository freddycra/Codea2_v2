package com.example.codea2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Preguntas extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    Pregunta pregunta;
    List<Pregunta> listaPreguntas;
    int puntos = 0;
    ProgressDialog pd;

    final CountDownTimer cdt = new CountDownTimer(11000, 1000) {
        public void onTick(long millisUntilFinished) {
            getSupportActionBar().setTitle("PLAY TIME - TIMER: " + millisUntilFinished / 1000 + " - SCORE: " + puntos);
        }
        public void onFinish() {
            Mensaje("Se agotó el tiempo...");
            verificaRespuesta(false);
        }
    }.start();


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    //TODO agregar un array con las preguntas que ya se han contestado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        // getSupportActionBar().setTitle("PLAY TIME");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Background)));

        OnclickDelButton(R.id.r1);
        OnclickDelButton(R.id.r2);
        OnclickDelButton(R.id.r3);
        OnclickDelButton(R.id.r4);

        Button aux = (Button) findViewById(R.id.reproducirAudio);
        aux.setVisibility(aux.INVISIBLE); // Se hace invisible el botón de reproducir

        VideoView video = (VideoView) findViewById(R.id.reproducirVideo);
        video.setVisibility(video.INVISIBLE); // Se hace invisible el botón de reproducir video

        ImageView MiImageView = (ImageView) findViewById(R.id.volverRepreoducir);
        MiImageView.setVisibility(MiImageView.INVISIBLE);

        listaPreguntas = new ArrayList();
        leerPreguntasFirebase();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Preguntas.this, singin_activity.class));
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();

    } // Fin del Oncreate de la Actividad 02

    public void leerPreguntasFirebase() {
        VariablesGlobales.getInstance().getMyRef().child("preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        listaPreguntas.add(postSnapshot.getValue(Pregunta.class)); //Se meten todas las preguntas en un arraylist
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        continue;
                    }
                }
                Collections.shuffle(listaPreguntas); //Se mezclan las preguntas
                cargarPregunta(); //Se lee una nueva pregunta
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void cargarPregunta() {
        Button aux = (Button) findViewById(R.id.reproducirAudio);
        aux.setVisibility(aux.INVISIBLE); // Se hace invisible el botón de reproducir

        VideoView video = (VideoView) findViewById(R.id.reproducirVideo);
        video.setVisibility(video.INVISIBLE); // Se hace invisible el botón de reproducir video

        ImageView MiImageView = (ImageView) findViewById(R.id.volverRepreoducir);
        MiImageView.setVisibility(MiImageView.INVISIBLE);

        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setVisibility(img.VISIBLE);
        img.setImageResource(R.drawable.code);

        leerPregunta();
    }

    public void leerPregunta() {

        if(listaPreguntas.size()>0) {
            pregunta = listaPreguntas.remove(listaPreguntas.size()-1);
            TextView Mi_textview = (TextView) findViewById(R.id.textViewPregunta);
            Mi_textview.setText(pregunta.getPregunta());
            Button Mi_button = (Button) findViewById(R.id.r1);
            Mi_button.setText(pregunta.getOpciones().get(0).getValor());
            Mi_button = (Button) findViewById(R.id.r2);
            Mi_button.setText(pregunta.getOpciones().get(1).getValor());
            if (pregunta.getOpciones().size() > 2) {
                findViewById(R.id.r3).setVisibility(View.VISIBLE);
                findViewById(R.id.r4).setVisibility(View.VISIBLE);
                Mi_button = (Button) findViewById(R.id.r3);
                Mi_button.setText(pregunta.getOpciones().get(2).getValor());
                Mi_button = (Button) findViewById(R.id.r4);
                Mi_button.setText(pregunta.getOpciones().get(3).getValor());
            } else {
                findViewById(R.id.r3).setVisibility(View.INVISIBLE);
                findViewById(R.id.r4).setVisibility(View.INVISIBLE);
            }


            try {
                cargarMultimedia(pregunta.getTipo(), pregunta.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Activamos el timer.
            cdt.cancel();
            cdt.start();

        }else{ //Se acabaron las preguntas del ArrayList! Game Over.
            cdt.cancel();
            VariablesGlobales.getInstance().setContScore(VariablesGlobales.getInstance().getContScore()+1);

            Puntaje p = new Puntaje(
                    String.valueOf(VariablesGlobales.getInstance().getContScore()),
                    puntos,
                    mAuth.getCurrentUser().getEmail()
            );

            VariablesGlobales.getInstance().getMyRef().child("puntajes")
                    .child(String.valueOf(VariablesGlobales.getInstance().getContScore()))
                    .setValue(p);

            VariablesGlobales.getInstance().getMyRef().child("puntajes")
                    .child("contScore")
                    .setValue(VariablesGlobales.getInstance().getContScore());

            MensajeOK("FELICIDADES Ha ganado el juego con el puntaje maximo "+String.valueOf(puntos));
            //finish();
        }
    }

    private void cargarMultimedia(int tipo, final String urlPregunta) throws IOException {
        if (tipo == 1) {
            ImageView imgView = (ImageView) findViewById(R.id.imageView);
            Glide.with(this)
                    .load(urlPregunta)
                    .into(imgView);  // imageview object
        } else if (tipo == 2) {
            ImageView aux = (ImageView) findViewById(R.id.imageView);
            aux.setVisibility(aux.INVISIBLE); // Se hace invisible el image view4

            Button reproducir = (Button) findViewById(R.id.reproducirAudio);
            reproducir.setVisibility(reproducir.VISIBLE); // Se hace visible el botón de reproducir audio


            reproducir.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View arg0) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(urlPregunta);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();

                }

            });

        } else if (tipo == 3) {

            ImageView aux = (ImageView) findViewById(R.id.imageView);
            aux.setVisibility(aux.INVISIBLE); // Se hace invisible el image view4

            VideoView video = (VideoView) findViewById(R.id.reproducirVideo);
            video.setVisibility(video.VISIBLE); // Se hace invisible el botón de reproducir video


            pd = new ProgressDialog(Preguntas.this);
            pd.setMessage("Buffering video please wait...");
            pd.show();

            Uri uri = Uri.parse(urlPregunta);
            video.setVideoURI(uri);
            video.start();

            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //close the progress dialog when buffering is done
                    pd.dismiss();
                }
            });

            ImageView MiImageView = (ImageView) findViewById(R.id.volverRepreoducir);
            MiImageView.setVisibility(MiImageView.VISIBLE);

            MiImageView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View arg0) {
                    VideoView video = (VideoView) findViewById(R.id.reproducirVideo);
                    Uri uri = Uri.parse(urlPregunta);
                    video.setVideoURI(uri);
                    video.start();

                    video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //close the progress dialog when buffering is done
                            pd.dismiss();
                        }
                    });

                }

            });
        }
    }


    public void OnclickDelButton(int ref) {

        View view = findViewById(ref);
        Button miButton = (Button) view;
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.r1:
                        if ("1".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        } else {
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }

                        break;

                    case R.id.r2:
                        if ("2".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        } else {
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;

                    case R.id.r3:
                        if ("3".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        } else {
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;

                    case R.id.r4:
                        if ("4".equals(pregunta.getResputesta())) {
                            verificaRespuesta(true);
                            Mensaje("Respuesta Correcta");
                        } else {
                            verificaRespuesta(false);
                            Mensaje("Mala tontin");
                        }
                        break;
                    default:
                        break;
                }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

    public void verificaRespuesta(boolean correcta) {
        if (correcta) {
            // TextView tv = (TextView) findViewById(R.id.TextViewPuntos);
            puntos = puntos + 1;
            // tv.setText(String.valueOf(puntos));
            cargarPregunta();
        } else {
            VariablesGlobales.getInstance().setContScore(VariablesGlobales.getInstance().getContScore()+1);

            Puntaje p = new Puntaje(
                    String.valueOf(VariablesGlobales.getInstance().getContScore()),
                    puntos,
                    mAuth.getCurrentUser().getEmail()
                    );

            VariablesGlobales.getInstance().getMyRef().child("puntajes")
                    .child(String.valueOf(VariablesGlobales.getInstance().getContScore()))
                    .setValue(p);

            VariablesGlobales.getInstance().getMyRef().child("puntajes")
                    .child("contScore")
                    .setValue(VariablesGlobales.getInstance().getContScore());

            DialogSiNO_01();
        }
    }

    public void compartirResultados(String mensaje) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        startActivity(Intent.createChooser(intent, "Share with"));
    }


    public void DialogSiNO_01() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Desea compartir el puntaje obtenido?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String mensaje = "Hola! Mira mi nuevo resultado en Codea2:  " + puntos + "pts!";
                        compartirResultados(mensaje);
                        finish();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Mensaje("END GAME");
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    ;

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
    }

} // [3:47:42 PM] Fin de la Clase Actividad 02