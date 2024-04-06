package application.jf;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.FileReader;
import java.util.Scanner;
import javafx.stage.Stage;

public class DayCont implements Initializable {

    UserData user = Controller.findByUsername(Controller.loadUserData(), Controller.UN);

    @FXML
    private TableView tDay;
    @FXML
    private TableColumn<List<String>, String> exercises;
    @FXML
    private TableColumn<List<String>, String> sets;
    @FXML
    private TableColumn<List<String>, String> reps;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exercises.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst()));
        sets.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1)));
        reps.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2)));
        try {
            FileReader reader = new FileReader("src/main/resources/"
                    + "CSVs/" + user.getWorkoutsPerWeek() + "day" + ButtonsCont.day + ".csv");
            Scanner scanner = new Scanner(reader);
            List<List<String>> workoutList = new ArrayList<List<String>>();
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] row = str.split(",", -1);
                workoutList.add(List.of(row));
            }
            ObservableList<List<String>> obList = FXCollections.observableList(workoutList);
            tDay.setItems(obList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("JFApp" + user.getWorkoutsPerWeek() + ".fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

