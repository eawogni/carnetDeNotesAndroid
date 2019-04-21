package com.example.carnet2notesmetier;

import java.io.Serializable;

/**
 * Définie une note
 */
public class Note implements Serializable {
	private float valeur;
	private boolean absence;

	/**
	 * Instance une note
	 * @param Valeur float
	 * @param Absence boolean
	 */
	public Note(float Valeur, boolean Absence)
	{
		this.valeur = Valeur;
		this.absence = Absence;
	}

	/**
	 * Modifie la valeur d'e la note
	 * @param aValeur float
	 */
	public void setValeur(float aValeur) {
		this.valeur = aValeur;
	}

	/**
	 * Retourne la valeur de la note
	 * @return float
	 */
	public float getValeur() {
		return this.valeur;
	}

	/**
	 * Modifie la valeur de l'absence
	 * @param aAbsent
	 */
	public void setAbsence(boolean aAbsent) {
		this.absence = aAbsent;
	}

	/**
	 * Retourne la valeur de l'absensce
	 * @return boolean
	 */
	public boolean getAbsence() {
		return this.absence;
	}

	/**
	 * Retourne la note sur 20
	 * @return String
	 */
	public  String toString()
	{
		if (this.absence)
		{
			return" Absent";
		}
		else
		{
			return (this.valeur + "/20");
		}

	}
}