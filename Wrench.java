import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Wrench extends Mechanism{ 
   private int squareX, squareY; 
   private double xShift, yShift;
   private boolean isShown = true;
   
   
   public Wrench(int squareX, int squareY) { 
      
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
    public  boolean offAxisX(){
      return false;
   }
   @Override
   public  boolean offAxisY(){
      return false;
   }
   @Override 
   public void setCordinates(double xCordinate, double yCordinate){ //set the x and y to draw the square
   }
   
   @Override 
   public String getName(){ //get the name of mechanism
      return "wrench";
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
   
       double x = xShift;
       double y = yShift +37.5;
       if(isShown){
       gc.setFill(javafx.scene.paint.Color.RED);
       gc.fillRect(x,y,50,11);
       gc.setFill(javafx.scene.paint.Color.DARKGREY);
       gc.fillRect(x+50,y-3,4,20);
       
       gc.fillRect(x+50,y+11,30,10);
       
       gc.fillRect(x+75,y-3,5,23.5);

      double spacing = 1;
      
      double size = 3;

      for (int i = 0; i < 3; i++) {
        // Calculate the x position for each triangle
        double triangleY = y + (i * (size + spacing));
        double triangleX = x + 72; 
        // Create points for the triangle
        double[] xPoints = {
             triangleX,           // Left point
             triangleX+ size,  // Top point
             triangleX+ size     // Right point
        };
        
        double[] yPoints = {
            triangleY + size/2,    // Left point
            triangleY,           // Top point
            triangleY + size     // Right point
        };
        
        // Draw the triangle
        gc.setFill(javafx.scene.paint.Color.LIGHTGREY);

        gc.fillPolygon(xPoints, yPoints, 3);
        
        // Add a slight outline for definition
        gc.setStroke(Color.rgb(80, 80, 80));
        gc.setLineWidth(1);
        gc.strokePolygon(xPoints, yPoints, 3);
        }
        
          }
      }
    
   
    
   public void setDisplay(boolean isShown) { 
      this.isShown = isShown;
   } 
   public String toString(){ 
      String toReturn = "wrench "+squareX+" "+squareY + "\n";
      return toReturn;
   }
   
   public boolean colliding(double playerX, double playerY) {
       int PLAYER_SIZE = 40;
       double xPos = xShift;
       double yPos = yShift;
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