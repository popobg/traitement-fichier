package tools;

import java.util.Scanner;

public class WaitTools {
    public static void attendreUtilisateur() {
        System.out.println();
        System.out.println("Appuyez deux fois sur Entrée pour continuer.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
