package pl.sylwekczmil.timetableclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.sylwekczmil.timetableclient.MainSceneChanger;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.WrongCredentialsException;

public class LoginController implements Initializable {

    private UserService us = UserService.getInstance();
    private MainSceneChanger sceneChanger = MainSceneChanger.getInstance();

    @FXML
    private Label label;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button buttonLogin;

    @FXML
    private void handleButtonLoginAction(ActionEvent event) throws IOException {

        try {
            us.login(txtUsername.getText(), txtPassword.getText());
            sceneChanger.goToHome();
        } catch (WrongCredentialsException e) {
            label.setText("Wrong credentials!");
        } catch (RuntimeException e) {
            label.setText("No conection!");
        }
    }

    @FXML
    private void signUp(ActionEvent event) {

        sceneChanger.goToRegister();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
        sceneChanger.getPrimaryStage().setTitle("Timetable!");
        txtUsername.setPromptText("Username");
        txtPassword.setPromptText("Password");

    }

}
