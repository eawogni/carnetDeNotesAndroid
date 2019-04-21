package com.example.carnet2notesmetier;

import android.location.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * Created by Awogni on 21/02/2018.
 */

/**
 * classe singleton qui permet de gérer la sauvegarde d'un carnet
 */
public final class SauvegardeCarnet {
    private ObjectOutputStream os ;
    private ObjectInputStream is ;
    private static  SauvegardeCarnet instance ;


    /**
     * mise en place du singleton
     * @throws IOException
     */
    private SauvegardeCarnet() throws IOException {


    }

    /**
     * Permet de récupérer ma seule instance de la classe
     * @return SauvegardeCarnet
     * @throws IOException
     */
    public  static  SauvegardeCarnet  getInstance() throws IOException {
        if (instance == null)
        {
            instance = new SauvegardeCarnet();
        }
        return   instance ;
    }

    /**
     * permet de recuperer un carnet existant
     * @param f le fichier dans lequel on recupère le fichier
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public Carnet Charger(FileInputStream f) throws IOException, ClassNotFoundException {
       // Carnet c = null ;

          is = new ObjectInputStream(f);
          Carnet  c =  (Carnet) this.is.readObject() ;


            is.close();


        return c ;
    }

    /**
     * permet de sauvegarder un carnet
     * @param c le carnet à sauvegarder
     * @param f le fichier dans lequel la sauvegarde doit être faite
     * @throws IOException
     */
    public void Sauver(Carnet c,FileOutputStream f) throws IOException {
        os = new ObjectOutputStream(f);

        os.writeObject(c);
        os.flush();
        os.close();

    }
}
