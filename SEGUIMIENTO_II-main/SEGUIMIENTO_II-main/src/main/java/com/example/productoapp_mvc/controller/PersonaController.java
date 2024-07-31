package com.example.productoapp_mvc.controller;

import com.example.productoapp_mvc.view.LoginView;
import com.example.productoapp_mvc.view.PersonaView;
import com.example.productoapp_mvc.util.DataBaseConnection;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class PersonaController {
    private PersonaView personaView;
    private Stage stage;
    private boolean isRegistro;

    public PersonaController(PersonaView personaView, Stage stage, boolean isRegistro) {
        this.personaView = personaView;
        this.stage = stage;
        this.isRegistro = isRegistro;
        initializeListeners();
        applyStyles();
    }

    private void initializeListeners() {
        personaView.getSubmitButton().setOnAction(e -> handleSubmit());
        personaView.getVolverButton().setOnAction(e -> handleVolver());
    }

    private void handleSubmit() {
        String nombre = personaView.getNombreField().getText();
        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(personaView.getFechaNacimientoPicker().getValue());

        if (nombre.isEmpty() || fechaNacimiento == null) {
            showAlert("Error", "Por favor, complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        if (!isAdult(fechaNacimiento)) {
            showAlert("Error", "Debe ser mayor de edad para registrarse", Alert.AlertType.ERROR);
            return;
        }

        if (isRegistro) {
            String username = personaView.getUsernameField().getText();
            String password = personaView.getPasswordField().getText();
            String email = personaView.getEmailField().getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                showAlert("Error", "Por favor, complete todos los campos", Alert.AlertType.ERROR);
                return;
            }

            try {
                if (registrarUsuario(username, password, nombre, email, fechaNacimiento)) {
                    showAlert("Éxito", "Registro exitoso", Alert.AlertType.INFORMATION);
                    openLoginWindow();
                } else {
                    showAlert("Error", "El usuario ya existe", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Error al registrar: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            try {
                if (isClientExist(nombre, fechaNacimiento)) {
                    showAlert("Error", "El cliente ya está registrado", Alert.AlertType.ERROR);
                    return;
                }

                String query = "INSERT INTO clientes (nombre, fecha_nacimiento) VALUES (?, ?)";
                try (Connection conn = DataBaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, nombre);
                    pstmt.setDate(2, fechaNacimiento);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        showAlert("Éxito", "Cliente agregado exitosamente", Alert.AlertType.INFORMATION);
                        ((Stage) personaView.getScene().getWindow()).close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Error al agregar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private boolean registrarUsuario(String username, String password, String nombre, String email, java.sql.Date fechaNacimiento) throws SQLException {
        String query = "INSERT INTO usuarios (username, password, nombre, email, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, nombre);
            pstmt.setString(4, email);
            pstmt.setDate(5, fechaNacimiento);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private boolean isClientExist(String nombre, java.sql.Date fechaNacimiento) throws SQLException {
        String query = "SELECT * FROM clientes WHERE nombre = ? AND fecha_nacimiento = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setDate(2, fechaNacimiento);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error al verificar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            throw e;
        }
    }

    private boolean isAdult(java.sql.Date fechaNacimiento) {
        return Period.between(fechaNacimiento.toLocalDate(), LocalDate.now()).getYears() >= 18;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openLoginWindow() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        new LoginController(loginView, loginStage);
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginView, 300, 200));
        loginStage.show();
        stage.close();
    }

    private void handleVolver() {
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        new LoginController(loginView, loginStage);
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginView, 300, 200));
        loginStage.show();
        stage.close();
    }

    private void applyStyles() {
        personaView.getNombreField().setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        personaView.getFechaNacimientoPicker().setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        personaView.getSubmitButton().setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        personaView.getVolverButton().setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
    }
}