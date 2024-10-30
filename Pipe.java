import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Pipe extends Mechanism{ 
   private int squareX, squareY; 
   public double xShift, yShift;
   private boolean isShown = true;

   
   
   public Pipe(int squareX, int squareY) { 
      
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
      return "pipe";
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
       double y = yShift;
       double height = 18.75;
       double width = 75;
       gc.setFill(javafx.scene.paint.Color.GREY);
       
       gc.fillRect(x,y, width,height);
       gc.setStroke(javafx.scene.paint.Color.BLACK);
       

       for(int i = 0;i <= 5; i++){
         int mod = i * 4;
         gc.strokeLine(x+mod, x, y+mod, y + height);
         gc.strokeLine(x +width-mod, y, x+width-mod, y + height);

       }
   
   }
   
    
   public void setDisplay(boolean isShown) { 
      this.isShown = isShown;
   } 
   public String toString(){ //screwdriver
      String toReturn = "pipe "+squareX+" "+squareY + "\n";
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