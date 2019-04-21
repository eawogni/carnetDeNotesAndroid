package com.example.carnet2notesmetier;

import java.io.Serializable;

public class Examen implements Serializable {
	private String nomProf;
	private float ponderation;
	private String dateExam;
	public Note note;

	/**
	 * Création d'un  examen
	 */
	public Examen(String aDate, String aNomProf, float aPonderation, Note note) {
		this.dateExam = aDate;
		this.nomProf = aNomProf;
		this.ponderation = aPonderation;
		this.note = note;
	}

	/**
	 * permet d'ajouetr la note assicier à cet examen
	 */
	public void setNote(Note aNote) {
		this.note = aNote;
	}

	/**
	 * Retourne la note associé à cet examen
	 * @return
	 */
	public Note getNote() {
		return this.note;
	}

	/**
	 * Modifie le  nom du prof associé à cet examen
	 * @param aNomProf
	 */

	public void setNomProf(String aNomProf) {
		this.nomProf = aNomProf;
	}

	/**
	 * Retourne le nom du prof associé à cet examen
	 * @return
	 */
	public String getNomProf() {
		return this.nomProf;
	}

	/**
	 * Modifie la pondération associé à cet examen
	 * @param aPonderation float
	 */
	public void setPonderation(float aPonderation) {
		this.ponderation = aPonderation;
	}

	/**
	 * Retourne la pondération associé à cet examen
	 * @return float
	 */
	public float getPonderation() {
		return this.ponderation;
	}

	/**
	 * Modifie ma date de l'examen associé à cet examen
	 * @param aDateExam String
	 */
	public void setDateExam(String aDateExam) {
		this.dateExam = aDateExam;
	}

	/**
	 * Retourne la date de l'examen
	 * @return
	 */
	public String getDateExam() {
		return this.dateExam;
	}


}