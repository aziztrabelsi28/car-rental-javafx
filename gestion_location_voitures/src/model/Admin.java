package model;

import service.GestionVoitures;
import service.GestionClients;
import service.GestionDemandesLocations;

import java.util.List;

public class Admin extends User {

    private GestionVoitures gestionVoitures;
    private GestionClients gestionClients;
    private GestionDemandesLocations gestionDemandesLocations;

    public Admin(int idUser, String username, String password, String role) {
        super(idUser, username, password, role);
        this.gestionVoitures = null;
        this.gestionClients = null;
        this.gestionDemandesLocations = null;
    }

    public void setGestionVoitures(GestionVoitures gestionVoitures) {
        this.gestionVoitures = gestionVoitures;
    }

    public void setGestionClients(GestionClients gestionClients) {
        this.gestionClients = gestionClients;
    }

    public void setGestionDemandesLocations(GestionDemandesLocations gestionDemandesLocations) {
        this.gestionDemandesLocations = gestionDemandesLocations;
    }

    public GestionDemandesLocations getGestionDemandesLocations() {
        return gestionDemandesLocations;
    }

    public GestionClients getGestionClients() {
        return gestionClients;
    }

    public GestionVoitures getGestionVoitures() {
        return gestionVoitures;
    }

    

    public List<Voiture> getListeVoitures() {
        return (gestionVoitures != null) ? gestionVoitures.getListeVoitures()
                                         : java.util.Collections.emptyList();
    }

    public List<Client> getListeClients() {
        return (gestionClients != null) ? gestionClients.getListeClients()
                                        : java.util.Collections.emptyList();
    }

    public List<DemandeLocation> getListDemandeLocations() {
        return (gestionDemandesLocations != null) ? gestionDemandesLocations.getListeDemandesLocations()
                                                  : java.util.Collections.emptyList();
    }

    

    public void ajouterVoiture(Voiture voiture) {
        if (gestionVoitures != null) {
            gestionVoitures.ajouterVoiture(voiture);
        }
    }

    public Voiture rechercherVoiture(int idVoiture) {
        return (gestionVoitures != null) ? gestionVoitures.rechercherVoiture(idVoiture) : null;
    }

    public void supprimerVoiture(int idVoiture) {
        if (gestionVoitures != null) {
            gestionVoitures.supprimerVoiture(idVoiture);
        }
    }

    public void afficherVoiture(int idVoiture) {
        if (gestionVoitures != null) {
            gestionVoitures.afficherVoiture(idVoiture);
        }
    }

    public void afficherListeVoitures() {
        if (gestionVoitures != null) {
            gestionVoitures.afficherListeVoitures();
        }
    }

    public void afficherListeVoituresDispo() {
        if (gestionVoitures != null) {
            gestionVoitures.afficherListeVoituresDispo();
        }
    }

    

    public Client rechercherClient(int idUser) {
        return (gestionClients != null) ? gestionClients.rechercherClient(idUser) : null;
    }

    public void supprimerClient(int idUser) {
        if (gestionClients != null) {
            gestionClients.supprimerClient(idUser);
        }
    }

    public void afficherClient(int idUser) {
        if (gestionClients != null) {
            gestionClients.afficherClient(idUser);
        }
    }

    public void afficherListeClients() {
        if (gestionClients != null) {
            gestionClients.afficherListeClients();
        }
    }

    

    public void accepterDemandeLocation(int idDemande, String typePaiement) {
        if (gestionDemandesLocations != null) {
            gestionDemandesLocations.accepterDemandeLocation(idDemande, typePaiement);
        }
    }

    public void supprimerDemandeLocation(int idDemande) {
        if (gestionDemandesLocations != null) {
            gestionDemandesLocations.supprimerDemandeLocation(idDemande);
        }
    }

    public void afficherListeLocations() {
        if (gestionDemandesLocations != null) {
            gestionDemandesLocations.afficherListeLocations();
        }
    }
}

