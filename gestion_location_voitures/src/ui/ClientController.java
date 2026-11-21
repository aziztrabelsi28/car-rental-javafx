package ui;

import model.Client;
import model.Facture;
import model.Voiture;
import model.DemandeLocation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientController {

    @FXML private Button btnLogout, btnViewCars, btnRequestRent, btnViewInvoices;
    @FXML private TableView<Voiture> tblVoituresDispo;
    @FXML private TableColumn<Voiture, Integer> colId;
    @FXML private TableColumn<Voiture, String> colMarque;
    @FXML private TableColumn<Voiture, String> colType;
    @FXML private TableColumn<Voiture, Double> colPrixJour;
    @FXML private TableColumn<Voiture, Boolean> colDisponible;

    private Client client; 

    @FXML
    public void initialize() {
        btnLogout.setOnAction(e -> logout());
        btnViewCars.setOnAction(e -> viewAvailableCars());
        btnRequestRent.setOnAction(e -> requestRent());
        btnViewInvoices.setOnAction(e -> viewInvoices());

        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdVoiture()).asObject());
        colMarque.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMarque()));
        colType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTypeVoiture()));
        colPrixJour.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrixJour()).asObject());
        colDisponible.setCellValueFactory(data ->
                new javafx.beans.property.SimpleBooleanProperty(data.getValue().getIsDispo()).asObject());

        tblVoituresDispo.setVisible(false);
    }

    public void setClient(Client client) {
        this.client = client;
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

    private void viewAvailableCars() {
        if (client == null) return;
        List<Voiture> dispoCars = MainApp.sharedGestionVoitures.getListeVoituresDispo();
        tblVoituresDispo.setItems(FXCollections.observableArrayList(dispoCars));
        tblVoituresDispo.setVisible(true);
    }

    private void requestRent() {
        if (client == null) return;

        Voiture selectedCar = tblVoituresDispo.getSelectionModel().getSelectedItem();
        if (selectedCar == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une voiture disponible.").showAndWait();
            return;
        }

        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Options de location");

        Label lblLoc = new Label("Type de location:");
        ToggleGroup groupLoc = new ToggleGroup();
        RadioButton rbStandard = new RadioButton("standard");
        RadioButton rbWeekend = new RadioButton("week-end");
        RadioButton rbPremium = new RadioButton("premium");
        rbStandard.setToggleGroup(groupLoc);
        rbWeekend.setToggleGroup(groupLoc);
        rbPremium.setToggleGroup(groupLoc);
        rbStandard.setSelected(true);

        Label lblPay = new Label("Type de paiement:");
        ToggleGroup groupPay = new ToggleGroup();
        RadioButton rbCarte = new RadioButton("carte");
        RadioButton rbEspeces = new RadioButton("espèces");
        rbCarte.setToggleGroup(groupPay);
        rbEspeces.setToggleGroup(groupPay);
        rbCarte.setSelected(true);

        Label lblRetour = new Label("Date de retour:");
        DatePicker dpRetour = new DatePicker(LocalDate.now().plusDays(2));
        dpRetour.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(lblLoc, 0, 0);
        grid.add(rbStandard, 1, 0);
        grid.add(rbWeekend, 2, 0);
        grid.add(rbPremium, 3, 0);
        grid.add(lblPay, 0, 1);
        grid.add(rbCarte, 1, 1);
        grid.add(rbEspeces, 2, 1);
        grid.add(lblRetour, 0, 2);
        grid.add(dpRetour, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(b -> {
            if (b == ButtonType.OK) {
                String typeLocation =
                        rbStandard.isSelected() ? "standard" :
                        rbWeekend.isSelected() ? "week-end" : "premium";
                String typePaiement = rbCarte.isSelected() ? "carte" : "espèces";
                LocalDate retourDate = dpRetour.getValue();
                return new Object[]{typeLocation, typePaiement, retourDate};
            }
            return null;
        });

        Optional<Object[]> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        String typeLocation = (String) result.get()[0];
        String typePaiement = (String) result.get()[1];
        LocalDate retourDate = (LocalDate) result.get()[2];

        LocalDateTime debut = LocalDateTime.now();
        LocalDateTime fin = retourDate.atStartOfDay();

        DemandeLocation demande = new DemandeLocation(
                0,
                client,
                selectedCar,
                debut,
                fin,
                typeLocation,
                typePaiement
        );

        MainApp.sharedGestionDemandesLocations.ajouterDemandeLocation(demande);

        new Alert(Alert.AlertType.INFORMATION, "Demande de location envoyée!").showAndWait();
    }

    private void viewInvoices() {
        if (client == null) return;

        List<Facture> factures;
        try {
            factures = MainApp.sharedGestionDemandesLocations
                    .getFactureService()
                    .getFactureDAO()
                    .findByClientId(client.getIdUser());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement des factures.").showAndWait();
            return;
        }

        if (factures.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucune facture disponible pour ce client.");
            alert.showAndWait();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Facture f : factures) {
            sb.append("Facture ID: ").append(f.getIdFacture()).append("\n")
              .append("Voiture: ")
              .append(f.getLocation().getDemandeLocation().getVoiture().getMarque()).append("\n")
              .append("Type Paiement: ").append(f.getTypePaiement()).append("\n")
              .append("Prix Total: ").append(f.getPrix()).append(" DT\n\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, sb.toString());
        alert.setHeaderText("Mes Factures");
        alert.showAndWait();
    }
}









