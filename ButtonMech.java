//Button Mechanism Class for the new file format

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList; 

public class ButtonMech extends Mechanism{
   
   //Create the private instance variables
   private boolean isUp, canWalk, offAxisX, offAxisY, offAxis;
   private int squareX, squareY;
   private double xCordinate, yCordinate, axisOffSetX, axisOffSetY;
   private String mechID, color, secondPrint;
   private long pressTime;
   private boolean savedUpdate = false; //used inCase the game is saved mid timeout
   private ArrayList<String> IDs = new ArrayList<>(); 
   private boolean springTimeOut; 
   
   //spoofing variables
   private boolean spoofSave = false;
   private boolean spoofDown = false;
  
   //make the construcor
   public ButtonMech(int squareX, int squareY, String mechID, boolean canWalk, boolean isUp, boolean offAxisX, boolean offAxisY, boolean springTimeOut, String color){
      //Set the values equal to eachother
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      this.isUp = isUp;
      this.color = color;
      this.offAxisX = offAxisX;  
      this.offAxisY = offAxisY;  
      this.springTimeOut = springTimeOut; 
      
      
      if(offAxisY || offAxisX){
         offAxis = true;
      } else {
          offAxis = false;
      }
      
      if(mechID.contains("r")){
         spoofSave = true;
      }
          
   }
   
   public boolean getSpringTimeOut() { 
      return this.springTimeOut; 
   } 
   public void addIDs(String id) { 
      this.IDs.add(id); 
   } 
   
   public ArrayList<String> getIDs() {
      return this.IDs;
   } 
   
   //Override all of the abstract methods
   public void update(){ //Update button based on time
      return;    
   }  
   public boolean getState(){ //get the state if the button is up
      return isUp;
   }
   public void draw(GraphicsContext gc){ //draw the buttons
      //Setting the shift for the button being off axis.
      if(offAxisX){
         axisOffSetX = 37.5;
      } else {
         axisOffSetX = 0;
      }
      
      if(offAxisY){
         axisOffSetY = 37.5;
      } else {
         axisOffSetY = 0;
      }


       // Set the opacity based on the button's state
       double opacity = isUp ? 1.0 : 0.5;
       gc.setGlobalAlpha(opacity);
       
       // Calculate the top-left corner of the button square
       double buttonSize = 50; // Size of the button square
       double topLeftX = xCordinate + (75 - buttonSize) / 2; // Center the button
       double topLeftY = yCordinate + (75 - buttonSize) / 2; // Center the button
       
       // Set the button's color
       Color buttonColor = Color.web(color); // Assuming color is a valid color string
       
       // Draw the button (square)
       gc.setFill(buttonColor);
       gc.fillRect(topLeftX + axisOffSetX, topLeftY + axisOffSetY, buttonSize, buttonSize); // Draw the square
       
       // Draw the border if the button is not up
       if (!isUp) {
           gc.setStroke(Color.BLACK); // Border color
           gc.setLineWidth(2); // Border width
           gc.strokeRect(topLeftX + axisOffSetX, topLeftY + axisOffSetY, buttonSize, buttonSize); // Draw the border
              
           
       }
       
       // Reset opacity back to 1 for other drawings
       gc.setGlobalAlpha(1.0);
   }
   public int getSquareX(){ //get the square X values
      return squareX;
   }
   
   public boolean offAxis(){
      return offAxis;
   }
   
     public  boolean offAxisX(){
      return offAxisX;
   }
     public  boolean offAxisY(){
      return offAxisY;
   }
   
   public int getSquareY(){ //get the Square Y values
      return squareY;     
   }
   public void setCordinates(double x, double y){ //set the various cordinates of the square the button lies on
      this.xCordinate = x;
      this.yCordinate = y;
   }
   public String getName(){
      return "button";
   }
   public String getID(){
      return mechID;
   }
   public boolean canWalk(){
      return canWalk;
   }
   public String toString(){ //create the button TooString
      String ids = " " + this.IDs.size(); 
      for(int i = 0; i < this.IDs.size(); i++) { 
         ids += " " + this.IDs.get(i); 
      }    

      if(secondPrint == null){
         String toReturn = "button "+squareX+" "+squareY+" "+mechID+" "+canWalk+" " +isUp+ " " +offAxisX+ " " +offAxisY+" "+springTimeOut+" "+color+" "+ ids + " ";    
         toReturn += "\n";
         return toReturn;
      } else {
         return secondPrint;
      }
      
   }
   
   //Create the methods unique to the Button Class
   public boolean getIsUp(){
      return isUp;
   }
   public void setIsUp(boolean isUp){
      
      this.isUp = isUp;

      if(isUp == false && spoofSave == true){
         String ids = " " + this.IDs.size(); 
         for(int i = 0; i < this.IDs.size(); i++) { 
            ids += " " + this.IDs.get(i); 
         }   
         secondPrint = "button "+squareX+" "+squareY+" "+mechID+" "+canWalk+" " + "false" + " " +offAxisX+ " " +offAxisY+" "+springTimeOut+" "+color+" "+ ids + "\n";

      }

      
   }  
   public String getColor() {
      return this.color; 
   }
}