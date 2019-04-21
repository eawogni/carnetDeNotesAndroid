package com.example.awogni.carnetdenotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.Etudiant;
import com.example.carnet2notesmetier.SauvegardeCarnet;
import com.example.carnet2notesmetier.Semestre;
import com.example.carnet2notesmetier.UE;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;                //la listView qui affiche les UE d'un semestre
    private Spinner sp;                 //lalistBox qui permet de sélectionner un semestre
    private static Carnet carnet ;             //le carnet qui est géré
    private Button nvoSemestre;         // le boutton qui permet de créer une nvo semestre dans le carnet
    private Semestre semestreActuel;      //le semestre actuellement sélectionné par l'utilisateur
    private int numSemestreActuel  ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //le button de réinitialiation

        Button btnSupCarnet = (Button) findViewById(R.id.btnReset) ;

        //Le boutton pour la moyenne
        Button btnMoyenne= (Button) findViewById(R.id.btnMoyenne) ;

        btnMoyenne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (semestreActuel != null)
               {
                   Intent intent = new Intent(MainActivity.this,MoyenneActivity.class);
                   intent.putExtra("numSemestre",numSemestreActuel);
                   startActivity(intent);

               }else
               {
                   Toast.makeText(MainActivity.this, "Choisir un semestre", Toast.LENGTH_LONG).show();
               }

            }
        });



        btnSupCarnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity.this);
                msgBox.setTitle("Réinitialiser le carnet");
                msgBox.setMessage("Voulez-vous vraiment supprimer le carnet et perdre vos données ?" );
                msgBox.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carnet = null ;
                        Intent intent = new Intent(MainActivity.this,CreerCarnetActivity.class);
                        startActivity(intent);
                        Semestre.resetCountNbre2Semestre();
                        finish();

                    }

                });

                msgBox.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                msgBox.show();
                /////


            }
        });


        //Gestion de la sauvegarde


    try {
        FileInputStream f= getApplicationContext().openFileInput("sauvegarde.txt");
        carnet = SauvegardeCarnet.getInstance().Charger(f);
        f.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }



        if (carnet== null)
        {
            if (!CreerCarnetActivity.getEtudiant().getNom().equals(""))
            {
                Etudiant etd = new Etudiant(CreerCarnetActivity.getEtudiant().getNom(),CreerCarnetActivity.getEtudiant().getPrenom());
                carnet = new Carnet(etd);

            }else{
                Intent intent = new Intent(MainActivity.this, CreerCarnetActivity.class);
                startActivity(intent);
                Semestre.resetCountNbre2Semestre();
                finish();
            }

        }


//---------------------------------
        if (carnet != null)
        {

//Recuperation des objets de la layout

            TextView txtProprio = (TextView) findViewById(R.id.txtProprietaire) ;
            txtProprio.setText(carnet.getProprietaire().getPrenom()+" "+carnet.getProprietaire().getNom()); //Affichage du prénom et nom de l'étudiant


            //Le spinner
            sp = (Spinner) findViewById(R.id.spinner);
            sp.setAdapter(new CustumListView(this, carnet.getListeSemestreEnString()));
            sp.setPrompt("Choisir un semestre");

            //La listView pour les UE
            lv = (ListView) findViewById(R.id.listUE);

            //le boutton d'ajout d'une nouvelle UE

            Button newUe = (Button)findViewById(R.id.btnNvoUe) ;

            newUe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (semestreActuel!= null)
                    {
                        Intent intent = new Intent(MainActivity.this,Modifier_Ue_Activity.class);
                        intent.putExtra("numSem",numSemestreActuel) ;
                        intent.putExtra("action","ajouter") ;
                        startActivityForResult(intent,2);

                    }else
                    {
                        Toast.makeText(MainActivity.this, "Choisir un semestre", Toast.LENGTH_LONG).show();
                    }

                }
            });

            //le boutton nvoSemestre


            this.nvoSemestre = (Button) findViewById(R.id.nvoSemestre);

            //Les événements liés au objets de la layout

            this.nvoSemestre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (carnet.getSemestres().length < 4) {
                        carnet.AjouterSemestre();
                        sp.setAdapter(new CustumListView(MainActivity.this, carnet.getListeSemestreEnString())); //mise a jour de la list des semestre

                        Toast.makeText(MainActivity.this, "Nouveau Semestre ajouté ! ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Impossible d'avoir plus de quatre semestres", Toast.LENGTH_LONG).show();

                    }



                }
            });


            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    semestreActuel = carnet.getSemestres()[i];
                    numSemestreActuel = i ;
                    lv.setAdapter(new CustumListView(MainActivity.this, semestreActuel.getListUeEnString()));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(MainActivity.this,UeActivity.class);
                    intent.putExtra("numSem",numSemestreActuel) ;
                    intent.putExtra("numUe",i);
                    startActivityForResult(intent,0);


                }
            });

            //evenement utilisé comme un double-clic mais il sagit en effet d'un maintient sur un elt de la listView
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(MainActivity.this,Modifier_Ue_Activity.class);
                    intent.putExtra("numSem",numSemestreActuel) ;
                    intent.putExtra("numUe",i);
                    intent.putExtra("action","modifier") ;
                    //startActivity(intent);
                    startActivityForResult(intent,1);

                    return false;
                }
            });
        }

    }
    public static Carnet  getCarnet()  {

        return  carnet ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       sp.setAdapter( new CustumListView( MainActivity.this,carnet.getListeSemestreEnString()));

    }

    @Override
    protected void onPause() {
        super.onPause();
        //On fait la sauvegarde du carnet
        try {
            FileOutputStream f= getApplicationContext().openFileOutput("sauvegarde.txt",0);
            SauvegardeCarnet.getInstance().Sauver(carnet,f);      //SAUVEGARDE
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
