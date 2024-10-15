package services;

import entites.*;
import entites.generique.Pair;

import java.util.*;

/**
 * Classe proposant des méthodes pour récupérer des éléments précis
 * de l'objet Stock contenant une liste d'objets Produit.
 */
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
        // Utilisation d'un set pour éviter les doublons
        Set<Marque> marques = new HashSet<>();

        for (Produit produit : this.stock.getProduits()) {
            marques.add(produit.getMarque());
        }

        // Conversion en liste pour pouvoir trier la liste
        ArrayList<Marque> listeMarques = new ArrayList<>(marques);
        Collections.sort(listeMarques);

        return listeMarques;
    }

    /**
     * Retourne une liste des catégories des produits contenues dans les données
     * triée par ordre alphabétique (adapté aux caractères spéciaux français).
     * @return une liste d'objet Marque.
     */
    public List<Categorie> getCategories() {
        List<Categorie> categories = new ArrayList<>();

        for (Produit produit : this.stock.getProduits()) {
            // Alternative pour la méthode getMarques() pour gérer les doublons
            if (!categories.contains(produit.getCategorie())) {
                categories.add(produit.getCategorie());
            }
        }

        Collections.sort(categories);

        return categories;
    }

    /**
     * Retourne l'objet Marque correspondant à la recherche utilisateur,
     * ou "null" si rien ne correspond.
     * @param marqueDesiree chaîne de caractères String issue de l'input utilisateur,
     *                      correspondant à la marque qu'il recherche dans
     *                      les données.
     * @return un objet Marque ou null.
     */
    public Marque getMarque(String marqueDesiree) {
        return this.stock.getProduits().stream()
                .map(Produit::getMarque)
                .filter(marque -> marque.getNom().equalsIgnoreCase(marqueDesiree.toLowerCase().trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne l'objet Categorie correspondant à la recherche utilisateur,
     * ou "null" si rien ne correspond.
     * @param categorieDesiree chaîne de caractères String issue de l'input
     *                         utilisateur, correspondant à la catégorie
     *                         qu'il recherche dans les données.
     * @return un objet Marque ou null.
     */
    public Categorie getCategorie(String categorieDesiree) {
        return this.stock.getProduits().stream()
                .map(Produit::getCategorie)
                .filter(categorie -> categorie.getLibelle().equalsIgnoreCase(categorieDesiree.toLowerCase().trim()))
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

    /**
     * Retourne la liste des produits de grade nutritionnel A de la catégorie
     * demandée par l'utilisateur.
     * @param categorie l'objet Categorie pour lequel l'utilisateur souhaite obtenir des données.
     * @return une liste de produits.
     */
    public List<Produit> getMeilleursProduitsParCategorie(Categorie categorie) {
        List<Produit> meilleursProduits = new ArrayList<Produit>();

        for (Produit produit : this.stock.getProduits()) {
            if (produit.getCategorie() == categorie && produit.getScoreNutritionnel().equals("A")) {
                meilleursProduits.add(produit);
            }
        }

        return meilleursProduits;
    }

    /**
     * Retourne la liste des produits de grade nutritionnel A de la catégorie
     * et de la marque demandées par l'utilisateur.
     * @param categorie l'objet Categorie pour lequel l'utilisateur souhaite
     *                  obtenir des données.
     * @param marque l'objet Marque pour lequel l'utilisateur souhaite
     *               obtenir des données.
     * @return une liste de produits.
     */
    public List<Produit> getMeilleursProduitsParMarqueEtCategorie(Categorie categorie, Marque marque) {
        List<Produit> meilleursProduits = new ArrayList<>();

        for (Produit produit : this.stock.getProduits()) {
            if (produit.getCategorie() == categorie
            && produit.getMarque() == marque
            && produit.getScoreNutritionnel().equals("A")) {
                meilleursProduits.add(produit);
            }
        }

        return meilleursProduits;
    }

    /**
     * Retourne la liste des n allergènes les plus récurrents dans la liste
     * de produits, ainsi que leur nombre d'occurences.
     * @param n nombre d'allergènes que l'utilisateur veut consulter.
     * @return une liste de paire d'allergènes et leur nombre d'occurences dans
     *          la liste de produits.
     */
    public List<Pair<Allergene, Integer>> getAllergenesRecurrents(int n) {
        List<Pair<Allergene, Integer>> allergenesCount = new ArrayList<>();

        for (Produit produit : this.stock.getProduits()) {
            for (Allergene allergene : produit.getAllergenes()) {
                boolean foundAllergene = false;

                for (Pair<Allergene, Integer> element : allergenesCount) {
                    if (element.first.equals(allergene)) {
                        element.second++;
                        foundAllergene = true;
                        break;
                    }
                }

                if (!foundAllergene) {
                    allergenesCount.add(new Pair<>(allergene, 1));
                }
            }
        }

        allergenesCount.sort(Comparator.comparingInt(p -> p.second));

        if (n >= allergenesCount.size()) {
            return allergenesCount.reversed();
        }

        return allergenesCount.subList((allergenesCount.size() - n), allergenesCount.size()).reversed();
    }

    /**
     * Retourne la liste des n additifs les plus récurrents dans la liste
     * de produits, ainsi que leur nombre d'occurences.
     * @param n nombre d'additifs que l'utilisateur veut consulter.
     * @return une liste de paire d'additifs et leur nombre d'occurences dans
     *          la liste de produits.
     */
    public List<Pair<Additif, Integer>> getAdditifsRecurrents(int n) {
        List<Pair<Additif, Integer>> additifsCount = new ArrayList<>();

        for (Produit produit : this.stock.getProduits()) {
            for (Additif additif : produit.getAdditifs()) {
                boolean foundAdditif = false;

                for (Pair<Additif, Integer> element : additifsCount) {
                    if (element.first.equals(additif)) {
                        element.second++;
                        foundAdditif = true;
                        break;
                    }
                }

                if (!foundAdditif) {
                    additifsCount.add(new Pair<>(additif, 1));
                }
            }
        }

        additifsCount.sort(Comparator.comparingInt(p -> p.second));

        if (n >= additifsCount.size()) {
            return additifsCount;
        }

        return additifsCount.subList((additifsCount.size() - n), additifsCount.size());
    }
}
