module hospital.hospital_management_system2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.web;

    requires java.sql;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens hospital.hospital_management_system2 to javafx.fxml;
    opens hospital.hospital_management_system2.controller to javafx.fxml;

    exports hospital.hospital_management_system2;
}
