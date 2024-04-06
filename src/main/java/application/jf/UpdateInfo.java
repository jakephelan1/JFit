package application.jf;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateInfo implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TextField name;
    @FXML
    private TextField height;
    @FXML
    private TextField age;
    @FXML
    private TextField weight;
    @FXML
    private TextField goalWeight;
    @FXML
    private TextField workoutsPerWeek;
    @FXML
    private ChoiceBox<String> activity;
    @FXML
    private CheckBox male;
    @FXML
    private CheckBox female;

    @FXML
    private Label errName;
    @FXML
    private Label errAge;
    @FXML
    private Label errMorF;
    @FXML
    private Label errWeight;
    @FXML
    private Label errGoalWeight ;
    @FXML
    private Label errTimesPerWeek;
    @FXML
    private Label errActivity;
    @FXML
    private Label errHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<UserData> users = Controller.loadUserData();
        UserData user = Controller.findByUsername(users, Controller.UN);
        name.setText(user.getName());
        height.setText(Double.toString(Math.round(user.getHeight() * 0.393701)));
        age.setText(Integer.toString(user.getAge()));
        weight.setText(Double.toString(Math.round(user.getWeight() * 2.20462)));
        goalWeight.setText(Double.toString(Math.round(user.getGoalWeight() * 2.20462)));
        workoutsPerWeek.setText(Integer.toString(user.getWorkoutsPerWeek()));
        List<String> choices = new ArrayList<>();
        choices.add("Sedentary (little or no exercise)");
        choices.add("Lightly active (exercise 1-3 days/week)");
        choices.add("Moderately active (exercise 3-5 days/week");
        choices.add("Active (exercise 6-7 days/week)");
        choices.add("Very active (hard exercise 6-7 days/week)");
        ObservableList<String> obList = FXCollections.observableList(choices);
        activity.setItems(obList);
        double AMRVal = user.getAMRval();
        if (AMRVal == 1.2) {
            activity.setValue("Sedentary (little or no exercise)");
        } else if (AMRVal == 1.375) {
            activity.setValue("Lightly active (exercise 1-3 days/week)");
        } else if (AMRVal == 1.55) {
            activity.setValue("Moderately active (exercise 3-5 days/week");
        } else if (AMRVal == 1.725) {
            activity.setValue("Active (exercise 6-7 days/week)");
        } else {
            activity.setValue("Very active (hard exercise 6-7 days/week)");
        }
        if (user.getGender().equals("male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }

    @FXML
    public void back(ActionEvent e) throws IOException {
        List<UserData> users = Controller.loadUserData();
        UserData user = Controller.findByUsername(users, Controller.UN);
        root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("JFApp" + user.getWorkoutsPerWeek() + ".fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void save (ActionEvent e) throws IOException {
        String Name = null;
        if (name.getText().isEmpty()) {
            errName.setText("Invalid name");
        } else {
            Name = name.getText();
            errName.setText("");
        }

        double weightInKG = 0.0;
        try {
            weightInKG = Double.parseDouble(weight.getText()) * 0.453592;
            errWeight.setText("");
        } catch (NumberFormatException r) {
            errWeight.setText("Please enter a valid weight");
        }

        double goalWeightInKG = 0.0;
        try {
            goalWeightInKG = Double.parseDouble(goalWeight.getText()) * 0.453592;
            errGoalWeight.setText("");
        } catch (NumberFormatException n) {
            errGoalWeight.setText("Please enter a valid weight");
        }

        int liftsPerWeek = 0;
        try {
            int times = Integer.parseInt(workoutsPerWeek.getText());
            if (times < 3 || times > 7 && workoutsPerWeek.getText() != null) {
                if (times < 3) {
                    errTimesPerWeek.setText("Must be a minimum of 3");
                } else {
                    errTimesPerWeek.setText("Must be a maximum of 7");
                }
            } else {
                liftsPerWeek = times;
                errTimesPerWeek.setText("");
            }
        } catch (NumberFormatException l) {
            errTimesPerWeek.setText("Please enter a valid number between 3 and 7");
        }

        String gender = null;
        if (male.isSelected() && female.isSelected()) {
            errMorF.setText("Please select only male or female");
        } else if (male.isSelected()) {
            gender = "male";
            errMorF.setText("");
        } else if (!male.isSelected() && !female.isSelected()) {
            errMorF.setText("Please select a gender");
        } else {
            gender = "female";
            errMorF.setText("");
        }

        double AMRval = 0.0;
        String value = activity.getValue();
        if (value == null) {
            errActivity.setText("Please pick an activity level");
        } else {
            int index = value.indexOf("(");
            value = value.substring(0, index - 1);
            switch (value) {
                case "Sedentary" -> AMRval = 1.2;
                case "Lightly active" -> AMRval = 1.375;
                case "Moderately active" -> AMRval = 1.55;
                case "Active" -> AMRval = 1.725;
                case "Very active" -> AMRval = 1.9;
            }
        }

        double heightcm = 0.0;
        try {
            heightcm = Double.parseDouble(height.getText()) * 2.54;
            errHeight.setText("");
        } catch (NumberFormatException z) {
            errHeight.setText("Please enter a valid height in inches");
        }

        int Age = 0;
        try {
            Age = Integer.parseInt(age.getText());
            errAge.setText("");
        } catch (NumberFormatException z) {
            errAge.setText("Please enter your age");
        } if (Name != null && weightInKG != 0.0 && goalWeightInKG != 0.0
                && liftsPerWeek != 0 && gender != null && AMRval != 0.0 && heightcm != 0.0 &&
                Age != 0) {
            List<UserData> users = Controller.loadUserData();
            UserData user = Controller.findByUsername(users, Controller.UN);
            user.setName(Name);
            user.setWeight(weightInKG);
            user.setGoalWeight(goalWeightInKG);
            user.setWorkoutsPerWeek(liftsPerWeek);
            user.setGender(gender);
            user.setAMRval(AMRval);
            user.setHeight(heightcm);
            user.setAge(Age);
            Controller.saveUserData(users);
            root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("JFApp" + user.getWorkoutsPerWeek() + ".fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
    }
}
