package projet;

import projet.modele.*;

import java.util.Observable;

public class Controleur extends Observable {
    int lastC, lastR;
    public Grille grille;
    boolean allowDrag;

    public Controleur() {
        super();

        grille = new Grille(4, 4);
        allowDrag = false;
    }

    public boolean enfoncerClicGrille(int r, int c) {
        boolean cheminCree = grille.nouveauChemin(c, r);

        if (cheminCree) {
            System.out.println("Début du chemin : " + c + "-" + r);
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
            System.out.println("Fin chemin : " + c + "-" + r + " -> " + lastC + "-" + lastR);

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
            System.out.println("Case que vous pointez : " + c + "-" + r);

            if (!grille.creerLignePourCase(c, r))
                return false;
        }

        this.update();

        return true;
    }

    public void rejouer()
    {
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
