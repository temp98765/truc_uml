package application;

import metier.Catalogue;

public class ControleurEnregistrerUnAchatOuUneVente {
	public static boolean acheterStock(String nomProduit, String qteAchetee) {
		int qt;
		try {
			qt = Integer.parseInt(qteAchetee);
		} catch (NumberFormatException e) {
			return false;
		}
		return Catalogue.getCatalogue().acheterStock(nomProduit, qt);
	}
}
