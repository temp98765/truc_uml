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
	
	public ProduitDAO() throws ProduitDAOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", "amiotj", "salade");
			addProductStm = connection.prepareStatement("insert into Produit values (null, ?, ?, ?)");
			updateProductStm = connection.prepareStatement("update Produit set prixHT = ?, quantite = ? where nom = ?");
			removeProductStm = connection.prepareStatement("delete from Produit where nom = ?");
			getAllProductStm = connection.prepareStatement("select nom, prixHt, quantite from Produit");
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
	
	@Override
	public void addProduct(I_Produit product) throws ProduitDAOException {
		try {
			addProductStm.setString(1, product.getNom());
			addProductStm.setDouble(2, product.getPrixUnitaireHT());
			addProductStm.setInt(3, product.getQuantite());
			
			addProductStm.executeUpdate();
		} catch (SQLException e) {
			dispose();
			throw new ProduitDAOException(e);
		}
	}
	
	@Override
	public void updateProduct(I_Produit product) throws ProduitDAOException {
		try {
			updateProductStm.setDouble(1, product.getPrixUnitaireHT());
			updateProductStm.setInt(2, product.getQuantite());
			updateProductStm.setString(3, product.getNom());
			
			updateProductStm.executeUpdate();
		} catch (SQLException e) {
			dispose();
			throw new ProduitDAOException(e);
		}
	}

	@Override
	public void removeProduct(I_Produit product) throws ProduitDAOException {
		try {
			removeProductStm.setString(1, product.getNom());
			removeProductStm.executeQuery();
		} catch (SQLException e) {
			dispose();
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
				String string = result.getString(1);
				double prixHt = result.getDouble(2);
				int qt = result.getInt(3);
				produits.add(new Produit(string, prixHt, qt));
			}
			return produits;
		} catch (SQLException e) {
			dispose();
			throw new ProduitDAOException(e);
		}
	}
}
