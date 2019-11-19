package projet;

import projet.modele.Grille;

import java.util.Observable;

public class Modele extends Observable {
    public Grille grille;

    public Modele() {
        super();

        grille = new Grille(3, 3);
    }
}
