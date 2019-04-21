package com.example.carnet2notesmetier;

import java.io.Serializable;

/**
 * propriétaire d'un carnet
 */
public class Etudiant implements Serializable {
	private String nom;
	private String prenom;

	public Etudiant(String aNom, String aPrenom)
	{
		this.nom = aNom.toUpperCase() ;
		this.prenom = aPrenom.toUpperCase() ;
	}


	/**
	 * Modifie le nom de l'étudiant
	 * @param aNom String
	 */
	public void setNom(String aNom)
	{
		this.nom = aNom.toUpperCase();
	}

	/**
	 * Retourne le  nom de l'étudiant
	 * @return string
	 */
	public String getNom()
	{
		return this.nom;
	}

	/**
	 * Modifie le prénom de l'étudiant
	 * @param aPrenom String
	 */


	public void setPrenom(String aPrenom)
	{
		this.prenom = aPrenom.toUpperCase();
	}

	/**
	 * Retourne le prénom de l'étudiant
	 * @return String
	 */
	public String getPrenom()
	{
		return this.prenom;
	}
}