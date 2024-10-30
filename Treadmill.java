// Treadmill Mechanism

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Treadmill extends Mechanism {

   // Private instance variables
   private int squareX, squareY;
   private double xCoordinate, yCoordinate, treadX, treadY;
   private boolean turnedOn;
   private String mechID, position;
   private int[] linePositions = new int[5]; // Positions of the black lines
   private boolean treadLoaded = false;
   private boolean canWalk;

   // Create the constructor
   public Treadmill(int squareX, int squareY, String mechID, boolean turnedOn, String position, double treadX, double treadY) {
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      this.turnedOn = turnedOn;
      this.position = position;
      this.treadX = treadX;
      this.treadY = treadY;
   }

   // Override all abstract methods
   public void update() {
      if (turnedOn && treadLoaded) {
         for (int i = 0; i < linePositions.length; i++) {
            // Move the lines based on the position
            if (position.equals("up")) {
               linePositions[i] -= 1; // Move up
               if (linePositions[i] < yCoordinate) {
                  linePositions[i] = (int) (yCoordinate + 75); // Wrap around
               }
            } else if (position.equals("down")) {
               linePositions[i] += 1; // Move down
               if (linePositions[i] >= yCoordinate + 75) {
                  linePositions[i] = (int) yCoordinate; // Wrap around
               }
            } else if (position.equals("right")) {
               linePositions[i] += 1; // Move right
               if (linePositions[i] >= xCoordinate + 75) {
                  linePositions[i] = (int) xCoordinate; // Wrap around
               }
            } else if (position.equals("left")) {
               linePositions[i] -= 1; // Move left
               if (linePositions[i] < xCoordinate) {
                  linePositions[i] = (int) (xCoordinate + 75); // Wrap around
               }

           }
         }
      }
   }
   public void turnOn(boolean turnOn) {
      this.turnedOn = turnOn;
   }

   public void setState() {
      if (turnedOn) {
         if (position.equals("up")) {
            position = "down";
         } else if (position.equals("down")) {
            position = "up";
         }
      }
   }

   public boolean getState() {
      return turnedOn;
   }

   public int getSquareX() {
      return squareX;
   }

   public int getSquareY() {
      return squareY;
   }

   public void setCordinates(double x, double y) {
      this.xCoordinate = x;
      this.yCoordinate = y;

      // Initialize the positions of the lines
      if(position.equals("up") || position.equals("down")) {
         // Initialize the positions of the lines
         for (int i = 0; i < linePositions.length; i++) {
            linePositions[i] = (int) yCoordinate + i * 15; // Start positions
         }
      } else if(position.equals("left") || position.equals("right")) { 
         // Initialize the positions of the lines
         for (int i = 0; i < linePositions.length; i++) {
            linePositions[i] =  (int) xCoordinate + i * 15; // Start positions
         }
      }

      treadLoaded = true;
   }

   public String getName() {
      return "treadmill";
   }

   public String getID() {
      return mechID;
   }

   public boolean canWalk() {
      return true;
   }

   public String toString() {
      String toReturn = "treadmill " + squareX + " " + squareY + " " + mechID + " " + turnedOn + " " + position + " " + treadX + " " + treadY + "\n";
      return toReturn;
   }

   public boolean offAxis() {
      return false;
   }

   public void draw(GraphicsContext gc) {
      if(position.equals("up") || position.equals("down")) { 
         // Draw the treadmill background
         gc.setFill(Color.BURLYWOOD);
         gc.fillRect(xCoordinate, yCoordinate, 75, 75);
   
         // Draw the black lines
         gc.setStroke(Color.BLACK);
         for (int i = 0; i < linePositions.length; i++) {
            gc.strokeLine(xCoordinate, linePositions[i], xCoordinate + 75, linePositions[i]); // Horizontal lines
         }
      } else if(position.equals("left") || position.equals("right")) {
         gc.setFill(Color.BURLYWOOD);
         gc.fillRect(xCoordinate, yCoordinate, 75, 75);
         
         // Draw the black lines
         gc.setStroke(Color.BLACK);
         for (int i = 0; i < linePositions.length; i++) {
            gc.strokeLine(linePositions[i], yCoordinate, linePositions[i], yCoordinate + 75);
         }
      }  

   }

   // Create a move on treadmill method
   public boolean canMove(double playerX, double playerY) {
      // Define the player's dimensions
      int playerWidth = 40;
      int playerHeight = 40;

      // Calculate the boundaries of the player
      double playerRight = playerX + playerWidth;
      double playerBottom = playerY + playerHeight;

      // Calculate the boundaries of the treadmill
      double treadmillRight = xCoordinate + 75; // Treadmill is 75 units wide
      double treadmillBottom = yCoordinate + 75; // Treadmill is 75 units high

      // Check for overlap
      boolean isTouching = playerX < treadmillRight && playerRight > xCoordinate &&
                           playerY < treadmillBottom && playerBottom > yCoordinate;

      // If the treadmill is turned off and the player is touching it, return false
      if (!turnedOn && isTouching) {
         return false;
      }

      // Otherwise, return true
      return true;

   }

   public String getPosition() {
      return position;
   }

   // Unique treadmill methods (going to use old spring code)
   public double getTreadX() {
      return treadX;
   }

   public double getTreadY() {
      return treadY;
   }

   @Override
   public boolean offAxisX() {
      return false;
   }

   @Override
   public boolean offAxisY() {
      return false;
   }
}
