package pl.sylwekczmil.timetableclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.sylwekczmil.timetableclient.Timetable;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.WrongCredentialsException;

public class LoginController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    UserService us = new UserService();    

    @FXML
    private void handleButtonLoginAction(ActionEvent event) throws IOException {

        try {
            us.login(txtUsername.getText(), txtPassword.getText());
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            Scene scene = new Scene(p);
            Stage appStage = Timetable.getPrimaryStage();
            appStage.hide();
            appStage.setScene(scene);
            appStage.show();
        } catch (WrongCredentialsException e) {
            label.setText("Wrong credentials!");
        } catch (RuntimeException e) {
            label.setText("No conection!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
