package metier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Catalogue implements I_Catalogue {
    
    private final List<Produit> produits = new ArrayList<>();
    
    private static Catalogue catalogue = null;
    
    private Catalogue() {
    }
    
    public static Catalogue getCatalogue() {
    	if (catalogue == null) {
    		catalogue = new Catalogue();
    	}
    	return catalogue;
    }
    
    
    
    private String cleanName(String name) {
    	String newName = name.trim();
    	newName = newName.replaceAll("\t", " ");
    	return newName;
    }
    
    private Produit getProductByName(String name) {
        for (Produit produit : produits) {
            if (produit.getNom().equals(name)) {
                return produit;
            }
        }
        return null;
    }
    
    @Override
    public boolean addProduit(I_Produit produit) {
        if (produit == null) {
            return false;
        }
        
        Produit myProduct = new Produit(cleanName(produit.getNom()), produit.getPrixUnitaireHT(), produit.getQuantite());
        
        if (getProductByName(myProduct.getNom()) != null) {
            return false;
        }
        
        if (myProduct.getQuantite() < 0) {
            return false;
        }
        
        
        if (myProduct.getPrixUnitaireHT() <= 0) {
            return false;
        }
        produits.add(myProduct);
        return true;
    }
    
    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        Produit myProduct = new Produit(cleanName(nom), prix, qte);
        return addProduit(myProduct);
    }
    
    @Override
    public int addProduits(List<I_Produit> liste) {
        if (liste == null) {
            return 0;
        }
        
        int qt = 0;
        for (I_Produit produit : liste) {
            if (addProduit(produit)) {
                qt++;
            }
        }
        return qt;
    }
    
    @Override
    public double getMontantTotalTTC() {
        double total = 0;
        for (Produit produit : produits) {
        	total += (produit.getPrixUnitaireHT() + produit.getPrixUnitaireHT() * 0.2f) * produit.getQuantite();
        }
        return (double)Math.round(total * 100d) / 100d;
    }

    @Override
    public boolean removeProduit(String nom) {
        if (nom == null) {
            return false;
        }
        
        String myName = nom.trim();
        Iterator<Produit> it = produits.iterator();
        while (it.hasNext()) {
            Produit produit = it.next();
            if (produit.getNom().equals(myName)) {
                it.remove();
            	return true;
            }
        }
        return false;
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
        Produit product = getProductByName(nomProduit.trim());
        if (product == null) {
            return false;
        }
        
        return product.ajouter(qteAchetee);
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
        Produit product = getProductByName(nomProduit.trim());
        if (product == null) {
            return false;
        }
        
        return product.enlever(qteVendue);
    }

    @Override
    public String[] getNomProduits() {
        String[] noms = new String[produits.size()];
        for (int i = 0; i < produits.size(); i++) {
            noms[i] = produits.get(i).getNom();
        }
        Arrays.sort(noms);
        return noms;
    }

    @Override
    public void clear() {
        produits.clear();
    }
    
    @Override
    public String toString() {
        String str = new String();
        for (Produit produit : produits) {
            str += produit.toString();
        }
        str += "\nMontant total TTC du stock : " + new DecimalFormat("#0.00").format(getMontantTotalTTC()) + " €";
        return str;
    }
}
