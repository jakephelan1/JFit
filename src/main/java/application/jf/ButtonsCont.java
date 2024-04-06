package application.jf;

import static java.lang.Double.parseDouble;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ButtonsCont implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcome;
    @FXML
    private Label proteinCounter;
    @FXML
    private Label calorieCounter;
    @FXML
    private ProgressBar proteinTracker;
    @FXML
    private ProgressBar calTracker;
    @FXML
    private TextField food;

    protected static int day;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<UserData> userList = Controller.loadUserData();
        UserData user = Controller.findByUsername(userList, Controller.UN);
        welcome.setText("Hello " + user.getName() + "!\nHere is the workout "
                + "program created for you\nand a recommended diet to follow. Click the\nbuttons to "
                + "see what each day consists of.");
        welcome.setFont(Font.font("Impact", 20));
        double AMR = user.getGender().equals("male") ? (66.47 +
                (13.75 * user.getWeight()) + (5.003 * user.getHeight()) - (6.755 *
                user.getAge())) * user.getAMRval() : (655.1 + (9.563 *
                user.getWeight()) + (1.85 * user.getHeight()) - (4.676 * user.getAge())) *
                user.getAMRval();
        if (user.getWeight() > user.getGoalWeight()) {
            double goalCals = Math.round(.85 * AMR);
            calorieCounter.setText(user.getCurrentCals() + "/" + goalCals + " calories");
            calTracker.setProgress(user.getCurrentCals()/goalCals);
        } else if (user.getWeight() == user.getGoalWeight()) {
            double goalCals = Math.round(AMR);
            calorieCounter.setText(user.getCurrentCals() + "/" + goalCals + " calories");
            calTracker.setProgress(user.getCurrentCals()/goalCals);
        } else {
            double goalCals = Math.round(1.15 * AMR);
            calorieCounter.setText(user.getCurrentCals() + "/" + goalCals + " calories");
            calTracker.setProgress(user.getCurrentCals()/goalCals);
        }
        double goalProtein = Math.round(1.2 * (2.20462 * user.getWeight()));
        proteinCounter.setText(user.getCurrentProtein() + "/" + goalProtein + " g protein");
        proteinTracker.setProgress(user.getCurrentProtein()/goalProtein);

    }

    @FXML
    public void toDay1(ActionEvent e) throws IOException {
        day = 1;
        load(e);
    }

    @FXML

    public void toDay2(ActionEvent e) throws IOException {
        day = 2;
        load(e);
    }

    @FXML
    public void toDay3(ActionEvent e) throws IOException {
        day = 3;
        load(e);
    }

    @FXML
    public void toDay4(ActionEvent e) throws IOException {
        day = 4;
        load(e);
    }

    @FXML
    public void toDay5(ActionEvent e) throws IOException {
        day = 5;
        load(e);
    }

    @FXML
    public void toDay6(ActionEvent e) throws IOException {
        day = 6;
        load(e);
    }

    @FXML
    public void toDay7(ActionEvent e) throws IOException {
        day = 7;
        load(e);
    }

    private void load(ActionEvent e) throws IOException {
        root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("table.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updatePref(ActionEvent e) throws IOException {
        root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("update.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void calc(ActionEvent e) throws IOException {
        String APIKey = "miFkJ/MJZb5O4I5kwM5yHA==u01KCzOxCBWkNRin";
        String foodQuery = URLEncoder.encode(food.getText(), StandardCharsets.UTF_8);
        URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query=" + foodQuery);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-Api-Key", APIKey);
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);
        System.out.println(root.toString());
        List<UserData> userSet = Controller.loadUserData();
        UserData user = Controller.findByUsername(userSet, Controller.UN);
        for (JsonNode foodItem : root) {
            user.setCurrentCals(user.getCurrentCals() + foodItem.at("/calories").asDouble());
            user.setCurrentProtein(user.getCurrentProtein() + foodItem.at("/protein_g").asDouble());
        } Controller.saveUserData(userSet);
        initProgress(user.currentCals, user.currentProtein);
    }
    private void initProgress(double cals, double protein) {
        String goalCals = calorieCounter.getText().substring(calorieCounter.getText().
                indexOf("/") + 1, calorieCounter.getText().indexOf(" "));
        calorieCounter.setText(cals + "/" + goalCals + " calories");
        String goalProtein = proteinCounter.getText().substring(proteinCounter.getText().
                indexOf("/") + 1, proteinCounter.getText().indexOf(" "));
        proteinCounter.setText(protein + "/" + goalProtein + " g protein");
        calTracker.setProgress(cals/parseDouble(goalCals));
        proteinTracker.setProgress(protein/parseDouble(goalProtein));
    }
}
