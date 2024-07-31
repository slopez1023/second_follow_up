package com.example.productoapp_mvc.view;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class RegistroView extends StackPane {
    private TextField nombreField;
    private DatePicker fechaNacimientoPicker;
    private Button registroButton;
    private Button volverButton;

    public RegistroView() {
        // Crear componentes
        nombreField = new TextField();
        fechaNacimientoPicker = new DatePicker();
        registroButton = new Button("Registrar");
        volverButton = new Button("Volver");

        // Crear un GridPane para organizar los componentes
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nombre:"), 0, 0);
        gridPane.add(nombreField, 1, 0);
        gridPane.add(new Label("Fecha de Nacimiento:"), 0, 1);
        gridPane.add(fechaNacimientoPicker, 1, 1);
        gridPane.add(registroButton, 1, 2);
        gridPane.add(volverButton, 1, 3);

        // Crear la imagen de fondo
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/productoapp_mvc/FXML/images/Fondo_inicio_sesion.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);  // Ajusta según sea necesario
        backgroundImageView.setFitHeight(600); // Ajusta según sea necesario
        backgroundImageView.setPreserveRatio(false);

        // Añadir la imagen de fondo al StackPane
        this.getChildren().add(backgroundImageView);

        // Añadir el GridPane encima del fondo
        this.getChildren().add(gridPane);
        this.setStyle("-fx-background-color: transparent;"); // Fondo transparente para que se vea la imagen de fondo
    }

    public TextField getNombreField() {
        return nombreField;
    }

    public DatePicker getFechaNacimientoPicker() {
        return fechaNacimientoPicker;
    }

    public Button getRegistroButton() {
        return registroButton;
    }

    public Button getVolverButton() {
        return volverButton;
    }
}
