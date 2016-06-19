package pl.sylwekczmil.timetableclient.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.sylwekczmil.timetableclient.MainSceneChanger;
import pl.sylwekczmil.timetableclient.model.Event;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.model.User;
import pl.sylwekczmil.timetableclient.service.EventService;
import pl.sylwekczmil.timetableclient.service.TimetableService;
import pl.sylwekczmil.timetableclient.service.UserService;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

public class HomeController implements Initializable {

    private UserService userService = UserService.getInstance();
    private TimetableService timetableService = TimetableService.getInstance();
    private EventService eventService = EventService.getInstance();
    private MainSceneChanger sceneChanger = MainSceneChanger.getInstance();

    private User currentUser;
    private ObservableList<Timetable> timetableList = FXCollections.observableArrayList();
    private List<Event> eventList;
    private Timetable selectedTimetable;

    @FXML
    BorderPane borderPane;

    @FXML
    GridPane gridPane;

    @FXML
    ListView timetableListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            System.out.println("Init HOME");
            currentUser = userService.getCurrentUser();
            sceneChanger.getPrimaryStage().setTitle("Hello " + currentUser.getUsername() + "!");
            timetableList = FXCollections.observableArrayList(timetableService.getTimetablesByUserId(currentUser.getIdUser()));
            timetableListView.setItems(timetableList);

            if (timetableList.size() > 0) {
                timetableListView.getSelectionModel().select(0);
                selectedTimetable = timetableList.get(0);
                changeViewedTimetable(selectedTimetable);
            }
            timetableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (timetableList.size() > 0) {
                    selectedTimetable = (Timetable) newValue;
                    if (selectedTimetable != null) {
                        changeViewedTimetable(selectedTimetable);
                    }

                }
            });

        } catch (NotLoggedInException ex) {
            handleNotLoggedException();
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
            for (Timetable t : timetableList) {
                if (t.getName().equals(newValue)) {
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

                Timetable newTimetable = new Timetable(tName, currentUser);
                timetableService.addTimetable(newTimetable);
                timetableList.add(newTimetable);
                timetableList = FXCollections.observableArrayList(timetableService.getTimetablesByUserId(currentUser.getIdUser()));
                timetableListView.setItems(timetableList);
                selectedTimetable = timetableList.get(timetableList.size() - 1);
                timetableListView.getSelectionModel().select(timetableList.size() - 1);

            } catch (NotLoggedInException ex) {
                handleNotLoggedException();
            } catch (NotModifiedException ex) {
                handleNotModifiedException();
            }
        });
    }

    @FXML
    private void deleteTimetable() {
        if (timetableList.size() != 0 && selectedTimetable != null) {
            try {

                timetableService.removeTimetable(selectedTimetable.getIdTimetable());                
                timetableList = FXCollections.observableArrayList(timetableService.getTimetablesByUserId(currentUser.getIdUser()));
                timetableListView.setItems(timetableList);
                if (!timetableList.isEmpty()) {   
                    selectedTimetable = timetableList.get(0);
                    timetableListView.getSelectionModel().select(0);
                } else {                      
                    clearGridView();
                }

            } catch (NotLoggedInException ex) {
                handleNotLoggedException();
            } catch (NotModifiedException ex) {
                handleNotModifiedException();
            }
        } else {

            Alert alert = new Alert(AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/icon/calendar.png").toExternalForm()));
            alert.setHeaderText("No timetable to delete!");
            alert.showAndWait();

        }
    }

    @FXML
    private void logoutButtonClicked() {
        sceneChanger.goToLogin();
    }

    private void changeViewedTimetable(Timetable timetable) {

        try {
            eventList = eventService.getEventByTimetableId(timetable.getIdTimetable());
            System.out.println(eventList);

        } catch (NotLoggedInException ex) {
            handleNotLoggedException();
        }

        if (timetableList.size() > 0) {
            System.out.println("" + timetable.getName());

            for (Node n : gridPane.getChildren()) {//           
                if (n instanceof AnchorPane) {
                    AnchorPane pane = (AnchorPane) n;
                    ((AnchorPane) n).getChildren().clear();
                    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    Optional<Event> optionalEvent = eventList.stream()
                            .filter(e -> (e.getDay() == GridPane.getColumnIndex(n) && e.getStart() == GridPane.getRowIndex(n)))
                            .findAny();
                    if (optionalEvent.isPresent()) {

                        Label name = new Label(optionalEvent.get().getName());
                        Label lecturer = new Label(optionalEvent.get().getLecturer());
                        VBox p = new VBox();
                        p.setPadding(new Insets(5, 5, 5, 5));
                        p.setSpacing(5);
                        p.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                        TitledPane t1 = new TitledPane("Name", name);
                        t1.setCollapsible(false);
                        TitledPane t2 = new TitledPane("Lecturer", lecturer);
                        t2.setCollapsible(false);
                        Button b = new Button("Delete");
                        b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        p.getChildren().add(t1);
                        p.getChildren().add(t2);
                        p.getChildren().add(b);

                        b.setOnAction((ActionEvent event) -> {
                            System.out.println(GridPane.getColumnIndex(n) + " " + GridPane.getRowIndex(n));
                            //pane.getChildren().remove(b);
                        });

                        p.setMaxSize(pane.getWidth(), pane.getHeight());
                        AnchorPane.setBottomAnchor(p, 0.0);
                        AnchorPane.setTopAnchor(p, 0.0);
                        AnchorPane.setLeftAnchor(p, 0.0);
                        AnchorPane.setRightAnchor(p, 0.0);
                        pane.getChildren().add(p);
                    } 
                        Button b = new Button("Add");
                        b.setOnAction((ActionEvent event) -> {
                            System.out.println(GridPane.getColumnIndex(n) + " " + GridPane.getRowIndex(n));
                            pane.getChildren().remove(b);
                        });
                        AnchorPane.setBottomAnchor(b, 0.0);
                        AnchorPane.setTopAnchor(b, 0.0);
                        AnchorPane.setLeftAnchor(b, 0.0);
                        AnchorPane.setRightAnchor(b, 0.0);
                        pane.getChildren().add(b);                    
                }
            }

        }
    }

    private void clearGridView(){
        for (Node n : gridPane.getChildren()) {//           
                if (n instanceof AnchorPane) {
                    AnchorPane pane = (AnchorPane) n;
                    ((AnchorPane) n).getChildren().clear();
                    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);                  
                }
            }
    }
    
    private void handleNotLoggedException() {

        Alert alert = new Alert(AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/icon/calendar.png").toExternalForm()));
        alert.setHeaderText("You are not logged in, or your token expired!");
        alert.showAndWait();
        sceneChanger.goToLogin();

    }

    private void handleNotModifiedException() {
        Alert alert = new Alert(AlertType.WARNING);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/icon/calendar.png").toExternalForm()));
        alert.setHeaderText("Timetable was not deleted, server error!");
        alert.showAndWait();
    }

}
