package projet;

import java.util.Observable;

public class Modele extends Observable {
    Grille grille;

    public Modele() {
        super();

        grille = new Grille(3, 3);
    }
}
