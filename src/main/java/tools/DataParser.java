package tools;

import entites.*;

import java.util.*;

/**
 * Classe permettant de créer les objets nécessaires à la création
 * de l'objet Stock à partir des lignes d'un fichier de données.
 */
public class DataParser {
    // Optimisation : on s'assure qu'une seule instance
    // d'un objet réutilisable est créé.
    /**
     * HashMap stockant en clé le nom de la catégorie et en valeur l'objet Categorie
     * créé à partir de celui-ci.
     */
    private final HashMap<String, Categorie> mapCategories = new HashMap<>();

    /**
     * HashMap stockant en clé le nom de la marque et en valeur l'objet Marque
     * créé à partir de celui-ci.
     */
    private final HashMap<String, Marque> mapMarques = new HashMap<>();

    /**
     * HashMap stockant en clé le nom de l'ingrédient et en valeur l'objet Ingredient
     * créé à partir de celui-ci.
     */
    private final HashMap<String, Ingredient> mapIngredients = new HashMap<>();

    /**
     * HashMap stockant en clé le nom de l'allergène et en valeur l'objet Allergene
     * créé à partir de celui-ci.
     */
    private final HashMap<String, Allergene> mapAllergenes = new HashMap<>();

    /**
     * HashMap stockant en clé le nom de l'additif et en valeur l'objet Additif
     * créé à partir de celui-ci.
     */
    private final HashMap<String, Additif> mapAdditifs = new HashMap<>();

    /**
     * Récupère les données d'une liste de chaînes de caractères obtenues
     * à partir d'un fichier répertoriant des produits alimentaires
     * et les répartit dans différents objets.
     *
     * L'objet Produit créé à partir d'une ligne contient toutes ces données
     * organisées en un ensemble d'attributs.
     * Chaque produit est ensuite ajouté à une liste de produits contenue
     * dans l'objet Stock retourné.
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
            Categorie categorie = this.mapCategories.computeIfAbsent(categorieNom, key -> new Categorie(categorieNom));

            // Marque
            String marqueNom = splittedLigne[1].trim().toLowerCase().replaceAll("-", " ");

            // faute de frappe dans les données
            if (marqueNom.endsWith("'")) {
                marqueNom = marqueNom.substring(0, (marqueNom.length() - 1));
            }

            String marqueNomFinale = marqueNom;

            Marque marque = this.mapMarques.computeIfAbsent(marqueNom, key -> new Marque(marqueNomFinale));

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

    /**
     * Méthode privée utilisée par la méthode parseData() pour
     * extraire les ingrédients de la ligne afin de créer des objets Ingredient.
     * Retourne la liste des ingrédients récupérés.
     * @param ligne tableau de chaîne de caractères qui représente
     *              la ligne à traiter.
     * @return  une liste d'objets de type Ingredient.
     */
    private List<Ingredient> parseIngredients(String[] ligne) {
        Set<Ingredient> listeIngredients = new HashSet<>();
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

        return listeIngredients.stream().toList();
    }

    /**
     * Méthode privée utilisée par la méthode parseData() pour
     * extraire les allergènes de la ligne afin de créer des objets Allergene.
     * Retourne la liste des allergènes récupérés.
     * @param ligne tableau de chaîne de caractères qui représente
     *              la ligne à traiter.
     * @return  une liste d'objets de type Allergene.
     */
    private List<Allergene> parseAllergenes(String[] ligne) {
        Set<Allergene> listeAllergenes = new HashSet<>();

        if (ligne.length > 28) {
            String allergenes = ligne[28].trim();
            if (!allergenes.isEmpty()) {
                String[] tabAllergenes = allergenes.replace("_", " ").replace("-", " ").split("[,;]");

                for (String al : tabAllergenes) {
                    al = al.trim().toLowerCase();

                    if (al.startsWith("en:") || al.startsWith("fr:")) {
                        al = al.substring(3);
                    }

                    if (al.endsWith("s") || al.endsWith("*") || al.endsWith("/")) {
                        al = al.substring(0, al.length() - 1);
                    }

                    if (al.length() < 2) {
                        continue;
                    }

                    // traductions des termes anglais et fautes de frappe
                    // les plus fréquemment retrouvés,
                    // ainsi que les équivalents (oe = œ)
                    al = traductionEtEquivalence(al);

                    Allergene allergene = this.mapAllergenes.computeIfAbsent(al, Allergene::new);
                    listeAllergenes.add(allergene);
                }
            }
        }

        return listeAllergenes.stream().toList();
    }

    /**
     * Méthode privée utilisée par la méthode parseData() pour
     * extraire les additifs de la ligne afin de créer des objets Additif.
     * Retourne la liste des additifs récupérés.
     * @param ligne tableau de chaîne de caractères qui représente
     *              la ligne à traiter.
     * @return  une liste d'objets de type Additif.
     */
    private List<Additif> parseAdditifs(String[] ligne) {
        Set<Additif> listeAdditifs = new HashSet<>();

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

        return listeAdditifs.stream().toList();
    }

    /**
     * Méthode privée utilisée par la méthode parseData() pour
     * extraire les valeurs nutritionnelles de la ligne et les stocker dans
     * Retourne la liste des additifs récupérés.
     * @param ligne tableau de chaîne de caractères qui représente
     *              la ligne à traiter.
     * @return  une liste d'objets de type Additif.
     */
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

    /**
     * Compare la chaîne de caractères à un certain nombre
     * d'allergènes anglais, ou dont l'écriture utilise des
     * caractères spéciaux, ou comportant des fautes de frappes récurrentes,
     * afin de les traduire dans leur équivalent français,
     * avec des caractères plus simples.
     * @param allergene chaîne de caractères String
     * @return une chaîne de caractères String
     */
    private static String traductionEtEquivalence(String allergene) {
        if (allergene.equals("fish")) {
            allergene = "poisson";
        }
        else if (allergene.equals("egg")
                || allergene.equals("œuf")) {
            allergene = "oeuf";
        }
        else if (allergene.equals("milk")
                || allergene.equals("iait")
                || allergene.equals("lalt")) {
            allergene = "lait";
        }
        else if (allergene.equals("soybean")) {
            allergene = "soja";
        }
        else if (allergene.equals("nut")) {
            allergene = "noix, fruits à coque";
        }
        else if (allergene.equals("mustard")) {
            allergene = "moutarde";
        }
        else if (allergene.equals("ble")) {
            allergene = "blé";
        }

        return allergene;
    }
}
