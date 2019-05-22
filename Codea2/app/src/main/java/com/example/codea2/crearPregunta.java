package com.example.codea2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class crearPregunta extends AppCompatActivity {
    //TODO poner estas variables en una clase VariablesGlobales

    int correcta = 1; //Marca la opcion que es correcta
    Pregunta p;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Uri imgUri;


    public static final int REQUEST_CODE = 1234;
    public static String url2 = "";
    public static int MULTIMEDIA; // 1->Image, 2->Audio, 3->Video


    private MediaRecorder recorder;
    private String fileName = null;
    private static final String LOG_TAG = "Record_log";

    private ProgressDialog mProgress;

    static final int REQUEST_VIDEO_CAPTURE = 100;
    private Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta);

        numeroOpciones();
        opcionCorrecta();
        botonCrear();

        // MULTIMEDIA START

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Button buttonGrabar = (Button) findViewById(R.id.grabarButton);
        buttonGrabar.setVisibility(buttonGrabar.INVISIBLE);



        Button MiButton = (Button) findViewById(R.id.addButton);
        registerForContextMenu(MiButton);

        MiButton.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                openContextMenu(findViewById(R.id.addButton));

            }

        });

        mProgress = new ProgressDialog(this);
        p = new Pregunta();
    }


    //TODO validar que los campos no esten vacios
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
                //Se puede hacer poniendo un documento la cantidad de preguntas
                VariablesGlobales.getInstance().setContador(VariablesGlobales.getInstance().getContador()+1);

                //Se crea la pregunta
                EditText etPregunta = (EditText) findViewById(R.id.editTextPregunta);
                escribirPregunta(String.valueOf(VariablesGlobales.getInstance().getContador()),
                        etPregunta.getText().toString(),
                        String.valueOf(correcta),
                        ops);

                Mensaje("Se ha agregado la pregunta");
                finish();

            }
        });
    }

    private void escribirPregunta(String id, String pregunta, String respuesta, List<Opcion> opciones) {
        p.setId(id);
        p.setPregunta(pregunta);
        p.setResputesta(respuesta);
        p.setOpciones(opciones);
        VariablesGlobales.getInstance().getMyRef().child("preguntas").child(String.valueOf(VariablesGlobales.getInstance().getContador())).setValue(p);//Acá se llama al contador de preguntas.......
        VariablesGlobales.getInstance().getMyRef().child("preguntas").child("cantidad").setValue(VariablesGlobales.getInstance().getContador());
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


    //Métodos del menu de contexto

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.addButton:
                MenuInflater infla =getMenuInflater();
                infla.inflate(R.menu.add, menu);
                break;

            default:  Mensaje("No clasificado"); break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int opcionseleccionada = item.getItemId();
        switch (opcionseleccionada) {
            case R.id.imagenItem:
                saveImage();
                break;
            case R.id.audioItem:


                Button buttonAdd = (Button) findViewById(R.id.addButton);
                buttonAdd.setVisibility(buttonAdd.INVISIBLE);


                fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                fileName += "/recorded_audio.mp3";

                Button buttonGrabar = (Button) findViewById(R.id.grabarButton);
                buttonGrabar.setVisibility(buttonGrabar.VISIBLE);

                buttonGrabar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        MULTIMEDIA = 2;
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            sonidoGrabar();
                            startRecording();
                        }else if(event.getAction() == MotionEvent.ACTION_UP){
                            sonidoGrabar();
                            stopRecording();
                        }
                        return false;
                    }
                });

                break;
            case R.id.videoItem:
                dispatchTakeVideoIntent();
                break;

            default:  Mensaje("No clasificado"); break;
        }

        return true;
    }


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};




    // MULTIMEDIAS MULTIMEDIAS MULTIMEDIAS


    //Parte de imágenes
    public void saveImage(){
        MULTIMEDIA = 1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(MULTIMEDIA==1){//Se trata de imagen
            if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
                imgUri = data.getData();
                btnUpload_Click();
            }
        }else if(MULTIMEDIA==3){//Se trata de Audio
            if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri videoUri = data.getData();//Revisar
                Toast.makeText(getApplicationContext(), "Video Record Successfully", Toast.LENGTH_LONG).show();
                uploadvideo(videoUri);
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void btnUpload_Click(){
        if(imgUri != null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();
            //Get the storage reference
            final StorageReference ref = mStorageRef.child("image/" + System.currentTimeMillis() + "."+getImageExt(imgUri));
            //Add file to reference
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //Dimiss dialog when succes
                            dialog.dismiss();
                            Uri downloadUrl = uri;
                            url2 = downloadUrl.toString();


                            //Display success toas msg
                            Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                            p.setUrl(url2);
                            p.setTipo(1);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Dimiss dialog when succes
                    dialog.dismiss();

                    //Display success toas msg
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //Show uploaded progress

                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialog.setMessage("Uploaded " + (int)progress + "%");
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }



    //Métodos del Audio
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        uploadAudio();
    }


    private void sonidoGrabar(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.burbuja);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    private void uploadAudio() {
        mProgress.setMessage("Uploading Audio...");
        mProgress.show();

        //Generando un nombre unico para el audio

        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i=0; i < 10 ; i++) {
            n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
            cadena += n; }
        cadena = cadena + ".mp3";
        //FIN

        final StorageReference filepath = mStorageRef.child("audio/").child(cadena);
        Uri uri = Uri.fromFile(new File(fileName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //Dimiss dialog when succes
                        mProgress.dismiss();
                        Uri downloadUrl = uri;
                        url2 = downloadUrl.toString();
                        //Display success toas msg
                        Toast.makeText(getApplicationContext(), "Audio uploaded", Toast.LENGTH_SHORT).show();
                        p.setUrl(url2);
                        p.setTipo(2);

                        Button buttonGrabar = (Button) findViewById(R.id.grabarButton);
                        buttonGrabar.setVisibility(buttonGrabar.INVISIBLE);

                        Button buttonAdd = (Button) findViewById(R.id.addButton);
                        buttonAdd.setVisibility(buttonAdd.VISIBLE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Dimiss dialog when succes
                mProgress.dismiss();

                //Display success toas msg
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //Show uploaded progress

                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                mProgress.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }



    //Métodos de Video Recorder

    public void dispatchTakeVideoIntent(){
        MULTIMEDIA = 3;
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
    }



    private void uploadvideo(Uri uri) {
        mProgress.setMessage("Uploading Video...");
        mProgress.show();

        //Generando un nombre unico para el audio

        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i=0; i < 10 ; i++) {
            n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
            cadena += n; }
        cadena = cadena + ".mp4";
        //FIN

        final StorageReference filepath = mStorageRef.child("video/").child(cadena);

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //Dimiss dialog when succes
                        mProgress.dismiss();
                        Uri downloadUrl = uri;
                        url2 = downloadUrl.toString();


                        //Display success toas msg
                        Toast.makeText(getApplicationContext(), "Video uploaded", Toast.LENGTH_SHORT).show();
                        p.setUrl(url2);
                        p.setTipo(3);
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Dimiss dialog when succes
                mProgress.dismiss();

                //Display success toast msg
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //Show uploaded progress

                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
               mProgress.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }
}
