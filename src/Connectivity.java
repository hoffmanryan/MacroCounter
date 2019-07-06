
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Alert;

/**
*  @Course: SDEV 250 ~ Java Programming I
*  @Author Name: Ryan Hoffman
*  @Assignment Name: 
*  @Date: Jun 9, 2019
*  @Subclass Connectivity Description:
*/
//Imports
//Begin Subclass Connectivity
public class Connectivity
 {
     // Enter the Database driver url, username, and passsword here
    private String host = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9293398";
    private String userName = "sql9293398";
    private String password = "5UPXYpEPHW";
    private boolean connectionEstablished = false;
    //arraylist we can use for strings
    ArrayList<String> userNames = new ArrayList<>();

    //Enter the Database name to select from below
    String TableName = "SELECT * FROM userData";
    String Display = "SELECT `PK_userID`,`firstName`, `lastName`,`userName` FROM userData";
    
    //need a Connection object here
    Connection dbConnection;
    //hashmap helps us with organizing all the data
    HashMap map;
    
    
    ResultSet rst = null;      //Row & column data  
    ResultSetMetaData mymeta = null;
    int columnNo;
    
    //arrayList to hold userNames selected by department
    ArrayList<String> departmentUsers;

    public boolean ConnectDB() {
        
        //this method is going to create a connection to the database
        try{
            dbConnection = DriverManager.getConnection(host,userName,password);
            //if we get here, we know the database has been connected
            this.connectionEstablished = true;
        }catch(SQLException e){
            //System.out.println(e);
            this.connectionEstablished = false;
        }
        return this.connectionEstablished;
    }
    //getter methods
    
    //this method returns an arrayList of all usernames in the db
    //before using this, you have to call the populateUserList() method in this class
    public ArrayList<String> getUserNames(){
        return this.userNames;
    }
    //you have to connect to the database before using this method
    public boolean addNewUser(String f,String l,String e,String p,String u, String pr, String ca, String fa){
       /*
        f-> first name
        l-> last name
        e-> email
        p-> password
        u-> userName
        pr -> protein
        ca-> carb
        fa-> fat
        */
        boolean success = false;
        Statement statement ;
        String query = "INSERT INTO `userData` (`firstName`, `lastName`,`userName`,`password`,`email`,`protein`,`carbohydrate`,`fat`) VALUES ('"+f+"','"+l+"','"+u+"','"+p+"','"+e+"','"+pr+"','"+ca+"','"+fa+"');";
                 
        try{
            statement = this.dbConnection.createStatement();
            int executed = statement.executeUpdate(query);
            if(executed ==1){
                success = true;
                
                
                 /**
                 * **********************DISPLAYS USER TABLE********************
                 */
                rst = statement.executeQuery(Display);
                mymeta = rst.getMetaData();
                columnNo = mymeta.getColumnCount();

                try {
                    //Displays Table Names
                    for (int i = 1; i <= columnNo; i++) {
                        System.out.print(mymeta.getColumnName(i) + "\t\t");
                    }
                    System.out.println();

                    //Displays Table data
                    while (rst.next()) {

                        for (int i = 1; i <= columnNo; i++) {
                            System.out.print(rst.getObject(i) + "\t\t\t");
                        }
                        System.out.println();
                    }

                } catch (SQLException sa) {
                    sa.getMessage();
                }

                /**
                 * **********************************************************************
                 */
            }
            statement.close();
            
        }catch(SQLException error){
            //this tested Good
            success = false;
        }finally{
            try{
                this.dbConnection.close();
                this.connectionEstablished = false;
            }catch(SQLException err){
                success = false;
                }
        }
        
        return success;
    }
    
    /*
    find out if an email or a userName already
    exits in the database.  To check for the username, just call the function and 
    pass the parameter userName and second parameter "LoginID".  For email, pass
    the email and then pass the second flag, which is "email"
    */
    public boolean valueExists(String val,String valFlag){
        boolean exists = false;
        //get all the values in the userName colum of the User table
        String query = "";
        String columnName="";
        
        switch(valFlag){
            case "Email": query = "SELECT Email FROM userData";
                          columnName = "Email";
                          break;
                
            case "userName": query = "Select userName FROM userData";
                            columnName = "userName";
                            break;
                            
            default:
                    break;
        }
        
        if(this.ConnectDB()){
            try{
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                while(rst.next()){
                    if(rst.getString(columnName).equals(val)){
                        exists = true;
                    }
                }
                
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
        //iterate through and see if there is a match, set the flag accordingly
        
        return exists;
        
    }


    
    //Verify Password from Database
    public boolean verifyPassword(String userName, String password){
        boolean exists = false;
        //get all the values in the userName colum of the User table
        if(this.ConnectDB()){
            String query = "SELECT password FROM userData WHERE userName = '"
                    + userName + "';";
            try{
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                while(rst.next()){
                    if(rst.getString("password").equals(password)){
                        exists = true;
                    }
                }
                
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
        //iterate through and see if there is a match, set the flag accordingly
        
        return exists;
        
    }
    
    public boolean isUser(String userName){
        boolean is = false;
        //get all the values in the userName colum of the User table
        if(this.ConnectDB()){
            String query = "SELECT userName FROM userData WHERE userName = '"
                    + userName + "';";
            try{
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                while(rst.next()){
                    if(rst.getString("userName").equals(userName)){
                        is = true;
                    }
                }
                
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
        //iterate through and see if there is a match, set the flag accordingly
        
        return is;
        
    }
    
          //Method to update user
    public boolean UpdateUser(String f) {
 
        
        boolean success = false;
        Statement statement;
//        String query = "UPDATE `User` SET `lastName` = '" + f + "' WHERE `PK_userID` = '" + 93 + "'";
        String query = "UPDATE `userData` SET `lastName` = '" + f + "' WHERE `firstName` = '" + f + "'";



        try {
            statement = this.dbConnection.createStatement();
            int executed = statement.executeUpdate(query);
            if (executed == 1) {
                success = true;

                /**
                 * **********************DISPLAYS USER TABLE********************
                 */
                rst = statement.executeQuery(Display);
                mymeta = rst.getMetaData();
                columnNo = mymeta.getColumnCount();

                try {
                    //Displays Table Names
                    for (int i = 1; i <= columnNo; i++) {
                        System.out.print(mymeta.getColumnName(i) + "\t\t");
                    }
                    System.out.println();

                    //Displays Table data
                    while (rst.next()) {

                        for (int i = 1; i <= columnNo; i++) {
                            System.out.print(rst.getObject(i) + "\t\t\t");
                        }
                        System.out.println();
                    }

                } catch (SQLException sa) {
                    sa.getMessage();
                }

                /**
                 * **********************************************************************
                 */
            }

            statement.close();

        } catch (SQLException error) {
            //this tested Good
            success = false;
        } finally {
            try {
                this.dbConnection.close();
                this.connectionEstablished = false;
            } catch (SQLException err) {
                success = false;
            }
        }

        return success;
    }
    
    
    
    //removing user from database
    public boolean RemoveUser(String f) {
        System.out.println(f);
        
        boolean success = false;
        Statement statement;
        String query = "DELETE FROM `userData` WHERE `firstName` = '" + f + "'";
        

        try {
            statement = this.dbConnection.createStatement();
            int executed = statement.executeUpdate(query);
            if (executed == 1) {
                success = true;

                /**
                 * **********************DISPLAYS USER TABLE********************
                 */
                rst = statement.executeQuery(Display);
                mymeta = rst.getMetaData();
                columnNo = mymeta.getColumnCount();

                try {
                    //Displays Table Names
                    for (int i = 1; i <= columnNo; i++) {
                        System.out.print(mymeta.getColumnName(i) + "\t\t");
                    }
                    System.out.println();

                    //Displays Table data
                    while (rst.next()) {

                        for (int i = 1; i <= columnNo; i++) {
                            System.out.print(rst.getObject(i) + "\t\t\t");
                        }
                        System.out.println();
                    }

                } catch (SQLException sa) {
                    sa.getMessage();
                }

                /**
                 * **********************************************************************
                 */
            }

            statement.close();

        } catch (SQLException error) {
            //this tested Good
            success = false;
        } finally {
            try {
                this.dbConnection.close();
                this.connectionEstablished = false;
            } catch (SQLException err) {
                success = false;
            }
        }

        return success;
    }
    
//    public boolean removeUserFromDb(String userName){
//        
//        boolean deleted=false;
//        String deleteQuery = "DELETE FROM `User` WHERE `userName` = '"+userName+"'";
//        
//        
//        if(this.ConnectDB()){
//            //connected to the database
//           try{
//               Statement st=this.dbConnection.createStatement();
//               boolean deletedResult =st.execute(deleteQuery);
//               
//               if(deletedResult){
//                   
//                   deleted = true;
//               }
//               
//           }catch(SQLException e){
//               System.out.println(e);
//           }finally{
//               try{
//                  this.dbConnection.close();
//               }catch(SQLException e2){
//                   System.out.println(e2);
//               }
//           }
//        }
//        return deleted;
//        
//    }
    
    
    //method to add a foodentry to the database
    public boolean addFoodToDB(int UserID, String Date,
      String foodType,String foodWeight,String carbohydrates,String protein,String fat){
         
        boolean success = false;
        Statement statement ;
      
       
       
        if(this.ConnectDB()){
                 
String query = "INSERT INTO `FoodLog` (`UserID`, `Date`,`foodType`,`foodWeight`,`carbohydrates`,`protein`,`fat`) VALUES ('"+UserID+"','"+Date+"','"+foodType+"','"+foodWeight+"','"+carbohydrates+"','"+protein+"','"+fat+"');";
                                
           
           
                    
            try{
                statement = this.dbConnection.createStatement();
            int executed = statement.executeUpdate(query);
            if(executed ==1){
                success = true;
            }
           statement.close();
  
                
            }catch(SQLException e1){
                System.out.println(e1);
            }           
            
        }else{
            System.out.println("Could not connect to the database");
        }  
        return success;
    } 
    
     //Return Password from Database
    public String returnPassword(String email){
        boolean exists = false;
        String userpassword = "";
        //get all the values in the email colum of the User table
        if(this.ConnectDB()){
            String query = "SELECT password FROM userData WHERE email = '"
                    + email + "';";
            try{
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                if(rst.next()){
                    exists = true;
                      userpassword = rst.getString("password");
                    }
                
                
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
       
       //iterate through and see if there is a match, set the flag accordingly  
   return userpassword;
    }
    

        public int getUserID(String login){
        int userID= 0;
       
        if(this.ConnectDB()){
            String query = "SELECT UserID FROM userData WHERE" 
                + " userName = '" + login + "';";
            
            try{
                
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                
                while(rst.next()){
                    
                      userID = rst.getInt("UserID");
                      System.out.println("REsult = " + userID);
                    }
                
                
                
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
       //System.out.println("User ID:" + userID);
        return userID;
    }
        
    public String getDepartmentName(String login){
        String departmentName= "";
        
       
        if(this.ConnectDB()){
            String queryName = "SELECT department FROM userData WHERE" 
                + " userName = '" + login + "';";
            
                    
            try{
                Statement  st = this.dbConnection.createStatement();
                ResultSet rst = st.executeQuery(queryName);
                
                while(rst.next()){
                    
                      departmentName = rst.getString("department");
                    }
                
            }catch(SQLException e1){
                System.out.println(e1);
            }           
            
        }else{
            System.out.println("Could not connect to the database");
        }
       
       
        return departmentName;
    }  
    
        public int getDepartmentID(String departmentName){
        
        int departmentID = 0;
       
        if(this.ConnectDB()){
            
            String queryID = "SELECT PK_departmentID FROM Department WHERE" 
                    + " departmentName = '" + departmentName + "';";
                    
            
            try{
                
                Statement  st = this.dbConnection.createStatement();
              
                ResultSet rst = st.executeQuery(queryID);
                
                while(rst.next()){
                        
                      departmentID = rst.getInt("PK_departmentID");
                    }
                
            }catch(SQLException e1){
                System.out.println(e1);
            }
        }else{
            System.out.println("Could not connect to the database");
        }
//       System.out.println(departmentName);
//       System.out.println(departmentID);
        return departmentID;
    }
        
         //alert message for email not in DB
     public void alertInvalidEmail(){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Warning");
          alert.setHeaderText("Email is incorrect");

          alert.showAndWait();
       
 }
          //alert message for password not in DB
     public void alertInvalidPassword(){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Warning");
          alert.setHeaderText("Password is incorrect");

          alert.showAndWait();
       
 }
     public String[] getUserInformation(String userName){
         String[] infoArray = new String[5];
         if(this.ConnectDB()){
             //query selects all user information except for the password
             String query = "SELECT firstName,lastName,email FROM userData WHere userName = '"+userName+"'";
             try{
                 Statement st = this.dbConnection.createStatement();
                 ResultSet r = st.executeQuery(query);
                 
                 //get the information out of teh database
                 while(r.next()){
                     for(int i = 1;i<=5;i++){
                         infoArray[i-1]= r.getString(i);
                     }
                 }
             }catch(SQLException e){
                 System.out.println(e);
             }finally{
                 try{
                     this.dbConnection.close();
                     this.connectionEstablished=false;
                 }catch(SQLException event){
                     System.out.println("There was an issue closing things");
                 }
             }
             
         }
         return infoArray;
     }
     
     public boolean updateUser(String fn,String ln,String em,String dept,String role,String userName){
         boolean updated = false;
         System.out.println("Updating user"+ userName);
         
         String query = String.format("UPDATE User SET firstName = \'%s\',lastName =\'%s\',email=\'%s\',department=\'%s\',role=\'%s\' WHERE userName =\'%s\'", fn,ln,em,dept,role,userName);
         //connect to the database
         if(this.ConnectDB()){
             //we connected to the database
             //create the statement
             try{
             Statement tempSt = this.dbConnection.createStatement();
             int result = tempSt.executeUpdate(query);
             
             if(result == 1){
                 updated = true;
             }
         }catch(SQLException e){
                 System.out.println(e);
                 }finally{
                 try{
                     this.dbConnection.close();
                     this.connectionEstablished=false;
                 }catch(SQLException e2){
                     System.out.println(e2);
                 }
             }
         }
         return updated;
     }
     //function locks out the user from the system
     public boolean lockUser(String userName){
         boolean isLocked = false;
         
         //connect to the database
        if(this.ConnectDB()){
         //connected to the database
         String query = String.format("UPDATE User SET locked=%d WHERE userName =\'%s\'",1, userName);
         try{
             Statement lockSt = this.dbConnection.createStatement();
             int lockResult = lockSt.executeUpdate(query);
             
             if(lockResult ==1){
                 System.out.println("User "+ userName+" was locked out");
                 isLocked = true;
             }
         }catch(SQLException e){
             System.out.println(e);
         }finally{
             try{
                 this.dbConnection.close();
                 this.connectionEstablished = false;
             }catch(SQLException e2){
                 System.out.println(e2);
             }
         }
        }
        
        return isLocked;
     }
    
     public boolean isLocked(String user){
         //connect to the database
         boolean isLocked=false;
         if(this.ConnectDB()){
             //we are connected so we need to query the column where
             //build the query
             String query = String.format("SELECT locked FROM userData WHERE userName=\'%s\'",user);
             
             try{
                 //create the statement
                 Statement st= this.dbConnection.createStatement();
                 boolean rst = st.execute(query);
                 
                 //get the result
                 if(rst){
                     
                     ResultSet set = st.getResultSet();
                     int colVal =0;
                     while(set.next()){
                         colVal = Integer.parseInt(set.getString("locked"));
                     }
                     //check the value
                     if(colVal==1){
                         isLocked = true;
                         
                     }else{
                         
                         isLocked =false;
                     }
                     
                    
                }

             }catch(SQLException e){
                   System.out.println(e);
             }
         }else{
             System.out.println("Could not connect to database");
         }
         
         return isLocked;
     }

}  //End Subclass Connectivity