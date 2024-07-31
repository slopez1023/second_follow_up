package com.example.productoapp_mvc.view;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainView extends BorderPane {
    private ComboBox<String> consultaComboBox;
    private TextArea resultadoTextArea;
    private Button logoutButton;
    private Label derechosLabel;

    public MainView() {
        // Crear componentes
        consultaComboBox = new ComboBox<>();
        consultaComboBox.getItems().addAll(
                "Listar todos los productos",
                "Listar productos con precio mayor a $5.000",
                "Listar productos por tipo",
                "Listar productos por ubicación",
                "Listar todos los clientes",
                "Listar todos los productos ordenados alfabéticamente",
                "Mostrar productos con precio superior a $10.000",
                "Obtener productos de la categoría 'Lácteos'",
                "Listar productos en el pasillo 2",
                "Mostrar los 5 primeros productos",
                "Consultar productos que comienzan con 'A'",
                "Buscar productos que contienen 'Arroz'",
                "Obtener productos sin ubicación específica",
                "Listar clientes ordenados por fecha de nacimiento",
                "Mostrar clientes nacidos en 1990 o posterior"
        );

        resultadoTextArea = new TextArea();
        resultadoTextArea.setEditable(false);

        logoutButton = new Button("Salir");

        derechosLabel = new Label("Derechos reservados © 2024");

        // Organizar componentes
        HBox topBox = new HBox(10, consultaComboBox, logoutButton);
        topBox.setSpacing(10);

        this.setTop(topBox);
        this.setCenter(resultadoTextArea);
        this.setBottom(derechosLabel);
    }

    public ComboBox<String> getConsultaComboBox() {
        return consultaComboBox;
    }

    public TextArea getResultadoTextArea() {
        return resultadoTextArea;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}

