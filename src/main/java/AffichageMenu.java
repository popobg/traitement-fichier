import entites.Categorie;
import entites.Marque;
import entites.Produit;

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
        System.out.println("6. Afficher les allergènes les plus courants et leurs récurrences.");
        System.out.println("7. Afficher les additifs les plus courants et leurs récurrences.");
        System.out.println("8. Quitter l'application.");
        System.out.println();
    }

    public static void afficherInputIncorrect() {
        System.out.println("Le mot que vous avez entré n'est pas retrouvé dans la base de données.");
        System.out.println();
    }

    public static void afficherMarques(List<Marque> marques) {
        System.out.println();
        System.out.println("Voici les marques répertoriées dans la base de données :");
        for (Marque marque : marques) {
            System.out.println(marque.getNom());
        }
        System.out.println();
    }

    public static void afficherCategories(List<Categorie> categories) {
        System.out.println();
        System.out.println("Voici les catégories répertoriées dans la base de données :");
        for (Categorie categorie : categories) {
            System.out.println(categorie.getLibelle());
        }
        System.out.println();
    }

    public static void afficherMeilleursProduitsMarque(List<Produit> meilleursProduitsMarque) {
        System.out.println();
        System.out.println("Produits de grade nutritionnel A de la marque " + meilleursProduitsMarque.getFirst().getMarque() + " :");
        for (Produit produit : meilleursProduitsMarque) {
            System.out.printf("Produit : %s, catégorie : %s.%n", produit.getNom(), produit.getCategorie().getLibelle());
        }
        System.out.println();
    }

    public static void afficherFermetureProgramme() {
        System.out.println();
        System.out.println("Fermeture du programme...");
    }
}
