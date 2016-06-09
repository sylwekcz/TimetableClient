package pl.sylwekczmil.timetableclient;

import pl.sylwekczmil.timetableclient.resource.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;

public class FXMLController implements Initializable {

    @FXML
    private Label label;

    UserService us = new UserService();

    @FXML
    private void handleButtonAction(ActionEvent event) {

        try {
            User u = us.getCurrentUser();
            label.setText("Hello "+u.getUsername()+"!");
        } catch (NotLoggedInException e) {
            
        }      

    }

    @FXML
    private void handleButtonLoginAction(ActionEvent event) {

        try {
            us.login("sylwek", "haslo123");
        } catch (Exception e) {
            System.out.println("WRONG CREDENTIALS");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
