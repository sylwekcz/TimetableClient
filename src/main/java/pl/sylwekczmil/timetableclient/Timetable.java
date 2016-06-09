package pl.sylwekczmil.timetableclient;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class Timetable extends Application {

    private static Stage pStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        setPrimaryStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Timetable");
        stage.setScene(scene);
        stage.show();
    }

   
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        Timetable.pStage = pStage;
    }
    
    
    
}
