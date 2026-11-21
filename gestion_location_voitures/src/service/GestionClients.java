package service;

import dao.ClientDAO;
import dao.UserDAO;
import model.Client;
import model.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GestionClients {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    public GestionClients(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

   
    public GestionClients() {
        this.clientDAO = null;
        this.userDAO = null;
    }

    

    public List<Client> getListeClients() {
        if (clientDAO == null) return Collections.emptyList();
        try {
            return clientDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    

    
    public void ajouterClient(String username,
                              String password,
                              String nom,
                              String prenom) {
        if (clientDAO == null || userDAO == null) {
            System.out.println("DAOs non initialisés dans GestionClients.");
            return;
        }

        try {
            
            User u = new User(0, username, password, "client");
            int idUser = userDAO.insert(u);

            
            clientDAO.insertClientPart(idUser, nom, prenom);

            System.out.println("Client créé avec idUser = " + idUser);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création du client.");
        }
    }

    public Client rechercherClient(int idUser) {
        if (clientDAO == null) return null;
        try {
            return clientDAO.findById(idUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void supprimerClient(int idUser) {
        if (clientDAO == null || userDAO == null) {
            System.out.println("DAOs non initialisés dans GestionClients.");
            return;
        }

        try {
            Client c = clientDAO.findById(idUser);
            if (c != null) {
                
                clientDAO.delete(idUser);
                
                userDAO.delete(idUser);
                System.out.println("Le client a été supprimé.");
            } else {
                System.out.println("Aucun client avec cet ID n'est trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du client.");
        }
    }

    

    public void afficherListeClients() {
        List<Client> liste = getListeClients();
        for (Client c : liste) {
            System.out.println("ID: " + c.getIdUser());
            System.out.println("NOM: " + c.getNom());
            System.out.println("PRENOM: " + c.getPrenom());
            System.out.println("---------------------");
        }
    }

    public void afficherClient(int idUser) {
        Client c = rechercherClient(idUser);
        if (c != null) {
            System.out.println("ID: " + c.getIdUser());
            System.out.println("NOM: " + c.getNom());
            System.out.println("PRENOM: " + c.getPrenom());
        } else {
            System.out.println("Aucun client avec cet ID n'est trouvé.");
        }
    }
}

