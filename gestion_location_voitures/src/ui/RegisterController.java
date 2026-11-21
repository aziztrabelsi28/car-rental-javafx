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

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private Button btnRegister;
    @FXML private Button btnBack;

    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    public void initialize() {
        btnRegister.setOnAction(e -> handleRegister());
        btnBack.setOnAction(e -> goBackToLogin());
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();

        if (username.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields.").showAndWait();
            return;
        }

        boolean success = (authService != null)
                && authService.register(username, password, "client", nom, prenom);

        if (!success) {
            new Alert(Alert.AlertType.WARNING,
                    "Ce username est déjà existant, veuillez choisir un autre username.")
                    .showAndWait();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Utilisateur créé!").showAndWait();
        goBackToLogin();
    }

    private void goBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setAuthService(authService);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






