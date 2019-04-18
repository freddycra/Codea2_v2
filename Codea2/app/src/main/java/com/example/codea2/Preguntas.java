package com.example.codea2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preguntas extends AppCompatActivity {

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


    } // Fin del Oncreate de la Actividad 02


    public void OnclickDelButton(int ref) {

        // Ejemplo  OnclickDelButton(R.id.MiButton);
        // 1 Doy referencia al Button
        View view =findViewById(ref);
        Button miButton = (Button) view;
        //  final String msg = miButton.getText().toString();
        // 2.  Programar el evento onclick
        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(msg.equals("Texto")){Mensaje("Texto en el botón ");};
                switch (v.getId()) {

                    case R.id.r1:
                        Mensaje("Implementar Button1");

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