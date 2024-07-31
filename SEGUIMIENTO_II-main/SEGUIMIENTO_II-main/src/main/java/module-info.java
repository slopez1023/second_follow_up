module com.example.productoapp_mvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.productoapp_mvc to javafx.fxml;
    exports com.example.productoapp_mvc;
}