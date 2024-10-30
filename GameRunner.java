//Game Runner Class
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class GameRunner extends Canvas {

   //Create instance variables needed for the GameRunner
   private String fileName = "Room1.txt"; //default filename
   private String newRoom;
   private Scanner read; 
   private Square[][] sq;
   private char[][] sqValues; //for saving the progress of the game       
   private double playerX = 240; //CHANGE BACK 
   private double playerY = 340; //CHANGE BACK 
   private int x, y;
   private ArrayList<Mechanism> allMechanisms = new ArrayList(); 
   private boolean roomChange = false;   
   private boolean springPressed = false;
   private double springTargetX;
   private double springTargetY;
   private int springIndex = 0;
   private int intervalIndex = 0; 
   private boolean startSpringTimer = false; 
   private long springCurrentTime;
   private long springPressTime; 
   private Spring prevSpring; 
   private long[] intervals = {1000, 2000, 3000, 4000, 5000};
   private ArrayList<Spring> springs = new ArrayList<>();  
   private static boolean hasScrewDriver = false; 
   private static boolean hasWrench = false;
   private static boolean hasCrescentWrench = false; 
   private static boolean hasPipe = false;
   private static boolean hasTape = false;  
   
   //instance variables for reloading the room (Sorry I need the x and y when the room loads in for the restart area)
   private double roomStartX = 240;
   private double roomStartY = 340;
   
   // Collision with Lever 
   private long leverLastCollisionTime = 0; // To store the last collision time
   private static final long LEVER_COLLISION_DELAY = 400; // Delay in milliseconds (adjust as needed)
   
   // Collision with Spring 
    private long springLastCollisionTime = 0; // To store the last collision time
   private static final long SPRING_COLLISION_DELAY = 400; // Delay in milliseconds (adjust as needed)
   
   //Static boolean for saving the orange spikes on other rooms
   private static boolean room8Button = false; 
   private static boolean room8OrangeSpike = false;
   private static boolean room7PurpleButton = true;
   private static boolean room10Button = false; 
   
   //Create the constructor (no PreGame)
   public GameRunner() {
      //set the width and the height of the canvas
      setWidth(680);
      setHeight(700);
   }
   
   public GameRunner(String fileName, double playerX, double playerY){ //loaded game progress is being passed in.
      this.fileName = fileName;
      this.playerX = playerX;
      this.playerY = playerY;
      setWidth(680);
      setHeight(700);
   }
   
   //Draw the Room
   public void drawRoom(GraphicsContext gc){
      gc = getGraphicsContext2D();
      gc.setFill(Color.BLACK);
      gc.fillRect(0,0,680,700);
      
      //load in the squares
      try {
         read = new Scanner(new File(fileName));
         
         //Read in the Roomstart PlayerX and the RoomstartPlayer Y
         this.roomStartX = read.nextDouble();
         this.roomStartY = read.nextDouble();
         
         //Create the dyanmic mapping
         x = read.nextInt();
         y = read.nextInt();
         this.sq = new Square[x][y];  
         this.sqValues = new char[x][y]; //for the saving the game
                
         //Calculate start for the squares
         int xStart = (9-x)/2 * 75;
         int yStart = (9-y)/2 * 75;
         
         //Loop over the files and add it to the 2d array THIS IS BACKWARDS ON PURPOUSE BECAUSE THE CLIENT FILLS OUT THE ROWS BEFORE THE COLUMNS
         for(int j=0;j<y;j++){
            double yPos = yStart + 75 * j; //yvalue calculated
            for(int i=0;i<x;i++){
               if(read.hasNext()){  
               
                  //Square Values
                   double xPos = xStart + 75 * i;
                   char sqVal = read.next().charAt(0);
                   switch(sqVal){
                     case 'v': 
                        sq[i][j] = new Square(xPos,yPos,"BLACK",false,i,j);
                        sqValues[i][j] = 'v';
                        break;
                     case 'b': 
                        sq[i][j] = new Square(xPos,yPos,"BLACK",true,i,j);
                        sqValues[i][j] = 'b';
                        break;
                     case 'w': 
                        sq[i][j] = new Square(xPos,yPos,"WHITE",true,i,j);
                        sqValues[i][j] = 'w';
                        break;
                     case 'g':
                        sq[i][j] = new Square(xPos,yPos,"GREY",true,i,j);
                        sqValues[i][j] = 'g';
                        break;
                     case 'c':
                        sq[i][j] = new Square(xPos,yPos,"DIMGREY",true,i,j);
                        //sq[i][j].setImagePath("images/cobbolstone.png"); 
                        sqValues[i][j] = 'c';
                        break;
                     case 't':
                        sq[i][j] = new Square(xPos,yPos,"WHITE",true,i,j);
                        sq[i][j].setImagePath("images/tile.png"); 
                        sqValues[i][j] = 't';
                        break;
                     case 'a':
                        sq[i][j] = new Square(xPos,yPos,"BLUE",false,i,j);
                        sqValues[i][j] = 'a';
                        break;
                     case 'r':
                        sq[i][j] = new Square(xPos,yPos,"BLUE",true,i,j);
                        sq[i][j].setImagePath("images/redbrick.jpeg"); 
                        sqValues[i][j] = 'r';
                        break;
                     case 'm':
                        sq[i][j] = new Square(xPos,yPos,"BLUE",true,i,j);
                        sq[i][j].setImagePath("images/Benjamin-Mood.jpg"); 
                        sqValues[i][j] = 'm';
                        break;

                    }   
                   sq[i][j].draw(gc); //Draw the Squares
                   
                  }
               }
            }
                                                
            //Read in the mechanisms
            while(read.hasNext()) {
               String name = read.next();
               Mechanism tempMech;
               
               int xplace;
               int yplace;
               if(name.equals("door")) {
                  tempMech = new Door(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean(),read.next(),read.next(), read.nextDouble(), read.nextDouble());
               } else if(name.equals("spring")) {
                  tempMech = new Spring(read.nextInt(), read.nextInt(), read.next(),read.nextBoolean(), read.nextBoolean(), read.nextBoolean(), read.next(), read.nextInt(), read.nextInt());
                  Spring spring = (Spring) tempMech; 
                  int size = read.nextInt(); 
                  if(size != 0) { 
                     for(int i = 0; i < size; i++) { 
                     String text = read.next(); 
                     spring.addIDs(text); 
                     } 
                  } 
                  
                  tempMech = (Mechanism) tempMech; 
               } else if(name.equals("button")) {
                  tempMech = new ButtonMech(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean(), read.nextBoolean(), read.nextBoolean(), read.nextBoolean(), read.nextBoolean(), read.next());
                  ButtonMech button = (ButtonMech) tempMech; 
                  int size = read.nextInt(); 
                  if(size != 0) {
                     for(int i = 0; i < size; i++) { 
                        String text = read.next(); 
                        button.addIDs(text); 
                     }
                  }
                  
                  tempMech = (Mechanism) button; 
               }  else if(name.equals("circlebutton")) {
                  tempMech = new CircleButton(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean(),read.nextBoolean(), read.nextBoolean(), read.nextBoolean(),read.nextBoolean(), read.next(), read.nextLong(), read.nextLong());

                  CircleButton button = (CircleButton) tempMech; 
                  int size = read.nextInt();
                  for(int i = 0; i < size; i++) { 
                     String text = read.next(); 
                     button.addIDs(text); 
                  }
                  tempMech = (Mechanism) button;
               } else if(name.equals("gate")){
                  tempMech = new Gate(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean(),read.nextBoolean(),read.nextInt(),read.next());
               } else if(name.equals("spike")){
                  tempMech = new SpikeMech(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean(),read.next(),read.nextBoolean(),read.nextInt());
               } else if(name.equals("screwdriver")){ 
                  tempMech = new ScrewDriver(read.nextInt(), read.nextInt(), read.nextInt(), read.nextInt(), read.nextDouble(), read.nextDouble()); 
               } else if(name.equals("lever")) { 
                  tempMech = new Lever(read.next(), read.nextInt(), read.nextInt(), read.nextInt(), read.nextInt(), read.nextDouble(), read.nextDouble(), read.nextBoolean());
                  Lever lever = (Lever) tempMech; 
                  int size = read.nextInt();
                  if(size != 0) { 
                     for(int i = 0; i < size; i++) { 
                        String text = read.next(); 
                        lever.addIDs(text); 
                     }
                  } 
                  
                  tempMech = (Mechanism) lever; 
               } else if(name.equals("electricity")) { 
                  tempMech = new Electricity(read.nextInt(), read.nextInt(), read.next(), read.nextInt(), read.nextInt(), read.nextDouble(), read.nextDouble(), read.nextBoolean(), read.nextBoolean());
               } else if(name.equals("wall")) { 
                  tempMech = new Wall(read.nextInt(), read.nextInt(), read.next(), read.nextInt(), read.nextInt()); 
               }  else if(name.equals("taperole")) { 
                  tempMech = new TapeRole(read.nextInt(), read.nextInt());
               }  else if(name.equals("pipe")) { 
                  tempMech = new Pipe(read.nextInt(), read.nextInt());
               } else if(name.equals("wrench")) { 
                  tempMech = new Wrench(read.nextInt(), read.nextInt());
               } else if(name.equals("crescentwrench")) { 
                  tempMech = new CrescentWrench(read.nextInt(), read.nextInt());
               } else if(name.equals("water")){
                  tempMech = new Water(read.nextInt(), read.nextInt(), read.next(), read.nextBoolean());
               } else if(name.equals("treadmill")){
                  tempMech = new Treadmill(read.nextInt(),read.nextInt(),read.next(), read.nextBoolean(), read.next(), read.nextDouble(), read.nextDouble());
               } else if(name.equals("screw")) {
                  tempMech = new Screw(read.nextInt(), read.nextInt(), read.next());
                  Screw screw = (Screw) tempMech; 
                  int size = read.nextInt();
                  if(size != 0) { 
                     for(int i = 0; i < size; i++) { 
                        String text = read.next(); 
                        screw.addIDs(text); 
                     }
                  }  
               } else {
                  tempMech = new MusicBox(read.nextInt(),read.nextInt(),read.next(),read.nextBoolean());   
               }     
               //Detect where the mechanism will be placed based on square position
               for(int i=0;i<x;i++){
                  for(int j=0;j<x;j++){
                     //Check and see if cordinates are the same
                     if(tempMech.getSquareX() == sq[i][j].getMechX() && tempMech.getSquareY() == sq[i][j].getMechY()){                     
                        tempMech.setCordinates(sq[i][j].getX(),sq[i][j].getY());
                        //Check and see if the Mechanism allows the square to be walked on
                        if (tempMech.canWalk() == false && !tempMech.getName().equals("spike") && !tempMech.getName().equals("electricity")) {
                           sq[i][j].setWalk(false);

                        }
                        
                     }
                               
                  }
               }
               //Draw the mechanism and add it to the arrayList
               tempMech.draw(gc);
               allMechanisms.add(tempMech);

         }
      }
      catch(FileNotFoundException fnfe) {
         System.out.println("ERROR ACCESSING FILE");
         System.exit(10);
      } 
      catch(Exception e) {
         System.out.println(e);
         System.exit(10);
      }
      
      //Draw the player
      gc.setFill(Color.RED);
      gc.fillRect(playerX, playerY, 40, 40);
   }
   
   //Update Room based on animation input
   public void draw(double playerX, double playerY, GraphicsContext gc){
      gc = getGraphicsContext2D();
      gc.clearRect(0,0,680,700);
      gc.setFill(Color.BLACK);
      gc.fillRect(0,0,680,700);

      //Make values equal
      this.playerX = playerX;
      this.playerY = playerY;
          
      // Update gate positions and check if player is on a gate
      for (Mechanism mech: allMechanisms) {   
          if (mech instanceof Gate) {
              Gate gate = (Gate) mech;
              gate.setPlayerCoordinates(playerX, playerY);
          }      
      }
       
       
      for(int i = 0; i < allMechanisms.size(); i++) { 
         if(allMechanisms.get(i) instanceof SpikeMech) { 
            SpikeMech spike = (SpikeMech) allMechanisms.get(i); 
            if(spike.getID().equals("spikers1")) {
               if(room8OrangeSpike) { 
                  spike.setIsUp(true); 
                  allMechanisms.set(i, spike); 
               }
            } 
          }
      }   
      //Draw the squares
      for(int i=0;i<x;i++){
         for(int j=0;j<y;j++){
            sq[i][j].draw(gc);
          }
       }
      
       //Loop over and update Mechanisms
       for(int i=0;i<allMechanisms.size();i++) { 
         allMechanisms.get(i).update();
         if(this.allMechanisms.get(i) instanceof Spring) {
            Spring spring = (Spring) this.allMechanisms.get(i); 
            if(spring.getState() == true) { 
               int x = spring.getSquareX(); 
               int y = spring.getSquareY(); 
               sq[x][y].setWalk(false); 
            } else if(spring.getState() == false) { 
               int x = spring.getSquareX(); 
               int y = spring.getSquareY(); 
               sq[x][y].setWalk(true);
            } 
         } 
         
         if(this.allMechanisms.get(i) instanceof CircleButton) { 
            CircleButton cb = (CircleButton) this.allMechanisms.get(i); 
            if(cb.getTimeOut() == true) { 
               long elapsedTime = cb.getElapsedTime();
               long duration = cb.getDuration();
               if(elapsedTime >= duration - 30) { 
                  cb.setTimeOut(false); 
                  cb.setIsUp(true);
                  ArrayList<String> ids = cb.getIDs(); 
                  for(int a = 0; a < ids.size(); a++) { 
                     for(int j = 0; j < this.allMechanisms.size(); j++) { 
                        if(ids.get(a).equals(this.allMechanisms.get(j).getID())) {
                           if(this.allMechanisms.get(j) instanceof SpikeMech) { 
                              SpikeMech spike = (SpikeMech) this.allMechanisms.get(j); 
                              spike.setState(); 
                              this.allMechanisms.set(j, spike); 
                           } 
                        } 
                     }  
                  } 
               } 
            }   
         } 

         if(room10Button) {
            if(allMechanisms.get(i) instanceof ButtonMech) { 
               ButtonMech b = (ButtonMech) allMechanisms.get(i);
               ArrayList<String> ids = b.getIDs(); 
               for(int a = 0; a < ids.size(); a++) { 
                  for(int c = 0; c < allMechanisms.size(); c++) { 
                     Mechanism tempMech = allMechanisms.get(c); 
                     if(ids.get(a).equals(tempMech.getID())) {
                        if(tempMech instanceof SpikeMech) { 
                           SpikeMech spike = (SpikeMech) tempMech; 
                           spike.setState();
                           allMechanisms.set(c, spike); 
                        } 
                        
                     }
                     
                  }
                   
               } 
               room10Button = false; 
            } 
         } 
         
        
       }
       
       if(this.startSpringTimer) { 
         this.springCurrentTime = System.currentTimeMillis();
         long elapsedTime = this.springCurrentTime - this.springPressTime;  
         if(elapsedTime >= intervals[intervalIndex]) {
            if(prevSpring != null) { 
               prevSpring.setIsUp(false); 
               prevSpring.setWalk(true); 
               for(int i = 0; i < allMechanisms.size(); i++) { 
                  Mechanism mech = allMechanisms.get(i); 
                  if(prevSpring.getID().equals(mech.getID())) { 
                     allMechanisms.set(i, prevSpring); 
                  } 
               }  
            } 
            Spring spring = springs.get(this.springIndex);
            prevSpring = spring; 
            spring.setIsUp(true);
            spring.setWalk(false);
            spring.draw(gc);
            if(isPlayerOnMechanism()) { 
               Mechanism currentMech = getCurrentMechanism();
               if(currentMech instanceof Spring) { 
                  Spring tspring = (Spring) currentMech; 
                  Spring arraySpring = springs.get(springIndex);
                  if(tspring.getID().equals(arraySpring.getID())) { 
                     springTargetX = spring.getNewPlayerX();
                     springTargetY = spring.getNewPlayerY();
                     springPressed = true;
                  } 
               
               }
            }
            String id = spring.getID(); 
            for(int i = 0; i < this.allMechanisms.size(); i++) { 
               Mechanism mech = this.allMechanisms.get(i);
               
               if(mech instanceof Spring) { 
                  Spring tmpSpring = (Spring) mech; 
                  if(id.equals(tmpSpring.getID())) { 
                     this.allMechanisms.set(i, (Mechanism) spring); 
                  }   
               } 
            }  
            this.springPressTime = System.currentTimeMillis();
            this.intervalIndex++; 
            this.springIndex++; 
            if(this.springIndex >= 4) {
               this.springIndex = 0;
            }
            if(this.intervalIndex >= 5) { 
               this.intervalIndex = 0;  
            }
         } 
       } 
       
       //Check and see if the player is on top of a mechanism
       if(isPlayerOnMechanism()){
         Mechanism currentMechanism = getCurrentMechanism();
         if (currentMechanism != null) {
            if (currentMechanism instanceof Door) { //player is on a door
               Door door = (Door) currentMechanism;
               this.newRoom = door.getRoom();
               this.roomChange = true;
               this.playerX = door.getNewRoomPlayerX(); //These will load in the x values for where player will spawn in the new room
               this.playerY = door.getNewRoomPlayerY();
            } else if(currentMechanism instanceof Spring) { //player is on a spring
               Spring spring = (Spring) currentMechanism; 
               ArrayList<String> ids = spring.getIDs();  
                              
               if (spring.getState() == false && spring.canWalk() == true && spring.getHasTimeOut() == false) {
                  for(int i = 0; i < this.allMechanisms.size(); i++) { 
                     Mechanism tempMech = this.allMechanisms.get(i); 
                     for(int j = 0; j < ids.size(); j++) {
                        if(ids.get(j).equals(tempMech.getID())) { 
                           if(tempMech instanceof CircleButton) { 
                              CircleButton cb = (CircleButton) tempMech; 
                              cb.setIsUp(true); 
                           } 
                           else if(tempMech instanceof ButtonMech){
                              ButtonMech bm = (ButtonMech) tempMech; 
                              bm.setIsUp(true); 
                           }
                           else if(tempMech instanceof SpikeMech){
                              SpikeMech sp = (SpikeMech) tempMech;
                              
                              long currentTime = System.currentTimeMillis();
                              if (currentTime - this.springLastCollisionTime >= this.SPRING_COLLISION_DELAY) {
                                 this.springLastCollisionTime = currentTime;
                                 sp.setState();
                              }
                           }
                        } 
                     } 
                  } 
                  spring.setIsUp(true);
                  spring.setWalk(false);
                  spring.draw(gc);
                  
                  //Loop over the 2d array and change it where it can't be walked on anymore
                  for(int i=0;i<x;i++){
                     for(int j=0;j<y;j++){
                                         
                        if(sq[i][j].getMechX() == spring.getSquareX() && sq[i][j].getMechY() == spring.getSquareY()){
                           sq[i][j].setWalk(false); //set the can't walk value to false
                        }
                     }
                  }
                  
                  // Set spring target coordinates
                  springTargetX = spring.getNewPlayerX();
                  springTargetY = spring.getNewPlayerY();
                  springPressed = true;
              } else if(spring.getState() == false && spring.canWalk() == true && spring.getHasTimeOut() == true) { 
                  int x = spring.getSquareX(); 
                  int y = spring.getSquareY(); 
                  sq[x][y].setWalk(true);   
              } 
              
           } else if(currentMechanism instanceof ButtonMech){ //player is on a button
               ArrayList<Lever> levArray = new ArrayList<>(); 
               ButtonMech button = (ButtonMech) currentMechanism;
               ArrayList<String> ids = button.getIDs(); 
               if(button.getIsUp() == true){ //Button is up set it down
                  //HERE
                  if(!fileName.equals("Room10.txt")) {
                     button.setIsUp(false);
                  }       
                 if(fileName.equals("Room4.txt") && button.getID().equals("o1")) {
                     room8OrangeSpike = true;
                 }   
                                      
                  for(int i = 0; i < this.allMechanisms.size(); i++) { 
                     Mechanism tempMech = this.allMechanisms.get(i); 
                     for(int j = 0; j < ids.size(); j++) {
                        if(fileName.equals("Room8") && room8OrangeSpike == true){
                           if(tempMech instanceof SpikeMech){
                              SpikeMech sp = (SpikeMech) tempMech;
                              if(sp.getID().equals("spike1")){
                                 sp.setState();
                              }
                           }
                        }
                        if(ids.get(j).equals(tempMech.getID())) { 
                           if(fileName.equals("Room10.txt")) { 
                                 if(tempMech instanceof Lever) { 
                                    Lever lev = (Lever) tempMech; 
                                    levArray.add(lev);
                                } 
                             }
                           if(!fileName.equals("Room10.txt")) {
                              // Button Triggers Spikes 
                              if(tempMech instanceof SpikeMech) { 
                                 SpikeMech s = (SpikeMech) tempMech; 
                                 s.setState();
                                 // Dont know what this is for 
                              } else if(tempMech instanceof ButtonMech) {
                                 ButtonMech b = (ButtonMech) tempMech; 
                                 b.setIsUp(!button.getIsUp()); 
                                 button.setIsUp(false);  
                                 this.allMechanisms.set(i, b); 
                              } 
                          }
                           
                        

                      } 
                   }    
               } 
               if(fileName.equals("Room10.txt")) { 
                  boolean isButtonOn = !levArray.get(0).getState() && !levArray.get(1).getState() && !levArray.get(2).getState();
                  if(isButtonOn) { 
                     button.setIsUp(false);  
                     room10Button = true; 
                 } 
               }  
 
               button.draw(gc);                          
             } 
          } else if(currentMechanism instanceof CircleButton){ //player is on a button
               CircleButton circlebutton = (CircleButton) currentMechanism;               
               if(circlebutton.getIsUp() == true) {
                  circlebutton.setIsUp(false);
                  ArrayList<String> ids = circlebutton.getIDs(); 
                  for(int i = 0; i < ids.size(); i++) { 
                     for(int j = 0; j < this.allMechanisms.size(); j++) { 
                        Mechanism tempMech = this.allMechanisms.get(j); 
                        if(ids.get(i).equals(tempMech.getID())) { 
                           // Circle button triggers spike 
                           if(tempMech instanceof SpikeMech) { 
                              SpikeMech spike = (SpikeMech) tempMech; 
                              spike.setState(); 
                              this.allMechanisms.set(j, spike); 
                           } 
                        }  
                     } 
                  } 
                  circlebutton.draw(gc);    
                  // Turn timer on for circle button.   
                  circlebutton.setTimeOut(true); 
               } 
               
            // If player is on treadmill move them                           
          } else if(currentMechanism instanceof Treadmill){
             Treadmill tread = (Treadmill) currentMechanism;
             springTargetX = tread.getTreadX();
             springTargetY = tread.getTreadY();
             springPressed = true;
           } 
        }
      }       

      //Draw the mechanisms
      for(int i=0;i<allMechanisms.size();i++)
         allMechanisms.get(i).draw(gc);
         
      //Draw the player
      gc.setFill(Color.RED);
      gc.fillRect(this.playerX,this.playerY,40,40);
  }
  
   // Add this method to check if the next square is walkable
    public boolean canMoveTo(double newX, double newY) {
       final int PLAYER_SIZE = 40; // Player width and height
       
       // Calculate the grid position based on the player's coordinates
       // For right/down movement, we check the right/bottom edge of the player
       int gridXLeft = (int)((newX - ((8-x)/2 * 75)) / 75);
       int gridXRight = (int)((newX + PLAYER_SIZE - ((8-x)/2 * 75)) / 75);
       int gridYTop = (int)((newY - ((8-y)/2 * 75)) / 75);
       int gridYBottom = (int)((newY + PLAYER_SIZE - ((8-y)/2 * 75)) / 75);
       
       // Check if any part of the player is out of bounds
       if (gridXLeft < 0 || gridXRight >= x || gridYTop < 0 || gridYBottom >= y) {
           return false; // Out of bounds, prevent movement
       }
       
       // Check if all squares the player is touching can be walked on
       for (int i = gridXLeft; i <= gridXRight; i++) {
           for (int j = gridYTop; j <= gridYBottom; j++) {
               if (!sq[i][j].getWalk()) {
                   return false; // Can't walk on this square, prevent movement
               }
           }
       }  
         // Check mechanisms
        for (int m=0;m<allMechanisms.size();m++) {
          Mechanism mech = allMechanisms.get(m);
          // Custom gate collision
          if (mech instanceof Gate) {
               Gate gate = (Gate) mech;
               if (!gate.canWalkThroughGate(newX, newY)) {
                   return false;
               }
          // Custom spike collision 
          } else if (mech instanceof SpikeMech) {
               SpikeMech spike = (SpikeMech) mech;
               if (!spike.canPassSpike(newX, newY)) {
                   return false;
               }
          
          // Custom lever collision
           } else if (mech instanceof Lever) { 
               Lever lev = (Lever) mech; 
               if(lev.colliding(newX, newY)) {
                  // Delays the animation from flipping lever
                  long currentTime = System.currentTimeMillis();
   
                  // Waits .2 seconds to delay switching state of lever.
                  if (currentTime - this.leverLastCollisionTime >= this.LEVER_COLLISION_DELAY) {
                     this.leverLastCollisionTime = currentTime; // Update the last collision time
                     lev.setUp(); 
                     ArrayList<String> ids = lev.getIDs(); 
                     for(int i = 0; i < ids.size(); i++) {
                        for(int j = 0; j < this.allMechanisms.size(); j++) {
                           if(ids.get(i).equals(this.allMechanisms.get(j).getID())) {
                              // If lever linked to electricity trigger electricity. 
                              if(this.allMechanisms.get(j) instanceof Electricity) { 
                                 Electricity el = (Electricity) this.allMechanisms.get(j); 
                                 el.setOn(lev.getState()); 
                              } else if(this.allMechanisms.get(j) instanceof Treadmill) {
                                 Treadmill td = (Treadmill) this.allMechanisms.get(j);
                                 if(fileName.equals("Room10.txt")) { 
                                    td.turnOn(true);
                                    td.setState(); 
                                 } 

                                 if(td.getState() == true) {
                                    td.setState(); 
                                    allMechanisms.set(j, td);
                                 } 
                                 
                             }
                              
                           } 
                        } 
                     
                     }
                 }
                  return false;
               }
          } else if (mech instanceof Electricity) {
               Electricity el = (Electricity) mech;
               if(el.colliding(newX, newY)) {
                  return false;
               }
               
         } else if (mech instanceof Treadmill){
            Treadmill tm = (Treadmill) mech;
            if(tm.canMove(newX, newY) == false)
               return false;
         } else if(mech instanceof Wall) { 
            Wall wall = (Wall) mech; 
            if(wall.colliding(newX, newY)) { 
               return false; 
            } 
         } else if(mech instanceof Screw) { 
            Screw screw = (Screw) mech; 
            ArrayList<String> ids = screw.getIDs(); 
            if(screw.colliding(newX, newY)) {
               if(hasScrewDriver) { 
                  for(int i = 0; i < ids.size(); i++) { 
                     for(int j = 0; j  < allMechanisms.size(); j++) { 
                        if(ids.get(i).equals(allMechanisms.get(j).getID())) { 
                           if(allMechanisms.get(j) instanceof Treadmill) { 
                              Treadmill td = (Treadmill) allMechanisms.get(j); 
                              if(td.getState() == false) { 
                                 td.turnOn(true); 
                                 allMechanisms.set(j, td); 
                              } 
                           } else if(allMechanisms.get(j) instanceof Spring) { 
                              Spring spring = (Spring) allMechanisms.get(j); 
                              spring.setIsUp(false);
                              spring.setWalk(true); 
                              int x = spring.getSquareX(); 
                              int y = spring.getSquareY(); 
                              sq[x][y].setWalk(true); 
                              allMechanisms.set(j, spring); 
                           }  
                        } 
                     }  
                  } 
               } 
               
               return false; 
            } 
         } else if(mech instanceof ScrewDriver) {
            ScrewDriver screwdriver = (ScrewDriver) mech; 
           
            if(screwdriver.colliding(newX, newY)) {
               screwdriver.setDisplay(false); 
               this.allMechanisms.remove(screwdriver);
               m--; 
               client.pickUpScrewdriver();
               hasScrewDriver = true;
                
            }
         } else if(mech instanceof Wrench) {
            Wrench wrench = (Wrench) mech; //
           
            if(wrench.colliding(newX, newY)) {//
               wrench.setDisplay(false); //
               this.allMechanisms.remove(wrench);//
               m--; 
               client.pickUpWrench();//
               hasWrench = true;//
                
            }
         } else if(mech instanceof CrescentWrench) {
            CrescentWrench crescentwrench = (CrescentWrench) mech; //
           
            if(crescentwrench.colliding(newX, newY)) {//
               crescentwrench.setDisplay(false); //
               this.allMechanisms.remove(crescentwrench);//
               m--; 
               client.pickUpCrescentWrench();//
               hasCrescentWrench = true;//
                
            }
         } else if(mech instanceof Pipe) {
            Pipe pipe = (Pipe) mech; //
           
            if(pipe.colliding(newX, newY)) {//
               pipe.setDisplay(false); //
               this.allMechanisms.remove(pipe);//
               m--; 
               client.pickUpPipe();//
               hasPipe = true;//
                
            }
         } else if(mech instanceof TapeRole) {
            TapeRole tape = (TapeRole) mech; //
           
            if(tape.colliding(newX, newY)) {//
               tape.setDisplay(false); //
               this.allMechanisms.remove(tape);//
               m--; 
               client.pickUpTape();//
               hasTape = true;//
                
            }
         }


    }    
    // If we've passed all checks, the movement is valid
    return true;
 }
 
 private boolean isPlayerOnMechanism() {
    final int PLAYER_SIZE = 40; // Player width and height
    final int MECHANISM_SIZE = 50; // Assuming mechanisms are 50x50 pixels
    final double OVERLAP_THRESHOLD = 20; // Minimum overlap to consider player "on" the mechanism

    // Calculate the center of the player
    double playerCenterX = playerX + PLAYER_SIZE / 2;
    double playerCenterY = playerY + PLAYER_SIZE / 2;

    for (Mechanism mech : allMechanisms) {
         double mechX, mechY, mechCenterX, mechCenterY, distanceX, distanceY;
                  
         if((mech instanceof ButtonMech ||  mech instanceof CircleButton)){
            if(mech instanceof ButtonMech) { 
               ButtonMech b = (ButtonMech) mech; 
               ArrayList<String> ids = b.getIDs(); 
               if(b.getSpringTimeOut() == true && b.getIsUp() == true) { 
                  this.startSpringTimer = true; 
                  this.springPressTime = System.currentTimeMillis();
                  for(int i = 0; i < ids.size(); i++) { 
                     for(int j = 0; j < this.allMechanisms.size(); j++) { 
                        if(ids.get(i).equals(this.allMechanisms.get(j).getID())) { 
                           if(this.allMechanisms.get(j) instanceof Spring) { 
                              Spring spring = (Spring) this.allMechanisms.get(j);
                              this.springs.add(spring);
                           } 
                            
                        }
                         
                     } 
                  } 
               } 
            } 
            
           if(mech.offAxis()){
              // Get the mechanism's coordinates
              
              mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
              mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;
                            
              // Calculate the center of the mechanism
              if(mech.offAxisX()){
                 mechCenterX = mechX + 75; 
              } else {
                 mechCenterX = mechX + 37.5;
              }
              
              if(mech.offAxisY()){
                 mechCenterY = mechY + 75; 
              } else {
                 mechCenterY = mechY + 37.5;
              }
      
              // Calculate the distance between the centers
              distanceX = Math.abs(playerCenterX - mechCenterX);
              distanceY = Math.abs(playerCenterY - mechCenterY);
      
              // Check if there's significant overlap between player and mechanism
              if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
                  distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
                  return true; // Player is on top of a mechanism
                  
              }

           } else {
           
           // Get the mechanism's coordinates
              mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
              mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;
                            
              // Calculate the center of the mechanism
              mechCenterX = mechX + 37.5; 
              mechCenterY = mechY + 37.5;
      
              // Calculate the distance between the centers
              distanceX = Math.abs(playerCenterX - mechCenterX);
              distanceY = Math.abs(playerCenterY - mechCenterY);
      
              // Check if there's significant overlap between player and mechanism
              if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
                  distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
                  return true; // Player is on top of a mechanism
                  
              }
           }
        } else {
           // Get the mechanism's coordinates
           mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
           mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;
   
           // Calculate the center of the mechanism
           mechCenterX = mechX + 37.5; // 37.5 is half of the 75x75 square
           mechCenterY = mechY + 37.5;
   
           // Calculate the distance between the centers
           distanceX = Math.abs(playerCenterX - mechCenterX);
           distanceY = Math.abs(playerCenterY - mechCenterY);
   
           // Check if there's significant overlap between player and mechanism
           if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
               distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
               return true; // Player is on top of a mechanism
           }
    }
    }
    
    return false; // Player is not on top of any mechanism
   }
   
   private Mechanism getCurrentMechanism() {
      final int PLAYER_SIZE = 40; // Player width and height
      final int MECHANISM_SIZE = 50; // Assuming mechanisms are 50x50 pixels
      final double OVERLAP_THRESHOLD = 20; // Minimum overlap to consider player "on" the mechanism

      // Calculate the center of the player
      double playerCenterX = playerX + PLAYER_SIZE / 2;
      double playerCenterY = playerY + PLAYER_SIZE / 2;

      for (Mechanism mech : allMechanisms) {
      double mechX, mechY, mechCenterX, mechCenterY, distanceX, distanceY;
          if((mech instanceof ButtonMech ||  mech instanceof CircleButton && !mech.getID().equals("y1"))){
             
           if(mech.offAxis()){
              // Get the mechanism's coordinates
              mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
              mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;
                            
              // Calculate the center of the mechanism
              if(mech.offAxisX()){
                 mechCenterX = mechX + 75; 
              } else {
                 mechCenterX = mechX + 37.5;
              }
              
              if(mech.offAxisY()){
                 mechCenterY = mechY + 75; 
              } else {
                 mechCenterY = mechY + 37.5;
              }
                    
              // Calculate the distance between the centers
              distanceX = Math.abs(playerCenterX - mechCenterX);
              distanceY = Math.abs(playerCenterY - mechCenterY);
      
              // Check if there's significant overlap between player and mechanism
              if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
                  distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
                  return mech; // Player is on top of a mechanism
              }
              
              } else {
                 
              // Get the mechanism's coordinates
                  mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
                  mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;
         
                  // Calculate the center of the mechanism
                  mechCenterX = mechX + 37.5; // 37.5 is half of the 75x75 square
                  mechCenterY = mechY + 37.5;
         
                  // Calculate the distance between the centers
                  distanceX = Math.abs(playerCenterX - mechCenterX);
                  distanceY = Math.abs(playerCenterY - mechCenterY);
         
                  // Check if there's significant overlap between player and mechanism
                  if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
                      distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
                      if(mech instanceof ButtonMech && fileName.equals("Room6.txt") && mech.getID().equals("y1")) {                         
                        ButtonMech b = (ButtonMech) mech; 
                        String id = b.getID(); 
                        if(id.equals("y1") && b.getIsUp() == true && b.getColor().equals("YELLOW") && b.getSquareX() == 5) {
                           springTargetX = this.playerX - 30 ;
                           springTargetY = this.playerY;
                           springPressed = true; 
                           this.playerX = this.playerX - 30;                            
                        } 
                         
                       } 
                     return mech; // Return the mechanism the player is on
                  }
              }
         
         } else {
         // Get the mechanism's coordinates
         mechX = mech.getSquareX() * 75 + (8 - x) / 2 * 75; // Adjust for the room offset
         mechY = mech.getSquareY() * 75 + (8 - y) / 2 * 75;

         // Calculate the center of the mechanism
         mechCenterX = mechX + 37.5; // 37.5 is half of the 75x75 square
         mechCenterY = mechY + 37.5;

         // Calculate the distance between the centers
         distanceX = Math.abs(playerCenterX - mechCenterX);
         distanceY = Math.abs(playerCenterY - mechCenterY);

         // Check if there's significant overlap between player and mechanism
         if (distanceX < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD &&
             distanceY < (PLAYER_SIZE + MECHANISM_SIZE) / 2 - OVERLAP_THRESHOLD) {
            return mech; // Return the mechanism the player is on
         }
        }
      }
      return null; // Player is not on top of any mechanism
    }
   
   public void saveGame(){ //save the game
      
      //Write the PlayerX and PlayerY to the first file
      try{
         FileWriter myWriter = new FileWriter("PlayerCords.txt");
         myWriter.write(playerX+" "+playerY);       
         myWriter.close();
      }
      catch(IOException e){
         e.printStackTrace();
      }
      
      writeRoomData("Progress.txt");
  }
  public void writeRoomData(String tempFileName){
      
      //Write the roomStart, map, and PlayerCordinates
      try{
         FileWriter myWriter = new FileWriter(tempFileName);
         myWriter.write(roomStartX+" "+roomStartY+"\n"+x+" "+y+"\n");
         
         //loop over the sqaures
         for(int j=0;j<y;j++){
            for(int i=0;i<x;i++){
               myWriter.write(sqValues[i][j]+" ");
            }
            myWriter.write("\n");
         }
         myWriter.write("\n");
         
         //Loop over the mechanisms
         for(int i=0;i<allMechanisms.size();i++){
            myWriter.write(allMechanisms.get(i).toString());
         }
         
         myWriter.close();
      }
      catch(IOException e){
         e.printStackTrace();
      }  
   }
   public int saveRoom(){ //Save the room data
      //Create functionality for every single room
      int roomNum = 1;
      if(fileName.equals("Room2.txt"))
         roomNum = 2;
      else if(fileName.equals("Room3.txt"))
         roomNum = 3;
      else if(fileName.equals("Room4.txt"))
         roomNum = 4;
      else if(fileName.equals("Room5.txt"))
         roomNum = 5;
      else if(fileName.equals("Room6.txt"))
         roomNum = 6;
      else if(fileName.equals("Room7.txt"))
         roomNum = 7;
      else if(fileName.equals("Room8.txt"))
         roomNum = 8;
      else if(fileName.equals("Room9.txt"))
         roomNum = 9;
      else if(fileName.equals("Room10.txt"))
         roomNum = 10;
         
      //Now write the file based on the calculated room #
      if(roomNum != 1){
         String tempRoomName = "room"+roomNum+"Save.txt";
         writeRoomData(tempRoomName);
      }
      
      return roomNum;
   
   }
   //All accessors and mutators
   public boolean hasRoomChanged(){ //see if there is a changed room
      return roomChange;
   }
   public String getRoomName(){ //get new room name
      return fileName;
   }
   public double getPlayerX(){ //get current player x for client
      return playerX;
   }
   public double getPlayerY(){ //get current player y for client
      return playerY;
   }
   public void setPlayerX(double playerX){ //set the new player x from client
      this.playerX = playerX;
   }
   public void setPlayerY(double playerY){ //set the new player y for the client
      this.playerY = playerY;
   } 
   public boolean getSpringPressed() { //get whether or not a spring has been pressed
        return springPressed;
    }
    public void setSpringPressed(boolean pressed) { //set the value of whether the spring has been pressed yet
        this.springPressed = pressed;
    }
    public double getSpringTargetX() { //retrieve the new value playerX from the spring class
        return springTargetX;
    }
    public double getSpringTargetY() { //get the new player y value from the spring class
        return springTargetY;
    }  
    public double getRoomStartX(){ //get the start of the room for loading in the Game Runner
      return roomStartX;
    }
    public double getRoomStartY(){ //get the start of the room for cordinates for loading in the Game Runner
      return roomStartY;
    }
    public String getNewRoomName(){
      return newRoom;
    }
    public ArrayList<Mechanism> getAllMechanisms() { 
      return allMechanisms; 
    }
}