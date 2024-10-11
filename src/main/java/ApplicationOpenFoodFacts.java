import entites.*;
import tools.ParseFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplicationOpenFoodFacts {
    public static void main(String[] args) {
        // PARSE LE FICHIER POUR RECUPERER LES DONNEES
        // Construction du chemin vers le fichier de données
        String repertoireActuel = Paths.get(".").toAbsolutePath().toString();
        String stringPath = repertoireActuel.substring(0, repertoireActuel.length() - 1) + "src\\main\\java\\fichier\\open-food-facts.csv";
        Path path = Paths.get(stringPath);

        List<String> lignesOpenFoodFacts = new ArrayList<>();
        try {
            lignesOpenFoodFacts = ParseFile.parseFile(path);
        }
        catch (IOException e) {
            System.out.println("Le contenu du fichier n'a pas pu être récupéré. Vérifiez que le fichier existe et que le chemin donné est correct.");
        }

        // Optimisation : on s'assure qu'une seule instance
        // d'un objet réutilisé est créé.
        HashMap<String, Categorie> mapCategories = new HashMap<>();
        HashMap<String, Marque> mapMarques = new HashMap<>();
        HashMap<String, Ingredient> mapIngredients = new HashMap<>();
        HashMap<String, Allergene> mapAllergenes = new HashMap<>();
        HashMap<String, Additif> mapAdditifs = new HashMap<>();

        // utiles pour les valeurs nutritionnelles
        String[] entetesTab = lignesOpenFoodFacts.get(0).split("\\|");
        List<Produit> produits = new ArrayList<>();

        // PARSE LES DONNEES POUR LES STOCKER DANS DES OBJETS
        // On saute la première ligne d'entêtes
        for (int i = 1; i < lignesOpenFoodFacts.size(); i++) {
            String[] splittedLigne = lignesOpenFoodFacts.get(i).split("\\|");

            // Catégorie
            String categorieNom = splittedLigne[0].trim().toLowerCase();
            Categorie categorie = mapCategories.computeIfAbsent(categorieNom, k -> new Categorie(categorieNom));

            // Marque
            String marqueNom = splittedLigne[1].trim();
            Marque marque = mapMarques.computeIfAbsent(marqueNom.toLowerCase(), k -> new Marque(marqueNom.toLowerCase()));

            // Nom
            String nom = splittedLigne[2].trim();

            // Score nutritionnel
            String score = splittedLigne[3].trim().toUpperCase();

            // Ingrédients
            List<Ingredient> listeIngredients = new ArrayList<>();
            String[] tabIngredients = splittedLigne[4].trim().replace("_", " ").split("[,;]");

            for (String ing : tabIngredients) {
                ing = ing.trim();

                if (ing.endsWith(".")) {
                    ing = ing.substring(0, ing.length() - 1);
                }

                if (!ing.isEmpty()) {
                    Ingredient ingredient = mapIngredients.computeIfAbsent(ing, Ingredient::new);
                    listeIngredients.add(ingredient);
                }
            }

            // Allergènes
            List<Allergene> listeAllergenes = new ArrayList<>();

            if (splittedLigne.length > 28) {
                String allergenes = splittedLigne[28].trim();
                if (!allergenes.isEmpty()) {
                    String[] tabAllergenes = allergenes.replace("_", " ").split("[,;]");

                    for (String al : tabAllergenes) {
                        al = al.trim().toLowerCase();

                        if (al.startsWith("en:") || al.startsWith("fr:")) {
                            al = al.substring(0, 3);
                        }

                        Allergene allergene = mapAllergenes.computeIfAbsent(al, Allergene::new);
                        listeAllergenes.add(allergene);
                    }
                }
            }

            // Additifs
            List<Additif> listeAdditifs = new ArrayList<>();

            if (splittedLigne.length > 29) {
                String additifs = splittedLigne[29].trim();
                if (!additifs.isEmpty()) {
                    String[] tabAdditifs = additifs.replace("_", " ").split("[,;]");

                    for (String ad : tabAdditifs) {
                        ad = ad.trim().toLowerCase();

                        Additif additif = mapAdditifs.computeIfAbsent(ad, Additif::new);
                        listeAdditifs.add(additif);
                    }
                }
            }

            // présence d'huile de palme
            Boolean presenceHuileDePalme = null;
            if (!(splittedLigne.length < 28 || splittedLigne[27].isEmpty())) {
                int presenceHuileDePalmeInt = Integer.parseInt(splittedLigne[27].trim());
                presenceHuileDePalme = presenceHuileDePalmeInt == 1;
            }

            // Valeurs énergétiques
            HashMap<String, Double> valeursNutritionnelles = new HashMap<>();
            for (int j = 5; j < 27; j++) {
                if (splittedLigne.length <= j) {
                    break;
                }

                String donnee = splittedLigne[j].trim();
                Double valeur = null;

                if (!donnee.isEmpty()) {
                    valeur = Double.parseDouble(donnee);
                }

                valeursNutritionnelles.put(entetesTab[j].trim(), valeur);
            }

            Produit produit = new Produit(nom, marque, categorie, listeAllergenes, listeAdditifs, listeIngredients, score, valeursNutritionnelles, presenceHuileDePalme);

            produits.add(produit);
        }

        Stock stock = new Stock(produits);
    }
}
