//Water class for the water to animate

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Water extends Mechanism{
   
   //Create the private variables
   private int squareX, squareY;
   private double xCoordinate, yCoordinate;
   private boolean canWalk;
   private String mechID;
   private double[] yOffsets;
   private static final int WAVE_COUNT = 8;
   private static final double WAVE_HEIGHT = 3;
   private static final double SPEED = 0.05;
   
   //Make the constructor
   public Water(int squareX, int squareY, String mechID, boolean canWalk){
      
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.canWalk = canWalk;
      
      
      this.yOffsets = new double[WAVE_COUNT];
        for (int i = 0; i < WAVE_COUNT; i++) {
            yOffsets[i] = Math.random() * Math.PI * 2;
        }
   }
   
   //overide all the abstract methods
   public void update(){
      for (int i = 0; i < WAVE_COUNT; i++) { //Update offsets for animation
            yOffsets[i] += SPEED;
      }
      return;
   }
   public boolean getState(){
      return true;
   }
   public int getSquareX(){
      return squareX;
   }
   public int getSquareY(){
      return squareY;
   }
   public void setCordinates(double x, double y){
      this.xCoordinate = x;
      this.yCoordinate = y;
   }
   public String getName(){
      return "water";
   }
   public String getID(){
      return mechID;
   }
   public boolean canWalk(){
      return canWalk;
   }
   public String toString(){
      String toReturn = "water "+squareX+" "+squareY+" "+mechID+" "+canWalk+"\n";
      return toReturn;
   }
   public boolean offAxis(){
      return false;
   }  
   public void draw(GraphicsContext gc){
      // Clear the previous drawing area
      gc.clearRect(xCoordinate, yCoordinate, 74, 75);

      // Draw base water color
      gc.setFill(Color.rgb(0, 120, 255, 0.7));
      gc.fillRect(xCoordinate, yCoordinate, 74, 75);

      // Draw seven spaced vertical waves
      for (int i = 0; i < 7; i++) { // Loop for seven vertical waves
          double centerX = xCoordinate + 10 + (i * 10); // Centering and spacing each wave
          
          // Draw vertical waves
          for (int wave = 0; wave < WAVE_COUNT; wave++) {
              gc.beginPath();
              gc.setStroke(Color.rgb(255, 255, 255, 0.2 + (wave * 0.02))); // Varying opacity
              gc.setLineWidth(1 + (wave * 0.2)); // Varying line width

              for (int y = 0; y <= 75; y += 2) {
                  double x = centerX + Math.sin(y * 0.2 + yOffsets[wave]) * WAVE_HEIGHT; // Centering horizontally
                  if (y == 0) {
                      gc.moveTo(x, yCoordinate + y);
                  } else {
                      gc.lineTo(x, yCoordinate + y);
                  }
              }
              gc.stroke();
          }
      }

      // Draw seven spaced horizontal waves
      for (int i = 0; i < 7; i++) { // Loop for seven horizontal waves
          double centerY = yCoordinate + 10 + (i * 10); // Centering and spacing each wave
          
          // Draw horizontal waves
          for (int wave = 0; wave < WAVE_COUNT; wave++) {
              gc.beginPath();
              gc.setStroke(Color.rgb(255, 255, 255, 0.2 + (wave * 0.02))); // Varying opacity
              gc.setLineWidth(1 + (wave * 0.2)); // Varying line width

              for (int x = 0; x <= 75; x += 2) {
                  double y = centerY + Math.sin(x * 0.2 + yOffsets[wave]) * WAVE_HEIGHT; // Centering vertically
                  if (x == 0) {
                      gc.moveTo(xCoordinate + x, y);
                  } else {
                      gc.lineTo(xCoordinate + x, y);
                  }
              }
              gc.stroke();
          }
      }

      // Draw lighter overlay to create depth
      gc.setFill(Color.rgb(255, 255, 255, 0.1));
      gc.fillRect(xCoordinate, yCoordinate, 74, 75);   
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