package service;

import dao.VoitureDAO;
import model.Voiture;

import java.sql.SQLException;
import java.util.List;

public class GestionVoitures {

    private final VoitureDAO voitureDAO;

    public GestionVoitures() {
        this.voitureDAO = new VoitureDAO();
    }

    public GestionVoitures(VoitureDAO voitureDAO) {
        this.voitureDAO = voitureDAO;
    }

    

    public List<Voiture> getListeVoitures() {
        try {
            return voitureDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    public List<Voiture> getListeVoituresDispo() {
        try {
            return voitureDAO.findAllDisponibles();
        } catch (SQLException e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    

    public void ajouterVoiture(Voiture v) {
        try {
            voitureDAO.insert(v);
            System.out.println("Voiture ajoutée avec id = " + v.getIdVoiture());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la voiture.");
        }
    }

    public Voiture rechercherVoiture(int idVoiture) {
        try {
            return voitureDAO.findById(idVoiture);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void modifierVoiture(int idVoiture,
                                String nouveauMarque,
                                String nouveauType,
                                double nouveauPrixJour,
                                boolean nouveauDispo) {

        try {
            Voiture v = voitureDAO.findById(idVoiture);
            if (v != null) {
                v.setMarque(nouveauMarque);
                v.setTypeVoiture(nouveauType);
                v.setPrixJour(nouveauPrixJour);
                v.setIsDispo(nouveauDispo);
                voitureDAO.update(v);
                System.out.println("Voiture mise à jour !");
            } else {
                System.out.println("Aucune voiture trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de la voiture.");
        }
    }

    public void supprimerVoiture(int idVoiture) {
        try {
            Voiture v = voitureDAO.findById(idVoiture);
            if (v != null) {
                voitureDAO.delete(idVoiture);
                System.out.println("Voiture supprimée.");
            } else {
                System.out.println("Aucune voiture trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la voiture.");
        }
    }

    

    public void afficherListeVoitures() {
        List<Voiture> liste = getListeVoitures();
        for (Voiture v : liste) {
            System.out.println("ID: " + v.getIdVoiture());
            System.out.println("marque: " + v.getMarque());
            System.out.println("type: " + v.getTypeVoiture());
            System.out.println("Prix/jour: " + v.getPrixJour());
            System.out.println(v.getIsDispo() ? "disponible" : "non disponible");
            System.out.println("---------------");
        }
    }

    public void afficherVoiture(int idVoiture) {
        Voiture v = rechercherVoiture(idVoiture);
        if (v != null) {
            System.out.println("ID: " + v.getIdVoiture());
            System.out.println("marque: " + v.getMarque());
            System.out.println("type: " + v.getTypeVoiture());
            System.out.println("Prix/jour: " + v.getPrixJour());
            System.out.println(v.getIsDispo() ? "disponible" : "non disponible");
        } else {
            System.out.println("Aucune voiture avec cet ID n'est trouvée");
        }
    }

    public void afficherListeVoituresDispo() {
        List<Voiture> liste = getListeVoituresDispo();
        for (Voiture v : liste) {
            System.out.println("ID: " + v.getIdVoiture());
            System.out.println("marque: " + v.getMarque());
            System.out.println("type: " + v.getTypeVoiture());
            System.out.println("Prix/jour: " + v.getPrixJour());
            System.out.println("disponible");
            System.out.println("---------------");
        }
    }
}

