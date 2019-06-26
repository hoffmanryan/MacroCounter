/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class NewUser1Controller implements Initializable {

     private ArrayList<JFXTextField> fieldGroup= new ArrayList<JFXTextField>();

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
    private JFXTextField emailConfirmInput;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXButton clearButton;
    @FXML
    private JFXButton backToLogin;
    
    //feedback fields
    private ArrayList<Text> feedbackGroup = new ArrayList<>();
    @FXML private Text firstNameFeedback;
    @FXML private Text lastNameFeedback;
    @FXML private Text emailFeedback;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //add all text input fields to the arraylist
        this.fieldGroup.add(firstName);
        this.fieldGroup.add(lastName);
        this.fieldGroup.add(email);
        this.fieldGroup.add(emailConfirmInput);
        
        
        //add all feedback labels to the arraylist
        this.feedbackGroup.add(firstNameFeedback);
        this.feedbackGroup.add(lastNameFeedback);
        this.feedbackGroup.add(emailFeedback);
       
        
        //Iterate through the feedback group and set their text values to ""
        for(Text val: this.feedbackGroup){
            //set the fields to be empty
            val.setText("");
            //set the field colors to red
            val.setFill(Paint.valueOf("red"));
        }
        //iterate through the text input fields and set their focus color
        for(JFXTextField val: this.fieldGroup){
            val.setFocusColor(Paint.valueOf("#5892ef"));
        }
    }
    
    //this function is called by all the text input fields when the user clicks
    //on one.  It then determines whether the opacity needs to be changed or not
    //by calling the changeOpacity() method
    public void fieldsSelected(){
        //check which field is on focus
        //we have to check the first name, last name, email, employ ID, dept ID fields
        for(JFXTextField field: this.fieldGroup){
            if(field.isFocused()){
                changeOpacity(field.getText().length(),field);
            }
        }
    }
    //changes opacity for the JFXTextfields
    public void changeOpacity(int len,JFXTextField field){
        if(len !=0){
            field.setStyle("-fx-opacity: 1;");
        }
        else{
            field.setStyle("-fx-opacity:.50;");
        }
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
    
       public void inputFieldChanged(){
        for(JFXTextField input: fieldGroup){
            if(input.isFocused()){
                changeFieldOpacity(input.getText().length(),input);
            }
        }
    }
    
    //changes opacity for the JFXTextfields
    public void changeFieldOpacity(int len,JFXTextField field){       
        if(field.getText().equals("")){
            field.setStyle("-fx-opacity:.50;");            
        }       
        else{
            field.setStyle("-fx-opacity:1;");
        }
    }
    
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
        emailConfirmInput.clear();
      
        
    }

    public void validateAndContinue(ActionEvent event)throws IOException{
        boolean validFields = true;
        //we are going to use the DataValidator class
        InputValidator dataValidator = new InputValidator();
        //validate the names
        dataValidator.validateName(this.firstName.getText(),this.firstNameFeedback);
        dataValidator.validateName(this.lastName.getText(), this.lastNameFeedback);
        dataValidator.validateEmails(this.email.getText(), this.emailConfirmInput.getText(), this.emailFeedback);
      
        
        for(Text label:this.feedbackGroup){
            if(label.getText().isEmpty()){
                continue;
            }else{
                validFields = false;
                break;
            }
        }
        //if the last iteration set the flag to true, we know all fields are valid
        if(validFields){
            
            /*
            Here we need to check that the email the user entered deosnt' already
            exist in the database.
            */
            this.databaseConnection = new Connectivity();
            if(this.databaseConnection.valueExists(this.email.getText(), "Email")){
               /*
                Email was found in the database, so set feedback accordingly
                */
               this.emailFeedback.setText("Email already exists");
            }else{
            //now that all the fields are valid, we need to save all the values
            //into the newUser object instance created
            //create a new instance of the NewUser class
            newUser = new NewUser(this.firstName.getText(),
            this.lastName.getText(),this.email.getText());
            
            
            //This is where we change the scene and pass the newUser objec to it
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewUser2.fxml"));
            //this will give us access and the ability to pass the object
            Parent newUserScene2Parent = loader.load();
            
            Scene newUserScene2 = new Scene(newUserScene2Parent);
            
            NewUser2Controller controller = loader.getController();
            controller.initData(newUser);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newUserScene2);
            window.show();
            }
            
        }
    }
}
    

