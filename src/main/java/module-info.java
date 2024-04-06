module application.jf {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens application.jf to javafx.fxml;
    exports application.jf;
}