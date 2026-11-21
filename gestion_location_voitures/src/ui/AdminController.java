package ui;

import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import service.GestionClients;
import service.GestionDemandesLocations;
import service.GestionVoitures;

import java.util.List;

public class AdminController {

    @FXML private Button btnLogout;
    @FXML private Button btnAddClient, btnDeleteClient, btnAddCar, btnDeleteCar, btnEditCar;
    @FXML private Button btnAcceptDemande, btnDeleteDemande;

    @FXML private TableView<Client> tblClients;
    @FXML private TableColumn<Client, Integer> colClientId;
    @FXML private TableColumn<Client, String> colClientNom;
    @FXML private TableColumn<Client, String> colClientPrenom;

    @FXML private TableView<Voiture> tblCars;
    @FXML private TableColumn<Voiture, Integer> colCarId;
    @FXML private TableColumn<Voiture, String> colCarMarque;
    @FXML private TableColumn<Voiture, String> colCarType;
    @FXML private TableColumn<Voiture, Double> colCarPrix;
    @FXML private TableColumn<Voiture, Boolean> colCarDispo;

    @FXML private TableView<DemandeLocation> tblDemandes;
    @FXML private TableColumn<DemandeLocation, Integer> colDemandeId;
    @FXML private TableColumn<DemandeLocation, String> colClient;
    @FXML private TableColumn<DemandeLocation, String> colVoiture;
    @FXML private TableColumn<DemandeLocation, String> colTypeVoiture;
    @FXML private TableColumn<DemandeLocation, String> colType;
    @FXML private TableColumn<DemandeLocation, String> colDebut;
    @FXML private TableColumn<DemandeLocation, String> colFin;
    @FXML private TableColumn<DemandeLocation, String> colTypePaiement;
    @FXML private TableColumn<DemandeLocation, String> colPrix;

    
    @FXML private TableView<Location> tblLocations;
    @FXML private TableColumn<Location, Integer> colLocId;
    @FXML private TableColumn<Location, String> colLocClient;
    @FXML private TableColumn<Location, String> colLocVoiture;
    @FXML private TableColumn<Location, String> colLocTypeVoiture;
    @FXML private TableColumn<Location, String> colLocTypeLocation;
    @FXML private TableColumn<Location, String> colLocDebut;
    @FXML private TableColumn<Location, String> colLocFin;
    @FXML private TableColumn<Location, String> colLocTypePaiement;
    @FXML private TableColumn<Location, String> colLocPrix;

    private Admin admin;

    
    private GestionVoitures gestionVoitures;
    private GestionClients gestionClients;
    private GestionDemandesLocations gestionDemandesLocations;

    @FXML
    public void initialize() {
        
        colClientId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdUser()).asObject());
        colClientNom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colClientPrenom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));

        
        colCarId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdVoiture()).asObject());
        colCarMarque.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMarque()));
        colCarType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTypeVoiture()));
        colCarPrix.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrixJour()).asObject());
        colCarDispo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleBooleanProperty(data.getValue().getIsDispo()).asObject());

        
        colDemandeId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdDemande()).asObject());
        colClient.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getClient().getNom()));
        colVoiture.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getVoiture().getMarque()));
        colTypeVoiture.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getVoiture().getTypeVoiture()));
        colType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTypeLocation()));
        colDebut.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDateDebutLocation().toString()));
        colFin.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDateFinLocation().toString()));
        colTypePaiement.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTypePaiement()));
        colPrix.setCellValueFactory(data -> {
            DemandeLocation d = data.getValue();
            long days = java.time.Duration.between(d.getDateDebutLocation(), d.getDateFinLocation()).toDays();
            if (days == 0) days = 1;
            double prixEstime = d.getVoiture().getPrixJour() * days;
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(prixEstime));
        });

        
        colLocId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdLocation()).asObject());
        colLocClient.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getClient().getNom()));
        colLocVoiture.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getVoiture().getMarque()));
        colLocTypeVoiture.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getVoiture().getTypeVoiture()));
        colLocTypeLocation.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getTypeLocation()));
        colLocDebut.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getDateDebutLocation().toString()));
        colLocFin.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getDateFinLocation().toString()));
        colLocTypePaiement.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDemandeLocation().getTypePaiement()));
        colLocPrix.setCellValueFactory(data -> {
            Location loc = data.getValue();
            double prix = 0.0;
            if (MainApp.sharedGestionDemandesLocations.getFactureService() != null) {
                List<Facture> factures = MainApp.sharedGestionDemandesLocations.getFactureService().getListeFactures();
                for (Facture f : factures) {
                    if (f.getLocation().getIdLocation() == loc.getIdLocation()) {
                        prix = f.getPrix();
                        break;
                    }
                }
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(prix));
        });

        
        btnLogout.setOnAction(e -> logout());
        btnAddClient.setOnAction(e -> addClient());
        btnDeleteClient.setOnAction(e -> deleteClient());
        btnAddCar.setOnAction(e -> addCar());
        btnEditCar.setOnAction(e -> editCar());
        btnDeleteCar.setOnAction(e -> deleteCar());
        btnAcceptDemande.setOnAction(e -> acceptDemande());
        btnDeleteDemande.setOnAction(e -> deleteDemande());

        
        this.gestionVoitures = MainApp.sharedGestionVoitures;
        this.gestionDemandesLocations = MainApp.sharedGestionDemandesLocations;
        this.gestionClients = MainApp.sharedGestionClients;

        refreshTables();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        refreshTables();
    }

    private void refreshTables() {
        
        if (gestionClients != null) {
            tblClients.setItems(FXCollections.observableArrayList(gestionClients.getListeClients()));
        }

        
        if (gestionVoitures != null) {
            tblCars.setItems(FXCollections.observableArrayList(gestionVoitures.getListeVoitures()));
        }

        
        if (gestionDemandesLocations != null) {
            tblDemandes.setItems(FXCollections.observableArrayList(
                    gestionDemandesLocations.getListeDemandesLocations()
            ));

            
            tblLocations.setItems(FXCollections.observableArrayList(
                    gestionDemandesLocations.getListeLocations()
            ));
        }
    }

    private void logout() {
        try {
            MainApp.sharedAuthService.logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setAuthService(MainApp.sharedAuthService);
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Car Rental Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la déconnexion.").showAndWait();
        }
    }

    private void addClient() {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un Client");
        dialog.setHeaderText("Entrer les informations du nouveau client");

        Label lblNom = new Label("Nom:");
        TextField tfNom = new TextField();
        Label lblPrenom = new Label("Prénom:");
        TextField tfPrenom = new TextField();
        Label lblUsername = new Label("Username:");
        TextField tfUsername = new TextField();
        Label lblPassword = new Label("Mot de passe:");
        PasswordField pfPassword = new PasswordField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblNom, 0, 0); grid.add(tfNom, 1, 0);
        grid.add(lblPrenom, 0, 1); grid.add(tfPrenom, 1, 1);
        grid.add(lblUsername, 0, 2); grid.add(tfUsername, 1, 2);
        grid.add(lblPassword, 0, 3); grid.add(pfPassword, 1, 3);

        dialog.getDialogPane().setContent(grid);
        ButtonType addBtnType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);

        dialog.setResultConverter(b -> {
            if (b == addBtnType) {
                return new Client(
                        -1,
                        tfUsername.getText().trim(),
                        pfPassword.getText().trim(),
                        "client",
                        tfNom.getText().trim(),
                        tfPrenom.getText().trim(),
                        MainApp.sharedGestionVoitures,
                        MainApp.sharedGestionDemandesLocations
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(client -> {
            if (client.getUsername().isEmpty() ||
                client.getPassword().isEmpty() ||
                client.getNom().isEmpty() ||
                client.getPrenom().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Tous les champs sont obligatoires.").showAndWait();
                return;
            }

            boolean ok = MainApp.sharedAuthService.register(
                    client.getUsername(),
                    client.getPassword(),
                    "client",
                    client.getNom(),
                    client.getPrenom()
            );
            if (ok) {
                refreshTables();
                new Alert(Alert.AlertType.INFORMATION, "Client ajouté avec succès!").showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Nom d'utilisateur déjà pris ou erreur.").showAndWait();
            }
        });
    }

    private void deleteClient() {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected != null && gestionClients != null) {
            gestionClients.supprimerClient(selected.getIdUser());
            refreshTables();
        }
    }

    private void addCar() {
        Dialog<Voiture> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une voiture");
        dialog.setHeaderText("Entrez les informations de la nouvelle voiture");

        Label lblMarque = new Label("Marque:");
        TextField tfMarque = new TextField();

        Label lblType = new Label("Type:");
        ToggleGroup groupType = new ToggleGroup();
        RadioButton rbCoupe = new RadioButton("coupe");
        RadioButton rbSedan = new RadioButton("sedan");
        RadioButton rbSuv   = new RadioButton("suv");
        rbCoupe.setToggleGroup(groupType);
        rbSedan.setToggleGroup(groupType);
        rbSuv.setToggleGroup(groupType);
        rbSedan.setSelected(true); 

        Label lblPrixJour = new Label("Prix/jour:");
        TextField tfPrixJour = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(lblMarque, 0, 0); grid.add(tfMarque, 1, 0);

        grid.add(lblType, 0, 1);
        grid.add(rbCoupe, 1, 1);
        grid.add(rbSedan, 2, 1);
        grid.add(rbSuv,   3, 1);

        grid.add(lblPrixJour, 0, 2); grid.add(tfPrixJour, 1, 2);

        dialog.getDialogPane().setContent(grid);
        ButtonType addBtnType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);

        dialog.setResultConverter(b -> {
            if (b == addBtnType) {
                try {
                    double prix = Double.parseDouble(tfPrixJour.getText().trim());
                    Toggle selectedToggle = groupType.getSelectedToggle();
                    if (selectedToggle == null) {
                        return null;
                    }
                    String typeVoiture = ((RadioButton) selectedToggle).getText().trim();

                    return new Voiture(
                            0,
                            tfMarque.getText().trim(),
                            true,
                            typeVoiture,
                            prix
                    );
                } catch (Exception ex) {
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(voiture -> {
            if (voiture == null || voiture.getMarque().isEmpty() || voiture.getTypeVoiture().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Tous les champs doivent être valides.").showAndWait();
                return;
            }
            gestionVoitures.ajouterVoiture(voiture);
            refreshTables();
            new Alert(Alert.AlertType.INFORMATION, "Voiture ajoutée avec succès!").showAndWait();
        });
    }

    private void editCar() {
        Voiture selected = tblCars.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une voiture à modifier.").showAndWait();
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Modifier une voiture");
        dialog.setHeaderText("Modifiez les informations de la voiture");

        Label lblMarque = new Label("Marque:");
        TextField tfMarque = new TextField(selected.getMarque());
        Label lblType = new Label("Type:");
        TextField tfType = new TextField(selected.getTypeVoiture());
        Label lblPrixJour = new Label("Prix/jour:");
        TextField tfPrixJour = new TextField(String.valueOf(selected.getPrixJour()));
        Label lblDispo = new Label("Disponible:");
        CheckBox cbDispo = new CheckBox();
        cbDispo.setSelected(selected.getIsDispo());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(lblMarque, 0, 0); grid.add(tfMarque, 1, 0);
        grid.add(lblType, 0, 1); grid.add(tfType, 1, 1);
        grid.add(lblPrixJour, 0, 2); grid.add(tfPrixJour, 1, 2);
        grid.add(lblDispo, 0, 3); grid.add(cbDispo, 1, 3);

        dialog.getDialogPane().setContent(grid);
        ButtonType saveBtnType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

        dialog.setResultConverter(b -> {
            if (b == saveBtnType) {
                try {
                    String newMarque = tfMarque.getText().trim();
                    String newType = tfType.getText().trim();
                    double newPrix = Double.parseDouble(tfPrixJour.getText().trim());
                    boolean newDispo = cbDispo.isSelected();
                    gestionVoitures.modifierVoiture(
                            selected.getIdVoiture(),
                            newMarque,
                            newType,
                            newPrix,
                            newDispo
                    );
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Données invalides !").showAndWait();
                }
            }
            return null;
        });

        dialog.showAndWait();
        refreshTables();
        tblCars.refresh();
    }

    private void deleteCar() {
        Voiture selected = tblCars.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionVoitures.supprimerVoiture(selected.getIdVoiture());
            refreshTables();
        }
    }

    private void acceptDemande() {
        DemandeLocation selected = tblDemandes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionDemandesLocations.accepterDemandeLocation(
                    selected.getIdDemande(),
                    selected.getTypePaiement()
            );
            refreshTables();
            tblCars.refresh();
            new Alert(Alert.AlertType.INFORMATION, "Demande acceptée!").showAndWait();
        }
    }

    private void deleteDemande() {
        DemandeLocation selected = tblDemandes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionDemandesLocations.supprimerDemandeLocation(selected.getIdDemande());
            refreshTables();
            new Alert(Alert.AlertType.INFORMATION, "Demande supprimée!").showAndWait();
        }
    }
}













