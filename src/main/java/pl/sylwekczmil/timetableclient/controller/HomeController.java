package pl.sylwekczmil.timetableclient.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.sylwekczmil.timetableclient.SceneChanger;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;


public class HomeController implements Initializable {
    
    SceneChanger sc = SceneChanger.getInstance();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private void click() {
        try {
            throw new NotLoggedInException();
        } catch (NotLoggedInException ex) {
            sc.goToLogin();
        }
    }
    
}
