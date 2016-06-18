package pl.sylwekczmil.timetableclient;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneChanger {

    

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
            Scene currentScene = new Scene(p);            
            changeScene(currentScene);
        } catch (IOException e) {
        }
    }
    
    public void goToRegister() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
            Scene currentScene = new Scene(p);            
            changeScene(currentScene);
        } catch (IOException e) {
        }
    }
    
     public void goToHome() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            Scene currentScene = new Scene(p);            
            changeScene(currentScene);
        } catch (IOException e) {
        }
    }

    private void changeScene(Scene currentScene) {
        
        Stage appStage = getPrimaryStage();
        appStage.close();
        appStage.setScene(currentScene);
        appStage.show();
    }

    public Stage getPrimaryStage() {
        return TimetableApp.getPrimaryStage();
    }
}
