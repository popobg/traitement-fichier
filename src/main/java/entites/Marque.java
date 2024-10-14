package entites;

import java.text.Collator;
import java.util.Locale;

public class Marque implements Comparable<Marque> {
    private String nom;

    public Marque(String libelle) {
        this.nom = libelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nouveauNom) {
        this.nom = nouveauNom;
    }

    @Override
    public int compareTo(Marque autreMarque) {
        Collator collator = Collator.getInstance(Locale.FRANCE);
        return collator.compare(this.nom.toLowerCase(), autreMarque.getNom().toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Marque)) {
            return false;
        }

        Marque autreMarque = (Marque)obj;

        return this.nom.equals(autreMarque.getNom());
    }
}
