package entites;

import java.util.HashMap;
import java.util.List;

public class Produit {
    private final Categorie categorie;
    private Marque marque;
    private String nom;
    private String scoreNutritionnel;
    private HashMap<String, Double> valeursEnergetiques100g;
    private Boolean presenceHuilePalme;
    private List<Ingredient> ingredients;
    private List<Allergene> allergenes;
    private List<Additif> additifs;

    public Produit(String nom, Marque marque, Categorie categorie, List<Allergene> allergenes, List<Additif> additifs, List<Ingredient> ingredients, String scoreNutritionnel, HashMap<String, Double> valeursEnergetiques100g, Boolean presenceHuilePalme) {
        this.nom = nom;
        this.marque = marque;
        this.categorie = categorie;
        this.allergenes = allergenes;
        this.additifs = additifs;
        this.ingredients = ingredients;
        this.scoreNutritionnel = scoreNutritionnel;
        this.valeursEnergetiques100g = valeursEnergetiques100g;
        this.presenceHuilePalme = presenceHuilePalme;
    }

    public List<Additif> getAdditifs() {
        return additifs;
    }

    public List<Allergene> getAllergenes() {
        return allergenes;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Marque getMarque() {
        return marque;
    }

    public String getNom() {
        return nom;
    }

    public Boolean isPresenceHuilePalme() {
        return presenceHuilePalme;
    }

    public String getScoreNutritionnel() {
        return scoreNutritionnel;
    }

    public HashMap<String, Double> getValeursEnergetiques100g() {
        return valeursEnergetiques100g;
    }

    public void setAdditifs(List<Additif> additifs) {
        this.additifs = additifs;
    }

    public void setAllergenes(List<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPresenceHuilePalme(Boolean presenceHuilePalme) {
        this.presenceHuilePalme = presenceHuilePalme;
    }

    public void setScoreNutritionnel(String scoreNutritionnel) {
        this.scoreNutritionnel = scoreNutritionnel;
    }

    public void setValeursEnergetiques100g(HashMap<String, Double> valeursEnergetiques100g) {
        this.valeursEnergetiques100g = valeursEnergetiques100g;
    }
}
