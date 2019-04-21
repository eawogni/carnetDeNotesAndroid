package com.example.carnet2notesmetier;

import java.io.Serializable;

/**
 * Peut être un module ou une UE
 */
public class Enseignement implements Serializable
{
	private String nom;

	public Enseignement(String aNom)
	{
		this.nom = aNom ;

	}

	/**
	 * Modifie le nom de l'enseignement
	 * @param aNom le nom de l'enseignement
	 */
	public void setNom(String aNom)
	{
		this.nom = aNom;
	}

	/**
	 * Retourne le nom de cet enseignementt
	 * @return
	 */
	public String getNom()
	{
		return this.nom;
	}
}