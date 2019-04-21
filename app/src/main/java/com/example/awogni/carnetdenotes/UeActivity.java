package com.example.awogni.carnetdenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.SauvegardeCarnet;
import com.example.carnet2notesmetier.UE;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Permet de gérer un UE
 */

public class UeActivity extends AppCompatActivity {
    private UE ueActuelle;
    private Carnet carnet;
    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_ue);

        this.carnet = MainActivity.getCarnet() ;

        final int numSem = this.getIntent().getIntExtra("numSem",0);
        final int numUE = this.getIntent().getIntExtra("numUe",0);
        this.ueActuelle = carnet.getSemestres()[numSem].getListUE()[numUE] ;

    //Recuperation des objets de la layout
        TextView txt = (TextView) findViewById(R.id.txtNomUe) ;
        txt.setText(this.ueActuelle.getNom());

        Button nvoModule = (Button) findViewById(R.id.btnNvoModule);
        nvoModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UeActivity.this,Modifier_Module_Activity.class);
                intent.putExtra("numSem",numSem );
                intent.putExtra("numUe", numUE);
                intent.putExtra("action", "ajouter");

                startActivityForResult(intent,3);

            }
        });



        lv = (ListView) findViewById(R.id.lvModules);
        lv.setAdapter( new CustumListView(UeActivity.this,this.ueActuelle.getListeModulesString()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(UeActivity.this,ModuleActivity.class);
                intent.putExtra("numSem", getIntent().getIntExtra("numSem",0));
                intent.putExtra("numUe", getIntent().getIntExtra("numUe",0));
                intent.putExtra("numMod", i);

                startActivity(intent);


            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UeActivity.this,Modifier_Module_Activity.class);
                intent.putExtra("numSem", numSem);
                intent.putExtra("numUe", numUE);
                intent.putExtra("numMod", i);
                intent.putExtra("action", "modifier");

                startActivityForResult(intent,4);


                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        lv.setAdapter( new CustumListView( UeActivity.this,ueActuelle.getListeModulesString()));
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
