package com.example.productoapp_mvc;

import com.example.productoapp_mvc.controller.LoginController;
import com.example.productoapp_mvc.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear el StackPane y añadir la imagen de fondo
            StackPane root = new StackPane();

            // Ruta de la imagen de fondo (relative path from resources)
            Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/productoapp_mvc/FXML/images/Fondo_inicio_sesion.jpg"));
            ImageView backgroundImageView = new ImageView(backgroundImage);

            // Ajustar el tamaño de la imagen para que ocupe todo el espacio disponible
            backgroundImageView.setFitWidth(800);  // Ancho de la ventana
            backgroundImageView.setFitHeight(600); // Alto de la ventana
            backgroundImageView.setPreserveRatio(false);

            // Añadir la imagen de fondo al StackPane
            root.getChildren().add(backgroundImageView);

            // Cargar la vista de inicio de sesión
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView, primaryStage);

            // Añadir el resto del contenido (loginView) encima del fondo
            root.getChildren().add(loginView);

            // Crear la escena y añadir el StackPane
            Scene scene = new Scene(root, 800, 600); // Asegúrate de que el tamaño coincida con el fondo
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}