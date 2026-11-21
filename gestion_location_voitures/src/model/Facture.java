package model;

public class Facture {
    private int idFacture;
    private Location location;
    private double prix;
    private String typePaiement;

    public Facture(int idFacture, Location location, double prix, String typePaiement) {
        this.idFacture = idFacture;
        this.location = location;
        this.typePaiement = typePaiement;
        this.prix = this.calculerPrixTotale();
    }

    public Facture() {}

    public int getIdFacture() {
         return idFacture; 
        }
    public void setIdFacture(int idFacture) {
         this.idFacture = idFacture;
        }
    public Location getLocation() {
         return location;
        }
    public String getTypePaiement() {
         return typePaiement; 
        }
    public void setTypePaiement(String typePaiement) {
         this.typePaiement = typePaiement; 
        }
    public double getPrix() {
         return prix; 
        }
    public void setPrix(double prix) {
         this.prix = prix; 
        }

    public double calculerPrixTotale() {
        double prixBase = location.calculerPrixBase();

        switch (location.getDemandeLocation().getTypeLocation().toLowerCase()) {
            case "week-end":
                prixBase *= 1.2;
                break;
            case "premium":
                prixBase *= 1.5;
                break;
            case "standard":
            default:
                prixBase *= 1.0; 
                break;
        }

        String typeVoiture = location.getDemandeLocation().getVoiture().getTypeVoiture().toLowerCase();
        switch (typeVoiture) {
            case "suv":
                prixBase *= 1.15;
                break;
            case "sedan":
                prixBase *= 1.05;
                break;
            case "coupe":
                prixBase *= 1.0;
                break;
        }

        switch (location.getDemandeLocation().getTypePaiement().toLowerCase()) {
            case "espèces":
                prixBase *= 0.95;
                break;
            case "carte":
            default:
                prixBase *= 1.0;
                break;
        }

        return prixBase;
    }
}

