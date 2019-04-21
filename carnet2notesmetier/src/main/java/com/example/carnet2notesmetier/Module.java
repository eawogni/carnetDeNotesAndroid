package com.example.carnet2notesmetier;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.System.in;

/**
 * Il s'agit d'un cas partoculier d'enseignement
 */
public class Module extends Enseignement implements Serializable {
	private float coefficient;
	private String idModule;
	private ArrayList<Examen> listExamen = new ArrayList<Examen>();

	public Module(String IdModule, String aNom, float aCoeff)
	{
		super(aNom);
		this.idModule = IdModule ;
		this.coefficient = aCoeff ;
	}

	/**
	 * permet d'ajouter un examen à ce module
	 */
	public void ajouterExamen(String aDate, String aNomProf, float aPonderation, Note aNote) {
		this.listExamen.add(new Examen(aDate,aNomProf,aPonderation,aNote));
	}

	/**
	 * retourne la liste des Examens de ce module
	 * @return tableau d'examen
	 */

	public Examen[] getListExamen()
	{
		return this.listExamen.toArray(new Examen[this.listExamen.size()]);
	}

	/**
	 * Retourne la liste de toute les notes contenues dans ce module
	 * @return Note[]
	 */
	public Note[] getNotes()
	{
		ArrayList<Note> notes = new ArrayList<Note>() ;
		for (Examen e : this.listExamen)
		{
			notes.add(e.getNote());
		}
		return notes.toArray(new Note[notes.size()]);

	}

	/**
	 * Retourne le coefficient de cemodule
	 * @return float
	 */
	public float getCoefficient() {
		return this.coefficient;
	}

	/**
	 * Retourn l'idModule de ce module
	 * @return String
	 */
	public String getIdModule() {
		return this.idModule;
	}
	public void setIdModule(String idMod) {
		this.idModule = idMod;
	}
	public void setCoefficient (float coef) {
		this.coefficient = coef;
	}

	/**
	 * Retourne la chaîne de caractère représentant ce module
	 * @return String
	 */
	public String toString()
	{
		return this.getNom() + " (" + this.coefficient + ")";
	}

	/**
	 * Retourne la liste des valeurs (en chaîne de caractère)  des notes de ce modules
	 * @return Liste de chaîne de caractères
	 */
	public ArrayList<String> getListeNotesEnString()
	{
		ArrayList<String> listeNotes = new ArrayList<String>();
		for (Note n: this.getNotes())
		{
			listeNotes.add(n.toString());

		}
		return listeNotes ;
	}



	/**
	 * Permet de calculer la moyenne du module
	 *
	 * @return float
	 */

	public float getMoyenne()
	{

		float sommeNotes = 0;
		float sommePonderation = 0;

		for (Examen ex : this.getListExamen())
		{
			//somme des notes avec pondération et somme des pondération
			sommeNotes += ex.getNote().getValeur() * ex.getPonderation();
			sommePonderation += ex.getPonderation();
		}

		return sommeNotes / sommePonderation;
	}
}