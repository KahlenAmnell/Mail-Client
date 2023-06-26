package pl.bernat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.bernat.view.ViewFactory;

import java.util.Objects;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}