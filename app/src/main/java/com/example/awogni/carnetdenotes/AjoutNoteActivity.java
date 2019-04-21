package com.example.awogni.carnetdenotes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.Module;
import com.example.carnet2notesmetier.Note;
import com.example.carnet2notesmetier.SauvegardeCarnet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Activité permettant de gérer l'ajout d'une note dans un module
 */
public class AjoutNoteActivity extends AppCompatActivity {
    private Carnet carnet;
    private Module mod;
    private Boolean absence = false ;
    private static  TextView txtDateExam ;
    private static FragmentDate frag ; // Fragmment pour gérer la date à partir de l'objet datePicker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_notes_et_examen);

        this.carnet = MainActivity.getCarnet();
        final int numSem = this.getIntent().getIntExtra("numSem",0);
        final int numUE = this.getIntent().getIntExtra("numUe",0);
        final int numMod = this.getIntent().getIntExtra("numMod",0);
        this.mod = carnet.getSemestres()[numSem].getListUE()[numUE].getListModule()[numMod] ;

       // Recuperation des objets de la layout

         txtDateExam = (TextView) findViewById(R.id.editTextDate);
        final EditText txtNomProf = (EditText) findViewById(R.id.editTextNomProf);
        final EditText txtPondérationExam = (EditText) findViewById(R.id.editTextPond);
        final EditText txtNoteExam = (EditText) findViewById(R.id.editTextNote);
        final Switch swAbsence = (Switch) findViewById(R.id.switchAbsence);
        final Button btnValider = (Button) findViewById(R.id.btnValider);
        final Button btnChoixDate = (Button) findViewById(R.id.btnChoixDate);

        btnChoixDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                frag = new FragmentDate();
                frag.show(transaction,"Choix Date");
            }
        });

        swAbsence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                absence =b ;
                if(absence) {
                    txtNoteExam.setText(0.0+"");
                }
            }
        });


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if ((  txtDateExam.getText().toString().length()==0
                        || txtNomProf.getText().toString().length()==0
                        || txtPondérationExam.getText().toString().length()==0
                        || txtNoteExam.getText().toString().length()==0
                        )){

                        Toast.makeText(AjoutNoteActivity.this,"Veuillez vérifier les champs",Toast.LENGTH_SHORT).show();

                          }else
                    {

                        //Gestion de la Date ;
                        String dateExam = txtDateExam.getText().toString();
                        float note = Float.parseFloat(txtNoteExam.getText().toString());
                        if (dateExam.equals("Choisir date") )
                        {  Toast.makeText(AjoutNoteActivity.this,"Date non saisie",Toast.LENGTH_SHORT).show();}
                        else  if (note <0  || note>20) {  Toast.makeText(AjoutNoteActivity.this,"Note incorrecte",Toast.LENGTH_SHORT).show();}

                        else{
                            String nomProf =txtNomProf.getText().toString();
                            float pond = Float.parseFloat(txtPondérationExam.getText().toString());

                            //GestionNote
                            Note n = new Note(note,absence) ;
                            if (absence)
                            {
                                n.setValeur(0);
                            }

                            mod.ajouterExamen(dateExam,nomProf,pond,n);

                            Toast.makeText(AjoutNoteActivity.this,"Note ajoutée !",Toast.LENGTH_SHORT).show();
                            finish();

                        }



                    }

            }
        });

    }

    public static void setDate(String Date)
    {
        txtDateExam.setText(Date);
        frag.dismiss(); // Fermeture de du fragment
    }
    @Override
    protected void onPause() {
        super.onPause();
        //On fait la sauvegarde du carnet
        try {
            FileOutputStream f= getApplicationContext().openFileOutput("sauvegarde.txt",0);
            SauvegardeCarnet.getInstance().Sauver(carnet,f);      //SAUVEGARDE
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
