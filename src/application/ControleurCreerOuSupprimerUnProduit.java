package application;

import java.util.List;

import metier.Catalogue;
import metier.I_Produit;

public class ControleurCreerOuSupprimerUnProduit {
	public static boolean addProduit(I_Produit produit) {
		return Catalogue.getCatalogue().addProduit(produit);
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
			
		return Catalogue.getCatalogue().addProduit(nom, prixNumber, qteNumber);
	}
	
	public static boolean supprimerProduit(String nomProduit) {
		return Catalogue.getCatalogue().removeProduit(nomProduit);
	}
}
