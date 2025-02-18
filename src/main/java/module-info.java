module tn.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.eventcraft to javafx.graphics;
    opens tn.esprit.controllers to javafx.fxml;
    exports tn.esprit.test;
    exports tn.esprit.controllers;
}