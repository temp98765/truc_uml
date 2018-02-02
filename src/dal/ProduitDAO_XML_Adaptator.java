package dal;

import java.util.List;

import metier.I_Produit;

public class ProduitDAO_XML_Adaptator implements I_ProduitDAO {

	private final ProduitDAO_XML instance;
	
	public ProduitDAO_XML_Adaptator() {
		instance = new ProduitDAO_XML();
	}
	
	@Override
	public void dispose() throws ProduitDAOException {
		
	}

	@Override
	public void addProduct(I_Produit product) throws ProduitDAOException {
		instance.creer(product);
	}

	@Override
	public void updateProduct(I_Produit product) throws ProduitDAOException {
		instance.maj(product);
	}

	@Override
	public void removeProduct(I_Produit product) throws ProduitDAOException {
		instance.supprimer(product);
		
	}

}
