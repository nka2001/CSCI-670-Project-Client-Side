module csci610.dataclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens csci610.dataclient to javafx.fxml;
    exports csci610.dataclient;
}
