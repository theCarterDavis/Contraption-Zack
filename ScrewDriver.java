import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ScrewDriver extends Mechanism{ 
   private int xPos, yPos; 
   private int squareX, squareY; 
   private boolean display;
   private double xCordinate, yCordinate; //where to draw on the specific square
   private static final int WIDTH = 110;
   private static final int HEIGHT = 40;
   private static final int PLAYER_SIZE = 40; 

   
   public ScrewDriver(int squareX, int squareY, int xPos, int yPos, double xCordinate, double yCordinate) { 
      this.xPos = xPos; 
      this.yPos = yPos; 
      this.squareX = squareX;
      this.squareY = squareY; 
      this.xCordinate = xCordinate; 
      this.yCordinate = yCordinate; 
      this.display = true; 
   } 
   public boolean offAxis() {  
      return false; 
   } 
   // Does nothing since only need to update display once 
   @Override 
   public void update() { 
      return; 
   } 
   
   @Override 
   public boolean getState() {
      return this.display; 
   }     
   
   @Override 
   public int getSquareX() { 
      return this.squareX;
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
   public int getSquareY() { 
      return this.squareY; 
   } 
   
   @Override 
   public void setCordinates(double xCordinate, double yCordinate){ //set the x and y to draw the square
      this.xCordinate = xCordinate;
      this.yCordinate = yCordinate;
      
      return;
   }
   
   @Override 
   public String getName(){ //get the name of mechanism
      return "screwdriver";
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
   public void draw(GraphicsContext gc) { 
        // Set line width and color
        gc.setLineWidth(2);
        
        // Flathead tip: draw the two diagonal lines
        gc.setStroke(Color.GRAY);
        gc.strokeLine(this.xPos + 95, this.yPos + 15, this.xPos + 110, this.yPos + 10);  // Line 1 (x1, y1, x2, y2)
        gc.strokeLine(this.xPos + 95, this.yPos + 25, this.xPos + 110, this.yPos + 30);  // Line 2 (x1, y1, x2, y2)
         
        // Fill the area between the diagonal lines with a polygon
       gc.setFill(Color.GRAY);
       double[] xPoints = {this.xPos + 95, this.xPos + 95, this.xPos + 110, this.xPos + 110};
       double[] yPoints = {this.yPos + 15, this.yPos + 25, this.yPos + 30, this.yPos + 10};
       gc.fillPolygon(xPoints, yPoints, 4);  // Fill the flathead tip
        
        // Shaft: draw a thin rectangle for the shaft
        gc.setFill(Color.GRAY);
        gc.fillRect(this.xPos + 68, this.yPos + 15, 30, 10);  // (x, y, width, height)
        
        // Handle: draw a rounded rectangle for the handle
        gc.setFill(Color.BROWN);
        gc.fillRoundRect(this.xPos, this.yPos, 70, 40, 40, 40);  // (x, y, width, height, arcWidth, arcHeight)
   
   } 
   
   public boolean colliding(double playerX, double playerY) {
       // Define the bounding box for the base plate only
       double baseXStart = this.xPos;           // Left side of the base
       double baseXEnd = this.xPos + WIDTH;        // Right side of the base (width = 20 pixels)
       double baseYStart = this.yPos;           // Top side of the base
       double baseYEnd = this.yPos + HEIGHT;        // Bottom side of the base (height = 40 pixels)
   
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
   
   public void setDisplay(boolean isShown) { 
      this.display = isShown; 
   } 
   public String toString(){ //screwdriver
      String toReturn = "screwdriver "+squareX+" "+squareY+" "+xPos+" "+yPos+" "+xCordinate+" "+yCordinate+"\n";
      return toReturn;
   }
} 