package com.example.carnet2notesmetier;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Un Cas particulier d'enseignement
 */
public class Semestre implements Serializable {

	private static int countNbre2Semestre=1;
	private  int numSemestre;
	private ArrayList<UE> listUE ;

	/**
	 * instancie un semestre
	 */
	public Semestre()
	{

        if (countNbre2Semestre>4)
        {
            countNbre2Semestre =1 ;
        }
        this.numSemestre = countNbre2Semestre ;
        countNbre2Semestre++ ;              //faire en sorte qu'on ne puisse créer plus de 4 semestre
		listUE = new ArrayList<UE>();
	}


	/**
	 * permet de créer une nouvelle unité d'enseignement
	 */
	public void ajouterUE(UE aUe)
	{
		this.listUE.add(aUe) ;
	}

	/**
	 * Renvoie la liste de tous les modules
	 */
	public Module[] getListModules()
	{
		ArrayList<Module> modules = new ArrayList<Module>();
		for (UE e : this.listUE)
		{
			for (Module m : e.getListModule())
			{
				modules.add(m);

			}

		}
		return modules.toArray(new Module[modules.size()]);
	}

	/**
	 * renvoie la liste de tous les UE
	 */
	public UE[] getListUE()

	{
		return this.listUE.toArray(new UE[this.listUE.size()]);
	}

	/**
	 * obtenir un UE à partir de son nom
	 */
	public UE getUE(String aNomEU)
	{

		for (UE e : this.listUE)
		{
			if (e.getNom() == aNomEU)
			{
				return e;
			}


		}

		return null;
	}

	/**
	 * Retourne le semestre sous une chaîne de caractère
	 * @return String
	 */
	public  String toString()
	{
		return "Semestre "+ this.numSemestre ;
	}

	/**
	 * Retourne la liste des UE(leurs noms) de ce semestre
	 * @return ArayList
	 */
	public  ArrayList<String> getListUeEnString()
	{
		ArrayList<String> listeUe= new ArrayList<String>();
		for (UE e : this.listUE)
		{
			listeUe.add(e.toString()) ;
		}
		return listeUe ;
	}

	/**
	 * permet de remettre à 1 la création du prochain Semestre et recommercer la numérotation des semestres
	 */
	public  static  void resetCountNbre2Semestre()
	{
		countNbre2Semestre =1 ;
	}




}