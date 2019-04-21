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
import com.example.carnet2notesmetier.Examen;
import com.example.carnet2notesmetier.Module;
import com.example.carnet2notesmetier.Note;
import com.example.carnet2notesmetier.SauvegardeCarnet;

import java.io.FileOutputStream;
import java.io.IOException;

public class ModuleActivity extends AppCompatActivity {
    private Carnet carnet ;
    private Module mod;
    private  ListView lv ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_module);

        this.carnet = MainActivity.getCarnet();
        final int numSem = this.getIntent().getIntExtra("numSem",0);
        final int numUE = this.getIntent().getIntExtra("numUe",0);
        final int numMod = this.getIntent().getIntExtra("numMod",0);

        this.mod = carnet.getSemestres()[numSem].getListUE()[numUE].getListModule()[numMod] ;



        //Recuperation des objets de la layout

        TextView txtNomModule = (TextView)findViewById(R.id.txtNomModule);
        txtNomModule.setText(this.mod.getNom());

        final TextView txtDetailsExam = (TextView)findViewById(R.id.txtDetailsExam) ;

        final Button nouvelleNote = (Button) findViewById(R.id.btnNewNote) ;

        nouvelleNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ModuleActivity.this,AjoutNoteActivity.class);
                intent.putExtra("numSem",numSem) ;
                intent.putExtra("numUe",numUE);
                intent.putExtra("numMod",numMod);
                startActivityForResult(intent,6);


            }
        });

        lv = (ListView) findViewById(R.id.lvNotes);

        lv.setAdapter(new CustumListView(ModuleActivity.this,this.mod.getListeNotesEnString()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Note n= mod.getNotes()[i] ;
               for (Examen ex : mod.getListExamen())
               {
                   if (ex.getNote().equals(n))
                   {
                       txtDetailsExam.setText( "Examen du : "+ ex.getDateExam()+ " effectué par :" + ex.getNomProf()+ " Pondération = "+ ex.getPonderation());
                   }
               }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ModuleActivity.this,Modification_Note_Activity.class);
                intent.putExtra("numSem",numSem) ;
                intent.putExtra("numUe",numUE);
                intent.putExtra("numMod",numMod);
                intent.putExtra("numNote",i);


                startActivityForResult(intent,5);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        lv.setAdapter( new CustumListView(ModuleActivity.this, mod.getListeNotesEnString()));

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
