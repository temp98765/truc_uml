package application;

import metier.I_Catalogue;

public class ControleurEnregistrerUnAchatOuUneVente {
	
	private static I_Catalogue catalogue;
	
	public static void setCatalogue(I_Catalogue _catalogue) {
		catalogue = _catalogue;
	}
	
	
	public static boolean acheterStock(String nomProduit, String qteAchetee) {
		int qt;
		try {
			qt = Integer.parseInt(qteAchetee);
		} catch (NumberFormatException e) {
			return false;
		}
		return catalogue.acheterStock(nomProduit, qt);
	}
	
	public static boolean vendreStock(String nomProduit, String qteVendu) {
		int qt;
		try {
			qt = Integer.parseInt(qteVendu);
		} catch (NumberFormatException e) {
			return false;
		}
		return catalogue.vendreStock(nomProduit, qt);
	}
}
