package pl.sylwekczmil.timetableclient;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneChanger {

    Scene currentScene;

    private static SceneChanger instance = null;

    protected SceneChanger() {
    }

    public static SceneChanger getInstance() {
        if (instance == null) {
            instance = new SceneChanger();
        }
        return instance;
    }

    public void goToLogin() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            currentScene = new Scene(p);
            Alert alert = new Alert(AlertType.WARNING);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("You are not logged in, or your token expired!");   
            alert.showAndWait();
            changeScene();
        } catch (IOException e) {
        }
    }

    private void changeScene() {
        Stage appStage = TimetableApp.getPrimaryStage();
        appStage.hide();
        appStage.setScene(currentScene);
        appStage.show();
    }

}
