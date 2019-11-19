/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.scene.input.*;
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

import java.io.Console;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author p1710505
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();
        GridPane gPane = new GridPane();

        Modele modele = new Modele();
        Text[][] tabText = new Text[modele.grille.dimX][modele.grille.dimY];
        /*Text affichage = new Text("Grille Drag&Drop");
        affichage.setFont(Font.font("Verdana", 30));
        affichage.setFill(Color.RED);
        border.setTop(affichage); TODO Modifier pour avoir les règles*/

        modele.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                // TODO
            }
        });


        for (int y = 0; y < modele.grille.dimY; y++) {
            for (int x = 0; x < modele.grille.dimX; x++) {

                final int clicPosY = y;
                final int clicPosX = x;

                final Text t = new Text(Character.toString(modele.grille.plateau[x][y].caractere));
                tabText[y][x] = t;
                System.out.print(modele.grille.plateau[x][y].caractere);
                t.setWrappingWidth(30);
                t.setFont(Font.font ("Verdana", 20));
                t.setTextAlignment(TextAlignment.CENTER);

                tabText[x][y] = t;
                t.setOnDragDetected(new EventHandler<MouseEvent>() { //Au premier clic
                    public void handle(MouseEvent event) {

                        Dragboard db = t.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString("");
                        db.setContent(content);
                        event.consume();
                        modele.enfoncerClicGrille(clicPosY, clicPosX);
                    }
                });

                t.setOnDragEntered(new EventHandler<DragEvent>() { //Quand le clic est maintenu
                    public void handle(DragEvent event) {

                        modele.parcoursGrille(clicPosY, clicPosX);
                        event.consume();
                    }
                });

                t.setOnDragDone(new EventHandler<DragEvent>() { //Clic relaché
                    public void handle(DragEvent event) {
                        modele.relacherClicGrille(clicPosY, clicPosX);
                    }
                });
                gPane.add(tabText[y][x], y, x);
            }
            System.out.println();
        }

        gPane.setGridLinesVisible(true);
        border.setCenter(gPane);

        Scene scene = new Scene(border, Color.LIGHTGOLDENRODYELLOW);

        primaryStage.setTitle("Casse-tête symboles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
