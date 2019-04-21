package com.example.awogni.carnetdenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.SauvegardeCarnet;
import com.example.carnet2notesmetier.UE;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Permet de modifier une UE
 */

public class Modifier_Ue_Activity extends AppCompatActivity {

    private UE ueAgérer;
    private Carnet carnet;
    private String nomUe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modification_ue);

        this.carnet = MainActivity.getCarnet();
        final int numSem = this.getIntent().getIntExtra("numSem", 0);


        //recup des objets de la layout
        final TextView txtNomAction =(TextView) findViewById(R.id.txtTitreFenetre);
        final EditText txtNomUe = (EditText) findViewById(R.id.editTxtNomUe);
        Button btnValider = (Button) findViewById(R.id.btnValider);

        //Cas modification d'un Ue

        if (this.getIntent().getStringExtra("action").equals("modifier")) {

            int numUE = this.getIntent().getIntExtra("numUe", 0);
            this.ueAgérer = carnet.getSemestres()[numSem].getListUE()[numUE];

            this.nomUe = ueAgérer.getNom();

            //utilisation des objets de la layout

            txtNomAction.setText("Modifier UE");
            txtNomUe.setText(this.ueAgérer.getNom());

            btnValider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ueAgérer.setNom(txtNomUe.getText().toString());
                    if (!nomUe.equals(txtNomUe.getText().toString()))
                        Toast.makeText(Modifier_Ue_Activity.this, "Ue modifiée ! ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


        }else   // Cas Ajout d'un UE
        {

            txtNomAction.setText("Ajouter une nouvelle UE");



            //apres validation

            btnValider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (txtNomUe.getText().toString().length() ==0)

                        Toast.makeText(Modifier_Ue_Activity.this, "Veuillez  saisir le nom de l'Ue! ", Toast.LENGTH_SHORT).show();
                    else
                    {
                        carnet.getSemestres()[numSem].ajouterUE(new UE (txtNomUe.getText().toString()));

                        Toast.makeText(Modifier_Ue_Activity.this, "UE ajoutée ! ", Toast.LENGTH_SHORT).show();
                        finish();;
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
