package tools;

import java.util.Scanner;

/**
 * Classe de traitement et gestion des cas d'erreurs de saisie utilisateur.
 */
public class InputTools {
    /**
     * Demande à l'utilisateur de saisir des données dans la console.
     * @param message chaîne de caractères affichées dans la console
     *                avant de récupérer l'input.
     * @return la chaîne de caractères saisie par l'utilisateur.
     */
    public static String getStringInput(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        System.out.println();
        System.out.println(message);
        while (userInput.isEmpty()) {
            userInput = scanner.nextLine();
        }

        return userInput;
    }

    /**
     * Demande à l'utilisateur de saisir un chiffre dans la console,
     * compris dans l'intervalle donné en paramètres.
     * Affiche un message donné avant de récupérer l'input utilisateur.
     * @param min un entier constituant la limite minimale autorisée.
     * @param max un entier constituant la limite maximale autorisée.
     * @param message une chaîne de caractères correspondant au message
     *                à afficher dans la console.
     * @return le nombre entier choisi par l'utilisateur.
     */
    public static int getIntInput(int min, int max, String message) {
        int userNumber = min - 1;
        Scanner scanner = new Scanner(System.in);

        while (userNumber < min || userNumber > max) {
            System.out.println();
            System.out.println(message);
            System.out.printf("Entrez un nombre entier compris entre %s et %s (inclus) :%n", min, max);

            try {
                userNumber = scanner.nextInt();
            }
            catch (Exception InputMismatchException) {
                System.out.println("Ceci n'est pas un nombre entier.");
                scanner.nextLine();
            }
        }

        return userNumber;
    }

    /**
     * Demande à l'utilisateur de saisir un nombre entier dans la console,
     * sans limite.
     * Affiche un message donné avant de récupérer l'input utilisateur.
     * @param message une chaîne de caractères correspondant au message
     *                à afficher dans la console.
     * @return le nombre entier choisi par l'utilisateur.
     */
    public static int getIntInput(String message) {
        int userNumber = -1;
        Scanner scanner = new Scanner(System.in);

        while (userNumber < 0) {
            System.out.println();
            System.out.println(message);
            System.out.println("Veuillez saisir un nombre entier :");

            try {
                userNumber = scanner.nextInt();
            }
            catch(Exception InputMismatchException) {
                System.out.println("Ceci n'est pas un nombre entier.");
                scanner.nextLine();
            }
        }

        return userNumber;
    }
}
