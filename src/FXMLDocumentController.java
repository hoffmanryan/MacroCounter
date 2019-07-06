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
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;
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
    private Text feedbackTextLabel;
        
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
    
    public void loginClicked() {
        //read the values in the password and username fields
        String username = userNameInput.getText();
        String password = passwordInput.getText();

        if (username.length() == 0 || password.length() == 0) {
            feedbackTextLabel.setText("One or more required fields is empty.");
            feedbackTextLabel.setFill(Paint.valueOf("red"));
        } else {
            this.feedbackTextLabel.setText("");

        }

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

    public void UserScreen(ActionEvent event) throws IOException {
        //create an instance of the connectivity class
        Connectivity connection = new Connectivity();
        Connection dbConnection;
        //Check if User name exists. if it does, validate password
        if (connection.valueExists(this.userNameInput.getText(), "userName")) {
          
            if (connection.verifyPassword(this.userNameInput.getText(), this.passwordInput.getText())) {
                if (connection.isUser(this.userNameInput.getText())) {

                    //This is where we change the scene and pass the newUser object to it
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("MacroLog.fxml"));
                    //this will give us access and the ability to pass the object
                    Parent macroLogParent = loader.load();

                    Scene macroLogScene = new Scene(macroLogParent);

                    MacroLogController controller = loader.getController();
                    controller.initData(this.userNameInput.getText());

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(macroLogScene);
                    window.show();


                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
                    //this will give us access and the ability to pass the object
                    Parent loginParent = loader.load();

                    Scene mainMenuDashboard = new Scene(loginParent);

                    FXMLDocumentController controller = loader.getController();
                  //  controller.initData(this.userNameInput.getText());

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(mainMenuDashboard);
                    window.show();
                }

            } else {
                this.userNameInput.clear();
                this.passwordInput.clear();
                connection.alertInvalidPassword();
            }
            }

         else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setContentText("This userName does not exist in the database");
                alert.showAndWait();
}     
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         userNameInput.setFocusColor(Paint.valueOf("#5892ef"));
        passwordInput.setFocusColor(Paint.valueOf("#5892ef"));
        feedbackTextLabel.setText("");

    }    
    
}
