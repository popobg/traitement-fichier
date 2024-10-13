package tools;

import java.util.Scanner;

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

        System.out.println(message);
        while (userInput.isEmpty()) {
            userInput = scanner.nextLine();
        }

        return userInput;
    }

    /**
     * Demande à l'utilisateur de saisir un chiffre dans la console,
     * compris dans l'intervalle donné en paramètres.
     * @param min un entier constituant la limite minimale autorisée.
     * @param max un entier constituant la limite maximale autorisée.
     * @return le nombre entier choisi par l'utilisateur.
     */
    public static int getIntInput(int min, int max) {
        int userNumber = min - 1;
        Scanner scanner = new Scanner(System.in);

        while (userNumber < min || userNumber > max) {
            System.out.printf("Entrez un nombre entier compris entre %s et %s (inclus) :%n", min, max);

            try {
                userNumber = scanner.nextInt();
            }
            catch(Exception InputMismatchException) {
                System.out.println("Ceci n'est pas un nombre entier.");
                System.out.println();
                scanner.nextLine();
            }
        }

        return userNumber;
    }
}
