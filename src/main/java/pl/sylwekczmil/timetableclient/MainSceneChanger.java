package pl.sylwekczmil.timetableclient;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneChanger {

    Scene currentScene;

    private static MainSceneChanger instance = null;

    protected MainSceneChanger() {
    }

    public static MainSceneChanger getInstance() {
        if (instance == null) {
            instance = new MainSceneChanger();
        }
        return instance;
    }

    public void goToLogin() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            currentScene = new Scene(p);
            changeScene();
        } catch (IOException e) {
        }
    }
    
    public void goToRegister() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
            currentScene = new Scene(p);            
            changeScene();
        } catch (IOException e) {
        }
    }
    
     public void goToHome() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            currentScene = new Scene(p);            
            changeScene();
        } catch (IOException e) {
        }
    }

    private void changeScene() {
        Stage appStage = getPrimaryStage();
        appStage.hide();
        appStage.setScene(currentScene);
        appStage.show();
    }

    public Stage getPrimaryStage() {
        return TimetableApp.getPrimaryStage();
    }
}
