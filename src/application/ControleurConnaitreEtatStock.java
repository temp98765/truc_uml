package application;

import java.util.List;

import dal.CatalogueDAOException;
import metier.I_Catalogue;

public class ControleurConnaitreEtatStock {
	
	private static I_Catalogue catalogue;
	
	public static void setCatalogue(I_Catalogue _catalogue) {
		catalogue = _catalogue;
	}
	
	public static String getStock() {
		return catalogue.toString();
	}

	public static String[] getAllProductName() {
		return catalogue.getNomProduits();
	}

}
