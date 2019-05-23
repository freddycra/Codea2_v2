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

import java.util.ArrayList;
import java.util.List;

public class EditarPregunta extends AppCompatActivity {

    int correcta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pregunta);



        Pregunta p = VariablesGlobales.getInstance().getAuxPregunta();
        if(p != null) {
            EditText et = (EditText) findViewById(R.id.editTextPreguntaEP);
            et.setText(p.getPregunta());

            TextView tv1 = (TextView) findViewById(R.id.op1EP);
            tv1.setText(p.getOpciones().get(0).getValor());
            tv1 = (TextView) findViewById(R.id.op2EP);
            tv1.setText(p.getOpciones().get(1).getValor());

            if(p.getOpciones().size() > 2){
                tv1 = (TextView) findViewById(R.id.op3EP);
                tv1.setText(p.getOpciones().get(2).getValor());
                tv1 = (TextView) findViewById(R.id.op4EP);
                tv1.setText(p.getOpciones().get(3).getValor());
            }

        }else{
            Mensaje("Se ha producido un error");
            finish();
        }

        numeroOpciones();
        opcionCorrecta();
        botonActualizar();

    }

    private void numeroOpciones(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupEP);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButtonEP);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2EP);
                if (rb1.isChecked()) {
                    findViewById(R.id.op3EP).setVisibility(View.VISIBLE);
                    findViewById(R.id.switch3EP).setVisibility(View.VISIBLE);
                    findViewById(R.id.op4EP).setVisibility(View.VISIBLE);
                    findViewById(R.id.switch4EP).setVisibility(View.VISIBLE);
                }
                if (rb2.isChecked()) {
                    findViewById(R.id.op3EP).setVisibility(View.INVISIBLE);
                    findViewById(R.id.switch3EP).setVisibility(View.INVISIBLE);
                    findViewById(R.id.op4EP).setVisibility(View.INVISIBLE);
                    findViewById(R.id.switch4EP).setVisibility(View.INVISIBLE);
                    Switch s1 = (Switch) findViewById(R.id.switch1EP);
                    s1.setChecked(true);
                    opcionCorrecta();
                }
            }
        });
    }

    private void opcionCorrecta(){
        final Switch s1 = (Switch) findViewById(R.id.switch1EP);
        final Switch s2 = (Switch) findViewById(R.id.switch2EP);
        final Switch s3 = (Switch) findViewById(R.id.switch3EP);
        final Switch s4 = (Switch) findViewById(R.id.switch4EP);
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

    private void botonActualizar(){
        Button MiButton = (Button) findViewById(R.id.btnActEP);
        MiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //Se crean las opciones
                ArrayList<Opcion> ops = new ArrayList<>();
                RadioButton r1 = (RadioButton) findViewById(R.id.radioButtonEP);

                TextView op = (TextView) findViewById(R.id.op1EP);
                ops.add(new Opcion("1",op.getText().toString()));
                op = (TextView) findViewById(R.id.op2EP);
                ops.add(new Opcion("2",op.getText().toString()));

                //Se crean 3 y 4 solo si son 4 opciones
                if(r1.isChecked()) {
                    op = (TextView) findViewById(R.id.op3EP);
                    ops.add(new Opcion("3", op.getText().toString()));
                    op = (TextView) findViewById(R.id.op4EP);
                    ops.add(new Opcion("4", op.getText().toString()));
                }

                //Se crea la pregunta
                EditText etPregunta = (EditText) findViewById(R.id.editTextPreguntaEP);
                ModificarObjetoEnFirebase(etPregunta.getText().toString(),ops);

            }
        });
    }

    public void ModificarObjetoEnFirebase(String preg, List<Opcion> ops) {
        Pregunta p = VariablesGlobales.getInstance().getAuxPregunta();
        if(p != null) {
            p.setOpciones(ops);
            p.setPregunta(preg);
            VariablesGlobales.getInstance().getMyRef().child("preguntas").child(VariablesGlobales.getInstance().getAuxPregunta().getId()).setValue(p);
            Mensaje("Se ha actualizado la pregunta");
            VariablesGlobales.getInstance().setAuxPregunta(null);
            finish();
        }
    }


        public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();}
}
