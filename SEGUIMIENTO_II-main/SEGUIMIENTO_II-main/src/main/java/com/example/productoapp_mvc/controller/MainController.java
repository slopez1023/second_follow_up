package com.example.productoapp_mvc.controller;

import com.example.productoapp_mvc.util.DataBaseConnection;
import com.example.productoapp_mvc.view.MainView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    private MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        initializeListeners();
        showWelcomeMessage();
    }

    private void initializeListeners() {
        mainView.getConsultaComboBox().setOnAction(e -> handleConsultaSelection());
        mainView.getLogoutButton().setOnAction(e -> handleLogout());
    }

    private void showWelcomeMessage() {
        mainView.getResultadoTextArea().setText("Bienvenido a la Aplicación de Consultas");
    }

    private void handleConsultaSelection() {
        String selectedConsulta = mainView.getConsultaComboBox().getValue();
        try {
            switch (selectedConsulta) {
                case "Listar todos los productos":
                    listarTodosLosProductos();
                    break;
                case "Listar productos con precio mayor a $5.000":
                    listarProductosPorPrecio(5000);
                    break;
                case "Listar productos por tipo":
                    listarProductosPorTipo("Cuidado Personal");
                    break;
                case "Listar productos por ubicación":
                    listarProductosPorUbicacion("Pasillo 7");
                    break;
                case "Listar todos los clientes":
                    listarTodosLosClientes();
                    break;
                case "Listar todos los productos ordenados alfabéticamente":
                    listarTodosLosProductosOrdenados();
                    break;
                case "Mostrar productos con precio superior a $10.000":
                    listarProductosPorPrecioSuperior(10000);
                    break;
                case "Obtener productos de la categoría 'Lácteos'":
                    listarProductosPorTipo("Lácteos");
                    break;
                case "Listar productos en el pasillo 2":
                    listarProductosPorUbicacion("Pasillo 2");
                    break;
                case "Mostrar los 5 primeros productos":
                    listarPrimerosProductos(5);
                    break;
                case "Consultar productos que comienzan con 'A'":
                    listarProductosPorNombreInicial("A");
                    break;
                case "Buscar productos que contienen 'Arroz'":
                    listarProductosPorNombreContiene("Arroz");
                    break;
                case "Obtener productos sin ubicación específica":
                    listarProductosSinUbicacion();
                    break;
                case "Listar clientes ordenados por fecha de nacimiento":
                    listarClientesOrdenadosPorFechaNacimiento();
                    break;
                case "Mostrar clientes nacidos en 1990 o posterior":
                    listarClientesNacidosEnODespuesDe(1990);
                    break;
                default:
                    mainView.getResultadoTextArea().setText("Seleccione una consulta válida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mainView.getResultadoTextArea().setText("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    private void listarTodosLosProductos() throws SQLException {
        String query = "SELECT * FROM productos";
        executeQueryAndDisplayResults(query);
    }

    private void listarProductosPorPrecio(double precio) throws SQLException {
        String query = "SELECT * FROM productos WHERE precio > ?";
        executeQueryAndDisplayResults(query, precio);
    }

    private void listarProductosPorTipo(String tipo) throws SQLException {
        String query = "SELECT * FROM productos WHERE tipo = ?";
        executeQueryAndDisplayResults(query, tipo);
    }

    private void listarProductosPorUbicacion(String ubicacion) throws SQLException {
        String query = "SELECT * FROM productos WHERE ubicacion = ?";
        executeQueryAndDisplayResults(query, ubicacion);
    }

    private void listarTodosLosClientes() throws SQLException {
        String query = "SELECT * FROM clientes";
        executeQueryAndDisplayResults(query);
    }

    private void listarTodosLosProductosOrdenados() throws SQLException {
        String query = "SELECT * FROM productos ORDER BY nombre";
        executeQueryAndDisplayResults(query);
    }

    private void listarProductosPorPrecioSuperior(double precio) throws SQLException {
        String query = "SELECT * FROM productos WHERE precio > ?";
        executeQueryAndDisplayResults(query, precio);
    }

    private void listarPrimerosProductos(int limite) throws SQLException {
        String query = "SELECT * FROM productos LIMIT ?";
        executeQueryAndDisplayResults(query, limite);
    }

    private void listarProductosPorNombreInicial(String inicial) throws SQLException {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        executeQueryAndDisplayResults(query, inicial + "%");
    }

    private void listarProductosPorNombreContiene(String palabra) throws SQLException {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        executeQueryAndDisplayResults(query, "%" + palabra + "%");
    }

    private void listarProductosSinUbicacion() throws SQLException {
        String query = "SELECT * FROM productos WHERE ubicacion IS NULL OR ubicacion = ''";
        executeQueryAndDisplayResults(query);
    }

    private void listarClientesOrdenadosPorFechaNacimiento() throws SQLException {
        String query = "SELECT * FROM clientes ORDER BY fecha_nacimiento";
        executeQueryAndDisplayResults(query);
    }

    private void listarClientesNacidosEnODespuesDe(int anio) throws SQLException {
        String query = "SELECT * FROM clientes WHERE YEAR(fecha_nacimiento) >= ?";
        executeQueryAndDisplayResults(query, anio);
    }

    private void executeQueryAndDisplayResults(String query, Object... parameters) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            displayResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void displayResults(ResultSet rs) throws SQLException {
        StringBuilder results = new StringBuilder();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            results.append(rs.getMetaData().getColumnName(i)).append("\t");
        }
        results.append("\n");

        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                results.append(rs.getString(i)).append("\t");
            }
            results.append("\n");
        }

        mainView.getResultadoTextArea().setText(results.toString());
    }

    private void handleLogout() {
        Platform.exit();
    }
}
