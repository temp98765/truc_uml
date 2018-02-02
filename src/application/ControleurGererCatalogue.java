package application;

import java.util.List;

import dal.CatalogueDAO;
import dal.CatalogueDAOException;
import dal.CatalogueDAOFactory;
import dal.I_CatalogueDAO;
import metier.I_Catalogue;

public class ControleurGererCatalogue {
	
	private static I_CatalogueDAO dao = CatalogueDAOFactory.createCatalogueDAO();
	
	public static List<String> getAllCataloguesName() {
		try {
			return dao.getAllCataloguesWithName();
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<String> getAllCataloguesNameWithNumberProduct() {
		try {
			return dao.getAllCataloguesNameWithNumberProduct();
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean createCatalogue(String name) {
		 List<String> names = getAllCataloguesName();
		 if (names.contains(names)) {
			 return false;
		 }
		 try {
			dao.createCatalogue(name);
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
		 return true;
	}
	
	public static void setCurrentCatalogue(String name) {
		I_Catalogue catalogue;
		try {
			catalogue = dao.getCatalogue(name);
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
		ControleurConnaitreEtatStock.setCatalogue(catalogue);
		ControleurCreerOuSupprimerUnProduit.setCatalogue(catalogue);
		ControleurEnregistrerUnAchatOuUneVente.setCatalogue(catalogue);
	}

	public static void removeCatalogue(String name) {
		try {
			dao.removeCatalogue(name);
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
	}
}
