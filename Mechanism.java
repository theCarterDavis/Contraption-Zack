import java.util.ArrayList; 
import javafx.scene.canvas.*;

public abstract class Mechanism { 

   private static ArrayList<Mechanism> allMechanisms = new ArrayList<>(); 
   
   
   // Creates arrayList for mechanisms 
   public Mechanism() { 
   
      this.allMechanisms.add(this); 
   
   } 
        
   //abstract methods
   public abstract void update();  
   public abstract boolean getState(); 
   public abstract void draw(GraphicsContext gc);
   public abstract int getSquareX();
   public abstract int getSquareY();
   public abstract void setCordinates(double x, double y);
   public abstract String getName();
   public abstract String getID();
   public abstract boolean canWalk();
   public abstract String toString();
   public abstract boolean offAxis();
   public abstract boolean offAxisX();
   public abstract boolean offAxisY();



   
   public void printAllMechanisms() { 
      for (Mechanism mech : allMechanisms) {
            System.out.println(mech);
        }
   } 
   
   public void clearAllMechanisms() { 
      System.out.println("Clearing all mechanisms");
   } 
   
   public ArrayList<Mechanism> getAllMechanisms() { 
      
      return this.allMechanisms; 
   }  
   
}  