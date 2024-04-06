package application.jf;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private Label err;

    @FXML
    public void login(ActionEvent e) throws IOException {
        List<UserData> users = Controller.loadUserData();
        if (!Controller.containsUser(users, username.getText())) {
            err.setText("Incorrect Username or Password");
        } else {
            UserData user = Controller.findByUsername(users, username.getText());
            if (!user.getPassword().equals(password.getText())) {
                err.setText("Incorrect Username or Password");
            } else {
                LocalDate today = LocalDate.now();
                if (!today.equals(user.lastLogin)) {
                    user.setCurrentCals(0.0);
                    user.setCurrentProtein(0.0);
                }
                user.setLastLogin(today);
                Controller.saveUserData(users);
                Controller.UN = username.getText();
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

    @FXML
    public void createAccount(ActionEvent e) throws IOException {
        root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("JFApp0.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
