package com.example.productoapp_mvc.controller;

import com.example.productoapp_mvc.view.LoginView;
import com.example.productoapp_mvc.view.MainView;
import com.example.productoapp_mvc.util.DataBaseConnection;
import com.example.productoapp_mvc.view.RegistroView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private LoginView loginView;
    private Stage stage;

    public LoginController(LoginView loginView, Stage stage) {
        this.loginView = loginView;
        this.stage = stage;
        initializeListeners();
        applyStyles();
    }

    private void initializeListeners() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
        loginView.getRegistroButton().setOnAction(e -> openRegistroWindow());
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor, complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            if (authenticate(username, password)) {
                showAlert("Éxito", "Inicio de sesión exitoso", Alert.AlertType.INFORMATION);
                openMainWindow();
            } else {
                showAlert("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error al autenticar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM clientes WHERE nombre = ? AND DATE_FORMAT(fecha_nacimiento, '%m%d') = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openMainWindow() {
        MainView mainView = new MainView();
        MainController mainController = new MainController(mainView);
        Stage mainStage = new Stage();
        mainStage.setTitle("Aplicación de Consultas");
        mainStage.setMaximized(true);
        mainStage.setScene(new Scene(mainView, 800, 600));
        mainStage.show();
        stage.close();
    }

    private void openRegistroWindow() {
        RegistroView registroView = new RegistroView();
        RegistroController registroController = new RegistroController(registroView, stage);
        Scene registroScene = new Scene(registroView, 300, 200);
        stage.setScene(registroScene);
        stage.show();
    }

    private void applyStyles() {
        loginView.getUsernameField().setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        loginView.getPasswordField().setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        loginView.getLoginButton().setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginView.getRegistroButton().setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
    }
}
