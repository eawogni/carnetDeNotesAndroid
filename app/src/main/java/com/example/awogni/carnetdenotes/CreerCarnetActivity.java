package com.example.awogni.carnetdenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Etudiant;

/**
 * Carnet permettant de renseignner les informations nécéssaire pour la création d'un nouveau carnet
 */
public class CreerCarnetActivity extends AppCompatActivity {
    private static String nom="" ;
    private static String  prenom="" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_accueil_etudiant);

        //Recuperation
        final EditText txtNom = (EditText) findViewById(R.id.editTextNom);
        final EditText txtPrenom = (EditText) findViewById(R.id.editTextPrenom);
        Button btnValider = (Button) findViewById(R.id.btnCreer);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNom.getText().toString().length()==0 ||txtPrenom.getText().toString().length()==0 )
                {
                    Toast.makeText(CreerCarnetActivity.this,"Veuillez vérifier les champs",Toast.LENGTH_SHORT).show();

                }else
                {
                    nom = txtNom.getText().toString() ;
                    prenom = txtPrenom.getText().toString();
                    Intent intent = new Intent(CreerCarnetActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        });

    }

    public static Etudiant getEtudiant()
    {
        return new Etudiant(nom,prenom);

    }




    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
