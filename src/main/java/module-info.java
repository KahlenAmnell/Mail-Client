module Mail.Client {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens pl.bernat;
    opens pl.bernat.controller;
    opens pl.bernat.model;
}