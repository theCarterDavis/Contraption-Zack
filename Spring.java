//Updated Spring Class for new Mech and File Format

import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

class Spring extends Mechanism { 
   
   //private instance variables
   private int squareX, squareY, newPlayerX, newPlayerY;
   private boolean canWalk, isUp, timeOut; // Timeout is for if spring is a timer 
   private String mechID, launchDirection;
   private double xCordinate, yCordinate; //where to draw the spring based on the cordinates of the square
   private ArrayList<String> IDs = new ArrayList<>(); 
   private long duration; // Duration is how long it will take before springs resets.
   private boolean savedUpdate = false; //used inCase the game is saved mid timeout
   private boolean offAxis = false;
   private boolean hasTimeOut; 
   
   //Special reset instance variables
   private long resetTime = 5000; //going to reset the screw after 2000 seconds
   private long elapsedResetTime;
   
   //Create the constructor
   public Spring(int squareX, int squareY, String mechID, boolean hasTimeOut, boolean canWalk, boolean isUp, String launchDirection, int newPlayerX, int newPlayerY){
      
      //Set the variables equal to eachother
      this.squareX = squareX;
      this.squareY = squareY;
      this.canWalk = canWalk;
      this.isUp = isUp;
      this.timeOut = timeOut;
      this.mechID = mechID;
      this.launchDirection = launchDirection;
      this.newPlayerX = newPlayerX;
      this.newPlayerY = newPlayerY;
      this.hasTimeOut = hasTimeOut; 
   }

   public boolean getHasTimeOut() { 
      return this.hasTimeOut; 
   } 
   public void addIDs(String id) { 
      this.IDs.add(id); 
   }  
   public ArrayList<String> getIDs() { 
      return this.IDs; 
   } 
   
   public boolean offAxis(){
      return this.offAxis();
   }  
   
   //Overide abstract methods
   public void update(){ //Update button based on time
     return;
   }
   
   public boolean getState(){
      return isUp;
   }
    
   public void draw(GraphicsContext gc){ //draw the spring
      if(isUp == false && canWalk == true){ //spring has not been activated yet
        // Define the size of the square and the smaller box
        int squareSize = 75;
        int boxSize = 50; // Smaller size for the gray box

        // Calculate the position for the gray box to be centered in the square
        double boxX = xCordinate + (squareSize - boxSize) / 2; // Centered within the square
        double boxY = yCordinate + (squareSize - boxSize) / 2; // Centered within the square

        // Draw the gray box
        gc.setFill(Color.GRAY);
        gc.fillRect(boxX, boxY, boxSize, boxSize);

        // Set the color for the lines
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2); // Set line width for better visibility

        // Draw three horizontal lines centered within the box
        double lineY1 = boxY + boxSize / 4;   // Top line
        double lineY2 = boxY + boxSize / 2;   // Middle line
        double lineY3 = boxY + 3 * boxSize / 4; // Bottom line

        // Draw the horizontal lines
        gc.strokeLine(boxX, lineY1, boxX + boxSize, lineY1);
        gc.strokeLine(boxX, lineY2, boxX + boxSize, lineY2);
        gc.strokeLine(boxX, lineY3, boxX + boxSize, lineY3);   
      }
      else{ //spring has been activated, draw it pointing to a specifc direction
         drawDirection(gc);      
         if(mechID.contains("r")){
            
            //Reset the spring
            long currentTime = System.currentTimeMillis();
            elapsedResetTime = currentTime - elapsedResetTime;
            if(elapsedResetTime > resetTime){ //make the spring go back up
               isUp = false;
               canWalk = true;
            }
         }      
      }
   }
   public int getSquareX(){ //get the square grid cordinates
      return squareX;
   }
   public int getSquareY(){ //get the y grind cordinates
      return squareY;
   }
   public void setCordinates(double xCordinate, double yCordinate){ //set the acutal x and y of spring object
      this.xCordinate = xCordinate;
      this.yCordinate = yCordinate;
   }
   public String getName(){ //get the spring name
      return "spring";
   }
   public String getID(){ //get the spring id
      return mechID;
   }
   public boolean canWalk(){ //return whether or not the spring can walk or not
      return canWalk;
   }
   
   //Create private methods specific to springs
   private void drawDirection(GraphicsContext gc){ //draw the direction of the spring based on what way it was pushed
    // Define the size of the square and the circles
    int squareSize = 75;
    double circleRadius = 10; // Radius for the circles
    double rectWidth = 10; // Width of the gray rectangle
    double rectHeight = 30; // Height of the gray rectangle

    // Calculate the center of the square
    double centerX = xCordinate + squareSize / 2;
    double centerY = yCordinate + squareSize / 2;

    // Draw the first circle (centered in the square)
    gc.setFill(Color.WHITE);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.strokeOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2);

    // Calculate positions for the next two circles based on the launch direction
    double offsetX = 0, offsetY = 0;

    switch (launchDirection) {
        case "left":
            offsetX = -circleRadius - 5; // Shift left
            break;
        case "right":
            offsetX = circleRadius + 5; // Shift right
            break;
        case "up":
            offsetY = -circleRadius - 5; // Shift up
            break;
        case "down":
            offsetY = circleRadius + 5; // Shift down
            break;
    }

    // Draw the second circle
    gc.strokeOval(centerX + offsetX - circleRadius, centerY + offsetY - circleRadius, circleRadius * 2, circleRadius * 2);
    // Draw the third circle slightly overlapping the second
    gc.strokeOval(centerX + offsetX - circleRadius, centerY + offsetY - circleRadius + (launchDirection.equals("up") ? -5 : 5), circleRadius * 2, circleRadius * 2);

   // Draw the gray rectangle at the end of the square's boundary
    double rectX = centerX + offsetX - rectWidth / 2;
    double rectY = centerY + offsetY - rectHeight / 2;

    switch (launchDirection) {
        case "left":
            rectX = centerX - rectWidth; // Shift rectangle to the left
            break;
        case "right":
            rectX = centerX + offsetX; // Rectangle aligned with the last circle
            break;
        case "up":
            rectY = centerY - rectHeight; // Shift rectangle up
            break;
        case "down":
            rectY = centerY + offsetY; // Rectangle aligned with the last circle
            break;
    }

    // Draw the gray rectangle
    gc.setFill(Color.GRAY);
    gc.fillRect(rectX, rectY, rectWidth, rectHeight);

   }
   public void setWalk(boolean canWalk){
      this.canWalk = canWalk;
   }
   public void setIsUp(boolean isUp){
      this.isUp = isUp;
      
      //Account for instant reset
      if(mechID.contains("r") && isUp == true){ 
         elapsedResetTime = System.currentTimeMillis();
      }
   }
   public int getNewPlayerX(){
      return newPlayerX;
   }
   public int getNewPlayerY(){
      return newPlayerY;
   }
   public String toString(){ //toString
      String ids = " " + this.IDs.size(); 
      for(int i = 0; i < this.IDs.size(); i++) { 
         ids += " " + this.IDs.get(i); 
      } 
      String toReturn = "spring "+squareX+" "+squareY+" "+mechID+" "+" "+hasTimeOut+" "+canWalk+" "+isUp+" "+launchDirection+" "+newPlayerX+" "+newPlayerY + ids + "\n";
      return toReturn;
   }
   
   @Override
    public  boolean offAxisX(){
      return false;
   }
   @Override
   public  boolean offAxisY(){
      return false;
   }
}