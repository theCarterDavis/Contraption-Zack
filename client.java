//Contraption Zach client
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.input.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList; 
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;


public class client extends Application{
   
   //Private instance variables related to the startScreen 
   private GridPane root = new GridPane();
   private VBox loadingScreen = new VBox(20);
   private Button newGameBtn, loadGameBtn;
   private ButtonListener buttonListener = new ButtonListener();
   
   //Stage
   private Stage primaryStage;

   //Key Listener booleans
    private boolean wKeyDown = false;
    private boolean aKeyDown = false;
    private boolean sKeyDown = false;
    private boolean dKeyDown = false;

   //private instance variables related to the gameBeing Played
   private FlowPane gamePane = new FlowPane();
   private Canvas canvas = new Canvas();
   private GraphicsContext gc = canvas.getGraphicsContext2D();
   private GameRunner gr = new GameRunner();
   private boolean gameBegin = false;
   private boolean isPaused = false;
   private double playerX = 240;
   private double playerY = 340;
   
   
   //private instance variables for the pausemenu
   private VBox pauseMenu = new VBox(5);
   private Button exitBtn = new Button("Exit");
   private Button restartAreaBtn = new Button("Restart Area");
   private Button restartLevelBtn = new Button("Restart Level");
   private Button saveBtn = new Button("Save");
   private Button loadPauseBtn = new Button("Load"); //special button for the load menu
   
   // Tool menu components
   private Stage toolMenuStage;
   private VBox toolMenu;
   private Label toolMenuTitle;
   private Canvas toolCanvas;
   private GraphicsContext toolGC;
   private boolean isToolMenuVisible = false;

   //instance spring animation variables
   private static final long SPRING_ANIMATION_DURATION = 500; // 500ms animation
   private boolean isSpringAnimationActive = false;
   private double springTargetX;
   private double springTargetY;
   private long springAnimationStartTime;
   
   //Variable keeping track of the ScrewDriver Pickup
   private static boolean hasScrewdriver = false;
   private static boolean hasCrescentWrench = false;
   private static boolean hasPipe = false;   
   private static boolean hasTape = false;
   private static boolean hasWrench = false;
   
   //file saving booleans
   private boolean room2, room3, room4, room5, room6, room7, room8, room9, room10 = false;
   
   @Override
   public void start(Stage stage){
      //Create the loadingScreen for the Game
      this.primaryStage = stage;

      root.setStyle("-fx-background-color: GREY;");

       Text title = new Text("Contraption Zack"); //title screen
       String titleText = "Contraption Zack!!!";

        TextFlow textFlow = new TextFlow();
        Color[] colors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE,
            Color.ORANGE, Color.LIGHTSEAGREEN, Color.MAGENTA, Color.BROWN,
            Color.LIME, Color.TEAL, Color.GOLD, Color.DARKBLUE,
            Color.DARKRED, Color.DARKORANGE, Color.DARKGREEN, Color.DARKVIOLET,Color.CORNFLOWERBLUE, Color.CRIMSON,
            Color.MEDIUMPURPLE
          };
       
        for (int i = 0; i < titleText.length(); i++) {
            Text letterText = new Text(String.valueOf(titleText.charAt(i)));
            letterText.setFont(Font.font("Impact", 60));
            letterText.setFill(colors[i % colors.length]);
            textFlow.getChildren().add(letterText);
        }
      Text authors = new Text("by Carter Davis, Ethan Emerson, and Braxton Hardman"); //names
      authors.setFont(Font.font("Arial", 20));
      
      newGameBtn = new Button("New Game"); //buttons
      loadGameBtn = new Button("Load Game");
      String buttonStyle = "-fx-font-size: 18px; -fx-pref-width: 200px; -fx-pref-height: 50px;";


      newGameBtn.setStyle(buttonStyle); //add them to the listener
      loadGameBtn.setStyle(buttonStyle);
      newGameBtn.setOnAction(buttonListener);
      loadGameBtn.setOnAction(buttonListener);
      
      loadingScreen.getChildren().addAll(textFlow,authors,newGameBtn,loadGameBtn);//add it to the scene
      loadingScreen.setAlignment(Pos.CENTER);
      root.getChildren().add(loadingScreen);
      root.setAlignment(Pos.CENTER);
      
      //Set the Pause Menu buttons on action and add them to the vBox
      exitBtn.setOnAction(buttonListener);
      restartAreaBtn.setOnAction(buttonListener);
      restartLevelBtn.setOnAction(buttonListener);
      saveBtn.setOnAction(buttonListener);
      loadPauseBtn.setOnAction(buttonListener);
      pauseMenu.getChildren().addAll(exitBtn,restartAreaBtn, restartLevelBtn, saveBtn, loadPauseBtn);
      pauseMenu.setAlignment(Pos.TOP_LEFT);
                  
      //Create the scene
      Scene scene = new Scene(root, 680, 700);
      stage.setScene(scene);
      stage.setTitle("Contraption Zack");
      stage.show();
      
      //Setting the animation handler
      KeyListenerDown keyListener = new KeyListenerDown();
      
      //Making the tool box
      createToolMenu();
      
      // Set up key pressed and released event handlers
      scene.setOnKeyPressed(keyListener);
      scene.setOnKeyReleased(keyListener::handleKeyReleased);      
      startAnimationTimer();
   }
   
       
   //Launch the Game
   public static void main(String []args){
      launch(args);
   }
   
   // Button listener class
   public class ButtonListener implements EventHandler<ActionEvent>{
      public void handle(ActionEvent e){
         if(e.getSource() == newGameBtn){ //new game
            
            //Clear the board and create the game
            root.getChildren().clear();
            gr.drawRoom(gc);
            root.getChildren().add(gr); 
            gr.requestFocus();
            gameBegin = true;
            playerX = gr.getPlayerX();
            playerY = gr.getPlayerY(); 
         }
         else if(e.getSource() == loadGameBtn || e.getSource() == loadPauseBtn){ //load a game
            
            //Check to see if there is same game progress on the device
            try{
               
               //Read in the booleanFile
               Scanner boolFile = new Scanner(new File("RoomChangeBools.txt"));
               room2 = boolFile.nextBoolean();
               room3 = boolFile.nextBoolean();
               room4 = boolFile.nextBoolean();
               room5 = boolFile.nextBoolean();   
               room6 = boolFile.nextBoolean(); 
               room7 = boolFile.nextBoolean();     
               room8 = boolFile.nextBoolean();     
               room9 = boolFile.nextBoolean(); 
               room10 = boolFile.nextBoolean();
                
               Scanner read = new Scanner(new File("PlayerCords.txt"));
               playerX = read.nextDouble();
               playerY = read.nextDouble();
               
               //load the game
               gr = new GameRunner("Progress.txt",playerX, playerY); 
               root.getChildren().clear();
               gr.drawRoom(gc);
               root.getChildren().add(gr);
               gameBegin = true;
               isPaused = false;
               gr.requestFocus();    
            }
            catch(FileNotFoundException fnfe){ //file not found
               
               //let the user know there is no saved progress on the device
               Text noFile = new Text("**There is no saved progress on this device, please begin a new game**"); //names
               noFile.setFont(Font.font("Arial", 15));
               loadingScreen.getChildren().add(noFile);               
            }
         }
         else if(e.getSource() == exitBtn){ //exit the game
            System.exit(0);
         }
         else if(e.getSource() == restartAreaBtn){ //restart the area
            
                        
            //Restart the room with the new file instead of the old file 
            String fileName = gr.getRoomName(); 
            switch(fileName){
               case "room2Save.txt": fileName = "Room2.txt"; 
                  room3 = false; 
                  room4 = false; 
                  room5 = false; 
                  room6 = false;
                  room7 = false;
                  room8 = false;
                  room9 = false;
                  room10 = false;
                  break;
               case "room3Save.txt": fileName = "Room3.txt"; 
                  room4 = false; 
                  room5 = false; 
                  room6 = false;
                  room7 = false;
                  room8 = false;
                  room9 = false;
                  room10 = false;
                  break;
               case "room4Save.txt": fileName = "Room4.txt"; 
                  room7 = false;
                  room8 = false;
                  room9 = false;
                  room10 = false;
                  break;
               case "room5Save.txt": fileName = "Room5.txt"; 
                  room6 = false;
                  room7 = false;
                  room8 = false;
                  room9 = false;
                  room10 = false;                  
                  break;
               case "room6Save.txt": fileName = "Room6.txt"; 
                  room7 = false;
                  room8 = false;
                  room9 = false;
                  room10 = false;               
                  break;
               case "room7Save.txt": fileName = "Room7.txt"; 
                  room8 = false;
                  room9 = false;
                  room10 = false;                  
                  break;
               case "room8Save.txt": fileName = "Room8.txt"; 
                  room9 = false;
                  room10 = false;                
                  break;
               case "room9Save.txt": fileName = "Room9.txt"; 
                  room10 = false;
                  break;
               case "room10Save.txt": fileName = "Room10.txt"; break;
            }
            
            gr = new GameRunner(fileName,gr.getRoomStartX(),gr.getRoomStartY());
            playerX = gr.getPlayerX();
            playerY = gr.getPlayerY();
            root.getChildren().clear();
            gr.drawRoom(gc);
            root.getChildren().add(gr);
            isPaused = false;
            gameBegin = true;
         }
         else if(e.getSource() == restartLevelBtn){ //restart the entire game
            //Reset all of the boolelans
            room2 = false;
            room3 = false;
            room4 = false;
            room5 = false;
            room6 = false;
            room7 = false;
            room8 = false;
            room9 = false;
            room10 = false;
            
            //Restart the game    
            gr = new GameRunner();
            playerX = 240;
            playerY = 340;
            root.getChildren().clear();
            gr.drawRoom(gc);
            root.getChildren().add(gr);
            isPaused = false;
         }
         else if(e.getSource() == saveBtn){ //save functionality to come                    
            //Save the booleanFile for loading in the game
            try{
               FileWriter myWriter = new FileWriter("RoomChangeBools.txt");
               myWriter.write(room2+" "+room3+" "+room4+" "+room5+" "+room6+" "+room7+" "+room8+" "+room9+" "+room10+"\n");
               
               myWriter.close();
            }
            catch(IOException er){
               er.printStackTrace();
            }         
            gr.saveGame();
         }          
      }
   }
   

 private void startAnimationTimer() {
    new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (!isPaused && gameBegin){                 
                 if (isSpringAnimationActive) { //if their is a current spring animation
                        updateSpringAnimation(now);
                 } 
                 else if (gr.hasRoomChanged() == false && gr.getSpringPressed() == false) { //normal animation
                     update();
                     gr.draw(playerX, playerY, gc);
                 } 
                 else if (gr.getSpringPressed() == true) { //spring has just been pressed
                     startSpringAnimation(gr.getSpringTargetX(), gr.getSpringTargetY());
                     gr.setSpringPressed(false);
                 }
                 else{ //There has been a room change
                 
                 
                 //Save currentRoom
                 String currentRoom = gr.getRoomName(); 
                 switch (currentRoom){
                  case "Room2.txt": gr.writeRoomData("room2Save.txt"); room2 = true; break;
                  case "Room3.txt": gr.writeRoomData("room3Save.txt"); room3 = true; break;
                  case "Room4.txt": gr.writeRoomData("room4Save.txt"); room4 = true; break;
                  case "Room5.txt": gr.writeRoomData("room5Save.txt"); room5 = true; break;
                  case "Room6.txt": gr.writeRoomData("room6Save.txt"); room6 = true; break;
                  case "Room7.txt": gr.writeRoomData("room7Save.txt"); room7 = true; break;
                  case "Room8.txt": gr.writeRoomData("room8Save.txt"); room8 = true; break;
                  case "Room9.txt": gr.writeRoomData("room9Save.txt"); room9 = true; break;
                  case "Room10.txt": gr.writeRoomData("room10Save.txt"); room10 = true; break;
                  case "room2Save.txt": gr.writeRoomData("room2Save.txt"); room2 = true; break;
                  case "room3Save.txt": gr.writeRoomData("room3Save.txt"); room3 = true; break;
                  case "room4Save.txt": gr.writeRoomData("room4Save.txt"); room4 = true; break;
                  case "room5Save.txt": gr.writeRoomData("room5Save.txt"); room5 = true; break;
                  case "room6Save.txt": gr.writeRoomData("room6Save.txt"); room6 = true; break;
                  case "room7Save.txt": gr.writeRoomData("room7Save.txt"); room7 = true; break;
                  case "room8Save.txt": gr.writeRoomData("room8Save.txt"); room8 = true; break;
                  case "room9Save.txt": gr.writeRoomData("room9Save.txt"); room9 = true; break;
                  case "room10Save.txt": gr.writeRoomData("room10Save.txt"); room10 = true; break;
                 } 
                 
                                   
                  //There has been a room change and see if you need to load in the new room or load in the saved room
                  String newRoom = gr.getNewRoomName();
                  if(newRoom.equals("Room2.txt") && room2)
                     newRoom = "room2Save.txt";
                  else if(newRoom.equals("Room3.txt") && room3)
                     newRoom = "room3Save.txt";
                  else if(newRoom.equals("Room4.txt") && room4)
                     newRoom = "room4Save.txt";
                  else if(newRoom.equals("Room5.txt") && room5)
                     newRoom = "room5Save.txt";
                  else if(newRoom.equals("Room6.txt") && room6)
                     newRoom = "room6Save.txt";
                  else if(newRoom.equals("Room7.txt") && room7)
                     newRoom = "room7Save.txt";
                  else if(newRoom.equals("Room8.txt") && room8)
                     newRoom = "room8Save.txt";
                  else if(newRoom.equals("Room9.txt") && room9)
                     newRoom = "room9Save.txt";
                  else if(newRoom.equals("Room10.txt") && room10)
                     newRoom = "room10Save.txt";
                                    
                  //Load in the player Values
                  playerX = gr.getPlayerX(); //get new x
                  playerY = gr.getPlayerY(); //get new y after room is done being drawn
                  gr = new GameRunner(newRoom,playerX,playerY);
         
                  //Clear the board and draw the new room
                  root.getChildren().clear();
                  gr.drawRoom(gc);
                  root.getChildren().add(gr);  
              }
            }
            drawOnToolCanvas();
        }
    }.start();
}
          
                  
                  
                  
                  
                  /*//Change the state of RoomVariables based on whether or not the Room has been saved
                  if(!(gr.getRoomName().equals("Room1.txt"))){ //only save if the room is not room one
                    
                    //Save the room and change the boolean
                    int currentRoomNum = gr.saveRoom();
                       switch(currentRoomNum){
                           case 2: room2 = true; break;
                           case 3: room3 = true; break;
                           case 4: room4 = true; break;
                           case 5: room5 = true; break;
                           case 6: room6 = true; break;
                           case 7: room7 = true; break;
                           case 8: room8 = true; break;
                           case 9: room9 = true; break;
                           case 10: room10 = true; break;  
                     }
                  }                                   
                  
                  
                  //There has been a room change and see if you need to load in the new room or load in the saved room
                  String newRoom = gr.getNewRoomName();
                  if(newRoom.equals("Room2.txt") && room2)
                     newRoom = "room2Save.txt";
                  else if(newRoom.equals("Room3.txt") && room3)
                     newRoom = "room3Save.txt";
                  else if(newRoom.equals("Room4.txt") && room4)
                     newRoom = "room4Save.txt";
                  else if(newRoom.equals("Room5.txt") && room5)
                     newRoom = "room5Save.txt";
                  else if(newRoom.equals("Room6.txt") && room6)
                     newRoom = "room6Save.txt";
                  else if(newRoom.equals("Room7.txt") && room7)
                     newRoom = "room7Save.txt";
                  else if(newRoom.equals("Room8.txt") && room8)
                     newRoom = "room8Save.txt";
                  else if(newRoom.equals("Room9.txt") && room9)
                     newRoom = "room9Save.txt";
                  else if(newRoom.equals("Room10.txt") && room10)
                     newRoom = "room10Save.txt";
                                    
                  //Load in the player Values
                  playerX = gr.getPlayerX(); //get new x
                  playerY = gr.getPlayerY(); //get new y after room is done being drawn
                  gr = new GameRunner(newRoom,playerX,playerY);
         
                  //Clear the board and draw the new room
                  root.getChildren().clear();
                  gr.drawRoom(gc);
                  root.getChildren().add(gr);
                }
            }*/
  
    private long pauseTime;
    private void togglePause() {
       isPaused = !isPaused;
       long currentTime = System.currentTimeMillis();
       if(isPaused == true && gameBegin == true){ //game is paused
         pauseTime = currentTime;
         root.getChildren().add(pauseMenu); 
         exitBtn.requestFocus();   
       }
       else{ //remove the pause menu
         pauseTime = currentTime - pauseTime;
         root.getChildren().remove(pauseMenu);
         gr.requestFocus();
         //gr.setPauseInterval(pauseTime);
       }
	}
    
 private void toggleToolMenu() {
    if (!isToolMenuVisible) {
        // Add the toolMenu and make it visible
        if (!root.getChildren().contains(toolMenu)) {
            root.getChildren().add(toolMenu);
        }
        toolMenu.setVisible(true);  // Ensure it's visible
        isToolMenuVisible = true;
    } else {
        // Remove the toolMenu and hide it
        toolMenu.setVisible(false);  // Hide the menu when it's toggled off
        if (root.getChildren().contains(toolMenu)) {
            root.getChildren().remove(toolMenu);
        }
        isToolMenuVisible = false;
    }
}


   
   //Make the Key Handler
   public class KeyListenerDown implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event){ 
      switch (event.getCode()) {
            case W: wKeyDown = true; break;
            case A: aKeyDown = true; break;
            case S: sKeyDown = true; break;
            case D: dKeyDown = true; break;
            case ESCAPE: togglePause(); break;
            case SPACE: toggleToolMenu(); break;

            
            default: break;
            }
      }
         
      public void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W: wKeyDown = false; break;
            case A: aKeyDown = false; break;
            case S: sKeyDown = false; break;
            case D: dKeyDown = false; break;
            default: break;
        }
      }    
   }
   
   public void update() {
        double speed = 2;
        double newX = playerX;
        double newY = playerY;
        boolean clear  = true;

        if (wKeyDown) newY -= speed;
        if (sKeyDown) newY += speed;
        if (aKeyDown) newX -= speed;
        if (dKeyDown) newX += speed;
        
        // Check if the new position is walkable before moving
        if(gr.canMoveTo(newX, newY)) {
           playerX = newX;
           playerY = newY;
        }
                
        //Check out of bounds conditions
        if(playerX > 640)
            playerX = 640;
        if(playerX < 0)
            playerX = 0;
        if(playerY > 680)
            playerY = 680;
        if(playerY < 0)
            playerY = 0;
    }
    
    //Series of private methods for movin the spring
    private void startSpringAnimation(double targetX, double targetY) {
        isSpringAnimationActive = true;
        springTargetX = targetX;
        springTargetY = targetY;
        springAnimationStartTime = System.nanoTime();
    }
    private void updateSpringAnimation(long now) {
        long elapsedTime = (now - springAnimationStartTime) / 1_000_000; // Convert to milliseconds
        if (elapsedTime >= SPRING_ANIMATION_DURATION) {
            // Animation complete
            playerX = springTargetX;
            playerY = springTargetY;
            isSpringAnimationActive = false;
        } else {
            // Interpolate position
            double progress = (double) elapsedTime / SPRING_ANIMATION_DURATION;
            playerX = lerp(playerX, springTargetX, progress);
            playerY = lerp(playerY, springTargetY, progress);
        }
        gr.draw(playerX, playerY, gc);
    }
    private double lerp(double start, double end, double progress) {
        return start + (end - start) * progress;
    }
    
    //Functions for creating the tool menu
   private void createToolMenu() {
    toolMenu = new VBox(10); // Spacing between elements
    
    toolCanvas = new Canvas(450, 375);
    toolGC = toolCanvas.getGraphicsContext2D();
    
    // Add elements to the VBox
    toolMenu.getChildren().addAll(toolCanvas);
    
    // Initially set the tool menu as not visible
    toolMenu.setVisible(false);
    
    // Add the tool menu to the root layout
}

    private void drawOnToolCanvas() {
    // Clear the canvas
    
    // Set the fill color
    toolGC.setFill(Color.LIGHTGREY);
    
    // Draw a rectangle
    toolGC.fillRect(0,0,toolCanvas.getWidth(), toolCanvas.getHeight());
    toolGC.setFill(Color.BLACK);
    
   //PipeWrench Top Left = (37.5,75)
       double x = 37.5;
       double y = 75;
       if(hasWrench){
         toolGC.setFill(javafx.scene.paint.Color.RED);
       }
       toolGC.fillRect(x,y,100,22);
       if(hasWrench){
         toolGC.setFill(javafx.scene.paint.Color.DARKGREY);
       }
       toolGC.fillRect(x+100,y-6,8,40);
       
       toolGC.fillRect(x+100,y+22,60,20);
       
       toolGC.fillRect(x+150,y-6,10,47);

      double spacing = 2;
      double size = 6;
      for (int i = 0; i < 3; i++) {
        // Calculate the x position for each triangle
        double triangleY = y + (i * (size + spacing));
        double triangleX = x + 144; 
        double[] xPoints = {
             triangleX,           // Left point
             triangleX+ size,  // Top point
             triangleX+ size     // Right point
        };
        
        double[] yPoints = {
            triangleY + size/2,    // Left point
            triangleY,           // Top point
            triangleY + size     // Right point
        };
        
        // Draw the triangle
        toolGC.fillPolygon(xPoints, yPoints, 3);
        toolGC.strokePolygon(xPoints, yPoints, 3);
      }
      
  //CrescentWrench top left(37.5,150)
    toolGC.setFill(Color.BLACK);
      
      if(hasCrescentWrench){
         toolGC.setFill(Color.DARKGREY);
         toolGC.setStroke(Color.BLACK);
      }
      x = 37.5;
      y = 150;
  
      double[] xPoints = {0,20,37.5,112.5,130,150,150,122,115,122,150,150,130,112.5,37.5,20,0,0,18,25,18,0};
      double[] yPoints = {0,0,20,20,0,0,20,20,37.5,55,55,75,75,55,55,75,75,55,55,37.5,20,20};

       // Begin the path
       toolGC.beginPath();
       toolGC.moveTo(xPoints[0] +x, yPoints[0] + y);
   
       // Draw lines to all points
       for (int i = 1; i < xPoints.length; i++) {
           toolGC.lineTo(xPoints[i] + x, yPoints[i]+y);
       }
   
       // Close the path back to the first point
       toolGC.lineTo(xPoints[0]+x, yPoints[0]+y);
       toolGC.closePath();
       toolGC.fill();
       toolGC.stroke();   
   
   //Pipe (262.5,150)
    toolGC.setFill(Color.BLACK);
      if(hasPipe){
          toolGC.setFill(javafx.scene.paint.Color.GREY);
          toolGC.setStroke(javafx.scene.paint.Color.BLACK);
      }
      
      x= 262.5;
      y=168.75;
   
      toolGC.fillRect(x,y, 150,37.5);
      
      for(int i = 0;i <= 5; i++){
         int mod = i * 4;
         toolGC.strokeLine(x+mod,y , x+mod, y + 37.5);
         toolGC.strokeLine(x +150-mod, y, x+150-mod, y + 37.5);

       }

    
   //Tape(187.5,262.5)
     toolGC.setFill(Color.BLACK);

      x = 187.5;
      y=262.6;
      if(hasTape){
          toolGC.setFill(javafx.scene.paint.Color.GREY);
      }
      toolGC.fillOval(x + 7.5, y +7.5, 60, 60);
        
        // Draw the smaller grey circle
      toolGC.setFill(javafx.scene.paint.Color.LIGHTGREY);
      toolGC.setFill(Color.BLACK);
      
      if(hasTape){
           toolGC.setFill(javafx.scene.paint.Color.GREY);
      }
      toolGC.setFill(Color.BLACK);
      toolGC.fillOval(x + 27.5, y + 27.5, 20, 20);
      
      

      // Set line width and color
      //Screw Driver (262.5,75)
      x = 262.5;
      y = 75;
        toolGC.setLineWidth(2);
         
       
        // Flathead tip: draw the two diagonal lines
        if(hasScrewdriver){
           toolGC.setStroke(Color.GRAY);
        }
        toolGC.strokeLine(x + 95, y + 15, x + 110, y + 10);  // Line 1 (x1, y1, x2, y2)
        toolGC.strokeLine(x + 95, y + 25, x + 110, y + 30);  // Line 2 (x1, y1, x2, y2)
         
        // Fill the area between the diagonal lines with a polygon
       if(hasScrewdriver){
         toolGC.setFill(Color.GRAY);
       }
       double[] xP = {x + 95, x + 95, x + 110, x + 110};
       double[] yP = {y + 15, y + 25, y + 30, y + 10};
       toolGC.fillPolygon(xP, yP, 4);  // Fill the flathead tip
        
        // Shaft: draw a thin rectangle for the shaft
        if(hasScrewdriver){
           toolGC.setFill(Color.GRAY);
        }
        toolGC.fillRect(x + 68, y + 15, 30, 10);  // (x, y, width, height)
        
        // Handle: draw a rounded rectangle for the handle
        if(hasScrewdriver){
          toolGC.setFill(Color.BROWN);
        }
        toolGC.fillRoundRect(x, y, 70, 40, 40, 40);  // (x, y, width, height, arcWidth, arcHeight)

        
    // Draw some text
    toolGC.setFill(Color.BLACK);
    toolGC.setFont(new Font("Comic Sans MS", 20));
    toolGC.fillText("Tool Box", 150, 30);
    }
    
    public static void pickUpScrewdriver(){
       hasScrewdriver = true;
    }
     public static void pickUpTape(){
       hasTape = true;
    }
     public static void pickUpWrench(){
       hasWrench = true;
    }
     public static void pickUpCrescentWrench(){
       hasCrescentWrench = true;
    }
     public static void pickUpPipe(){
       hasPipe = true;
    }
     
     
    
     
     
}    