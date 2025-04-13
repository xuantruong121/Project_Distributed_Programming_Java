module iuh.fit.qlksfxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires org.hibernate.orm.core;
    requires org.reflections;
    requires static lombok;

    exports iuh.fit.qlksfxapp to javafx.graphics;
    exports iuh.fit.qlksfxapp.controller;

    opens iuh.fit.qlksfxapp.controller to javafx.fxml;
}