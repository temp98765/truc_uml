package dal;

import java.util.List;

import metier.I_Catalogue;
import metier.I_Produit;

public interface I_CatalogueDAO {
	public List<String> getAllCataloguesWithName() throws CatalogueDAOException;
	public List<String> getAllCataloguesNameWithNumberProduct() throws CatalogueDAOException;
	public void createCatalogue(String name) throws CatalogueDAOException;
	public I_Catalogue getCatalogue(String name) throws CatalogueDAOException;
	public boolean removeCatalogue(String name) throws CatalogueDAOException;
	public List<I_Produit> getAllProductFromCatalogue(String name) throws CatalogueDAOException;
}
