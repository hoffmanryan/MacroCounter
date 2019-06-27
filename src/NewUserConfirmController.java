/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class NewUserConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String message ="";
    private String flag ="";
    @FXML private Text header;
    @FXML private Text feedback;
    @FXML private JFXButton okButton;
    private String loginID = "";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            
        feedback.setText("");
        okButton.setText("OK");
    }    
    
      public void initData(String message,String flag){
        //check the flag
        /*
        if flag is G, that means it was good and the message should be displayed
        in green.  If the flag is E, that means there was an error and the message
        will be displayed in red
        */
        this.feedback.setText(message);
        if(flag.equals("G")){
            //message in green
            this.feedback.setFill(Paint.valueOf("green"));
            this.header.setFill(Paint.valueOf("green"));
            
        }
        else if(flag.equals("E")){
                this.header.setFill(Paint.valueOf("red"));
                this.header.setText("Problem");
                this.feedback.setFill(Paint.valueOf("red"));
   
        }
        
    }
    
      /*
    Function: backToNewUserScene1()
    Description: returns to the login screen
    Input:None
    Return: none
    */
    public void backToNewUserScene1(ActionEvent event)throws IOException{
        Parent newUserParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene newUserScene = new Scene(newUserParent);
        
        //this is to ge the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(newUserScene);
        window.show();
    }
}
