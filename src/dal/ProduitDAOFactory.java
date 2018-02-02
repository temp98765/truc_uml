package dal;

public class ProduitDAOFactory {
	
	private static ProduitDAO produitDAO = null;
	private static ProduitDAO_XML_Adaptator produitXML = null;
	
	public static I_ProduitDAO getProduitDao() {
		if (produitDAO == null) {
			try {
				produitDAO = new ProduitDAO();
			} catch (ProduitDAOException e) {
				throw new RuntimeException(e);
			}
		}
		return produitDAO;
	}
	
	public static I_ProduitDAO getProduitDaoXML() {
		if (produitXML == null) {
			produitXML = new ProduitDAO_XML_Adaptator();
		}
		return produitXML;
	}

	public static void disposeAll() {
		try {
			if (produitDAO != null) {
				produitDAO.dispose();
				produitDAO = null;
			}
			
			if (produitXML != null) {
				produitXML.dispose();
				produitXML = null;
			}
		} catch (ProduitDAOException e) {
			throw new RuntimeException(e);
		}
	}

}
