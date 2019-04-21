package com.example.awogni.carnetdenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.Module;
import com.example.carnet2notesmetier.SauvegardeCarnet;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Permet de modifier un module
 */

public class Modifier_Module_Activity extends AppCompatActivity {

    private Carnet carnet ;
    private Module mod ;
    private String nomMod ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modification_module);

        this.carnet = MainActivity.getCarnet();



        final int numSem = this.getIntent().getIntExtra("numSem", 0);
        final int numUE = this.getIntent().getIntExtra("numUe",0);


        //recup des objets de la layout
         TextView txtNomAction =(TextView) findViewById(R.id.txtTitreFenetre);
         final EditText txtNomModule = (EditText) findViewById(R.id.editTxtNomModule);
         final EditText txtIdModule = (EditText) findViewById(R.id.editTextIdMod);
         final EditText txtCoef= (EditText) findViewById(R.id.editTextCoef);
         Button btnValider = (Button) findViewById(R.id.btnValider);

         //Cas modification d'un module

        if (getIntent().getStringExtra("action").equals("modifier"))
        {
            int numMod = getIntent().getIntExtra("numMod",0);
            this.mod = carnet.getSemestres()[numSem].getListUE()[numUE].getListModule()[numMod] ;
            this.nomMod = this.mod.getNom();

            txtNomAction.setText("Modifier Module");
            txtIdModule.setText(this.mod.getIdModule());
            txtNomModule.setText(this.mod.getNom());
            txtCoef.setText(this.mod.getCoefficient()+"");


            btnValider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        mod.setNom(txtNomModule.getText().toString());
                        mod.setIdModule(txtIdModule.getText().toString());
                        mod.setCoefficient(Float.parseFloat(txtCoef.getText().toString()));

                        Toast.makeText(Modifier_Module_Activity.this, "Module modifié ! ", Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (Exception ex)
                    {
                        Toast.makeText(Modifier_Module_Activity.this, "Entrez un nombre pour le coefficient ", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }else   // Cas Ajout d'un UE
        {

            txtNomAction.setText("Ajouter un nouveau module");


            //apres validation


            btnValider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (txtNomModule.getText().toString().length()==0 || txtIdModule.getText().toString().length()==0 || txtCoef.getText().toString().length()==0)

                        Toast.makeText(Modifier_Module_Activity.this, "Veuillez modifier les champs vides ou le coefficient entré ! ", Toast.LENGTH_SHORT).show();
                    else //ajoutdu nvo module avec les infos saisies
                    {
                       try
                       {
                           float coef = Float.parseFloat(txtCoef.getText().toString()) ;
                           carnet.getSemestres()[numSem].getListUE()[numUE].ajouterModule(txtIdModule.getText().toString(), txtNomModule.getText().toString(),coef);
                           Toast.makeText(Modifier_Module_Activity.this, "Module ajouté ! ", Toast.LENGTH_SHORT).show();
                           finish();
                       }catch ( Exception ex)
                       {
                           Toast.makeText(Modifier_Module_Activity.this, "Entrez un nombre pour le coefficient", Toast.LENGTH_SHORT).show();
                       }


                    }
                }
            });

        }

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
