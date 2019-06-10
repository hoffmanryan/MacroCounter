/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class NewUser1Controller implements Initializable {

     private ArrayList<JFXTextField> fieldGroup= new ArrayList<>();
//new instance of the newUser class
    private NewUser newUser;
    //instance of connectivity to test database value
    private Connectivity databaseConnection;
     @FXML
    private JFXTextField firstName;
     @FXML
    private JFXTextField lastName;
     @FXML
    private JFXTextField email;
     @FXML
    private JFXTextField userName;
     @FXML 
    private DatePicker dateOfBirth;
     @FXML
    private JFXPasswordField passwordInput;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXButton clearButton;
    @FXML
    private JFXButton backToLogin;
        
    
      //returns to login
    public void returnToLogin(ActionEvent event) throws IOException{
        Parent Parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene Scene = new Scene(Parent);
        
        //this is to ge the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
    
      //ACTION EVENT METHODS
    //if cancelled is clicked, then the password and username fields are cleared
    public void clearClicked() {
        firstName.clear();
        lastName.clear();
        email.clear();
        userName.clear();
        dateOfBirth.getEditor().clear();
        passwordInput.clear();
        
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
