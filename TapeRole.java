import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class TapeRole extends Mechanism{ 
   private int squareX, squareY; 
   public double xShift, yShift;
   private boolean offAxis = false;
   private boolean isShown = true;

   
   
   public TapeRole(int squareX, int squareY) { 
      
      this.squareX = squareX;
      this.squareY = squareY; 
      xShift = squareX * 75;
      yShift = squareY * 75;

      
      
   } 
   
   // Does nothing since only need to update display once 
   @Override 
   public void update() { 
      return; 
   } 
   @Override
   public boolean offAxis(){
      return offAxis;
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
      return "taperole";
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
        
        
        gc.setFill(javafx.scene.paint.Color.GREY);
        gc.fillOval(xShift+ 7.5, yShift +7.5, 60, 60);
        
        // Draw the smaller black circle
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillOval(xShift + 27.5, yShift + 27.5, 20, 20);
        
      
   } 
   
   public void setDisplay(boolean isShown) { 
      this.isShown = isShown;
   } 
   public String toString(){ 
      String toReturn = "taperole "+squareX+" "+squareY + "\n";
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