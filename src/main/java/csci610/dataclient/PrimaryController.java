package csci610.dataclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import java.util.*;
import java.net.*;
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
    
    
    @FXML
    private void OpenDashboard(ActionEvent event) {

        //1) check for empty string
        if (!usernameInput.getText().equalsIgnoreCase("")) {//if the login field is empty, then throw an alert

            //2) create the connection
            try{
               s = new Socket("localhost", 5000);//create the client socket
               in = new BufferedReader(new InputStreamReader(s.getInputStream()));
               out = new PrintWriter(s.getOutputStream(), true);
               
               userName = usernameInput.getText();
               password = passwordInput.getText();
               
               out.println(userName);
               out.println(password);
               
               
               
               
               
            } catch(SocketException e){
                System.out.println("error creating client socket");
            } catch(IOException e){
                System.out.println("error, IOException, client");
            }
            
            
        } else {//if the login field is empty

            throwBadUsername();//throw an alert if the username field is empty

        }

    }

    private void throwBadUsername() {
        Alert a = new Alert(Alert.AlertType.ERROR);//generate an alert if the user does not type a username / invalid username
        a.setHeaderText("Error Logging In");//set the error text
        a.setContentText("Error, please enter a valid username");//prompt the user
        a.showAndWait();//wait until the user closes the alert
    }
}
