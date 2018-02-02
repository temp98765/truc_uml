package dal;

public class CatalogueDAOFactory {
	
	public static CatalogueDAO catalogueDAO = null;
	
	public static I_CatalogueDAO getCatalogueDAO() {
		if (catalogueDAO == null) {
			try {
				catalogueDAO =  new CatalogueDAO();
			} catch (CatalogueDAOException e) {
				throw new RuntimeException(e);
			}
		}
		return catalogueDAO;
	}

	public static void disposeAll() {
		try {
			if (catalogueDAO != null) {
				catalogueDAO.dispose();
				catalogueDAO = null;
			}
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
	}
}
