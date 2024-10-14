package entites;

public class Additif {
    private String libelle;

    public Additif(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Additif)) {
            return false;
        }

        Additif autreAdditif = (Additif)obj;
        return this.libelle.equals(autreAdditif.getLibelle());
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
