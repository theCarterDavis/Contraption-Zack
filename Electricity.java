import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Electricity extends Mechanism { 

   private int xPos, yPos;
   private int squareX, squareY; 
   private double xCoordinate, yCoordinate; 
   private boolean isOn; 
   private String mechID;
   private int drawState; 
   private boolean canWalk;
   private boolean isVertical;  // New flag to control orientation
   private static final int PLAYER_SIZE = 40; 
   private boolean offAxis;
   
   public Electricity(int squareX, int squareY, String mechID, int xPos, int yPos, double xCoordinate, double yCoordinate, boolean isVertical, boolean isOn) { 
      this.mechID = mechID; 
      this.xPos = xPos; 
      this.yPos = yPos; 
      this.squareX = squareX; 
      this.squareY = squareY; 
      this.xCoordinate = xCoordinate; 
      this.yCoordinate = yCoordinate; 
      this.isOn = isOn; 
      this.drawState = 1; 
      this.canWalk = true;
      this.isVertical = isVertical;  // Initialize with given orientation 
   } 
   
   @Override 
   public void draw(GraphicsContext gc) { 
      
      if (this.isVertical) {
         drawVertical(gc);
      } else {
         drawHorizontal(gc);
      }
           
   }
   
   @Override 
   public void update() {
      return; 
   } 
   
   @Override   
   public boolean canWalk() {
      return false;
   } 
   
   @Override 
   public String getID() {
      return this.mechID; 
   }
   
   @Override 
   public String getName() {
      return "electricity";
   } 
   
   @Override 
   public void setCordinates(double x, double y) {
      this.xCoordinate = x; 
      this.yCoordinate = y;
   } 
   @Override
   public boolean offAxis(){
      return offAxis;
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
   public boolean getState() { 
      return this.isOn; 
   } 

   // Method to draw electricity in horizontal orientation
   private void drawHorizontal(GraphicsContext gc) {
      if(this.isOn == true) {
         if (this.drawState == 1) { 
            this.drawFirstState(gc);
            this.drawState += 1;
         } else if (this.drawState == 2) { 
            this.drawFirstState(gc);
            this.drawState += 1; 
         } else if (this.drawState == 3) { 
            this.drawSecondState(gc);
            this.drawState += 1; 
         } else if (this.drawState == 4) { 
            this.drawThirdState(gc);
            this.drawState += 1; 
         } else { 
            this.drawThirdState(gc); 
            this.drawState = 1;
         }
      }
      
      // Draw poles (circles) at both ends of the electricity, total width reduced to 75 pixels
      gc.setFill(Color.GRAY);
      gc.fillOval(xPos - 5, yPos - 5, 10, 10); // Left pole
      gc.fillOval(xPos + 65, yPos - 5, 10, 10); // Right pole
   }
   
   // Method to draw electricity in vertical orientation
   private void drawVertical(GraphicsContext gc) {
      if(this.isOn == true) {
         if (this.drawState == 1) { 
            this.drawFirstStateVertical(gc);
            this.drawState += 1;
         } else if (this.drawState == 2) { 
            this.drawFirstStateVertical(gc);
            this.drawState += 1; 
         } else if (this.drawState == 3) { 
            this.drawSecondStateVertical(gc);
            this.drawState += 1; 
         } else if (this.drawState == 4) { 
            this.drawThirdStateVertical(gc);
            this.drawState += 1; 
         } else { 
            this.drawThirdStateVertical(gc); 
            this.drawState = 1;
         }
   
      }
      
      // Draw poles (circles) at both ends of the electricity, total height reduced to 75 pixels
      gc.setFill(Color.GRAY);
      gc.fillOval(xPos - 5, yPos - 5, 10, 10); // Top pole
      gc.fillOval(xPos - 5, yPos + 65, 10, 10); // Bottom pole
   }

   // Horizontal squiggly lines
   private void drawFirstState(GraphicsContext gc) { 
      gc.setStroke(Color.BLUE);
      gc.beginPath();
      gc.moveTo(xPos + 0, yPos);   
      gc.bezierCurveTo(xPos + 8, yPos - 10, xPos + 14, yPos + 10, xPos + 21, yPos);   
      gc.bezierCurveTo(xPos + 28, yPos - 10, xPos + 36, yPos + 10, xPos + 43, yPos);   
      gc.bezierCurveTo(xPos + 50, yPos - 10, xPos + 58, yPos + 10, xPos + 65, yPos);   
      gc.stroke();
   }

   private void drawSecondState(GraphicsContext gc) { 
      gc.setStroke(Color.LIGHTBLUE);
      gc.beginPath();
      gc.moveTo(xPos - 2, yPos - 5);   
      gc.bezierCurveTo(xPos + 6, yPos + 5, xPos + 12, yPos - 15, xPos + 19, yPos - 5);   
      gc.bezierCurveTo(xPos + 26, yPos + 5, xPos + 34, yPos - 15, xPos + 41, yPos - 5);  
      gc.bezierCurveTo(xPos + 48, yPos + 5, xPos + 56, yPos - 15, xPos + 63, yPos - 5);  
      gc.stroke(); 
   }

   private void drawThirdState(GraphicsContext gc) { 
      gc.setStroke(Color.DEEPSKYBLUE);
      gc.beginPath();
      gc.moveTo(xPos + 2, yPos + 5);   
      gc.bezierCurveTo(xPos + 10, yPos - 5, xPos + 17, yPos + 15, xPos + 24, yPos + 5);   
      gc.bezierCurveTo(xPos + 31, yPos - 5, xPos + 39, yPos + 15, xPos + 46, yPos + 5);   
      gc.bezierCurveTo(xPos + 53, yPos - 5, xPos + 61, yPos + 15, xPos + 68, yPos + 5);   
      gc.stroke(); 
   }

   // Vertical squiggly lines
   private void drawFirstStateVertical(GraphicsContext gc) { 
      gc.setStroke(Color.BLUE);
      gc.beginPath();
      gc.moveTo(xPos, yPos);   
      gc.bezierCurveTo(xPos - 10, yPos + 8, xPos + 10, yPos + 14, xPos, yPos + 21);   
      gc.bezierCurveTo(xPos - 10, yPos + 28, xPos + 10, yPos + 36, xPos, yPos + 43);   
      gc.bezierCurveTo(xPos - 10, yPos + 50, xPos + 10, yPos + 58, xPos, yPos + 65);   
      gc.stroke();
   }

   private void drawSecondStateVertical(GraphicsContext gc) { 
      gc.setStroke(Color.LIGHTBLUE);
      gc.beginPath();
      gc.moveTo(xPos - 5, yPos - 2);   
      gc.bezierCurveTo(xPos + 5, yPos + 6, xPos - 15, yPos + 12, xPos - 5, yPos + 19);   
      gc.bezierCurveTo(xPos + 5, yPos + 26, xPos - 15, yPos + 34, xPos - 5, yPos + 41);  
      gc.bezierCurveTo(xPos + 5, yPos + 48, xPos - 15, yPos + 56, xPos - 5, yPos + 63);  
      gc.stroke(); 
   }

   private void drawThirdStateVertical(GraphicsContext gc) { 
      gc.setStroke(Color.DEEPSKYBLUE);
      gc.beginPath();
      gc.moveTo(xPos + 5, yPos + 2);   
      gc.bezierCurveTo(xPos - 5, yPos + 10, xPos + 15, yPos + 17, xPos + 5, yPos + 24);   
      gc.bezierCurveTo(xPos - 5, yPos + 31, xPos + 15, yPos + 39, xPos + 5, yPos + 46);   
      gc.bezierCurveTo(xPos - 5, yPos + 53, xPos + 15, yPos + 61, xPos + 5, yPos + 68);   
      gc.stroke(); 
   }

   public void setOn(boolean isOn) { 
      this.isOn = isOn; 
   }
   
   
    public boolean colliding(double playerX, double playerY) {
       // Define the bounding box for the base plate only
       double baseXStart = this.xPos;           // Left side of the base
       double baseXEnd = this.xPos + 75;        // Right side of the base (width = 20 pixels)
       double baseYStart = this.yPos - 5;           // Top side of the base
       double baseYEnd = this.yPos + 10;        // Bottom side of the base (height = 40 pixels)
   
       // Calculate player's bounding box
       double playerXStart = playerX;                     // Left side of the player
       double playerXEnd = playerX + PLAYER_SIZE;         // Right side of the player
       double playerYStart = playerY;                     // Top side of the player
       double playerYEnd = playerY + PLAYER_SIZE;         // Bottom side of the player
   
       // Check for collision: overlap of rectangles
       boolean collisionX = (playerXEnd >= baseXStart && playerXStart <= baseXEnd);
       boolean collisionY = (playerYEnd >= baseYStart && playerYStart <= baseYEnd);
   
       // Return true if there is a collision
       return collisionX && collisionY && isOn;
   }
   
   //Write the toString
   public String toString(){
      String toReturn = "electricity "+squareX+" "+squareY+" "+mechID+" "+xPos+" "+yPos+" "+xCoordinate+" "+yCoordinate+ " " + isVertical + " " + isOn + "\n";
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