import entites.*;
import service.StockService;
import tools.DataParser;
import tools.FileParser;
import tools.InputTools;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        // MENU UTILISATEUR
        StockService stockService = new StockService(stock);
        boolean finMenu = false;

        while(!finMenu) {
            AffichageMenu.afficherMenuPrincipal();
            int choice = InputTools.getIntInput(1, 8);

            switch (choice) {
                case 1:
                    List<Marque> marques = stockService.getMarques();
                    AffichageMenu.afficherMarques(marques);
                    break;
                case 2:
                    List<Categorie> categories = stockService.getCategories();
                    AffichageMenu.afficherCategories(categories);
                    break;
                case 3:
                    String inputMarque = InputTools.getStringInput("Entrez le nom de la marque dont vous souhaitez consulter les meilleurs produits :");
                    Marque marque = stockService.getMarque(inputMarque);

                    if (marque == null) {
                        AffichageMenu.afficherInputIncorrect();
                        break;
                    }

                    List<Produit> meilleursProduitsMarque = stockService.getMeilleursProduitsParMarque(marque);
                    AffichageMenu.afficherMeilleursProduitsMarque(meilleursProduitsMarque);
                    break;
                default:
                    finMenu = true;
                    AffichageMenu.afficherFermetureProgramme();
                    break;
            }
        }
    }
}
