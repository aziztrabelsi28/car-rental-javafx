package ui;

import dao.ClientDAO;
import dao.DemandeLocationDAO;
import dao.FactureDAO;
import dao.LocationDAO;
import dao.UserDAO;
import dao.VoitureDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.AuthService;
import service.GestionClients;
import service.GestionDemandesLocations;
import service.GestionVoitures;

public class MainApp extends Application {

    
    public static AuthService sharedAuthService;
    public static GestionVoitures sharedGestionVoitures;
    public static GestionDemandesLocations sharedGestionDemandesLocations;
    public static GestionClients sharedGestionClients;

    @Override
    public void start(Stage stage) throws Exception {

        
        VoitureDAO voitureDAO = new VoitureDAO();
        UserDAO userDAO = new UserDAO();
        
        sharedGestionVoitures = new GestionVoitures(voitureDAO);
        ClientDAO clientDAO = new ClientDAO(sharedGestionVoitures, null); // second arg unused now
        DemandeLocationDAO demandeLocationDAO = new DemandeLocationDAO(clientDAO, voitureDAO);
        LocationDAO locationDAO = new LocationDAO(demandeLocationDAO);
        FactureDAO factureDAO = new FactureDAO(locationDAO);

        
        sharedGestionDemandesLocations =
                new GestionDemandesLocations(demandeLocationDAO, locationDAO, factureDAO, voitureDAO);
        sharedGestionClients = new GestionClients(clientDAO, userDAO);
        sharedAuthService = new AuthService(sharedGestionVoitures, userDAO, clientDAO);

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setAuthService(sharedAuthService);

        Scene scene = new Scene(root, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Car Rental Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}










