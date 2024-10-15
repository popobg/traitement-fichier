package services;

import entites.*;
import entites.generique.Pair;

import java.util.List;

public class AffichageMenu {
    public static void afficherMenuPrincipal() {
        System.out.println();
        System.out.print("-------------------------------");
        System.out.print(" Bienvenue dans votre application YukaBis ");
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println("Que souhaitez-vous consulter ?");
        System.out.println();
        System.out.println("1. Afficher les marques répertoriées.");
        System.out.println("2. Afficher les catégories répertoriées.");
        System.out.println("3. Rechercher les meilleurs produits pour une marque donnée.");
        System.out.println("4. Rechercher les meilleurs produits pour une catégorie donnée.");
        System.out.println("5. Rechercher les meilleurs produits par marque et par catégorie.");
        System.out.println("6. Afficher les N allergènes les plus courants et leur occurence.");
        System.out.println("7. Afficher les N additifs les plus courants et leur occurence.");
        System.out.println("8. Quitter l'application.");
    }

    public static void afficherInputIncorrect() {
        System.out.println();
        System.out.println("Le mot que vous avez entré n'est pas retrouvé dans la base de données.");
    }

    public static void afficherMarques(List<Marque> marques) {
        System.out.println();
        System.out.println("Voici les marques répertoriées dans la base de données :");
        for (Marque marque : marques) {
            System.out.println("- " + marque.getNom());
        }
    }

    public static void afficherCategories(List<Categorie> categories) {
        System.out.println();
        System.out.println("Voici les catégories répertoriées dans la base de données :");
        for (Categorie categorie : categories) {
            System.out.println("- " + categorie.getLibelle());
        }
    }

    public static void afficherMeilleursProduitsMarque(List<Produit> meilleursProduitsMarque, Marque marque) {
        System.out.println();

        if (meilleursProduitsMarque.isEmpty()) {
            System.out.printf("Il n'y a pas de produits de grade nutritionnel A pour la marque \"%s\".%n", marque.getNom());
            return;
        }

        System.out.printf("Produits de grade nutritionnel A de la marque \"%s\" :%n", marque.getNom());
        for (Produit produit : meilleursProduitsMarque) {
            System.out.printf("- Produit : \"%s\", catégorie : \"%s\".%n", produit.getNom(), produit.getCategorie().getLibelle());
        }
    }

    public static void afficherMeilleursProduitsCategorie(List<Produit> meilleursProduitsCategorie, Categorie categorie) {
        System.out.println();

        if (meilleursProduitsCategorie.isEmpty()) {
            System.out.printf("Il n'y a pas de produits de grade nutritionnel A pour la catégorie \"%s\".%n", categorie.getLibelle());
            return;
        }

        System.out.printf("Produits de grade nutritionnel A de la catégorie \"%s\" :%n", categorie.getLibelle());
        for (Produit produit : meilleursProduitsCategorie) {
            System.out.printf("- Produit : \"%s\", marque : \"%s\".%n", produit.getNom(), produit.getMarque().getNom());
        }
    }

    public static void afficherMeilleursProduitsCategorieMarque(List<Produit> meilleursProduitsCategorieMarque, Categorie categorie, Marque marque) {
        System.out.println();

        if (meilleursProduitsCategorieMarque.isEmpty()) {
            System.out.printf("Il n'y a pas de produits de grade nutritionnel A pour la marque \"%s\" et la catégorie \"%s\".%n", marque.getNom(), categorie.getLibelle());
            return;
        }

        System.out.println("Produits de grade nutritionnel A de la marque " + marque.getNom() + " et de la catégorie " + categorie.getLibelle() + " :");
        for (Produit produit : meilleursProduitsCategorieMarque) {
            System.out.printf("- Produit : \"%s\"%n", produit.getNom());
        }
    }

    public static void afficherAllergenesRecurrents(List<Pair<Allergene, Integer>> allergenesRecurrents, int nAllergenes) {
        System.out.println();

        if (nAllergenes > allergenesRecurrents.size()) {
            System.out.println("Voici la liste de tous les allergènes retrouvés dans la liste de produits, ainsi que le nombre de produits dans lesquels ils apparaissent :");
        }
        else {
            System.out.printf("Voici la liste des %d allergènes les plus fréquemments retrouvés dans la liste de produits, ainsi que le nombre de produits dans lesquels ils apparaissent :%n", nAllergenes);
        }

        for (Pair<Allergene, Integer> pair : allergenesRecurrents) {
            System.out.printf("- Nom de l'allergène : \"%s\", nombre de produits dans lequel il est présent : %d.%n", pair.first.getLibelle(), pair.second);
        }
    }

    public static void afficherAdditifsRecurrents(List<Pair<Additif, Integer>> additifsRecurrents, int nAdditifs) {
        System.out.println();

        if (nAdditifs > additifsRecurrents.size()) {
            System.out.println("Voici la liste de tous les additifs retrouvés dans la liste de produits, ainsi que le nombre de produits dans lesquels ils apparaissent :");
        }
        else {
            System.out.printf("Voici la liste des %d additifs les plus fréquemments retrouvés dans la liste de produits, ainsi que le nombre de produits dans lesquels ils apparaissent :%n", nAdditifs);
        }

        for (Pair<Additif, Integer> pair : additifsRecurrents) {
            System.out.printf("- Nom de l'additif : \"%s\", nombre de produits dans lequel il est présent : %d.%n", pair.first.getLibelle(), pair.second);
        }
    }

    public static void afficherFermetureProgramme() {
        System.out.println();
        System.out.println("Fermeture du programme...");
    }
}
