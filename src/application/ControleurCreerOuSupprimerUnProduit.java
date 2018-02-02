package application;

import java.util.List;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;

public class ControleurCreerOuSupprimerUnProduit {
	
	private static I_Catalogue catalogue;
	
	public static void setCatalogue(I_Catalogue _catalogue) {
		catalogue = _catalogue;
	}
	
	public static boolean addProduit(I_Produit produit) {
		return catalogue.addProduit(produit);
	}
	
	public static boolean addProduit(String nom, String prix, String qte) {
		Double prixNumber;
		int qteNumber;
		
		try {
			prixNumber = Double.parseDouble(prix);
			qteNumber = Integer.parseInt(qte);
		} catch (NumberFormatException e) {
			return false;
		}
			
		return catalogue.addProduit(nom, prixNumber, qteNumber);
	}
	
	public static boolean supprimerProduit(String nomProduit) {
		return catalogue.removeProduit(nomProduit);
	}
}
