package com.example.carnet2notesmetier;
import java.io.Serializable;
import java.util.ArrayList;

public class UE extends Enseignement implements Serializable {
	/**
	 * il s'agit d'une propri�t� en lecture uniquement car le coefficient d'un Ue d�pend des modules (leur coefficients)qui le contiennent
	 */


	private ArrayList<Module> listModule ;

	public UE(String aNom)
	{

		super(aNom);
		listModule = new ArrayList<Module>();
	}

	public void ajouterModule(String IdModule, String aNom, float aCoef)
	{
		this.listModule.add (new Module(IdModule,aNom,aCoef));
	}

	/**
	 * renvoie la liste de tous les modules de l'UE en question
	 */
	public Module[] getListModule()
	{
		return this.listModule.toArray( new Module[this.listModule.size()]);
	}



	public float getCoefficient()
	{

	//calcul du coef de cette UE
			float coefficient = 0;
			for (Module m : this.listModule)
			{
				coefficient += m.getCoefficient();
			}
			return coefficient;

	}


	public String toString()
	{
		return this.getNom()+" ("+ this.getCoefficient()+") " ;
	}

	public ArrayList<String> getListeModulesString()
	{
		ArrayList<String> listModules = new ArrayList<String>();
		for (Module m : this.listModule)
		{
			listModules.add(m.toString()) ;
		}
		return  listModules ;
	}


	/**
	 * permet de calculer la moyenne de cet UE
	 * @return float
	 */
	public float  getMoyenne()
	{

		float sommeMoyennesModules=0;
		for (Module m : this.getListModule())
		{

			//calcul moyenne du module
			sommeMoyennesModules += m.getMoyenne() * m.getCoefficient();
		}
		return sommeMoyennesModules / this.getCoefficient() ;

	}
}