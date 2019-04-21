package com.example.awogni.carnetdenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.Note;
import com.example.carnet2notesmetier.SauvegardeCarnet;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Permet de modifier une Note
 */

public class Modification_Note_Activity extends AppCompatActivity {
    private Carnet carnet ;
    private Note note;
    private Boolean absence= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modification_notes);

        this.carnet = MainActivity.getCarnet();
        final int numSem = this.getIntent().getIntExtra("numSem",0);
        final int numUE = this.getIntent().getIntExtra("numUe",0);
        final int numMod = this.getIntent().getIntExtra("numMod",0);
        final int numNote = this.getIntent().getIntExtra("numNote",0);

        this.note = this.carnet.getSemestres()[numSem].getListUE()[numUE].getListModule()[numMod].getNotes()[numNote];


        //Recuperation des objets de la layout

        final EditText txtNote = (EditText)findViewById(R.id.editTextNote);

        final Switch swAbsence = (Switch) findViewById(R.id.switchAbsence) ;

        swAbsence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                absence =b ;

            }
        });

        txtNote.setText(this.note.getValeur()+"");
        if (note.getAbsence())
        {
            swAbsence.setChecked(true);
        }
        else
        {
            swAbsence.setChecked(false);
        }
        Button btnValider = (Button) findViewById(R.id.btnValiderNote);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNote.getText().toString().length()==0 )
                {
                    Toast.makeText(Modification_Note_Activity.this, "Veuillez saisir une note ", Toast.LENGTH_SHORT).show();
                }else
                {
                    try
                    {
                        float valeurNote = Float.parseFloat(txtNote.getText().toString());
                        if (absence)
                        {
                            note.setAbsence(true);
                            note.setValeur(0);
                        }
                        else
                        {
                            note.setAbsence(false);
                            note.setValeur(valeurNote);
                        }

                        Toast.makeText(Modification_Note_Activity.this, "Note modifiée ! ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    catch ( Exception er)
                    {

                        Toast.makeText(Modification_Note_Activity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


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
