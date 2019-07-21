/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ryanhoffman
 */
public class MacroDiaryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button viewGraph;
    @FXML
    private Button clearButton;
    @FXML
    private Button backButton;
    @FXML 
    private Button viewDiary;
    @FXML 
    public TextArea areaDiary;
    @FXML 
    private DatePicker startDate;
    @FXML 
    private String loginID;
    private Connectivity dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(String loginID) {
        this.loginID = loginID;
    }
    
    public void submitClicked(ActionEvent event) throws Exception {
        
         Connection connect = DriverManager.getConnection( 
         "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9293398" , 
         "sql9293398" , 
         "5UPXYpEPHW"
      );
          String stDate = null;
          String[] infoArray = new String[25];

        //build an entry object out of the controllers
        Connectivity connection = new Connectivity();
        if (fieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fields empty!");
            alert.setHeaderText("Fields can't be empty!");

            alert.showAndWait();
        } else {
            int UserID = connection.getUserID(this.loginID);
            System.out.println(UserID);
            System.out.println(loginID);
            LocalDate sDate = this.startDate.getValue();
            stDate = sDate.toString();
            System.out.println(stDate);
            
            
            
             //query selects all FoodLog data
             String query = "SELECT foodType, carbohydrates, protein, fat FROM FoodLog WHERE UserID = '" + UserID + "' AND Date = "
                     +"'" + stDate + "' AND foodType IS NOT NULL AND carbohydrates IS NOT NULL AND protein IS NOT NULL AND fat IS NOT NULL";
             try{
                 Statement st = connect.createStatement();
                 ResultSet r = st.executeQuery(query);
                 
                 //get the information out of the database
                 while(r.next()){
                     for(int i = 1;i<=25;i++){
                         infoArray[i-1]= r.getString(i);
                     }}
             }catch(SQLException e){
                 System.out.println(e);
             }
             }
             for(int counter = 0 ; counter < infoArray.length; counter++)
             {
             for(String a : infoArray){
                     areaDiary.appendText(a + " \t");
                     counter++;
                     if(counter>=4){
                     areaDiary.appendText("\n");
                     }
//                     if(a == null){
//                     areaDiary.appendText("");
//                     }
                 }
    }}
    
        //returns to login
    public void returnBack(ActionEvent event) throws IOException {
        Parent Parent = FXMLLoader.load(getClass().getResource("MacroLog.fxml"));
        Scene Scene = new Scene(Parent);

        //this is to ge the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
    //viewgraph
     public void viewGraph(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MacroGraph.fxml"));
        Parent macroDiaryParent = loader.load();
        
        Scene newMacroControllerScene = new Scene(macroDiaryParent);

        MacroGraphController controller = loader.getController();
        controller.initData(this.loginID);

        //this is to ge the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newMacroControllerScene);
        window.show();
    }
    
    public void clearButtonClicked() {
        //go through all the fields and clear them
        this.areaDiary.clear();
        this.startDate.setValue(null);

    }

       //checks if any of the fields are emptyCat
    public boolean fieldsEmpty() {
        boolean isEmpty = false;
        //check if any field is empty
        if (this.startDate.getValue() == null) {
            isEmpty = true;
        }
       
        
        return isEmpty;
    }

    

}
