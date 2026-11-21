package service;

import dao.FactureDAO;
import model.Client;
import model.Facture;
import model.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactureService {

    private final List<Facture> listeFactures;
    private final FactureDAO factureDAO;

    public FactureService(FactureDAO factureDAO) {
        this.listeFactures = new ArrayList<>();
        this.factureDAO = factureDAO;
    }

    
    public FactureService() {
        this.listeFactures = new ArrayList<>();
        this.factureDAO = null;
    }

    public FactureDAO getFactureDAO() {
        return factureDAO;
    }

    public List<Facture> getListeFactures() {
        if (factureDAO == null) {
            return Collections.unmodifiableList(listeFactures);
        }
        try {
            return factureDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.unmodifiableList(listeFactures);
        }
    }

    
    public Facture creeFacture(Location l, String typePaiement) {
        double prixBase = l.calculerPrixTotaleLocation();
        Facture facture = new Facture(0, l, prixBase, typePaiement);

        double prixFinal = facture.calculerPrixTotale();
        facture.setPrix(prixFinal);

        if (factureDAO != null) {
            try {
                factureDAO.insert(facture); 
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'insertion de la facture en base.");
            }
        }

        listeFactures.add(facture);

        Client client = l.getDemandeLocation().getClient();
        if (client != null) {
            client.addFacture(facture);
        }

        return facture;
    }

    public void afficherFacture(Facture facture) {
        System.out.println("========== FACTURE ==========");
        System.out.println("Facture ID: " + facture.getIdFacture());
        System.out.println("Client: " +
                facture.getLocation().getDemandeLocation().getClient().getNom() + " " +
                facture.getLocation().getDemandeLocation().getClient().getPrenom()
        );
        System.out.println("Voiture: " +
                facture.getLocation().getDemandeLocation().getVoiture().getMarque()
        );
        System.out.println("Type Paiement: " + facture.getTypePaiement());
        System.out.println("Prix Total: " + facture.getPrix() + " DT");
        System.out.println("=============================");
    }
}



