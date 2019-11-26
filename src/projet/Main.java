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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;

import projet.modele.*;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author p1710505
 */
public class Main extends Application {
    Modele modele;

    @Override
    public void start(final Stage primaryStage) {

        BorderPane border = new BorderPane();
        modele = new Modele();

        Text[][] tabText = new Text[modele.grille.dimX][modele.grille.dimY];
        /*
         * Text affichage = new Text("Grille Drag&Drop");
         * affichage.setFont(Font.font("Verdana", 30)); affichage.setFill(Color.RED);
         * border.setTop(affichage); TODO Modifier pour avoir les règles
         */

        final GridPane gPane = new GridPane();
        dessinerGrille(gPane, primaryStage);

        modele.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg != null) {
                    for (Case c : ((Chemin) arg).casesIntermediaires) {
                        if (c instanceof CaseLigne) {
                            ObservableList<Node> noeuPourCase = ((Group) gPane.getChildren().get(c.position.y * modele.grille.dimX + c.position.x)).getChildren();
                            if (noeuPourCase.size() > 1)
                                noeuPourCase.remove(1, 2);
                        }
                    }
                } else {
                    for (Chemin ch : modele.grille.chemins) {
                        for (Case c : ch.casesIntermediaires) {
                            if (c instanceof CaseLigne) {
                                ObservableList<Node> noeuPourCase = ((Group) gPane.getChildren().get(c.position.y * modele.grille.dimX + c.position.x)).getChildren();
                                if (noeuPourCase.size() > 1)
                                    noeuPourCase.remove(1, 2);
                                noeuPourCase.add(dessinerLigne(((CaseLigne) c).ligne));
                            }
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

    private Node dessinerLigne(Ligne type) {
        if (type == Ligne.VERTICALE) {
            Rectangle ligne = new Rectangle(47, 5, 6, 90);
            ligne.setFill(Color.DARKRED);
            ligne.setStrokeWidth(6);

            return ligne;
        }
        else if (type == Ligne.HORIZONTALE) {
            Rectangle ligne = new Rectangle(5, 47, 90, 6);
            ligne.setFill(Color.DARKRED);
            ligne.setStrokeWidth(6);

            return ligne;
        }
        else {
            final int startX = 50;
            final int startY = type == Ligne.HAUT_DROITE || type == Ligne.HAUT_GAUCHE ? 5 : 95;

            final int endX = type == Ligne.HAUT_GAUCHE || type == Ligne.BAS_GAUCHE ? 5 : 95;
            final int endY = 53;
            //Creating an object of the class named Path
            Path path = new Path();

            //Moving to the starting point
            MoveTo moveTo = new MoveTo();
            moveTo.setX(startX);
            moveTo.setY(startY);

            //Instantiating the class CubicCurve
            CubicCurveTo cubicCurveTo = new CubicCurveTo();

            //Setting properties of the class CubicCurve
            cubicCurveTo.setControlX1(50);
            cubicCurveTo.setControlY1(53);
            cubicCurveTo.setControlX2(50);
            cubicCurveTo.setControlY2(53);
            cubicCurveTo.setX(endX);
            cubicCurveTo.setY(endY);

            path.setStroke(Color.DARKRED);
            path.setStrokeWidth(6);
            //Adding the path elements to Observable list of the Path class
            path.getElements().add(moveTo);
            path.getElements().add(cubicCurveTo);

            return path;
        }
    }

    private void dessinerGrille(GridPane gPane, Stage primaryStage) {
        gPane.getChildren().clear();

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

                        if (!modele.enfoncerClicGrille(clicPosY, clicPosX)) {
                            Toast.makeText(primaryStage, "Tout chemin doit\ncommencer sur un symbôle.", 1000, 200, 200);
                        }
                    }
                });

                root.setOnDragEntered(new EventHandler<DragEvent>() { // Quand le clic est maintenu
                    public void handle(DragEvent event) {

                        if (!modele.parcoursGrille(clicPosY, clicPosX)) {
                            Toast.makeText(primaryStage, "Impossible d'aller sur cette case.", 1000, 200, 200);
                        }

                        event.consume();
                    }
                });

                root.setOnDragDone(new EventHandler<DragEvent>() { // Clic relaché
                    public void handle(DragEvent event) {
                        if (!modele.relacherClicGrille(clicPosY, clicPosX)) {
                            Toast.makeText(primaryStage, "Tout chemin doit finir sur un symbôle.", 1000, 200, 200);
                        }
                    }
                });

                gPane.add(root, x, y);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
