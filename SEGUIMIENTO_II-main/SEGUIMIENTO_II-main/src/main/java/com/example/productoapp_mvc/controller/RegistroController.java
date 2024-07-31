package com.example.productoapp_mvc.controller;

import com.example.productoapp_mvc.util.DataBaseConnection;
import com.example.productoapp_mvc.view.LoginView;
import com.example.productoapp_mvc.view.RegistroView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroController {

    private RegistroView registroView;
    private Stage stage;

    public RegistroController(RegistroView registroView, Stage stage) {
        this.registroView = registroView;
        this.stage = stage;
        initializeListeners();
    }

    private void initializeListeners() {
        registroView.getRegistroButton().setOnAction(e -> handleRegistro());
        registroView.getVolverButton().setOnAction(e -> backToLogin());
    }

    private void handleRegistro() {
        String nombre = registroView.getNombreField().getText();
        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(registroView.getFechaNacimientoPicker().getValue());

        if (nombre.isEmpty() || fechaNacimiento == null) {
            showAlert("Error", "Por favor, complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        if (!isAdult(fechaNacimiento)) {
            showAlert("Error", "Debe ser mayor de edad para registrarse", Alert.AlertType.ERROR);
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
                backToLogin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error al agregar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isAdult(java.sql.Date fechaNacimiento) {
        // Implementar lógica para validar si el cliente es mayor de edad
        // Ejemplo: return Period.between(fechaNacimiento.toLocalDate(), LocalDate.now()).getYears() >= 18;
        return true; // Placeholder
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void backToLogin() {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, stage);
        Scene loginScene = new Scene(loginView, 300, 200);
        stage.setScene(loginScene);
        stage.show();
    }
}
