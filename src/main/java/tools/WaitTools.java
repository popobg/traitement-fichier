package tools;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Classe composée de méthodes statiques permettant de générer des
 * "pauses" dans l'exécution du programme, soit en générant des temps
 * d'attente, soit via des inputs utilisateur.
 */
public class WaitTools {
    /**
     * Attend et lit un input utilisateur sans le traiter.
     * Récupère l'input après passage à la ligne.
     */
    public static void attendreUtilisateur() {
        System.out.println();
        System.out.println("Appuyez sur Entrée pour continuer.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Fait une pause dans l'exécution du programme
     * pendant le temps indiqué (secondes).
     * @param sec nombre de secondes à attendre
     */
    public static void attendre(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
