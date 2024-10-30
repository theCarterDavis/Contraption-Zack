import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Wall extends Mechanism {
  private int xPos, yPos;
  private String color;
  private boolean canWalk;
  private int width; 
  private int height;
  private static final int PLAYER_SIZE = 40; 
  private boolean offAxis = false;
  
   public Wall(int xPos, int yPos, String color, int width, int height) { 
      this.xPos = xPos; 
      this.yPos = yPos; 
      this.color = color; 
      this.canWalk = false; 
      this.width = width; 
      this.height = height; 
      
   }
   
   public boolean getWalk() { 
      return this.canWalk; 
   } 
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
   
   public int getWidth() { 
      return this.width; 
   } 
   public int getHeight() { 
      return this.height; 
   } 
   
   public int getXPos() { 
      return this.xPos; 
   }
   
   public int getYPos() { 
      return this.yPos; 
   }
   
   @Override 
   public String toString() { 
      return "wall" + " " + this.xPos + " " + this.yPos + " " + color + " " + width + " " + height+" \n"; 
   } 
   
   @Override 
   public boolean canWalk() { 
      return true; 
   } 
   
   @Override 
   public String getID() { 
      return ""; 
   } 
   
   @Override 
   public String getName() { 
      return "wall";
   } 
   
   @Override 
   public void setCordinates(double x, double y) {
      return; 
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
   public void draw(GraphicsContext gc) { 
      Color c = Color.valueOf(color);   // Convert string color to JavaFX Color
      gc.setFill(c);                    // Set the fill color
      gc.fillRect(xPos, yPos, this.width, this.height);  // Draw the rectangle
   } 
   
   @Override 
   public void update() { 
      return; 
   } 
   
   @Override 
   public boolean getState() { 
      return true; 
   } 
   
   public boolean colliding(double playerX, double playerY) {
       // Define the bounding box for the base plate only
       double baseXStart = this.xPos;           // Left side of the base
       double baseXEnd = this.xPos + width;        // Right side of the base (width = 20 pixels)
       double baseYStart = this.yPos;           // Top side of the base
       double baseYEnd = this.yPos + height;        // Bottom side of the base (height = 40 pixels)
   
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