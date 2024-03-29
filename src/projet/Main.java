/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;

import projet.modele.*;

import javax.swing.*;

/**
 *
 * @author p1710505
 */
public class Main extends Application {
    private Controleur controleur;

    @Override
    public void start(final Stage primaryStage) {

        BorderPane border = new BorderPane();
        controleur = new Controleur();

        final GridPane gPane = new GridPane();
        dessinerGrille(gPane, primaryStage);

        afficherSlectionNiveau(gPane, primaryStage);

        controleur.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                // Nettoyage du board
                for (int y = 0; y <controleur.grille.dimY; y++) {
                    for (int x = 0; x < controleur.grille.dimX; x++) {
                        Case c = controleur.grille.plateau[x][y];

                        if (!(c instanceof CaseSymbole)) {
                            ObservableList<Node> noeuPourCase = ((Group) gPane.getChildren()
                                    .get(y * controleur.grille.dimX + x)).getChildren();
                            if (noeuPourCase.size() > 1)
                                noeuPourCase.remove(1, 2);
                        }
                    }
                }

                // Affichage des chemins
                for (Chemin ch : controleur.grille.chemins) {
                    for (Case c : ch.casesIntermediaires) {
                        if (c instanceof CaseLigne) {
                            ObservableList<Node> noeuPourCase = ((Group) gPane.getChildren()
                                    .get(c.position.y * controleur.grille.dimX + c.position.x)).getChildren();
                            if (noeuPourCase.size() > 1)
                                noeuPourCase.remove(1, 2);
                            noeuPourCase.add(dessinerLigne(((CaseLigne) c).ligne));
                        }
                    }
                }

                if (controleur.grille.verifierVictoire() && !controleur.demandeSolution) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Victoire !");
                    alert.setHeaderText("Vous avez gagné !");

                    if (controleur.grille.nbNiveau == Niveau.nbNiveaux - 1) {
                        alert.setContentText("Vous avez terminé tous les niveaux du jeu !\nVous pouvez:\n\t- Rejouer");
                        alert.showAndWait();
                        afficherSlectionNiveau(gPane, primaryStage);
                    } else {
                        alert.showAndWait();

                        controleur.niveauSuivant();
                        dessinerGrille(gPane, primaryStage);
                        controleur.update();
                    }



                }
            }

        });

        gPane.setGridLinesVisible(true);
        gPane.setAlignment(Pos.CENTER);
        border.setCenter(gPane);

        Scene scene = new Scene(border, 600,700, Color.LIGHTGOLDENRODYELLOW);

        final GridPane bPane = new GridPane();

        final GridPane rPane = new GridPane();

        Button nouvellePartie = new Button("Vider Grille");
        nouvellePartie.setTextFill(Color.MAROON);
        nouvellePartie.setTextAlignment(TextAlignment.CENTER);

        Button ecranRegles = new Button("Règles");
        ecranRegles.setTextFill(Color.MAROON);
        ecranRegles.setTextAlignment(TextAlignment.CENTER);

        Button ecranSelectionNiveau = new Button("Changer Niveau");
        ecranSelectionNiveau.setTextFill(Color.MAROON);
        ecranSelectionNiveau.setTextAlignment(TextAlignment.CENTER);

        Button trouverSolutionBtn = new Button("Afficher Solution");
        trouverSolutionBtn.setTextFill(Color.MAROON);
        trouverSolutionBtn.setTextAlignment(TextAlignment.CENTER);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                controleur.rejouer();
                dessinerGrille(gPane,primaryStage);
            }
        };

        EventHandler<ActionEvent> regles = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Règles");
                alert.setHeaderText("Ensemble de règles :");
                alert.setContentText("- Pour gagner, il suffit de relier les paires de symboles entres elles\n - De plus, vous devez remplir toutes les cases de la grille qui ne\n sont pas des symboles par une ligne\n- Vous ne pouvez pas repasser sur une case contenant une ligne");
                alert.showAndWait();
            }
        };

        EventHandler<ActionEvent> changerNiveau = e -> {
            afficherSlectionNiveau(gPane, primaryStage);
        };

        EventHandler<ActionEvent> trouverSolution = e -> {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Solution");
            alert1.setHeaderText("Recherche de la solution");
            alert1.setContentText("Cela peut prendre un peu de temps...");

            alert1.show();

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    if(!controleur.trouverSolution()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Aucune solution");
                        alert.setHeaderText("Ce problème semble ne pas avoir de solution.");

                        alert.showAndWait();
                    }
                    alert1.close();
                }
            });

        };

        nouvellePartie.setOnAction(event);
        ecranRegles.setOnAction(regles);
        ecranSelectionNiveau.setOnAction(changerNiveau);
        trouverSolutionBtn.setOnAction(trouverSolution);

        Text affichage = new Text("Casse-tête symboles");
        affichage.setFont(Font.font("Verdana", 30));
        affichage.setFill(Color.MAROON);

        bPane.add(nouvellePartie, 0, 0);
        rPane.add(affichage,0,0);
        bPane.add(ecranSelectionNiveau, 2, 0);
        bPane.add(ecranRegles,1,0);
        bPane.add(trouverSolutionBtn, 3, 0);
        bPane.setAlignment(Pos.TOP_CENTER);
        border.setBottom(bPane);
        rPane.setAlignment(Pos.CENTER);
        border.setTop(rPane);



        border.setMargin(bPane, new Insets(10));
        border.setMargin(rPane, new Insets(20));
        border.setMargin(gPane, new Insets(20));


        primaryStage.setTitle("Casse-tête symboles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherSlectionNiveau(GridPane gPane, Stage primaryStage) {
        List<String> choices = new ArrayList<>();
        for (int i = 0; i < Niveau.nbNiveaux; i++) {
            choices.add("Niveau " + String.valueOf(i + 1));
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Niveau 1", choices);
        dialog.setTitle("Jeu Casse Tête");
        dialog.setHeaderText("Bienvenue sur le jeu du casse tête !");
        dialog.setContentText("Choisissez votre niveau: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            controleur.chargerNiveau(Integer.parseInt(result.get().split(" ")[1]) - 1);
            dessinerGrille(gPane, primaryStage);
            controleur.update();
        }
    }

    private Node dessinerLigne(Ligne type) {
        if (type == Ligne.VERTICALE) {
            Rectangle ligne = new Rectangle(47, 5, 6, 90);
            ligne.setFill(Color.DARKRED);
            ligne.setStrokeWidth(6);

            return ligne;
        } else if (type == Ligne.HORIZONTALE) {
            Rectangle ligne = new Rectangle(5, 47, 90, 6);
            ligne.setFill(Color.DARKRED);
            ligne.setStrokeWidth(6);

            return ligne;
        } else {
            final int startX = 50;
            final int startY = type == Ligne.HAUT_DROITE || type == Ligne.HAUT_GAUCHE ? 5 : 95;

            final int endX = type == Ligne.HAUT_GAUCHE || type == Ligne.BAS_GAUCHE ? 5 : 95;
            final int endY = 53;
            // Creating an object of the class named Path
            Path path = new Path();

            // Moving to the starting point
            MoveTo moveTo = new MoveTo();
            moveTo.setX(startX);
            moveTo.setY(startY);

            // Instantiating the class CubicCurve
            CubicCurveTo cubicCurveTo = new CubicCurveTo();

            // Setting properties of the class CubicCurve
            cubicCurveTo.setControlX1(50);
            cubicCurveTo.setControlY1(53);
            cubicCurveTo.setControlX2(50);
            cubicCurveTo.setControlY2(53);
            cubicCurveTo.setX(endX);
            cubicCurveTo.setY(endY);

            path.setStroke(Color.DARKRED);
            path.setStrokeWidth(6);
            // Adding the path elements to Observable list of the Path class
            path.getElements().add(moveTo);
            path.getElements().add(cubicCurveTo);

            return path;
        }
    }

    private void dessinerGrille(GridPane gPane, Stage primaryStage) {
        gPane.getChildren().clear();

        for (int y = 0; y < controleur.grille.dimY; y++) {
            for (int x = 0; x < controleur.grille.dimX; x++) {
                Group root = new Group();

                final int clicPosY = y;
                final int clicPosX = x;

                Rectangle r = new Rectangle(x, y, 100, 100);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);

                root.getChildren().add(r);

                if (controleur.grille.plateau[x][y] instanceof CaseSymbole) {
                    Symbole s = ((CaseSymbole) controleur.grille.plateau[x][y]).symbole;
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
                    if (s == Symbole.TRIANGLE) {
                        Polygon re = new Polygon();
                        re.getPoints().addAll(50.0, 20.0,
                                20.0, 80.0,
                                80.0, 80.0);
                        re.setFill(Color.RED);
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

                        if (!controleur.enfoncerClicGrille(clicPosY, clicPosX)) {
                            Toast.makeText(primaryStage, "Tout chemin doit\ncommencer sur un symbôle.", 1000, 200, 200);
                        }
                    }
                });

                root.setOnDragEntered(new EventHandler<DragEvent>() { // Quand le clic est maintenu
                    public void handle(DragEvent event) {

                        if (!controleur.parcoursGrille(clicPosY, clicPosX)) {
                            Toast.makeText(primaryStage, "Impossible d'aller sur cette case.", 1000, 200, 200);
                        }

                        event.consume();
                    }
                });

                root.setOnDragDone(new EventHandler<DragEvent>() { // Clic relaché
                    public void handle(DragEvent event) {
                        if (!controleur.relacherClicGrille(clicPosY, clicPosX)) {
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
