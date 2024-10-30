//Button Mechanism Class for the new file format

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList; 

public class CircleButton extends Mechanism{
   
   //Create the private instance variables
   private boolean isUp, canWalk, timeOut, offAxis, offAxisX, offAxisY;
   private int squareX, squareY;
   private double xCordinate, yCordinate,axisOffSetX, axisOffSetY;
   private String mechID, color;
   private long pressTime;
   private long currentTime; //used to keep track of updated time
   private long elapsedTime; 
   private long duration; 
   private long remainingTime; 
   private boolean isRemaining = false; 
   private boolean savedUpdate = false; //used inCase the game is saved mid timeout
   private ArrayList<String> IDs = new ArrayList<>(); 
  
   //make the construcor
   public CircleButton(int squareX, int squareY, String mechID, boolean canWalk, boolean isUp, boolean timeOut, boolean offAxisX, boolean offAxisY, String color, long duration, long remainingTime){

      
      //Set the values equal to eachother
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      this.isUp = isUp;
      this.color = color;
      this.timeOut = timeOut;
      this.remainingTime = remainingTime; 
      this.offAxisX = offAxisX;
      this.offAxisY = offAxisY;
      if (remainingTime > 0) { 
        // If remaining time is passed, recalculate `pressTime` based on the remaining time
        this.pressTime = System.currentTimeMillis() - (duration - remainingTime);
       } else {
           // Normal start without remaining time
           this.remainingTime = 0; 
       }
      if(offAxisY || offAxisX){
         offAxis = true;
      } else {
          offAxis = false;
      }

      if(this.duration > 0){
         savedUpdate = true;
         this.pressTime = System.currentTimeMillis();
      }
      this.duration = duration;
      this.offAxis = offAxis;
   }
   
   public ArrayList<String> getIDs() { 
      return this.IDs;
   } 
   
   public void addIDs(String id) { 
      this.IDs.add(id); 
   }  
   
   public long getElapsedTime() { 
      return this.elapsedTime; 
   } 
   
   public long getDuration() { 
      return this.duration; 
   } 
   //Override all of the abstract methods
   public void update() { 
       if (this.timeOut) { 
           this.currentTime = System.currentTimeMillis(); 
           this.elapsedTime = currentTime - pressTime;  // Calculate elapsed time
   
           // Update remaining time
           this.remainingTime = this.duration - this.elapsedTime;
   
           if (this.remainingTime <= 0) { 
               // Toggle the state of `isUp` and `canWalk`
               this.isUp = !this.isUp; 
               this.canWalk = !this.canWalk; 
   
               // Reset pressTime to restart the timer
               this.pressTime = System.currentTimeMillis();
               this.remainingTime = duration;  // Reset remainingTime to full duration
               System.out.println(this.remainingTime); 
           }
       }
   } 
   
   public boolean getState(){ //get the state if the button is up
      return isUp;
   }
   public void draw(GraphicsContext gc){ //draw the buttons
       
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
       gc.fillOval(topLeftX+axisOffSetX, topLeftY +axisOffSetY, buttonSize, buttonSize); // Draw the square
       
       // Draw the border if the button is not up
       if (!isUp) {
           gc.setStroke(Color.BLACK); // Border color
           gc.setLineWidth(2); // Border width
           gc.strokeOval(topLeftX + axisOffSetX, topLeftY+axisOffSetY, buttonSize, buttonSize); // Draw the border
       }
       
       // Reset opacity back to 1 for other drawings
       gc.setGlobalAlpha(1.0);
   }
   public int getSquareX(){ //get the square X values
      return squareX;
   }
   public int getSquareY(){ //get the Square Y values
      return squareY;     
   }
   public void setCordinates(double x, double y){ //set the various cordinates of the square the button lies on
      this.xCordinate = x;
      this.yCordinate = y;
   }
   public String getName(){
      return "circlebutton";
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
      String toReturn = "circlebutton "+squareX+" "+squareY+" "+mechID+" "+canWalk+" "+isUp + " " +timeOut + " "+ offAxisX+ " " +offAxisY+" "+color+" ";
      if(timeOut == false){ //toString for a timeout
         toReturn += duration;
      }
      else{ //toString for not a timeout
         toReturn += (duration - elapsedTime);
      }
      toReturn+= " " + this.remainingTime;
      toReturn += ids+"\n";
      
      return toReturn;
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

   
   //Create the methods unique to the Button Class
   public boolean getIsUp(){
      return isUp;
   }
   public void setIsUp(boolean isUp){
      if(timeOut == true){ //enforce timer only if there is a timeout
         this.pressTime = System.currentTimeMillis();
      }
      this.isUp = isUp;
   }  
   
   public boolean getTimeOut() { 
      return this.timeOut; 
   } 
   
   public void setTimeOut(boolean t) {
      this.timeOut = t; 
      this.pressTime = System.currentTimeMillis();
   }
   
}