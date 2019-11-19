/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.Console;
import java.util.Observable;
import java.util.Observer;
import projet.modele.CaseSymbole;
import projet.modele.Position;
import projet.modele.Symbole;

/**
 *
 * @author p1710505
 */
public class Main extends Application {
    Modele modele;

    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();
        modele = new Modele();

        Text[][] tabText = new Text[modele.grille.dimX][modele.grille.dimY];
        /*
         * Text affichage = new Text("Grille Drag&Drop");
         * affichage.setFont(Font.font("Verdana", 30)); affichage.setFill(Color.RED);
         * border.setTop(affichage); TODO Modifier pour avoir les règles
         */

        final GridPane gPane = dessinerGrille();

        modele.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg != null) {
                    Position p = (Position) arg;
                    ObservableList<Node> childrens = gPane.getChildren();

                    for (Node node : childrens) {
                        if (gPane.getRowIndex(node) == p.y && gPane.getColumnIndex(node) == p.x) {
                            Rectangle r = new Rectangle(47, 10, 6, 80);
                            r.setFill(Color.DARKRED);

                            ((Group) node).getChildren().add(r);
                            break;
                        }
                    }
                }


            }
        });

        gPane.setGridLinesVisible(true);
        border.setCenter(gPane);

        Scene scene = new Scene(border, Color.LIGHTGOLDENRODYELLOW);

        primaryStage.setTitle("Casse-tête symboles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane dessinerGrille() {
        GridPane gPane = new GridPane();

        for (int y = 0; y < modele.grille.dimY; y++) {
            for (int x = 0; x < modele.grille.dimX; x++) {
                Group root = new Group();

                final int clicPosY = y;
                final int clicPosX = x;

                Rectangle r = new Rectangle(x, y, 100, 100);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);

                root.getChildren().add(r);

                if (modele.grille.plateau[x][y] instanceof CaseSymbole) {
                    Symbole s = ((CaseSymbole) modele.grille.plateau[x][y]).symbole;
                    if (s == Symbole.CARRE) {
                        Rectangle re = new Rectangle(100 / 2 - 20, 100 / 2 - 20, 40, 40);
                        re.setFill(Color.BLUE);
                        root.getChildren().add(re);
                    }
                    if (s == Symbole.ROND) {
                        Circle re = new Circle(50, 50, 20);
                        re.setFill(Color.BLACK);
                        root.getChildren().add(re);
                    }

                }

                root.setOnDragDetected(new EventHandler<MouseEvent>() { // Au premier clic
                    public void handle(MouseEvent event) {

                        Dragboard db = root.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString("");
                        db.setContent(content);
                        event.consume();
                        modele.enfoncerClicGrille(clicPosY, clicPosX);
                    }
                });

                root.setOnDragEntered(new EventHandler<DragEvent>() { // Quand le clic est maintenu
                    public void handle(DragEvent event) {

                        modele.parcoursGrille(clicPosY, clicPosX);
                        event.consume();
                    }
                });

                root.setOnDragDone(new EventHandler<DragEvent>() { // Clic relaché
                    public void handle(DragEvent event) {
                        modele.relacherClicGrille(clicPosY, clicPosX);
                    }
                });

                gPane.add(root, x, y);
            }
        }

        return gPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
