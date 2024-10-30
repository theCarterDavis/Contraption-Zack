//Music Box Class

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MusicBox extends Mechanism{

   //private instance variables for the MusicBox
   private int squareX, squareY;
   private boolean canWalk;
   private String mechID;
   private double xCordinate, yCordinate; //where to draw on the specific square
   
   //Create the constructor
   public MusicBox(int squareX, int squareY, String mechID, boolean canWalk){
   
      //Set the values
      this.squareX = squareX;
      this.squareY = squareY;
      this.canWalk = canWalk;
      this.mechID = mechID;
   }
   public boolean offAxis() {
      return false;
   }

   //Overide all of the abstract methods
   public void update(){return;} //update does nothing  
   public boolean getState() {return true;} //always up
   public void draw(GraphicsContext gc){
      // Set the size of the music box
      double boxSize = 60; // Size of the music box
      double xCenter = xCordinate + (75 - boxSize) / 2; // Center x position
      double yCenter = yCordinate + (75 - boxSize) / 2; // Center y position

      // Set the fill color and draw the music box
      gc.setFill(Color.MOCCASIN);
      gc.fillRect(xCenter, yCenter, boxSize, boxSize);

      // Set the outline color and draw the outline
      gc.setStroke(Color.LIMEGREEN);
      gc.setLineWidth(2); // Set line width for the outline
      gc.strokeRect(xCenter, yCenter, boxSize, boxSize);
   }
   public int getSquareX(){
      return squareX;
   }
   public int getSquareY(){
      return squareY;
   }
   @Override
    public  boolean offAxisX(){
      return false;
   }
   @Override
   public  boolean offAxisY(){
      return false;
   }
   public void setCordinates(double x, double y)
   {
      this.xCordinate = x;
      this.yCordinate = y;
   }
   public String getName(){
      return "musicbox";
   }
   public String getID(){
      return mechID;
   }
   public boolean canWalk(){
      return canWalk;
   }
   public String toString(){ //toString
      String toReturn = "musicbox "+squareX+" "+squareY+" "+mechID+" "+canWalk+"\n";
      return toReturn;
   }
}