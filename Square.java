//Square Class

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class Square
{
   //Create the private instance variables used for the Square
   private double xPos, yPos;
   private String color;
   private boolean canWalk;
   private Mechanism mech;
   private int mechX, mechY;
   private ImageView imageView;
   private String imagePath; 
   
   //Create the constructor
   public Square(double xPos, double yPos, String color, boolean canWalk, int mechX, int mechY){
      //Set the values
      this.xPos = xPos;
      this.yPos = yPos;
      this.color = color;
      this.canWalk = canWalk;
      this.mechX = mechX;
      this.mechY = mechY;  
      // Default image parameters did this so didnt add more requirements to the text file 
      this.imageView = null; 
      this.imagePath = ""; 
      
   }
   
   public void setImagePath(String imagePath) { 
       this.imagePath = imagePath; 
       try { 
           // Load the image
           Image originalImage = new Image(imagePath);
   
           // Crop the image to remove transparent space
           Image croppedImage = cropTransparentImage(originalImage);
   
           // Create ImageView and set the cropped image
           this.imageView = new ImageView();
           this.imageView.setImage(croppedImage);
           
           // Set the fit dimensions to the size of the square (75x75)
           this.imageView.setFitWidth(75);
           this.imageView.setFitHeight(75);
           
           // Stretch the image if necessary to fill the whole square
           this.imageView.setPreserveRatio(false);
           this.imageView.setSmooth(true); 
   
       } catch (Exception e) { 
           System.out.println("Error Creating image"); 
           e.printStackTrace(); 
           System.exit(1); 
       } 
   }

   // Method to crop out transparent space from the image
   private Image cropTransparentImage(Image image) {
       PixelReader reader = image.getPixelReader();
       int width = (int) image.getWidth();
       int height = (int) image.getHeight();
   
       // Variables to store the cropping bounds
       int minX = width;
       int minY = height;
       int maxX = 0;
       int maxY = 0;
   
       // Loop through all pixels to find non-transparent boundaries
       for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
               Color color = reader.getColor(x, y);
               if (color.getOpacity() > 0) { // Check if pixel is not transparent
                   if (x < minX) minX = x;
                   if (x > maxX) maxX = x;
                   if (y < minY) minY = y;
                   if (y > maxY) maxY = y;
               }
           }
       }
   
       // If the image is fully transparent, return the original image
       if (minX > maxX || minY > maxY) {
           return image;
       }
   
       // Crop the image using the found bounds
       int croppedWidth = maxX - minX + 1;
       int croppedHeight = maxY - minY + 1;
       WritableImage croppedImage = new WritableImage(reader, minX, minY, croppedWidth, croppedHeight);
   
       return croppedImage;
   }
      
      
      
   //Create the draw methods
   public void draw(GraphicsContext gc){ //draw the Square on the board
         Color c = Color.valueOf(color);
         Color b = Color.valueOf("DARKGREY");

         //System.out.print("drawing square " + c);
         gc.setFill(c);
         gc.fillRect(xPos,yPos,75,75);
         
         
         if(color != "BLACK"){
         
            gc.setStroke(b);
            gc.strokeRect(xPos,yPos,75,75);
            
         }
         
         if(color == "DARKGREY" && canWalk == false){
            
            gc.setStroke(Color.valueOf("BLACK"));
            gc.setLineWidth(3.0);
            double yLoc;
            for(int i = 1; i <= 5;i++){
               yLoc = yPos + (i * 15);
               gc.strokeLine(xPos,yLoc,(xPos + 75),yLoc);
            
            }
              
         }
         
         if (!imagePath.equals("")) { 
            
            gc.drawImage(imageView.getImage(), xPos, yPos, 75, 75);
            
         } 
        
   }
   
     
   //Create the accessors
   public boolean getWalk(){
      return canWalk;
   }  
   public double getX() { 
      return xPos; 
   }   
   public double getY() { 
      return yPos; 
   }  
   public String getColor() { 
      return  color;
   } 
   public int getMechX(){
      return mechX;
   }
   public int getMechY(){
      return mechY;
   }
   public void setWalk(boolean canWalk){
      this.canWalk = canWalk;
   }

}