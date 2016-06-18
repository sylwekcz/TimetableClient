package pl.sylwekczmil.timetableclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.sylwekczmil.timetableclient.MainSceneChanger;
import pl.sylwekczmil.timetableclient.model.User;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

public class RegisterController implements Initializable {

    private UserService us = UserService.getInstance();
    private MainSceneChanger sceneChanger = MainSceneChanger.getInstance();

    @FXML
    private Label label;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPassword2;
    @FXML
    private Button buttonRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUsername.setPromptText("Username");
        txtPassword.setPromptText("Password");
    }

    @FXML
    private void goToLogin() {
        sceneChanger.goToLogin();
    }

    @FXML
    private void handleButtonRegisterAction() {
        try {
            if (txtPassword.getText().equals(txtPassword2.getText())) {
                us.addUser(new User(txtUsername.getText(), txtPassword.getText()));
                sceneChanger.goToLogin();
            } else {
                label.setText("Passwords dont match!");
            }

        } catch (NotModifiedException ex) {
            label.setText("User already exist!");

        } catch (RuntimeException e) {
            label.setText("No conection!");

        }
    }

}
