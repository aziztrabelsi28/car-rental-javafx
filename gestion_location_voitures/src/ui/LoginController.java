package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.AuthService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Client;
import model.Admin;
import model.User;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnCreate;

    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    public void initialize() {
        btnLogin.setOnAction(e -> handleLogin());
        btnCreate.setOnAction(e -> handleCreate());
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (authService != null && authService.login(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "login successful");
            alert.showAndWait();

            User current = authService.getCurrentUser();
            if (current != null) {
                if ("admin".equalsIgnoreCase(current.getRole())) {
                    goToAdminDashboard("/ui/admin.fxml", "Admin Dashboard", (Admin) current);
                } else {
                    goToClientDashboard("/ui/client.fxml", "Client Dashboard", (Client) current);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "login failed verifier les informations");
            alert.showAndWait();
        }
    }

    private void handleCreate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/register.fxml"));
            Parent root = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setAuthService(authService);
            Stage stage = (Stage) btnCreate.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToAdminDashboard(String fxmlPath, String title, Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            AdminController adminController = loader.getController();
            adminController.setAdmin(admin);

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToClientDashboard(String fxmlPath, String title, Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            ClientController clientController = loader.getController();
            clientController.setClient(client);

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







