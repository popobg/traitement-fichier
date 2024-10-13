package entites;

import java.text.Collator;
import java.util.Locale;

public class Categorie implements Comparable<Categorie> {
    private String libelle;

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int compareTo(Categorie autreCategorie) {
        Collator collator = Collator.getInstance(Locale.FRANCE);
        return collator.compare(this.libelle.toLowerCase(), autreCategorie.getLibelle().toLowerCase());
    }
}
