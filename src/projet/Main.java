/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

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
import projet.modele.CaseSymbole;
import projet.modele.Symbole;

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
        Modele modele = new Modele();
        Text[][] tabText = new Text[modele.grille.dimX][modele.grille.dimY];
        for (int y = 0; y < modele.grille.dimY; y++) {
            for (int x = 0; x < modele.grille.dimX; x++) {
                final Text t = new Text(modele.grille.plateau[x][y].caractere == '_' ? " " : ((CaseSymbole) modele.grille.plateau[x][y]).symbole.toString());

                System.out.print(modele.grille.plateau[x][y].caractere);
                t.setWrappingWidth(30);
                t.setFont(Font.font ("Verdana", 20));
                t.setTextAlignment(TextAlignment.CENTER);

                tabText[x][y] = t;
                gPane.add(t, x, y);
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
