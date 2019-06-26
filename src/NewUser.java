/**
*  @Course: SDEV 250 ~ Java Programming I
*  @Author Name: Ryan Hoffman
*  @Assignment Name: 
*  @Date: Jun 15, 2019
*  @Subclass NewUser Description:
*/
//Imports
//Begin Subclass NewUser
 import java.util.HashMap;
import java.util.Map;

 /*Description: This class is going to be used to hold
 * data on the new user Once all the data has been set for that object, a method
 * within this class can be used to add the new user to the database
 */
public class NewUser {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;
    private int protein;
    private int carb;
    private int fat;
    
 

    //Connectivity class instance
    Connectivity connectivity;
    
    //default constructor
    public NewUser() {
        this("", "", "");
    }

    public NewUser(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        
        

    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarb(int carb) {
        this.carb = carb;
    }
    
    public void setFat(int fat){
        this.fat = fat;
    }
    //function to add the new user information to the database
    public boolean addUserToDatabase() {
        boolean success = true;
        //get all the information for the database
        String first = this.getFirstName();
        String last = this.getLastname();
        String email = this.getEmail();
        String pwrd = this.getPassword();
        String uName = this.getuserName();
        int prot = this.getProtein();
        int carb = this.getCarb();
        int fatt = this.getFat();
       
        
        //make an array of all this data and send it to the Connectivity class instance
        Connectivity tempConnection = new Connectivity();
        
        /*if the connection to the database is established, then proceed to adding
        the user data
        */
        if(tempConnection.ConnectDB()){
           /*once connected call the .addNewUser in the Connection class to add
           the information to the database
           */
           //if the user was not added, then return the false flag
           if(!(tempConnection.addNewUser(first,last,email,pwrd,uName,prot,carb,fatt))){
               success = false;
           }
            
            
        }else{     
            //this means there was a problem with connecting to the database
            success = false;
        }
       
        return success;
    }

    //depending on the usage, some or all of these methods could be made private
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }

    public String getuserName() {
        return this.userName;
    }
    
     public int getProtein() {
       return this.protein;
    }

    public int getCarb() {
       return this.carb;
    }
    
    public int getFat(){
       return this.fat;
    }
    
    private void printReport() {
        System.out.println(this.firstName);
        System.out.println(this.lastName);
        System.out.println(this.email);
    }

}  //End Subclass NewUser