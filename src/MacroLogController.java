/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class MacroLogController implements Initializable {

    @FXML
    private Button addEntry;
    @FXML
    private Button clearButton;
    @FXML
    private Button returnToLoginButton;
    @FXML 
    private TextField foodItem;
    @FXML 
    private TextField foodWeigh;
    @FXML 
    private TextField foodProt;
    @FXML 
    private TextField foodCarbs;
    @FXML 
    private TextField foodFat;
    @FXML 
    private DatePicker dateEntry;
    private String loginID = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    //API key rENLkGf2q0ZzjSIoBq81gMpetoTQK29sOvz04k8d
    //example 
     public void initData(String loginID) {
        this.loginID = loginID;
    }
     
     
     public void submitClicked(ActionEvent event) throws Exception {
          String Date = null;

        //build an entry object out of the controllers
        Connectivity connection = new Connectivity();
        if (fieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fields empty!");
            alert.setHeaderText("Fields can't be empty!");

            alert.showAndWait();
        } else {
            int UserID = connection.getUserID(this.loginID);
            LocalDate lDate = this.dateEntry.getValue();
            Date = lDate.toString();
            System.out.println(Date);
            String foodType = this.foodItem.getText();
            String foodWeight = this.foodWeigh.getText();
            String carbohydrates = this.foodCarbs.getText();
            String protein = this.foodProt.getText();
            String fat = this.foodFat.getText();

            if (connection.addFoodToDB(UserID, Date,
                    foodType, foodWeight, carbohydrates, protein, fat)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Food log Successful");
                alert.setHeaderText("FOODLOG Submitted");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("FOODLOG not submitted");

                alert.showAndWait();
            }
                 Parent Parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                 Scene Scene = new Scene(Parent);

                 //this is to get the stage information
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(Scene);
                    window.show();
        }

    }
     
      //checks if any of the fields are emptyCat
    public boolean fieldsEmpty() {
        boolean isEmpty = false;
        //check if any field is empty

        //now check the textFields
        if (this.foodItem.getText().isEmpty()) {
            isEmpty = true;
        }
        //now check the textFields
        if (this.foodWeigh.getText().isEmpty()) {
            isEmpty = true;
        }//now check the textFields
        if (this.foodProt.getText().isEmpty()) {
            isEmpty = true;
        }//now check the textFields
        if (this.foodCarbs.getText().isEmpty()) {
            isEmpty = true;
        }//now check the textFields
        if (this.foodFat.getText().isEmpty()) {
            isEmpty = true;
        }//now check the textFields
        if (this.dateEntry.getValue() == null) {
            isEmpty = true;
        }

        return isEmpty;
    }
     //returns to login
    public void returnToLogin(ActionEvent event) throws IOException {
        Parent Parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene Scene = new Scene(Parent);

        //this is to ge the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
    
    public void clearButtonClicked() {
        //go through all the fields and clear them
        //clear the textarea
        this.foodItem.clear();
        this.foodWeigh.clear();
        this.foodProt.clear();
        this.foodCarbs.clear();
        this.dateEntry.setValue(null);

    }

   
}
