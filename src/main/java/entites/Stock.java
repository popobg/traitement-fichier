package entites;

import java.util.List;

public class Stock {
    private List<Produit> produits;

    public Stock(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
