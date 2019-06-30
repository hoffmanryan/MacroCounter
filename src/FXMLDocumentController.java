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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ryanhoffman
 */
public class FXMLDocumentController implements Initializable {
      //create the controls that will be used in the login screen
    @FXML
    private JFXTextField userNameInput;
    @FXML
    private JFXPasswordField passwordInput;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton passwordRecovery;
    @FXML
    private JFXButton newUser;
        
    @FXML
     //this is the method that will handle changing the scene
    public void newUserScreen(ActionEvent event) throws IOException {
        Parent newUserParent = FXMLLoader.load(getClass().getResource("NewUser1.fxml"));
        Scene newUserScene = new Scene(newUserParent);

        //this is to ge the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newUserScene);
        window.show();
    }
    
      //ACTION EVENT METHODS
    //if cancelled is clicked, then the password and username fields are cleared
    public void cancelClicked() {
        userNameInput.clear();
        passwordInput.clear();
        
    }

     public void forgotPassword(ActionEvent event) throws IOException {
        Parent newpasswordParent = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
        Scene forgotPasswordScene = new Scene(newpasswordParent);

        //this is to get the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(forgotPasswordScene);
        window.show();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
