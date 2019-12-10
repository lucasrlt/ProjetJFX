package projet;

import projet.modele.*;

import java.util.Observable;

public class Controleur extends Observable {
    private int lastC, lastR;
    public Grille grille;
    boolean demandeSolution;
    private boolean allowDrag;

    public Controleur() {
        super();

        grille = new Grille(4, 4);
        allowDrag = false;
        demandeSolution = false;
    }

    public boolean enfoncerClicGrille(int r, int c) {
        demandeSolution = false;
        boolean cheminCree = grille.nouveauChemin(c, r);

        if (cheminCree) {
            allowDrag = true;

            this.update();
        } else {
            allowDrag = false;
        }

        return cheminCree;
    }

    public boolean relacherClicGrille(int r, int c) {
        boolean success = true;

        if (allowDrag) {
            allowDrag = false;

            if (!grille.terminerChemin(lastC, lastR))
                success = false;
        }

        this.update();

        return success;
    }

    public boolean parcoursGrille(int r, int c) {
        if (allowDrag) {
            lastC = c;
            lastR = r;
            if (!grille.creerLignePourCase(c, r))
                return false;
        }

        this.update();

        return true;
    }

    public boolean trouverSolution() {
        Solveur g = new Solveur(grille);

        if(g.trouverSolution()) {
            demandeSolution = true;
            update();
            return true;
        }

        return false;
    }

    public void rejouer()
    {
        demandeSolution = false;
        grille.initGrille();
        this.update();
    }

    public void chargerNiveau(int niveau) {
        if (niveau >= 0 && niveau < Niveau.nbNiveaux) {
            grille.changerNiveau(niveau);
        }
    }

    public void niveauSuivant() {
        grille.changerNiveau(grille.nbNiveau != Niveau.nbNiveaux - 1 ? grille.nbNiveau + 1 : 0);
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

}
