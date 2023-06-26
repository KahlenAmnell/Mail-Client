package pl.bernat.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.bernat.EmailManager;
import pl.bernat.controller.BaseController;
import pl.bernat.controller.LoginWindowController;

import java.io.IOException;

public class ViewFactory {
    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }
    public void showLoginWindow(){
        System.out.println("show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "/view/LoginWindow.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try{
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
