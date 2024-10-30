//Spikes
//spike xcord ycord mechid(colorNumber) canwalk isUP Color order(1-4) orientation(True = horisantal)

//Button Mechanism Class for the new file format

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpikeMech extends Mechanism{
   
   //Create the private instance variables
   private boolean isDown, isHorizontal;
   private int squareX, squareY, position;
   private double xCordinate, yCordinate, cordShiftX, cordShiftY;
   private String mechID, color;
   private boolean offAxis = false;
   
   //Variables for the spike size
   private static final int CIRCLE_RADIUS = 8;
   private static final int SMALL_CIRCLE_RADIUS = 5;
   private static final int SQUARE_SIZE = 75;

  
   //make the construcor
   public SpikeMech(int squareX, int squareY, String mechID, boolean isDown, String color, boolean isHorizontal, int position){
      
      //Set the values equal to eachother
      this.squareX = squareX;
      this.squareY = squareY;
      this.mechID = mechID;
      this.isDown = isDown;
      this.color = color;
      this.position = position;
      this.isHorizontal = isHorizontal;
      
      
   }
   
   //Override all of the abstract methods
   public void update(){ //Nothing for the updates yet
   }  
   //Swaps state of the spike
   public void setState(){
      isDown = !isDown;
   }
   public boolean getState(){ //get the state if the button is up
      return isDown;
   }
   public void draw(GraphicsContext gc){ //draw the buttons
      
        Color c = Color.web(color);
        gc.setFill(c);

        int radius = isDown ? SMALL_CIRCLE_RADIUS : CIRCLE_RADIUS;
        int totalCircles = 3;
        int totalRows = 4;
        
        // Calculate the spacing dynamically
        double spacingX = (SQUARE_SIZE - (2 * radius * totalCircles)) / (totalCircles + 1);
        
        //Calculation to move to the right square on the board
        cordShiftX = 75 * squareX; // Center the button
        cordShiftY = 75 * squareY; // Center the button


        // Reduce vertical spacing
        double totalHeightNeeded = 2 * radius * totalRows;
        double remainingSpace = SQUARE_SIZE - totalHeightNeeded;
        double spacingY = remainingSpace / (totalRows + 1);
        
        for (int i = 0; i < totalCircles; i++) {
            double x, y;
            
            if (isHorizontal) {
                x = spacingX + radius + (2 * radius + spacingX) * i;
                y = spacingY + radius + (2 * radius + spacingY) * (position - 1);
            } else {
                x = spacingY + radius + (2 * radius + spacingY) * (position - 1);
                y = spacingX + radius + (2 * radius + spacingX) * i;
            }
            
            gc.fillOval(x - radius + cordShiftX, y - radius + cordShiftY, radius * 2, radius * 2);
            gc.setStroke(Color.valueOf("BLACK"));
            gc.setLineWidth(1.5);
            gc.strokeOval(x - radius + cordShiftX, y - radius + cordShiftY, radius * 2, radius * 2);
       
      }
   }
   public int getSquareX(){ //get the square X values
      return squareX;
   }
   public int getSquareY(){ //get the Square Y values
      return squareY;     
   }
   public void setCordinates(double x, double y){ //set the various cordinates of the square the button lies on
      this.xCordinate = x;
      this.yCordinate = y;
   }
   public String getName(){
      return "spike";
   }
   public String getID(){
      return mechID;
   }
   public boolean canWalk(){
      return isDown;
   }
   public boolean offAxis(){
      return offAxis();
   }
   
   
   public boolean canPassSpike(double newPlayerX, double newPlayerY) { //detect if tht player can walk through the gate or not
        if (isDown) return true;
        
        double spikeLeft;
        double spikeTop;
        double spikeRight;
        double spikeBottom;
        double spikeWidth = 18.75;        
        double playerRight = newPlayerX + 40;
        double playerBottom = newPlayerY + 40;

     
        if(isHorizontal){
        
           spikeLeft = cordShiftX;
           spikeTop = cordShiftY + (18.75 * (position-1));
           spikeRight = spikeLeft + 75;
           spikeBottom = spikeTop + 18.75;
           //System.out.println("Spike box =" + spikeLeft + " " + spikeTop);

           
           return (playerRight < spikeLeft || 
             newPlayerX > spikeRight || 
             playerBottom < spikeTop || 
             newPlayerY > spikeBottom);
           
        } else {
        
           spikeLeft = cordShiftX + (18.75 * (position-1));
           spikeTop = cordShiftY;
           spikeRight = spikeLeft + 18.5;
           spikeBottom = cordShiftY +75;
           //System.out.println("Spike box =" + spikeLeft + " " + spikeTop);
            
            return (playerRight < spikeLeft || 
             newPlayerX > spikeRight || 
             playerBottom < spikeTop || 
             newPlayerY > spikeBottom);
        
        }
   }
   
   public boolean getIsUp(){
      return isDown;
   }
   public void setIsUp(boolean isUp){
      this.isDown = isUp;
   }
   
   
   public String toString(){ //toString
      String toReturn = "spike "+squareX+" "+squareY+" "+mechID+" "+" "+isDown+" "+color+" "+isHorizontal+" "+position+"\n";
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

