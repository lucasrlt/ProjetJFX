package projet;

import projet.modele.*;

import java.util.Observable;

public class Controleur extends Observable {
    int lastC, lastR;
    public Grille grille;
    boolean allowDrag;

    public Controleur() {
        super();

        grille = new Grille(3, 3);
        allowDrag = false;
    }

    public boolean enfoncerClicGrille(int r, int c) {
        boolean cheminCree = grille.nouveauChemin(c, r);

        if (cheminCree) {
            System.out.println("Début du chemin : " + c + "-" + r);
            allowDrag = true;
            setChanged();
            notifyObservers();
        } else {
            allowDrag = false;
        }

        return cheminCree;
    }

    public boolean relacherClicGrille(int r, int c) {
        boolean success = true;

        if (allowDrag) {
            System.out.println("Fin chemin : " + c + "-" + r + " -> " + lastC + "-" + lastR);

            allowDrag = false;

            if (!grille.terminerChemin(lastC, lastR))
                success = false;
        }

        setChanged();
        notifyObservers();

        return success;
    }

    public boolean parcoursGrille(int r, int c) {
        if (allowDrag) {
            lastC = c;
            lastR = r;
            System.out.println("Case que vous pointez : " + c + "-" + r);

            if (!grille.creerLignePourCase(c, r))
                return false;
        }

        setChanged();
        notifyObservers();

        return true;
    }
    public void rejouer()
    {
        grille.initGrille();
        setChanged();
        notifyObservers();
    }

}
