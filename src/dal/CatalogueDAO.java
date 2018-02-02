package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

public class CatalogueDAO implements I_CatalogueDAO {
	private Connection connection;
	private PreparedStatement getAllCataloguesStm;
	private PreparedStatement getCountProductInCatalogue;
	private PreparedStatement createCatalogue;
	private PreparedStatement getCatalogue;
	private PreparedStatement removeCatalogue;
	private PreparedStatement getAllProductsInCatalogue;
	
	public CatalogueDAO() throws CatalogueDAOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", "amiotj", "salade");
			getAllCataloguesStm = connection.prepareStatement("select nom from Catalogue");
			getCountProductInCatalogue = connection.prepareStatement("select count(*) from Produit where catalogueId = (select id from Catalogue where nom = ?)");
			createCatalogue = connection.prepareStatement("insert into Catalogue values (null, ?)");
			getCatalogue = connection.prepareStatement("select nom from Catalogue where nom = ?");
			removeCatalogue = connection.prepareStatement("delete from Catalogue where nom = ?");
			getAllProductsInCatalogue = connection.prepareStatement("select Produit.nom, prixHT, quantite from Produit inner join Catalogue on Catalogue.id = Produit.catalogueID where Catalogue.nom = ?");
		} catch (SQLException | ClassNotFoundException e) {
			dispose();
			throw new CatalogueDAOException(e);
		}
	}

	
	@Override
	public List<String> getAllCataloguesWithName() throws CatalogueDAOException {
		try {
			List<String> catalogues = new ArrayList<>();
			ResultSet result = getAllCataloguesStm.executeQuery();
			while (result.next()) {
				//No need to check for null
				catalogues.add(result.getString(1));
			}
			return catalogues;
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
	}
	
	@Override
	public List<String> getAllCataloguesNameWithNumberProduct() throws CatalogueDAOException {
		List<String> cataloguesWithNb = new ArrayList<>();
		try {
			List<String> names = getAllCataloguesWithName();
			for (String name : names) {
				getCountProductInCatalogue.setString(1, name);
				ResultSet rst = getCountProductInCatalogue.executeQuery();
				rst.next();
				int nb = rst.getInt(1);	
				cataloguesWithNb.add(name + " : " + nb + " produits");
			}
			return cataloguesWithNb;
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
	}
	
	
	public void dispose() throws CatalogueDAOException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
	}

	@Override
	public void createCatalogue(String name) throws CatalogueDAOException {
		try {
			createCatalogue.setString(1, name);
			createCatalogue.executeUpdate();
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
		
	}

	@Override
	public I_Catalogue getCatalogue(String name) throws CatalogueDAOException {
		try {
			getCatalogue.setString(1, name);
			ResultSet rst = getCatalogue.executeQuery();
			if (rst.next()) {
				return new Catalogue(name);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
	}

	@Override
	public boolean removeCatalogue(String name) throws CatalogueDAOException {
		try {
			removeCatalogue.setString(1, name);
			int rst = removeCatalogue.executeUpdate();
			return rst == 1;
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
		
	}

	@Override
	public List<I_Produit> getAllProductFromCatalogue(String catalogueName) throws CatalogueDAOException {
		 List<I_Produit> produits = new ArrayList<>();
		try {
			getAllProductsInCatalogue.setString(1, catalogueName);
			ResultSet rst = getAllProductsInCatalogue.executeQuery();
			while (rst.next()) {
				String productName = rst.getString(1);
				double prixHT = rst.getDouble(2);
				int quantite = rst.getInt(3);
				produits.add(new Produit(productName, prixHT, quantite, catalogueName));
			}
			return produits;
		} catch (SQLException e) {
			throw new CatalogueDAOException(e);
		}
	}
}
