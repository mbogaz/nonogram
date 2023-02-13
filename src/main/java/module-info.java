module com.codefirst.nonogram_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires color.diff;
    requires lombok;

    opens com.codefirst.nonogram_fx to javafx.fxml;
    exports com.codefirst.nonogram_fx;
    exports com.codefirst.nonogram_fx.controller;
    opens com.codefirst.nonogram_fx.controller to javafx.fxml;
    exports com.codefirst.nonogram_fx.dto;
    opens com.codefirst.nonogram_fx.dto to javafx.fxml;
}