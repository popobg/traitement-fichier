import entites.*;
import entites.generique.Pair;
import services.AffichageMenu;
import services.StockService;
import tools.DataParser;
import tools.FileParser;
import tools.InputTools;
import tools.WaitTools;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ApplicationOpenFoodFacts {
    public static void main(String[] args) {
        // PARSE LE FICHIER POUR RECUPERER LES LIGNES DE DONNEES
        // Construction du chemin vers le fichier de données
        String repertoireActuel = Paths.get(".").toAbsolutePath().toString();
        String stringPath = repertoireActuel.substring(0, repertoireActuel.length() - 1) + "src\\main\\java\\fichier\\open-food-facts.csv";
        Path path = Paths.get(stringPath);

        List<String> lignesOpenFoodFacts = new ArrayList<>();
        try {
            lignesOpenFoodFacts = FileParser.parseFile(path);
        }
        catch (IOException e) {
            System.out.println("Le contenu du fichier n'a pas pu être récupéré. Vérifiez que le fichier existe et que le chemin donné est correct.");
        }

        // PARSE LES DONNEES DU FICHIER ET LES ORGANISE EN OBJETS
        DataParser parser = new DataParser();
        Stock stock = parser.parseData(lignesOpenFoodFacts);

        // IMPLEMENTATION DU MENU UTILISATEUR
        StockService stockService = new StockService(stock);
        boolean finMenu = false;

        while(!finMenu) {
            AffichageMenu.afficherMenuPrincipal();
            int choice = InputTools.getIntInput(1, 8, "Choisissez une option du menu.");

            switch (choice) {
                case 1:
                    List<Marque> marques = stockService.getMarques();

                    AffichageMenu.afficherMarques(marques);

                    WaitTools.attendreUtilisateur();
                    break;
                case 2:
                    List<Categorie> categories = stockService.getCategories();

                    AffichageMenu.afficherCategories(categories);

                    WaitTools.attendreUtilisateur();
                    break;
                case 3:
                    String inputMarque = InputTools.getStringInput("Entrez le nom de la marque dont vous souhaitez consulter les meilleurs produits :");
                    Marque marque = stockService.getMarque(inputMarque);

                    if (marque == null) {
                        AffichageMenu.afficherInputIncorrect();

                        WaitTools.attendreUtilisateur();
                        break;
                    }

                    List<Produit> meilleursProduitsMarque = stockService.getMeilleursProduitsParMarque(marque);

                    AffichageMenu.afficherMeilleursProduitsMarque(meilleursProduitsMarque, marque);

                    WaitTools.attendreUtilisateur();
                    break;
                case 4:
                    String inputCategorie = InputTools.getStringInput("Entrez le nom de la catégorie dont vous souhaitez consulter les meilleurs produits :");
                    Categorie categorie = stockService.getCategorie(inputCategorie);

                    if (categorie == null) {
                        AffichageMenu.afficherInputIncorrect();

                        WaitTools.attendreUtilisateur();
                        break;
                    }

                    List<Produit> meilleursProduitsCategorie = stockService.getMeilleursProduitsParCategorie(categorie);

                    AffichageMenu.afficherMeilleursProduitsCategorie(meilleursProduitsCategorie, categorie);

                    WaitTools.attendreUtilisateur();
                    break;
                case 5:
                    String inputMarque2 = InputTools.getStringInput("Entrez le nom de la marque dont vous souhaitez consulter les meilleurs produits :");
                    Marque marque2 = stockService.getMarque(inputMarque2);

                    if (marque2 == null) {
                        AffichageMenu.afficherInputIncorrect();

                        WaitTools.attendreUtilisateur();
                        break;
                    }

                    String inputCategorie2 = InputTools.getStringInput("Entrez le nom de la catégorie dont vous souhaitez consulter les meilleurs produits :");
                    Categorie categorie2 = stockService.getCategorie(inputCategorie2);

                    if (categorie2 == null) {
                        AffichageMenu.afficherInputIncorrect();

                        WaitTools.attendreUtilisateur();
                        break;
                    }

                    List<Produit> meilleursProduitsCategorieMarque = stockService.getMeilleursProduitsParMarqueEtCategorie(categorie2, marque2);

                    AffichageMenu.afficherMeilleursProduitsCategorieMarque(meilleursProduitsCategorieMarque, categorie2, marque2);

                    WaitTools.attendreUtilisateur();
                    break;
                case 6:
                    int nAllergenes = InputTools.getIntInput("Combien d'allergènes souhaitez-vous consulter ?");

                    List<Pair<Allergene, Integer>> allergenesRecurrents = stockService.getAllergenesRecurrents(nAllergenes);

                    AffichageMenu.afficherAllergenesRecurrents(allergenesRecurrents, nAllergenes);

                    WaitTools.attendreUtilisateur();
                    break;
                case 7:
                    int nAdditifs = InputTools.getIntInput("Combien d'additifs souhaitez-vous consulter ?");

                    List<Pair<Additif, Integer>> additifsRecurrents = stockService.getAdditifsRecurrents(nAdditifs);

                    AffichageMenu.afficherAdditifsRecurrents(additifsRecurrents, nAdditifs);

                    WaitTools.attendreUtilisateur();
                    break;
                default:
                    finMenu = true;

                    AffichageMenu.afficherFermetureProgramme();
                    break;
            }
        }
    }
}
