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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
public class MacroGraphController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button viewChart;
    @FXML
    private Button clearButton;
    @FXML
    private Button backButton;
    @FXML 
    public PieChart macroChart;
    @FXML 
    private DatePicker chartDate;
    @FXML 
    private String loginID;
    private Connectivity dbConnection;
    private int proteinSum;

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
          String chDate = null;
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
            LocalDate sDate = this.chartDate.getValue();
            chDate = sDate.toString();
            System.out.println(chDate);
            
            ObservableList<PieChart.Data> macroChart;
           
            //retrieve sum of the columns from the Database
            String proteinQuery = "SELECT SUM(protein) AS proteinSum FROM FoodLog WHERE UserID = '" + UserID + "' AND Date = '" + chDate + "'";
            String carbQuery = "SELECT SUM(carbohydrates) AS carbSum FROM FoodLog WHERE UserID = '" + UserID + "' AND Date = '" + chDate + "'";
            String fatQuery = "SELECT SUM(fat) AS fatSum FROM FoodLog WHERE UserID = '" + UserID + "' AND Date = '" + chDate + "'";
            //get proteinquery
            try{
                 Statement st = connect.createStatement();
                 ResultSet r = st.executeQuery(proteinQuery);
                 ResultSetMetaData rsmd = r.getMetaData();
                 int columnsNumber = rsmd.getColumnCount();
                 while (r.next()) {
                  for (int i = 1; i <= columnsNumber; i++) {
                  int columnValue = r.getInt(i);
                  System.out.print(columnValue + " " + rsmd.getColumnName(i));
       }
                 }
                 
       System.out.println("");
                 //get the information out of the database
             }catch(SQLException e){
                 System.out.println(e);
             }
            //get carbquery
             try{
                 Statement st = connect.createStatement();
                 ResultSet r = st.executeQuery(carbQuery);
                 ResultSetMetaData rsmd = r.getMetaData();
                 int columnsNumber = rsmd.getColumnCount();
                 while (r.next()) {
                  for (int i = 1; i <= columnsNumber; i++) {
                  int columnValue = r.getInt(i);
                  System.out.print(columnValue + " " + rsmd.getColumnName(i));
       }
                 }
                 
       System.out.println("");
                 //get the information out of the database
             }catch(SQLException e){
                 System.out.println(e);
             }
             //get fatquery
             try{
                 Statement st = connect.createStatement();
                 ResultSet r = st.executeQuery(fatQuery);
                 ResultSetMetaData rsmd = r.getMetaData();
                 int columnsNumber = rsmd.getColumnCount();
                 while (r.next()) {
                  for (int i = 1; i <= columnsNumber; i++) {
                  int columnValue = r.getInt(i);
                  System.out.print(columnValue + " " + rsmd.getColumnName(i));
       }
                 }
                 
       System.out.println("");
                 //get the information out of the database
             }catch(SQLException e){
                 System.out.println(e);
             }
        }
       }
             
        //returns to login
    public void returnBack(ActionEvent event) throws IOException {
        Parent Parent = FXMLLoader.load(getClass().getResource("MacroLog.fxml"));
        Scene Scene = new Scene(Parent);

        //this is to ge the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
    
    public void clearButtonClicked() {
        //go through all the fields and clear them
        //this.macroChart.;
        this.chartDate.setValue(null);

    }

       //checks if any of the fields are emptyCat
    public boolean fieldsEmpty() {
        boolean isEmpty = false;
        //check if any field is empty
        if (this.chartDate.getValue() == null) {
            isEmpty = true;
        }
       
        
        return isEmpty;
    }

    
}
