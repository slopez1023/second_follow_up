package com.example.productoapp_mvc.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registroButton;

    public LoginView() {
        // Configurar VBox
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.getStyleClass().add("login-view");

        // Crear el GridPane para los campos de texto
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Crear componentes
        usernameField = new TextField();
        usernameField.setPromptText("Usuario");
        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        loginButton = new Button("Iniciar Sesión");
        registroButton = new Button("Registrar");

        // Organizar componentes en el GridPane
        gridPane.add(new Label("Usuario:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Contraseña:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registroButton, 1, 3);

        // Añadir el GridPane al VBox
        this.getChildren().add(gridPane);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegistroButton() {
        return registroButton;
    }
}
