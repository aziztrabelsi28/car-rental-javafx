package service;

import dao.ClientDAO;
import dao.UserDAO;
import model.Admin;
import model.Client;
import model.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AuthService {

    private final UserDAO userDAO;
    private final ClientDAO clientDAO;
    private User currentUser;
    private final GestionVoitures sharedGestionVoitures;

    public AuthService(GestionVoitures sharedGestionVoitures,
                       UserDAO userDAO,
                       ClientDAO clientDAO) {
        this.sharedGestionVoitures = sharedGestionVoitures;
        this.userDAO = userDAO;
        this.clientDAO = clientDAO;
    }

    // --- Authentification ---

    public boolean login(String username, String password) {
        try {
            User u = userDAO.findByUsernameAndPassword(username, password);
            if (u != null) {
                System.out.println("login successful");

                
                if ("client".equalsIgnoreCase(u.getRole())) {
                    
                    Client fullClient = clientDAO.findById(u.getIdUser());
                    
                    if (fullClient != null) {
                        fullClient = new Client(
                                fullClient.getIdUser(),
                                fullClient.getUsername(),
                                fullClient.getPassword(),
                                fullClient.getRole(),
                                fullClient.getNom(),
                                fullClient.getPrenom(),
                                sharedGestionVoitures,
                                new GestionDemandesLocations() // à adapter si tu veux passer les DAO ici aussi
                        );
                        currentUser = fullClient;
                    } else {
                        
                        currentUser = new Client(
                                u.getIdUser(),
                                u.getUsername(),
                                u.getPassword(),
                                u.getRole(),
                                "", "",
                                sharedGestionVoitures,
                                new GestionDemandesLocations()
                        );
                    }
                } else if ("admin".equalsIgnoreCase(u.getRole())) {
                    currentUser = new Admin(u.getIdUser(), u.getUsername(), u.getPassword(), u.getRole());
                } else {
                    currentUser = u;
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("login failed please check credentials");
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    // --- Inscription ---

    public boolean register(String username,
                            String password,
                            String roleInput,
                            String nom,
                            String prenom) {
        try {
            if (userDAO.usernameExists(username)) {
                System.out.println("ce username est deja existant");
                return false;
            }

            String role = (roleInput != null && roleInput.equalsIgnoreCase("admin"))
                    ? "admin"
                    : "client";

            
            User newUser = new User(0, username, password, role);
            int idUser = userDAO.insert(newUser);

            
            if ("client".equalsIgnoreCase(role)) {
                clientDAO.insertClientPart(idUser, nom, prenom);
            }

            System.out.println("New user created: " + username + ", role: " + role);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'inscription.");
            return false;
        }
    }

    

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getUsers() {
        try {
            return userDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}




