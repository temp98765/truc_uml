package metier;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Produit implements I_Produit {
    
    private final String nom;
    private final double prixHT;
    private int quantite;
    
    public Produit(String nom, double prixHT, int quantite) {
        this.nom = nom;
        this.prixHT = prixHT;
        this.quantite = quantite;
    }
    
    static Produit createProduit(String nom, double prixHT, int quantite) {
        return new Produit(nom, prixHT, quantite);
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
}
