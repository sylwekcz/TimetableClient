package pl.sylwekczmil.timetableclient.controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.sylwekczmil.timetableclient.SceneChanger;
import pl.sylwekczmil.timetableclient.resource.User;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;


public class HomeController implements Initializable {
    
    User currentUser;
    UserService userService = UserService.getInstance();
    SceneChanger sceneChanger = SceneChanger.getInstance();
    
    @FXML
    Label lblUser;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            currentUser = userService.getCurrentUser();
            lblUser.setText("Welcome "+currentUser.getUsername()+"!");
        } catch (NotLoggedInException ex) {
            sceneChanger.goToLogin();
        }
    }    
    
     @FXML
    private void click() {
        try {
            throw new NotLoggedInException();
        } catch (NotLoggedInException ex) {
            sceneChanger.goToLogin();
        }
    }
    
}
