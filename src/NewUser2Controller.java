/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class NewUser2Controller implements Initializable {

    private NewUser newUser;
    @FXML private JFXTextField proteinInput;
    @FXML private JFXTextField carbInput;
    @FXML private JFXTextField fatInput;
    private ArrayList<JFXTextField> macroGroup = new ArrayList<JFXTextField>();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.proteinInput.clear();
        this.carbInput.clear();
        this.fatInput.clear();

        this.macroGroup.add(proteinInput);
        this.macroGroup.add(carbInput);
        this.macroGroup.add(fatInput);
    }    
       /*
    Function: initData()
    Description: grabs the data passed by the previous scene and saves it
    Input:NewUser obj
    Return: none
    */
    public void initData(NewUser newUser){
        this.newUser = newUser;
        
    }
    
     /*
    Function: backToNewUserScene1()
    Description: returns to the login screen
    Input:None
    Return: none
    */
    public void backToNewUserScene1(ActionEvent event)throws IOException{
        Parent newUserParent = FXMLLoader.load(getClass().getResource("NewUser1.fxml"));
        Scene newUserScene = new Scene(newUserParent);
        
        //this is to ge the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(newUserScene);
        window.show();
    }
    
      public void submitClicked(ActionEvent event)throws IOException{
        
            newUser = new NewUser(this.proteinInput.getText(),
            this.carbInput.getText(),this.fatInput.getText());
            
        
              //This is where we change the scene and pass the newUser objec to it
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewUser3.fxml"));
            //this will give us access and the ability to pass the object
            Parent newUserScene3Parent = loader.load();
            
            Scene newUserScene3 = new Scene(newUserScene3Parent);
            
            NewUser3Controller controller = loader.getController();
            controller.initData(newUser);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newUserScene3);
            window.show();
            }
                   

        
    
    
    public void clearClicked(){
        //clear all the fields
        for(JFXTextField val: macroGroup){
            val.clear();
            val.setStyle("-fx-opacity:.50;");
        }
    }
}

