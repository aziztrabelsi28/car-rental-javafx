package service;

import dao.DemandeLocationDAO;
import dao.FactureDAO;
import dao.LocationDAO;
import dao.VoitureDAO;
import model.DemandeLocation;
import model.Facture;
import model.Location;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GestionDemandesLocations {

    private final DemandeLocationDAO demandeLocationDAO;
    private final LocationDAO locationDAO;
    private final FactureDAO factureDAO;
    private final FactureService factureService;
    private final VoitureDAO voitureDAO;

    public GestionDemandesLocations(DemandeLocationDAO demandeLocationDAO,
                                    LocationDAO locationDAO,
                                    FactureDAO factureDAO,
                                    VoitureDAO voitureDAO) {
        this.demandeLocationDAO = demandeLocationDAO;
        this.locationDAO = locationDAO;
        this.factureDAO = factureDAO;
        this.voitureDAO = voitureDAO;
        this.factureService = new FactureService(factureDAO);
    }

    
    public GestionDemandesLocations() {
        this.demandeLocationDAO = null;
        this.locationDAO = null;
        this.factureDAO = null;
        this.voitureDAO = null;
        this.factureService = new FactureService();
    }

    public FactureService getFactureService() {
        return factureService;
    }

    

    public List<DemandeLocation> getListeDemandesLocations() {
        if (demandeLocationDAO == null) return Collections.emptyList();
        try {
            return demandeLocationDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Location> getListeLocations() {
        if (locationDAO == null) return Collections.emptyList();
        try {
            return locationDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    

    public void accepterDemandeLocation(int idDemande, String typePaiement) {
        if (demandeLocationDAO == null || locationDAO == null || factureDAO == null) {
            System.out.println("DAOs non initialisés dans GestionDemandesLocations.");
            return;
        }

        try {
            DemandeLocation d = demandeLocationDAO.findById(idDemande);
            if (d != null) {
                
                Location l = new Location();
                l.setDemandeLocation(d);
                locationDAO.insert(l); 

                
                d.getVoiture().setIsDispo(false);
                if (voitureDAO != null) {
                    voitureDAO.update(d.getVoiture());
                }

                
                Facture facture = factureService.creeFacture(l, typePaiement);
                double prixFinal = facture.getPrix();

                
                d.setPrix(prixFinal);
                d.setTypePaiement(typePaiement);
                demandeLocationDAO.updatePrixEtPaiement(d.getIdDemande(), prixFinal, typePaiement);

                
                demandeLocationDAO.markAccepted(d.getIdDemande());

                System.out.println("Demande acceptée, location et facture créées, voiture indisponible.");
            } else {
                System.out.println("Aucune demande de cet ID n'est trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'acceptation de la demande de location.");
        }
    }

    public void ajouterDemandeLocation(DemandeLocation d) {
        if (demandeLocationDAO == null) {
            System.out.println("DemandeLocationDAO non initialisé.");
            return;
        }

        try {
            if (d.getVoiture().getIsDispo()) {
                d.setPrix(0.0);
                demandeLocationDAO.insert(d);
                System.out.println("Demande de location ajoutée avec id = " + d.getIdDemande());
            } else {
                System.out.println("Erreur : voiture non disponible.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la demande de location.");
        }
    }

    public DemandeLocation rechercherDemandeLocation(int idDemande) {
        if (demandeLocationDAO == null) return null;
        try {
            return demandeLocationDAO.findById(idDemande);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void supprimerDemandeLocation(int idDemande) {
        if (demandeLocationDAO == null) {
            System.out.println("DemandeLocationDAO non initialisé.");
            return;
        }

        try {
            DemandeLocation d = demandeLocationDAO.findById(idDemande);
            if (d != null) {
                demandeLocationDAO.delete(idDemande);
                System.out.println("Demande de l'ID " + idDemande + " a été supprimée.");
            } else {
                System.out.println("Aucune demande de cet ID n'a été trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la demande de location.");
        }
    }

    

    public void afficherDemandeLocation(int idDemande) {
        DemandeLocation d = rechercherDemandeLocation(idDemande);
        if (d != null) {
            System.out.println("ID: " + d.getIdDemande());
            System.out.println("Voiture: " + d.getVoiture());
            System.out.println("client: " + d.getClient());
            System.out.println("Type de location: " + d.getTypeLocation());
            System.out.println("date de debut de location: " + d.getDateDebutLocation());
            System.out.println("date fin de location: " + d.getDateFinLocation());
            System.out.println("type paiement: " + d.getTypePaiement());
            System.out.println("prix: " + d.getPrix());
        } else {
            System.out.println("Aucune demande avec cet ID n'est trouvée.");
        }
    }

    public void afficherListeDemandesLocations() {
        for (DemandeLocation d : getListeDemandesLocations()) {
            System.out.println("ID: " + d.getIdDemande());
            System.out.println("Voiture: " + d.getVoiture());
            System.out.println("client: " + d.getClient());
            System.out.println("Type de location: " + d.getTypeLocation());
            System.out.println("date de debut de location: " + d.getDateDebutLocation());
            System.out.println("date fin de location: " + d.getDateFinLocation());
            System.out.println("type paiement: " + d.getTypePaiement());
            System.out.println("prix: " + d.getPrix());
            System.out.println("----------------------------");
        }
    }

    public void afficherListeLocations() {
        for (Location l : getListeLocations()) {
            System.out.println("Location ID: " + l.getIdLocation());
            System.out.println("Voiture: " + l.getDemandeLocation().getVoiture());
            System.out.println("Client: " + l.getDemandeLocation().getClient());
            System.out.println("Date debut: " + l.getDemandeLocation().getDateDebutLocation());
            System.out.println("Date fin: " + l.getDemandeLocation().getDateFinLocation());
            System.out.println("----------------------------");
        }
    }
}





