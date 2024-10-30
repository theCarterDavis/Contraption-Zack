//This is the Gate Class for the Mechanisms
//After closing the gate stays closed for 10 seconds and stay open for 3 seconds

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gate extends Mechanism {
   
   //Create the private instance variables for the gate
   private boolean canWalk, isOpen;
   private int squareX, squareY, startTime;
   private double xCordinate, yCordinate;
   private String mechID, gatePosition;
   private double playerX, playerY; //make sure the gate doesn't close on the player
   private static final int GATE_WIDTH = 15;
   private static final int GATE_LENGTH = 75;
   private boolean gateBegin = false; //start updating the gate only after the delay of time has elapsed
   private boolean isPaused = false;
   private boolean offAxis = false;
   
   // Timing variables
   private long lastToggleTime; // When the gate last changed state
   private long elapsedTime; // Time since the last toggle
   private long totalPausedTime; // New field to track total paused time
      
   //Create the constructor
   public Gate(int squareX, int squareY, String mechID, boolean canWalk, boolean isOpen, int startTime, String gatePosition){
      //Set the values
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      this.isOpen = isOpen;
      this.startTime = startTime; // convert start time to milliseconds
      this.lastToggleTime = System.currentTimeMillis(); // Initialize the toggle time
      this.elapsedTime = 0;
      this.gatePosition = gatePosition;
   }
   
   //Overide all of the abstract methods
   public void update() {
      
      long currentTime = System.currentTimeMillis() - totalPausedTime;
      
      elapsedTime = (currentTime-totalPausedTime) - lastToggleTime;

      if (!gateBegin && elapsedTime >= startTime*1000) {
          gateBegin = true;
          isOpen = true;
          lastToggleTime = currentTime;
          elapsedTime = 0;
      } 
      else if (gateBegin) {
          if (isOpen) {
              if (elapsedTime >= 4000) { // Time to close
                  isOpen = false;
                  lastToggleTime = currentTime;
                  elapsedTime = 0;
              }
          } 
          else {
              if (elapsedTime >= 12000) { // Time to open
                  isOpen = true;
                  lastToggleTime = currentTime;
                  elapsedTime = 0;
              }
          }
      }
  }
   public boolean getState(){
      return isOpen;
   } 
   public void draw(GraphicsContext gc){ //draw the gate
      if (isPhysicallyClosed()) { //draw only if is open
        gc.setFill(Color.GRAY);
        switch (gatePosition.toLowerCase()) {
            case "left":
                gc.fillRect(xCordinate, yCordinate, GATE_WIDTH, GATE_LENGTH);
                break;
            case "right":
                gc.fillRect(xCordinate + 60, yCordinate, GATE_WIDTH, GATE_LENGTH);
                break;
            case "up":
                gc.fillRect(xCordinate, yCordinate, GATE_LENGTH, GATE_WIDTH);
                break;
            case "down":
                gc.fillRect(xCordinate, yCordinate + 60, GATE_LENGTH, GATE_WIDTH);
                break;
            default:
                break;
        }
     }   
   }
   public boolean offAxis(){
      return offAxis;
   }
   public int getSquareX(){ //return the squareX
      return squareX;
   }
   public int getSquareY(){ //return the squareY
      return squareY;
   }
   public void setCordinates(double x, double y){ //set player Cordinates
      this.xCordinate = x;
      this.yCordinate = y;
   }
   public String getName(){ //get mech name
      return "gate";
   }
   public String getID(){ //get the mechID
      return mechID;
   }
   public boolean canWalk(){ //get the canWalk value
      return canWalk;
   }
   public String toString(){ //toString
      isOpen = false;   
      String toReturn = "gate "+squareX+" "+squareY+" "+mechID+" "+canWalk+" "+isOpen+" "+startTime+" "+gatePosition+"\n";
      return toReturn;
   }
   public void setPauseInterval(long pauseInterval){
      this.totalPausedTime += pauseInterval;
   }
   
   //methods only allowed in gate
   public void setPlayerCoordinates(double playerX, double playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
   }
   public int getGateWidth(){ //get the gate width
      return GATE_WIDTH;
   }
   public int getGateLength(){
      return GATE_LENGTH;
   }
   public void setOpen(boolean isOpen){ //don't have the gate close if the player is on it
      this.isOpen = isOpen;
   }
   
   //Methods to check if the player is in a gate or not and enforce policies based on it
    private boolean isPlayerInGate() { //check to see if the player is on the gate
        double gateLeft = xCordinate;
        double gateTop = yCordinate;
        double gateRight = xCordinate + 75;
        double gateBottom = yCordinate + 75;

        switch (gatePosition.toLowerCase()) {
            case "left":
                return playerX < gateLeft + GATE_WIDTH && playerX + 40 > gateLeft &&
                       playerY < gateTop + GATE_LENGTH && playerY + 40 > gateTop;
            case "right":
                return playerX < gateRight && playerX + 40 > gateRight - GATE_WIDTH &&
                       playerY < gateTop + GATE_LENGTH && playerY + 40 > gateTop;
            case "up":
                return playerX < gateLeft + GATE_LENGTH && playerX + 40 > gateLeft &&
                       playerY < gateTop + GATE_WIDTH && playerY + 40 > gateTop;
            case "down":
                return playerX < gateLeft + GATE_LENGTH && playerX + 40 > gateLeft &&
                       playerY < gateBottom && playerY + 40 > gateBottom - GATE_WIDTH;
            default:
                return false;
        }
    }

   public boolean canWalkThroughGate(double newPlayerX, double newPlayerY) { //detect if tht player can walk through the gate or not
        if (isOpen || isPlayerInGate()) return true;

        double gateLeft = xCordinate;
        double gateTop = yCordinate;
        double gateRight = xCordinate + 75;
        double gateBottom = yCordinate + 75;

        switch (gatePosition.toLowerCase()) {
            case "left":
                return !(newPlayerX < gateLeft + GATE_WIDTH && newPlayerX + 40 > gateLeft &&
                         newPlayerY < gateTop + GATE_LENGTH && newPlayerY + 40 > gateTop);
            case "right":
                return !(newPlayerX < gateRight && newPlayerX + 40 > gateRight - GATE_WIDTH &&
                         newPlayerY < gateTop + GATE_LENGTH && newPlayerY + 40 > gateTop);
            case "up":
                return !(newPlayerX < gateLeft + GATE_LENGTH && newPlayerX + 40 > gateLeft &&
                         newPlayerY < gateTop + GATE_WIDTH && newPlayerY + 40 > gateTop);
            case "down":
                return !(newPlayerX < gateLeft + GATE_LENGTH && newPlayerX + 40 > gateLeft &&
                         newPlayerY < gateBottom && newPlayerY + 40 > gateBottom - GATE_WIDTH);
            default:
                return true;
        }
    }
    // Add this method to check if the gate should be physically closed for timing purpouses
    public boolean isPhysicallyClosed() {
      return !isOpen && !isPlayerInGate();
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