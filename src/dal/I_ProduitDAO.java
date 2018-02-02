package dal;


import metier.I_Produit;

public interface I_ProduitDAO {
	public void dispose() throws ProduitDAOException;
	public void addProduct(I_Produit product) throws ProduitDAOException;
	public void updateProduct(I_Produit product) throws ProduitDAOException;
	public void removeProduct(I_Produit product) throws ProduitDAOException;
}
	
