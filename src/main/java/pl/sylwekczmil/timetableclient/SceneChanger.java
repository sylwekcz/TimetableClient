package pl.sylwekczmil.timetableclient;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            changeScene();            
        } catch (IOException e) {
        }
    }
    
    
    
    private void changeScene(){
        Stage appStage = Timetable.getPrimaryStage();
            appStage.hide();
            appStage.setScene(currentScene);
            appStage.show();
    }

}
