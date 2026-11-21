package model;


import service.GestionVoitures;
//import service.GestionClients;
import service.GestionDemandesLocations;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;


public class Client extends User {
    private String nom;
    private String prenom;
    private GestionVoitures gestionVoitures;
    private GestionDemandesLocations gestionDemandesLocations;
    private List<Facture> factures;



    public Client(int idUser,String username,String password,String role,String nom,String prenom,GestionVoitures gestionVoitures,GestionDemandesLocations gestionDemandesLocations){
        super(idUser,username,password,role);
        this.nom=nom;
        this.prenom=prenom;
        this.gestionVoitures=gestionVoitures;
        this.gestionDemandesLocations=gestionDemandesLocations;
        factures=new ArrayList<>(); 
    }


    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }


    public String getPrenom(){
        return prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    
    public void afficherListeVoituresDispo(){
        gestionVoitures.afficherListeVoituresDispo();
    }


    public void ajouterDemandeLocation(Voiture voiture,String typeLocation,LocalDateTime dateDebutLocation,LocalDateTime dateFinLocation,String typePaiement){
        DemandeLocation d= new DemandeLocation(
            gestionDemandesLocations.getListeDemandesLocations().size()+1,
            this,
            voiture,
            dateDebutLocation,
            dateFinLocation,
            typeLocation,
            typePaiement
        );
        gestionDemandesLocations.ajouterDemandeLocation(d);
    }

    public void addFacture(Facture facture){
        factures.add(facture);
    }

    public List<Facture> getFactures(){
        return factures;
    }


    public void afficherFactures() {
    if (factures.isEmpty()) {
        System.out.println("Aucune facture disponible pour ce client.");
        return;
    }

    System.out.println("Factures du client " + nom + " " + prenom + ":");
    for (Facture f : factures) {
        System.out.println("----------------------------");
        System.out.println("Facture ID: " + f.getIdFacture());
        System.out.println("Voiture: " + f.getLocation().getDemandeLocation().getVoiture().getMarque());
        System.out.println("Type Paiement: " + f.getTypePaiement());
        System.out.println("Prix Total: " + f.getPrix() + " DT");
    }
    System.out.println("----------------------------");
}

    public GestionVoitures getGestionVoitures(){
        return gestionVoitures;
    }


    public String toString() {
    return "Client{id= " + getIdUser() + ", nom= " + nom + ", prenom= " + prenom + " }";
}

}
