package csci610.dataclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import java.util.*;
import java.net.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {

    private Socket s;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;

    private String userName; //used for logging in
    private String password; //used for logging in

    private BufferedReader in;
    private PrintWriter out;

    private String receivedCode;//used to get the recevied code from the server

    private volatile boolean listening = true; //used to kill the thread in the login page if the login is successful

    @FXML
    public void initialize() {
        ConnectionManager.getInstance().connect("localhost", 5000);
        in = ConnectionManager.getInstance().getInputStream();
        out = ConnectionManager.getInstance().getOutputStream();
    }

    @FXML
    private void OpenDashboard(ActionEvent event) {

        //1) check for empty string
        if (!usernameInput.getText().equalsIgnoreCase("")) { //if the login field is empty, then throw an alert
            //2) create the connection

            userName = usernameInput.getText();//get the username
            password = passwordInput.getText();//get the password
            out.println(userName);//send the username to the server
            out.println(password);//send the password to the server
            Thread receiverThread = new Thread(() -> {

                try {
                    while (listening && (receivedCode = in.readLine()) != null) {
                        System.out.println(receivedCode);
                        /**
                         * codes that are generated by the server, switch will
                         * match the code thrown
                         */
                        switch (receivedCode) {

                            case "8"://good login?
                                listening = false;
                                Platform.runLater(() -> {
                                    try {
                                        App.setRoot("Dashboard");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }

                                });
                                break;
                            case "1"://login with admin rights?

                            case "10"://case 10 will be used for bad username/password or is disabled
                                Platform.runLater(() -> {
                                    createAlert("Error Logging In", "Error, Incorrect Username, Password, or account is disabled\nPlease contact your administrator");
                                });

                            case "20"://case 20 will be used for password change

                        }

                    }
                } catch (IOException e) {
                    System.out.println("Error in Client, Receiver Thread");
                    e.printStackTrace();
                }

            });
            receiverThread.start();

        } else {//if the login field is empty

            throwBadUsername();//throw an alert if the username field is empty

        }

    }

    private void createAlert(String header, String content) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();

    }

    private void throwBadUsername() {
        Alert a = new Alert(Alert.AlertType.ERROR);//generate an alert if the user does not type a username / invalid username
        a.setHeaderText("Error Logging In");//set the error text
        a.setContentText("Error, please enter a valid username");//prompt the user
        a.showAndWait();//wait until the user closes the alert
    }
}
