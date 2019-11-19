/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;  
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author p1710505
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        int dimW = 800;
        int dimH = 800;
        int colonne = 0;
        int ligne = 0;
   
        BorderPane border = new BorderPane();
        GridPane gPane = new GridPane();
        Group root = new Group();
        Scene scene = new Scene(root,dimW,dimH,Color.LIGHTGOLDENRODYELLOW);
        
        primaryStage.setTitle("Casse-tête symboles");
        primaryStage.setScene(scene);
        
        Rectangle rectangle = new Rectangle();
        rectangle.setX(5);
        rectangle.setY(5);
        rectangle.setWidth(200);
        rectangle.setHeight(200);
        rectangle.setFill(Color.BLUEVIOLET);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(10);
        root.getChildren().add(rectangle);
       
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
