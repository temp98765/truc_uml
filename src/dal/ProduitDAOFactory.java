package dal;

public class ProduitDAOFactory {
	
	public static I_ProduitDAO createProduitDao() throws ProduitDAOException {
		return new ProduitDAO();
	}
	
	public static I_ProduitDAO createProduitDaoXML() {
		return new ProduitDAO_XML_Adaptator();
	}
}
