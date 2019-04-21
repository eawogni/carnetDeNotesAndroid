package com.example.awogni.carnetdenotes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carnet2notesmetier.Carnet;
import com.example.carnet2notesmetier.Module;
import com.example.carnet2notesmetier.SauvegardeCarnet;
import com.example.carnet2notesmetier.UE;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class MoyenneActivity extends AppCompatActivity {

    private Carnet carnet;
    private String bulletin = "";
    private String Export="";
    private final int PermissionLectureCarteSD = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_getstion_moyenne);

        carnet = MainActivity.getCarnet();
        final int numSemestre = getIntent().getIntExtra("numSemestre", 0);

        TextView txtMoyenne = (TextView) findViewById(R.id.txtMoy);
//Formatage du bulletin
        if (carnet != null) {
            bulletin+="                    "+ this.carnet.getProprietaire().getPrenom()+" "+this.carnet.getProprietaire().getNom()+"\n" + "\n";

            for (UE e : carnet.getSemestres()[numSemestre].getListUE()) {
                for (Module m : e.getListModule()) {
                    this.bulletin += m.getIdModule() + " " + "(" + m.getNom() + ")" + " : " + m.getMoyenne() + " \n";
                }

                this.bulletin += "\n" + "            --> Moyenne UE : "+e.getNom() + " : " + e.getMoyenne() +" <--        " +" \n";

            }
            txtMoyenne.setText(this.bulletin);

        }

        Button btnExportPDF = (Button) findViewById(R.id.btnEXportPdf) ;
        Button btnExportCSV = (Button) findViewById(R.id.btnExportCsv) ;

        btnExportPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Export ="pdf";
                GénérationDuPdf();
            }
        });

        btnExportCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Export ="csv";
                GénérationDuCsv();
            }
        });


    }



    private void GénérationDuPdf()
    {
        String etat = Environment.getExternalStorageState();  // On recupere l'etat de la carte SD  (existe ou pas)?(
        // en réalité la premiere mémoireexterne cad la mémoire interne du téléphonr

        if (Environment.MEDIA_MOUNTED.equals(etat)) {  // si la carte Sd Existe alors la variable "etat" aura la valeur "Environment.MEDIA_MOUNTED"
            //Ecriture lecture possible sur la carte SD
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// Demande de permission pour les versions d'android suppérieur ou égale à 6(Marshmallow(M))'

                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //Si permission d'écrire sur la carte SD accordée
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionLectureCarteSD); // Sinon Demande de permission

                } else {
                    this.générerPdf(); // on écrit sur la carte sd
                    Toast.makeText(this,"Fichier Pdf générer dans votre stockage interne",Toast.LENGTH_LONG).show();
                }
            } else { // si version  d'android antérieures à Marshmallow on écrit sans gestion de permission

                this.générerPdf(); // on écrit sur la carte sd
                Toast.makeText(this,"Fichier Pdf générer dans votre stockage interne",Toast.LENGTH_LONG).show();
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(etat)) {
            Toast.makeText(this, "Impossible d'écrire dans le stockage interne", Toast.LENGTH_LONG).show();
            ;

        } else {
            Toast.makeText(this, "Stockage introuvable", Toast.LENGTH_LONG).show();
        }

    }

    private void GénérationDuCsv()
    {
        String etat = Environment.getExternalStorageState();  // On recupere l'etat de la carte SD  (existe ou pas)?(
        // en réalité la premiere mémoireexterne cad la mémoire interne du téléphonr

        if (Environment.MEDIA_MOUNTED.equals(etat)) {  // si la carte Sd Existe alors la variable "etat" aura la valeur "Environment.MEDIA_MOUNTED"
            //Ecriture lecture possible sur la carte SD
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// Demande de permission pour les versions d'android suppérieur ou égale à 6(Marshmallow(M))'

                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //Si permission d'écrire sur la carte SD accordée
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionLectureCarteSD); // Sinon Demande de permission

                } else {
                    this.générerCSV(); // on écrit sur la carte sd
                    Toast.makeText(this,"Fichier Csv généré dans votre stockage interne",Toast.LENGTH_LONG).show();
                }
            } else { // si version  d'android antérieures à Marshmallow on écrit sans gestion de permission

                this.générerCSV(); // on écrit sur la carte sd
                Toast.makeText(this,"Fichier Csv généré dans votre stockage interne",Toast.LENGTH_LONG).show();
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(etat)) {
            Toast.makeText(this, "Impossible d'écrire dans le stockage interne", Toast.LENGTH_LONG).show();
            ;

        } else {
            Toast.makeText(this, "Stockage intouvable", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Permet de générer le pdf contenant le bulletin
     */
    public void générerPdf() {

        Document document = new Document();

        try {

            FileOutputStream f = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "bulletin_"+carnet.getProprietaire().getPrenom()+"" +
                    "_"+  carnet.getProprietaire().getNom()+".pdf");
            BufferedOutputStream bos = new BufferedOutputStream(f);

            PdfWriter writer = PdfWriter.getInstance(document, bos);
            document.open();
            writer.open();
            boolean add = document.add(new Paragraph(bulletin));

            document.close();
            writer.close();
            bos.close();
            f.close();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    /**
     * Génère le  fichier csv associé au bulletin
     */
    public void générerCSV() {

        Document document = new Document();

        try {

            FileWriter fw = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + "bulletin_"+carnet.getProprietaire().getPrenom()+"" +
                    "_"+  carnet.getProprietaire().getNom()+".csv");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write( bulletin);
            bw.flush();
            fw.close();
            bw.close();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        //On fait la sauvegarde du carnet
        try {
            FileOutputStream f = getApplicationContext().openFileOutput("sauvegarde.txt", Context.MODE_PRIVATE); //Mode private== dossier privé visible uniquement parv l'application
            SauvegardeCarnet.getInstance().Sauver(carnet, f);      //SAUVEGARDE
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Fonction appelé à chaque fois qu'on effectue une demande de permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionLectureCarteSD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // si permission accordée alors on géénère le pdf
                   if (Export.equals("pdf"))
                   {
                       this.générerPdf();
                       Toast.makeText(this,"Fichier Pdf généré dans votre stockage interne",Toast.LENGTH_LONG).show();
                   }else if (Export.equals("csv"))
                   {
                       this.générerCSV();
                       Toast.makeText(this,"Fichier Csv généré dans votre stockage interne",Toast.LENGTH_LONG).show();
                   }

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new AlertDialog.Builder(this)
                                .setTitle("Permissions d'écrire sur la carte SD")
                                .setMessage("Vous devez l'accès en écriture de la carte SD pour générer le bulletin en pdf").show();
                    }else
                    {
                        new AlertDialog.Builder(this)
                                .setTitle("Permissions d'écrire sur la carte SD")
                                .setMessage("Il est possible de générer le bulletin en pdf sans l'autorisation d'écriture sur la carte sd").show();
                    }

                    break;
                }
            }
        }
    }
}
