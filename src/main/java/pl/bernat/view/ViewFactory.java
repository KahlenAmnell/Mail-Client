package pl.bernat.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.bernat.EmailManager;
import pl.bernat.controller.BaseController;
import pl.bernat.controller.LoginWindowController;
import pl.bernat.controller.MainWindowController;

import java.io.IOException;

public class ViewFactory {
    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }
    public void showLoginWindow(){
        System.out.println("show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "/view/LoginWindow.fxml");
        initializeStage(controller);
    }

    public void showMainWindow(){
        System.out.println("show login window called");

        BaseController controller = new MainWindowController(emailManager, this, "/view/MainWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
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
