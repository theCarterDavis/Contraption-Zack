//Door Class
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Door extends Mechanism {
   
   //private instance variables for door
   private int squareX, squareY; 
   private double newRoomPlayerX, newRoomPlayerY;
   private boolean canWalk;
   private String mechID, position, room;
   private double xCordinate, yCordinate; //where to draw on the specific square
   
   //Constructor
   public Door(int squareX, int squareY, String mechID, boolean canWalk, String position, String room, double newRoomPlayerX, double newRoomPlayerY){
   
      //set the values
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      this.position = position;
      this.room = room;
      this.newRoomPlayerX = newRoomPlayerX;
      this.newRoomPlayerY = newRoomPlayerY;
   }
   
   public boolean offAxis() {
      return false; 
   } 
   //Overide the abstract methods
   public void update(){ //update does nothing since it is a door
      return;
   }
   public boolean getState(){ //door is always up
      return true;
   }
   @Override
   public void draw(GraphicsContext gc) {
       // Door dimensions
       final int DOOR_WIDTH = 50;
       final int DOOR_HEIGHT = 50;
       final int ARROW_SIZE = 20;
   
       // Calculate center position
       double centerX = xCordinate + 37.5; // 75/2
       double centerY = yCordinate + 37.5; // 75/2
   
       // Draw door (grey rectangle)
       gc.setFill(Color.LIGHTGREY);
       gc.fillRect(centerX - DOOR_WIDTH/2, centerY - DOOR_HEIGHT/2, DOOR_WIDTH, DOOR_HEIGHT);
   
       // Draw arrow (red)
       gc.setFill(Color.RED);
       double[] arrowX = new double[3];
       double[] arrowY = new double[3];
   
       switch (position.toLowerCase()) {
           case "up":
               arrowX = new double[]{centerX - ARROW_SIZE/2, centerX, centerX + ARROW_SIZE/2};
               arrowY = new double[]{centerY + ARROW_SIZE/2, centerY - ARROW_SIZE/2, centerY + ARROW_SIZE/2};
               break;
           case "down":
               arrowX = new double[]{centerX - ARROW_SIZE/2, centerX, centerX + ARROW_SIZE/2};
               arrowY = new double[]{centerY - ARROW_SIZE/2, centerY + ARROW_SIZE/2, centerY - ARROW_SIZE/2};
               break;
           case "left":
               arrowX = new double[]{centerX + ARROW_SIZE/2, centerX - ARROW_SIZE/2, centerX + ARROW_SIZE/2};
               arrowY = new double[]{centerY - ARROW_SIZE/2, centerY, centerY + ARROW_SIZE/2};
               break;
           case "right":
               arrowX = new double[]{centerX - ARROW_SIZE/2, centerX + ARROW_SIZE/2, centerX - ARROW_SIZE/2};
               arrowY = new double[]{centerY - ARROW_SIZE/2, centerY, centerY + ARROW_SIZE/2};
               break;
       }
   
       gc.fillPolygon(arrowX, arrowY, 3);
   }
   public void setCordinates(double xCordinate, double yCordinate){ //set the x and y to draw the square
      this.xCordinate = xCordinate;
      this.yCordinate = yCordinate;
      
      return;
   }
   public int getSquareX(){ //get Square X
      return squareX;
   }
   public int getSquareY(){ //get square Y
      return squareY;
   }
   public String getName(){ //get the name of mechanism
      return "door";
   }
   public String getID(){ //get the mechID
      return mechID;
   }
   public boolean canWalk(){
      return canWalk;
   }
   public String toString(){
      String toReturn = "door "+squareX+" "+squareY+" "+mechID+" "+canWalk+" "+position+" "+room+" "+newRoomPlayerX+" "+newRoomPlayerY+"\n";
      return toReturn;
   }

   
   //Create unique door methods
   public String getRoom(){
      return room;
   }
   public double getNewRoomPlayerX(){
      return newRoomPlayerX;
   }
   public double getNewRoomPlayerY(){
      return newRoomPlayerY;
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