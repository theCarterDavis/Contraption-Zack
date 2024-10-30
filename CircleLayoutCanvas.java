import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CircleLayoutCanvas extends Application {

    private static final int SQUARE_SIZE = 75;
    private static final int CIRCLE_RADIUS = 8;
    private static final int SMALL_CIRCLE_RADIUS = 5;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(SQUARE_SIZE, SQUARE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Draw circles in all 4 rows/columns
        drawCircles(gc, false, true, "RED", 1);
        drawCircles(gc, true, true, "BLUE", 2);
        drawCircles(gc, false, true, "GREEN", 3);
        drawCircles(gc, true, true, "PURPLE", 4);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, SQUARE_SIZE, SQUARE_SIZE);

        primaryStage.setTitle("Circle Layout on Canvas (75x75)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawCircles(GraphicsContext gc, boolean isDown, boolean isHorizontal, String color, int position) {
        Color c = Color.web(color);
        gc.setFill(c);

        int radius = isDown ? SMALL_CIRCLE_RADIUS : CIRCLE_RADIUS;
        int totalCircles = 3;
        int totalRows = 4;
        
        // Calculate the spacing dynamically
        double spacingX = (SQUARE_SIZE - (2 * radius * totalCircles)) / (totalCircles + 1);
        
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
            
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            gc.setStroke(Color.valueOf("BLACK"));
            gc.setLineWidth(1.5);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}