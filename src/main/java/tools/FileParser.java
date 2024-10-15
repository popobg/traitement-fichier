package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe proposant une méthode permettant d'extraire les données d'un fichier
 * et les retourner dans un format interprétable par Java (liste de chaînes
 * de caractères).
 */
public class FileParser {
    /**
     * Récupère les données d'un fichier texte donné.
     * Chaque ligne est transformée en une chaîne de caractères,
     * et l'ensemble de ces lignes est organisée dans une liste.
     * @param path chemin vers le fichier sur votre machine.
     * @return une liste de chaîne de caractères String correspondant aux lignes
     *          du fichier.
     * @throws IOException en cas d'erreur lors de la tentative de lecture du fichier.
     */
    public static List<String> parseFile(Path path) throws IOException  {
        List<String> listelignes = new ArrayList<>();
        listelignes = Files.readAllLines(path);
        return listelignes;
    }
}
