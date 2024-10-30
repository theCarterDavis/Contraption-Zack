import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList; 

public class Screw extends Mechanism { 
   private int xPos, yPos; 
   private boolean isUp; 
   private String mechID; 
   private static final int PLAYER_SIZE = 40;
   private ArrayList<String> IDs = new ArrayList<>();  
   
   
   public Screw(int xPos, int yPos, String mechID) { 
      
      this.xPos = xPos;
      this.yPos = yPos; 
      this.mechID = mechID; 
      this.isUp = true; 
   } 
   public void addIDs(String id) { 
      this.IDs.add(id);
   } 
   
   public ArrayList<String> getIDs() {
      return this.IDs; 
   }
   public void setIsUp(boolean isUp) { 
      this.isUp = isUp; 
   } 
   
   // Does nothing since only need to update display once 
   @Override 
   public void update() { 
      return; 
   } 
   
   @Override 
   public boolean getState() {
   return true;
   }     
   
   @Override 
   public int getSquareX() { 
      return 0;
   } 
   
   @Override 
   public int getSquareY() { 
      return 0; 
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
   public void setCordinates(double xCordinate, double yCordinate){ //set the x and y to draw the square
      return; 
   }
   
   @Override 
   public String getName(){ //get the name of mechanism
      return "wrench";
   }
   
   // No mechID since doesn't link to anything 
   @Override 
   public String getID(){ //get the mechID
      return mechID;
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
      if(isUp == true) { 
         drawFirstScrewHead(gc); 
      } else { 
         drawSecondScrewHead(gc); 
      } 
   }
   
   private void drawFirstScrewHead(GraphicsContext gc) { 
      double size = 20; // Width and height of the screw head
        Color headColor = Color.DARKGRAY; // Hard-coded dark gray color
        Color plusColor = Color.BLACK; // Hard-coded black color for the plus

        gc.setFill(headColor);
        gc.fillOval(xPos - size / 2, yPos - size / 2, size, size); // Circular screw head

        gc.setStroke(plusColor);
        gc.setLineWidth(2);
        // Plus sign (horizontal and vertical lines)
        gc.strokeLine(xPos - size / 4, yPos, xPos + size / 4, yPos); // Horizontal
        gc.strokeLine(xPos, yPos - size / 4, xPos, yPos + size / 4); // Vertical
   }
   
   private void drawSecondScrewHead(GraphicsContext gc) { 
        double size = 20; // Width and height of the screw head
        Color headColor = Color.LIGHTGRAY; // Hard-coded light gray color
        Color plusColor = Color.GRAY; // Hard-coded gray color for the plus

        gc.setFill(headColor);
        gc.fillOval(xPos - size / 2, yPos - size / 2, size, size); // Circular screw head

        gc.setStroke(plusColor);
        gc.setLineWidth(2);
        // Plus sign (horizontal and vertical lines)
        gc.strokeLine(xPos - size / 4, yPos, xPos + size / 4, yPos); // Horizontal
        gc.strokeLine(xPos, yPos - size / 4, xPos, yPos + size / 4); // Vertical
   }
   public boolean colliding(double playerX, double playerY) {
       // Define the bounding box for the base plate only
       double baseXStart = this.xPos - 10;           // Left side of the base
       double baseXEnd = this.xPos + 5;        // Right side of the base (width = 20 pixels)
       double baseYStart = this.yPos - 10;           // Top side of the base
       double baseYEnd = this.yPos + 5;        // Bottom side of the base (height = 40 pixels)
   
       // Calculate player's bounding box
       double playerXStart = playerX;                     // Left side of the player
       double playerXEnd = playerX + PLAYER_SIZE;         // Right side of the player
       double playerYStart = playerY;                     // Top side of the player
       double playerYEnd = playerY + PLAYER_SIZE;         // Bottom side of the player
   
       // Check for collision: overlap of rectangles
       boolean collisionX = (playerXEnd >= baseXStart && playerXStart <= baseXEnd);
       boolean collisionY = (playerYEnd >= baseYStart && playerYStart <= baseYEnd);
   
       // Return true if there is a collision
       return collisionX && collisionY && isUp;
   }
   
   public String toString(){ //toString
      String ids = " " + this.IDs.size(); 
      for(int i = 0; i < this.IDs.size(); i++) { 
         ids += " " + this.IDs.get(i); 
      } 
      String toReturn = "screw " +xPos+" "+yPos+ " " + mechID + ids  + "\n";
      return toReturn;
   }
} 