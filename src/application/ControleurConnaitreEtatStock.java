package application;

import metier.Catalogue;

public class ControleurConnaitreEtatStock {
	public static String getStock() {
		return Catalogue.getCatalogue().toString();
	}
}
