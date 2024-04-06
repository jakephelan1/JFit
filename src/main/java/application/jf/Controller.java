package application.jf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    protected static String UN;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
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
    @FXML
    private Label errUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> choices = new ArrayList<>();
        choices.add("Sedentary (little or no exercise)");
        choices.add("Lightly active (exercise 1-3 days/week)");
        choices.add("Moderately active (exercise 3-5 days/week");
        choices.add("Active (exercise 6-7 days/week)");
        choices.add("Very active (hard exercise 6-7 days/week)");
        ObservableList<String> obList = FXCollections.observableList(choices);
        activity.setItems(obList);
    }

    public void cont(ActionEvent e) throws Exception {

        String Name = null;
        if (name.getText().isEmpty()) {
            errName.setText("Please enter your name");
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
        }

        String tempUN = username.getText();
        if (tempUN == null) {
            errUser.setText("Please enter a username");
        } else if (tempUN.length() > 16) {
            errUser.setText("Username must be at most 16 characters");
        } else if (tempUN.length() < 3) {
            errUser.setText("Username must be at least 3 characters");
        } else if (containsUser(loadUserData(), tempUN)) {
            errUser.setText("Username is taken");
        } else {
            errUser.setText("");
            UN = tempUN;
        }

        String PSWD = password.getText();

        if (UN != null && PSWD != null && Name != null && weightInKG != 0.0 && goalWeightInKG != 0.0
                && liftsPerWeek != 0 && gender != null && AMRval != 0.0 && heightcm != 0.0 &&
                Age != 0) {
            List<UserData> userList = loadUserData();
            UserData newUser = new UserData(UN, PSWD, Name, heightcm, Age, gender, weightInKG,
                    goalWeightInKG, liftsPerWeek, AMRval, LocalDate.now(), 0.0, 0.0);
            userList.add(newUser);
            saveUserData(userList);
            root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("JFApp" + liftsPerWeek + ".fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
    }

    protected static List<UserData> loadUserData() {
        List<UserData> userList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources"
                + "/userdata.ser"))) {
            userList = (List<UserData>) ois.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return userList;
    }

    protected static void saveUserData(List<UserData> userList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources"
                + "/userdata.ser"))) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static UserData findByUsername(List<UserData> userList, String username) {
        for (UserData user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    protected static boolean containsUser(List<UserData> userList, String username) {
        boolean result = false;
        for (UserData user : userList) {
            if (user.getUsername().equals(username)) {
                result = true;
                break;
            }
        } return result;
    }
}