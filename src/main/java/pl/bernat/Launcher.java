package pl.bernat;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.bernat.view.ViewFactory;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}