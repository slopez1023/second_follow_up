package com.example.productoapp_mvc.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class PersonaView extends GridPane {
    private TextField nombreField;
    private DatePicker fechaNacimientoPicker;
    private Button submitButton;
    private Button volverButton;

    // Campos adicionales para el registro de usuario
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;

    public PersonaView(boolean isRegistro) {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(8);
        setHgap(10);

        Label nombreLabel = new Label("Nombre:");
        nombreField = new TextField();
        add(nombreLabel, 0, 0);
        add(nombreField, 1, 0);

        Label fechaNacimientoLabel = new Label("Fecha de Nacimiento:");
        fechaNacimientoPicker = new DatePicker();
        add(fechaNacimientoLabel, 0, 1);
        add(fechaNacimientoPicker, 1, 1);

        if (isRegistro) {
            Label usernameLabel = new Label("Username:");
            usernameField = new TextField();
            add(usernameLabel, 0, 2);
            add(usernameField, 1, 2);

            Label passwordLabel = new Label("Password:");
            passwordField = new PasswordField();
            add(passwordLabel, 0, 3);
            add(passwordField, 1, 3);

            Label emailLabel = new Label("Email:");
            emailField = new TextField();
            add(emailLabel, 0, 4);
            add(emailField, 1, 4);

            submitButton = new Button("Registrar");
        } else {
            submitButton = new Button("Agregar Cliente");
        }

        volverButton = new Button("Volver");
        add(submitButton, 1, 5);
        add(volverButton, 1, 6);
    }

    public TextField getNombreField() {
        return nombreField;
    }

    public DatePicker getFechaNacimientoPicker() {
        return fechaNacimientoPicker;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getVolverButton() {
        return volverButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }
}