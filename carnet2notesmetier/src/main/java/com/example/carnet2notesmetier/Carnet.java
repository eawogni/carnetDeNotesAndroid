package com.example.carnet2notesmetier;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * objet possédé par une personne , à partir de l'objet on obtient le propriétaire qui est un étudiant
 */
public class Carnet implements Serializable{


	private ArrayList<Semestre> semestres = new ArrayList<Semestre>();
	private Etudiant proprietaire;

	/**
	 * Crée un carnet
	 * @param etd Etudiant associé à ce carnet
	 */
	public Carnet(Etudiant etd)
	{
		this.proprietaire = etd ;
	}

	/**
	 * Ajoute un semestre au carnet
	 */
	public void AjouterSemestre()
	{
		this.semestres.add(new Semestre());
	}

	/**
	 * Supprime un Semestre du carnet
	 * @param semestre le semestre à supprimer
	 */
	public void SupprimerSemestre(Semestre semestre)
	{

		for(Semestre se  : this.semestres)
		{
			if (semestre.equals(se))
			{
				this.semestres.remove(se);
			}
		}
	}

	/**
	 * Donne la liste des semestres que contient le carnet
	 * @return tableau de Semestre
	 */
	public Semestre[] getSemestres()
	{
		return this.semestres.toArray( new Semestre[this.semestres.size()]);
	}

	/**
	 * Donne la liste des noms des semestres
	 * @return liste de chaine de caractère
	 */
	public  ArrayList<String> getListeSemestreEnString()
	{
		ArrayList<String> listeSem = new ArrayList<String>();
		for (Semestre s : this.semestres)
		{
			listeSem.add(s.toString()) ;
		}
		return listeSem ;
	}

	/**
	 * Retourne l'étudiant associé à ce carnet
	 * @return
	 */

	public Etudiant getProprietaire()
	{
		return  this.proprietaire ;
	}

}