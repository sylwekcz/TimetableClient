package pl.sylwekczmil.timetableclient.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.sylwekczmil.timetableclient.MainSceneChanger;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.model.User;
import pl.sylwekczmil.timetableclient.service.TimetableService;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;

public class HomeController implements Initializable {

    private UserService userService = UserService.getInstance();
    private TimetableService timetableService = TimetableService.getInstance();
    private MainSceneChanger sceneChanger = MainSceneChanger.getInstance();

    private User currentUser;
    private ObservableList<String> timetableNameList = FXCollections.observableArrayList();
    private List<Timetable> timetableList = new ArrayList<Timetable>();

    @FXML
    BorderPane borderPane;

    @FXML
    ListView timetableListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            currentUser = userService.getCurrentUser();
            sceneChanger.getPrimaryStage().setTitle("Hello " + currentUser.getUsername() + "!");
            timetableList = timetableService.getTimetablesByUserId(currentUser.getIdUser());
            timetableList.forEach((Timetable t) -> timetableNameList.add(t.getName()));

            //TODO REMOVE
            timetableNameList.add("Jakies");
            timetableNameList.add("Makies");

            timetableListView.setItems(timetableNameList);
            timetableListView.getSelectionModel().select(0);
            timetableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
            });

        } catch (NotLoggedInException ex) {
            sceneChanger.goToLogin();
        }

    }

    @FXML
    private void addNewTimetable() {

        TextInputDialog dialog = new TextInputDialog("New Timetable Name");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        stage.getIcons().add(new Image(getClass().getResource("/icon/calendar.png").toExternalForm()));
        dialog.setGraphic(new ImageView(this.getClass().getResource("/icon/calendar.png").toString()));
        dialog.setHeaderText(null);
        dialog.setTitle("Create New Timetable");
        dialog.setContentText("Timetable name:");
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("New Timetable Name")) {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
            else{
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            }
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name
                -> {
            System.out.println(name);
        }
        );

    }

}
