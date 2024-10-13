package service;

import entites.Categorie;
import entites.Marque;
import entites.Produit;
import entites.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private final Stock stock;

    public StockService(Stock stock) {
        this.stock = stock;
    }

    /**
     * Retourne une liste des marques des produits contenues dans les données
     * triée par ordre alphabétique (adapté aux caractères spéciaux français).
     * @return une liste d'objet Marque.
     */
    public List<Marque> getMarques() {
        return this.stock.getProduits().stream()
                .map(Produit::getMarque).sorted()
                .collect(Collectors.toList());
    }

    /**
     * Retourne une liste des catégories des produits contenues dans les données
     * triée par ordre alphabétique (adapté aux caractères spéciaux français).
     * @return une liste d'objet Marque.
     */
    public List<Categorie> getCategories() {
        return this.stock.getProduits().stream()
                .map(Produit::getCategorie).sorted()
                .collect(Collectors.toList());
    }

    /**
     * Retourne l'objet Marque correspondant à la recherche utilisateur
     * ou "null" si rien ne correspond.
     * @param marqueDesiree chaîne de caractères String issue de l'input utilisateur,
     *                      correspondant à la marque qu'il recherche dans
     *                      les données.
     * @return un objet Marque ou null.
     */
    public Marque getMarque(String marqueDesiree) {
        return this.stock.getProduits().stream()
                .map(Produit::getMarque)
                .filter(marque -> marque.getNom().equalsIgnoreCase(marqueDesiree))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne la liste des produits de grade nutritionnel A de la marque
     * demandée par l'utilisateur.
     * @param marque l'objet Marque pour lequel l'utilisateur souhaite obtenir des données.
     * @return une liste de produits.
     */
    public List<Produit> getMeilleursProduitsParMarque(Marque marque) {
        List<Produit> meilleursProduits = new ArrayList<Produit>();

        for (Produit produit : this.stock.getProduits()) {
            if (produit.getMarque() == marque && produit.getScoreNutritionnel().equals("A")) {
                meilleursProduits.add(produit);
            }
        }

        return meilleursProduits;
    }
}
