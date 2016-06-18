package pl.sylwekczmil.timetableclient.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.sylwekczmil.timetableclient.MainSceneChanger;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.model.User;
import pl.sylwekczmil.timetableclient.service.TimetableService;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

public class HomeController implements Initializable {

    private UserService userService = UserService.getInstance();
    private TimetableService timetableService = TimetableService.getInstance();
    private MainSceneChanger sceneChanger = MainSceneChanger.getInstance();

    private User currentUser;
    private ObservableList<String> timetableNameList = FXCollections.observableArrayList();
    private List<Timetable> timetableList = new ArrayList<Timetable>();
    private String selectedTimetableName;

    @FXML
    BorderPane borderPane;

    @FXML
    GridPane gridPane;

    @FXML
    ListView timetableListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            currentUser = userService.getCurrentUser();
            sceneChanger.getPrimaryStage().setTitle("Hello " + currentUser.getUsername() + "!");
            timetableList = timetableService.getTimetablesByUserId(currentUser.getIdUser());
            timetableList.forEach((Timetable t) -> timetableNameList.add(t.getName()));

            timetableListView.setItems(timetableNameList);
            timetableListView.getSelectionModel().select(0);
            selectedTimetableName = timetableNameList.get(0);
            timetableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedTimetableName = (String) newValue;
            });

            for (Node n : gridPane.getChildren()) {
                if (n instanceof Control) {
//                    Control control = (Control) n;
//                    control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//                    control.setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
                }
                if (n instanceof Pane) {
                    Pane pane = (Pane) n;
                    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    pane.setStyle("-fx-background-color: red;");
                }
            }

            gridPane.add(new Label("Name: 1.1 "), 1, 1); //kolumna //wiersz
            gridPane.add(new Label("Name: 1.1 "), 1, 1); //kolumna //wiersz  // jako pane bo nachodzi na siebie
            gridPane.add(new Label("Name: 1.2"), 1, 2);

        } catch (NotLoggedInException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("You are not logged in, or your token expired!");
            alert.showAndWait();
            sceneChanger.goToLogin();
        }

    }

    @FXML
    private void addNewTimetable() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Create New Timetable");
        dialog.setHeaderText(null);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/icon/calendar.png").toExternalForm()));
        dialog.setGraphic(new ImageView(this.getClass().getResource("/icon/calendar.png").toString()));

        ButtonType createButton = new ButtonType("Create", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 10, 10, 10));
        TextField timetableName = new TextField();
        timetableName.setPromptText("Username");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(timetableName, 1, 0);
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        grid.add(errorLabel, 1, 1);
        Node createButtonNode = dialog.getDialogPane().lookupButton(createButton);
        createButtonNode.setDisable(true);

        timetableName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            boolean exist = false;
            for (String tName : timetableNameList) {
                if (tName.equals(newValue)) {
                    exist = true;
                }
            }
            if (exist) {
                createButtonNode.setDisable(true);
                errorLabel.setText("Name already taken!");
            } else {
                createButtonNode.setDisable(newValue.isEmpty());
                errorLabel.setText("");
            }
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> timetableName.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                return timetableName.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tName -> {
            try {
                timetableService.addTimetable(new Timetable(tName, currentUser));
                timetableNameList.add(tName);
            } catch (NotLoggedInException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText("You are not logged in, or your token expired!");
                alert.showAndWait();
                sceneChanger.goToLogin();
            } catch (NotModifiedException ex) {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Timetable was not created, server error!");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void deleteTimetablle() {
        if (timetableNameList.size() != 0) {
            try {
                timetableService.removeTimetable(selectedTimetableName);
                timetableNameList.remove(timetableNameList.indexOf(selectedTimetableName));
            } catch (NotLoggedInException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText("You are not logged in, or your token expired!");
                alert.showAndWait();
                sceneChanger.goToLogin();
            } catch (NotModifiedException ex) {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Timetable was not deleted, server error!");
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("No timetable to delete!");
            alert.showAndWait();

        }
    }
}
