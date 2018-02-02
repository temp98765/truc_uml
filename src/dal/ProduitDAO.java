package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.I_Produit;
import metier.Produit;

public class ProduitDAO implements I_ProduitDAO {
	
	private Connection connection;
	
	private PreparedStatement addProductStm;
	private PreparedStatement updateProductStm;
	private PreparedStatement removeProductStm;
	private PreparedStatement getAllProductStm;
	private PreparedStatement getIdFromCatalogueName;
	private PreparedStatement getNameFromCatalogueId;
	
	public ProduitDAO() throws ProduitDAOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", "amiotj", "salade");
			addProductStm = connection.prepareStatement("insert into Produit values (null, ?, ?, ?, ?)");
			updateProductStm = connection.prepareStatement("update Produit set prixHT = ?, quantite = ? where nom = ? and catalogueId = ?");
			removeProductStm = connection.prepareStatement("delete from Produit where nom = ? and catalogueId = ?");
			getAllProductStm = connection.prepareStatement("select catalogueId, nom, prixHt, quantite from Produit");
			getIdFromCatalogueName = connection.prepareStatement("select id from Catalogue where nom = ?");
			getNameFromCatalogueId = connection.prepareStatement("select nom from Catalogue where id = ?");
		} catch (SQLException | ClassNotFoundException e) {
			dispose();
			throw new ProduitDAOException(e);
		}
	}
	
	@Override
	public void dispose() throws ProduitDAOException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ProduitDAOException(e);
			}
		}
	}
	
	
	private String getCatalogueIdFromName(String catalogueName) throws ProduitDAOException {
		try {
			getIdFromCatalogueName.setString(1, catalogueName);
			ResultSet rst = getIdFromCatalogueName.executeQuery();
			rst.next();
			String id = rst.getString(1);
			return id;
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}
	
	@Override
	public void addProduct(I_Produit product) throws ProduitDAOException {
		try {
			String idCatalogue = getCatalogueIdFromName(product.getCatalogueNom());
			
			addProductStm.setString(1, idCatalogue);
			addProductStm.setString(2, product.getNom());
			addProductStm.setDouble(3, product.getPrixUnitaireHT());
			addProductStm.setInt(4, product.getQuantite());
			
			addProductStm.executeUpdate();
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}
	
	@Override
	public void updateProduct(I_Produit product) throws ProduitDAOException {
		try {
			updateProductStm.setDouble(1, product.getPrixUnitaireHT());
			updateProductStm.setInt(2, product.getQuantite());
			updateProductStm.setString(3, product.getNom());
			updateProductStm.setString(4, getCatalogueIdFromName(product.getCatalogueNom()));
			updateProductStm.executeUpdate();
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}

	@Override
	public void removeProduct(I_Produit product) throws ProduitDAOException {
		try {
			removeProductStm.setString(1, product.getNom());
			removeProductStm.setString(2, getCatalogueIdFromName(product.getCatalogueNom()));
			removeProductStm.executeQuery();
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}
	
	@Override
	public List<I_Produit> getAllProduct() throws ProduitDAOException {
		try {
			ResultSet result = getAllProductStm.executeQuery();
			List<I_Produit> produits = new ArrayList<>();
			while (result.next()) {
				//no need to check for null
				String catalogueId = result.getString(1);
				String catalogueName = getCatalogueNameFromId(catalogueId);
				String string = result.getString(2);
				double prixHt = result.getDouble(3);
				int qt = result.getInt(4);
				produits.add(new Produit(string, prixHt, qt, catalogueName));
			}
			return produits;
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}

	private String getCatalogueNameFromId(String catalogueId) throws ProduitDAOException {
		try {
			getNameFromCatalogueId.setString(1, catalogueId);
			ResultSet rst = getNameFromCatalogueId.executeQuery();
			rst.next();
			return rst.getString(1);
		} catch (SQLException e) {
			throw new ProduitDAOException(e);
		}
	}
}
