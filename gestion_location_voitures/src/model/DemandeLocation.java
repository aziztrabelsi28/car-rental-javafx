package model;

import java.time.LocalDateTime;

public class DemandeLocation {
    private int idDemande;
    private Client client;
    private Voiture voiture;
    private LocalDateTime dateDebutLocation;
    private LocalDateTime dateFinLocation;
    private String typeLocation;
    private String typePaiement;
    private double prix;

    public DemandeLocation(int idDemande, Client client, Voiture voiture, LocalDateTime dateDebutLocation, LocalDateTime dateFinLocation, String typeLocation, String typePaiement) {
        this.idDemande = idDemande; 
        this.client = client;
        this.voiture = voiture;
        this.dateDebutLocation = dateDebutLocation;
        this.dateFinLocation = dateFinLocation;
        this.typeLocation = typeLocation;
        this.typePaiement = typePaiement;
        this.prix = 0.0;
    }

    public DemandeLocation() {}

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande){
        this.idDemande=idDemande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public LocalDateTime getDateDebutLocation() {
        return dateDebutLocation;
    }

    public void setDateDebutLocation(LocalDateTime dateDebutLocation) {
        this.dateDebutLocation = dateDebutLocation;
    }

    public LocalDateTime getDateFinLocation() {
        return dateFinLocation;
    }

    public void setDateFinLocation(LocalDateTime dateFinLocation) {
        this.dateFinLocation = dateFinLocation;
    }

    public String getTypeLocation() {
        return typeLocation;
    }

    public void setTypeLocation(String typeLocation) {
        this.typeLocation = typeLocation;
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
}

