package dal;

public class CatalogueDAOFactory {
	public static I_CatalogueDAO createCatalogueDAO() {
		try {
			return new CatalogueDAO();
		} catch (CatalogueDAOException e) {
			throw new RuntimeException(e);
		}
	}
}
