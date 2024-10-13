package tools;

import entites.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    // Optimisation : on s'assure qu'une seule instance
    // d'un objet réutilisable est créé.
    private final HashMap<String, Categorie> mapCategories = new HashMap<>();
    private final HashMap<String, Marque> mapMarques = new HashMap<>();
    private final HashMap<String, Ingredient> mapIngredients = new HashMap<>();
    private final HashMap<String, Allergene> mapAllergenes = new HashMap<>();
    private final HashMap<String, Additif> mapAdditifs = new HashMap<>();

    /**
     * Récupère les données d'une liste de chaînes de caractères obtenues
     * à partir d'un fichier répertoriant des produits alimentaires
     * et les réparties dans différents objets.
     * L'objet Produit créé à partir d'une ligne contient
     * toutes ces données organisées en un ensemble d'attributs.
     * @param lignesFichier liste de chaînes de caractères List<String>
     *                      obtenue à partir d'un fichier.
     * @return un objet Stock qui contient une liste des produits
     *          qui ont pu être récupérés.
     */
    public Stock parseData(List<String> lignesFichier) {
        // Les en-têtes sont utiles pour les valeurs nutritionnelles
        String[] entetesTab = lignesFichier.getFirst().split("\\|");
        List<Produit> produits = new ArrayList<>();

        // Démarre la boucle après l'en-tête
        for (int i = 1; i < lignesFichier.size(); i++) {
            String[] splittedLigne = lignesFichier.get(i).split("\\|");

            // Gestion des cas d'erreurs liées au séparateur
            if (splittedLigne.length > entetesTab.length) {
                System.out.printf("ERREUR:ligne %d contient %d colonnes, mais l'entête contient %d colonnes.%n", i + 1, splittedLigne.length, entetesTab.length);
                continue;
            }

            // Catégorie
            String categorieNom = splittedLigne[0].trim().toLowerCase().replaceAll("-", " ");
            Categorie categorie = this.mapCategories.computeIfAbsent(categorieNom, k -> new Categorie(categorieNom));

            // Marque
            String marqueNom = splittedLigne[1].trim().replaceAll("-", " ");
            Marque marque = this.mapMarques.computeIfAbsent(marqueNom, k -> new Marque(marqueNom));

            // Nom
            String nom = splittedLigne[2].trim();

            // Score nutritionnel
            String score = splittedLigne[3].trim().toUpperCase();

            // Ingrédients
            List<Ingredient> listeIngredients = parseIngredients(splittedLigne);

            // Allergènes
            List<Allergene> listeAllergenes = parseAllergenes(splittedLigne);

            // Additifs
            List<Additif> listeAdditifs = parseAdditifs(splittedLigne);

            // présence d'huile de palme
            Boolean presenceHuileDePalme = null;
            if (!(splittedLigne.length < 28 || splittedLigne[27].isEmpty())) {
                int presenceHuileDePalmeInt = Integer.parseInt(splittedLigne[27].trim());
                presenceHuileDePalme = presenceHuileDePalmeInt == 1;
            }

            // Valeurs énergétiques
            HashMap<String, Double> valeursNutritionnelles = parseValeursNutritionnelles(splittedLigne, entetesTab);

            // Instanciation du produit
            Produit produit = new Produit(nom, marque, categorie, listeAllergenes, listeAdditifs, listeIngredients, score, valeursNutritionnelles, presenceHuileDePalme);

            produits.add(produit);
        }

        return new Stock(produits);
    }

    private List<Ingredient> parseIngredients(String[] ligne) {
        List<Ingredient> listeIngredients = new ArrayList<>();
        String[] tabIngredients = ligne[4].trim().replace("_", " ").split("[,;]");

        for (String ing : tabIngredients) {
            ing = ing.trim();

            if (ing.endsWith(".")) {
                ing = ing.substring(0, ing.length() - 1);
            }

            if (!ing.isEmpty()) {
                Ingredient ingredient = this.mapIngredients.computeIfAbsent(ing, Ingredient::new);
                listeIngredients.add(ingredient);
            }
        }

        return listeIngredients;
    }

    private List<Allergene> parseAllergenes(String[] ligne) {
        List<Allergene> listeAllergenes = new ArrayList<>();

        if (ligne.length > 28) {
            String allergenes = ligne[28].trim();
            if (!allergenes.isEmpty()) {
                String[] tabAllergenes = allergenes.replace("_", " ").split("[,;]");

                for (String al : tabAllergenes) {
                    al = al.trim().toLowerCase();

                    if (al.startsWith("en:") || al.startsWith("fr:")) {
                        al = al.substring(0, 3);
                    }

                    Allergene allergene = this.mapAllergenes.computeIfAbsent(al, Allergene::new);
                    listeAllergenes.add(allergene);
                }
            }
        }

        return listeAllergenes;
    }

    private List<Additif> parseAdditifs(String[] ligne) {
        List<Additif> listeAdditifs = new ArrayList<>();

        if (ligne.length > 29) {
            String additifs = ligne[29].trim();
            if (!additifs.isEmpty()) {
                String[] tabAdditifs = additifs.replace("_", " ").split("[,;]");

                for (String ad : tabAdditifs) {
                    ad = ad.trim().toLowerCase();

                    Additif additif = this.mapAdditifs.computeIfAbsent(ad, Additif::new);
                    listeAdditifs.add(additif);
                }
            }
        }

        return listeAdditifs;
    }

    private HashMap<String, Double> parseValeursNutritionnelles(String[] ligne, String[] header) {
        HashMap<String, Double> valeursNutritionnelles = new HashMap<>();

        for (int j = 5; j < 27; j++) {
            if (ligne.length <= j) {
                break;
            }

            String donnee = ligne[j].trim();
            Double valeur = null;

            if (!donnee.isEmpty()) {
                valeur = Double.parseDouble(donnee);
            }

            valeursNutritionnelles.put(header[j].trim(), valeur);
        }

        return valeursNutritionnelles;
    }
}
