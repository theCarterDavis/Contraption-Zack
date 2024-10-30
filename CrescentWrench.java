import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class CrescentWrench extends Mechanism{ 
   private int squareX, squareY; 
   private double xShift, yShift;
   private boolean isShown = true;
   
   
   public CrescentWrench(int squareX, int squareY) { 
      
      this.squareX = squareX;
      this.squareY = squareY;
      xShift = squareX * 75;
      yShift = squareY * 75 + 18.75;
      
   } 
   
   // Does nothing since only need to update display once 
   @Override 
   public void update() { 
      return; 
   } 
   
   @Override
    public  boolean offAxisX(){
      return false;
   }
   @Override
   public  boolean offAxisY(){
      return false;
   }
   
   @Override 
   public boolean getState() {
   return true;
   }     
   
   @Override 
   public int getSquareX() { 
      return this.squareX;
   } 
   
   @Override 
   public int getSquareY() { 
      return this.squareY; 
   } 
   
   @Override 
   public void setCordinates(double xCordinate, double yCordinate){ //set the x and y to draw the square
   }
   
   @Override 
   public String getName(){ //get the name of mechanism
      return "crescentwrench";
   }
   
   // No mechID since doesn't link to anything 
   @Override 
   public String getID(){ //get the mechID
      return "";
   }
   
   // Always can walk over it 
   @Override 
   public boolean canWalk(){
      return true;
   }
   
   @Override 
   public boolean offAxis(){
      return false;
   }

   
   @Override 
   public void draw(GraphicsContext gc) {
       
      double[] xPoints = {0,20,37.5,112.5,130,150,150,122,115,122,150,150,130,112.5,37.5,20,0,0,18,25,18,0};
      double[] yPoints = {0,0,20,20,0,0,20,20,37.5,55,55,75,75,55,55,75,75,55,55,37.5,20,20};

       gc.setFill(Color.DARKGREY);
       gc.setStroke(Color.BLACK); // Add an outline for visibility
       gc.setLineWidth(1);
   
       // Begin the path
       gc.beginPath();
       gc.moveTo((xPoints[0])/2 +xShift, (yPoints[0])/2 +yShift);
   
       // Draw lines to all points
       for (int i = 1; i < xPoints.length; i++) {
           gc.lineTo((xPoints[i])/2 +xShift, yPoints[i]/2 +yShift);
       }
   
       // Close the path back to the first point
       gc.lineTo((xPoints[0])/2 +xShift, (yPoints[0])/2 +yShift);
       gc.closePath();
       gc.fill();
       gc.stroke();       // Fill and/or stroke the shape   
    }
   
    
    public void setDisplay(boolean isShown) { 
      this.isShown = isShown;
   } 
   public String toString(){ //screwdriver
      String toReturn = "crescentwrench "+squareX+" "+squareY + "\n";
      return toReturn;
   }
   
   public boolean colliding(double playerX, double playerY) {
       int PLAYER_SIZE = 40;
       double xPos = xShift;/// + 37.5;
       double yPos = yShift;// + 37.5;
       // Define the bounding box for the base plate only
       double baseXStart = xPos;           // Left side of the base
       double baseXEnd = xPos + 75;        // Right side of the base (width = 20 pixels)
       double baseYStart = yPos;           // Top side of the base
       double baseYEnd = yPos + 75;        // Bottom side of the base (height = 40 pixels)
   
       // Calculate player's bounding box
       double playerXStart = playerX;                     // Left side of the player
       double playerXEnd = playerX + PLAYER_SIZE;         // Right side of the player
       double playerYStart = playerY;                     // Top side of the player
       double playerYEnd = playerY + PLAYER_SIZE;         // Bottom side of the player
   
       // Check for collision: overlap of rectangles
       boolean collisionX = (playerXEnd >= baseXStart && playerXStart <= baseXEnd);
       boolean collisionY = (playerYEnd >= baseYStart && playerYStart <= baseYEnd);
   
       // Return true if there is a collision
       return collisionX && collisionY;
   }

} 