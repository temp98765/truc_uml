package metier;

import java.text.DecimalFormat;

import dal.I_ProduitDAO;
import dal.ProduitDAO;
import dal.ProduitDAOException;
import dal.ProduitDAOFactory;

public class Produit implements I_Produit {
    
	private I_ProduitDAO dao = null;
	
    private final String nom;
    private final double prixHT;
    private int quantite;
    private String catalogueName;
    
    public Produit(String nom, double prixHT, int quantite, String catalogueName) {
     	dao = ProduitDAOFactory.getProduitDao();
    	
    	this.nom = nom;
        this.prixHT = prixHT;
        this.quantite = quantite;
        this.catalogueName = catalogueName;
    }
    
    
    @Override
    public String toString() {
        return nom + " - prix HT : " + new DecimalFormat("#0.00").format(prixHT) + " € - prix TTC : " + new DecimalFormat("#0.00").format(getPrixUnitaireTTC()) + " € - quantité en stock : " + quantite + "\n";
    }

    @Override
    public boolean ajouter(int qteAchetee) {
        if (qteAchetee <= 0) {
            return false;
        }
        quantite += qteAchetee;
        try {
			dao.updateProduct(this);
		} catch (ProduitDAOException e) {
			throw new RuntimeException(e);
		}
        return true;
    }

    @Override
    public boolean enlever(int qteVendue) {
        if (qteVendue <= 0) {
            return false;
        }
        if (quantite - qteVendue <= 0) {
            return false;
        } 
         
        quantite -= qteVendue;
        try {
			dao.updateProduct(this);
		} catch (ProduitDAOException e) {
			throw new RuntimeException(e);
		}
        return true;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getQuantite() {
        return quantite;
    }

    @Override
    public double getPrixUnitaireHT() {
        return prixHT;
    }

    @Override
    public double getPrixUnitaireTTC() {
    	double value = prixHT + prixHT * 0.2f;
    	return (double)Math.round(value * 100d) / 100d;
    }

    @Override
    public double getPrixStockTTC() {
    	double value = getPrixUnitaireTTC() * quantite;
    	return (double)Math.round(value * 100d) / 100d;
    }


	@Override
	public void save() {
		try {
    	   dao.addProduct(this);
		} catch (ProduitDAOException e) {
			throw new RuntimeException(e);
	    }
	}


	@Override
	public String getCatalogueNom() {
		return catalogueName;
	}
}
