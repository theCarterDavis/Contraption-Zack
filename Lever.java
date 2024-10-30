import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList; 

public class Lever extends Mechanism { 
   private int xPos, yPos;
   private int squareX, squareY; 
   private double xCoordinate, yCoordinate; 
   private boolean isUp; 
   private String mechID;
   private static final int PLAYER_SIZE = 40; // Player width and height
   private ArrayList<String> IDs = new ArrayList<>(); 
   private boolean offAxis = false;
   
   public Lever(String mechID, int xPos, int yPos, int squareX, int squareY, double xCoordinate, double yCoordinate, boolean isUp) { 
      this.mechID = mechID; 
      this.xPos = xPos; 
      this.yPos = yPos; 
      this.squareX = squareX; 
      this.squareY = squareY; 
      this.xCoordinate = xCoordinate; 
      this.yCoordinate = yCoordinate; 
      this.isUp = isUp; 
   } 
   
   public void addIDs(String id) { 
      this.IDs.add(id);
   } 
   
   public ArrayList<String> getIDs() {
      return this.IDs; 
   } 
     
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
      return this.isUp; 
   } 
   
   @Override 
   public void draw(GraphicsContext gc) { 
     
      if(this.isUp) { 
         this.drawUpState(gc); 
      } else {    
         this.drawDownState(gc); 
      }  
              
   } 
   
   public void drawUpState(GraphicsContext gc) { 
      // Base plate 
      gc.setFill(Color.GREY); 
      gc.fillRect(this.xPos, this.yPos, 20, 40); 
      
      //Black shadow for lever 
      gc.setFill(Color.BLACK); 
      gc.fillRect(this.xPos + 5, this.yPos + 5, 3, 30);
      gc.fillRect(this.xPos + 12, this.yPos + 5, 3, 30);
      
      //Rods connecting to the handle 
      gc.setFill(Color.SILVER); 
      gc.fillRect(this.xPos + 5, this.yPos - 10, 3, 30);
      gc.fillRect(this.xPos + 12, this.yPos - 10, 3, 30);
      
      // Red handle 
      gc.setFill(Color.RED); 
      gc.fillRect(this.xPos, this.yPos - 15, 20, 10);
   } 
   
   public boolean colliding(double playerX, double playerY) {
       // Define the bounding box for the base plate only
       double baseXStart = this.xPos;           // Left side of the base
       double baseXEnd = this.xPos + 20;        // Right side of the base (width = 20 pixels)
       double baseYStart = this.yPos;           // Top side of the base
       double baseYEnd = this.yPos + 40;        // Bottom side of the base (height = 40 pixels)
   
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
   
   public void drawDownState(GraphicsContext gc) { 
      //Base plate 
      gc.setFill(Color.GREY); 
      gc.fillRect(this.xPos, this.yPos, 20, 40); 
      
      //Black shadow for lever 
      gc.setFill(Color.BLACK); 
      gc.fillRect(this.xPos + 5, this.yPos + 5, 3, 30);
      gc.fillRect(this.xPos + 12, this.yPos + 5, 3, 30);
      
      //Rods connecting to the handle 
      gc.setFill(Color.SILVER); 
      gc.fillRect(this.xPos + 5, this.yPos + 25, 3, 30);
      gc.fillRect(this.xPos + 12, this.yPos + 25, 3, 30);
      
      // Red handle 
      gc.setFill(Color.RED); 
      gc.fillRect(this.xPos, this.yPos + 45, 20, 10);
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
   public void setCordinates(double x, double y) { 
      this.xCoordinate = x; 
      this.yCoordinate = y; 
   } 
   
   @Override 
   public String getName() { 
      return "";
   } 
   
   @Override 
   public String getID() { 
      return this.mechID; 
   }  
   
   // Not sure if this should be true or false because i think in game runner 
   // We are using can walk to determine if we can walk over a whole square. 
   @Override 
   public boolean canWalk() { 
      return true; 
   } 
   
   public void setUp() { 
      this.isUp = !this.isUp; 
   } 
   
   public String toString(){ //toString
      String ids = " " + this.IDs.size(); 
      for(int i = 0; i < this.IDs.size(); i++) { 
         ids += " " + this.IDs.get(i); 
      } 

      String toReturn = "lever "+mechID+" "+xPos+" "+yPos+" "+squareX+" "+squareY+" "+xCoordinate+" "+yCoordinate+ " " + isUp + 
                        ids  + "\n";
      return toReturn;
   }
      
} 