package projet;

import projet.modele.*;

import java.util.Observable;

public class Modele extends Observable {
    int lastC, lastR;
    public Grille grille;

    public Modele() {
        super();

        grille = new Grille(3, 3);
    }
        public void enfoncerClicGrille(int c, int r) {
            // TODO
            System.out.println("Début du chemin : " + c + "-" + r);
            setChanged();
            notifyObservers();
        }

        public void relacherClicGrille(int c, int r) {
            // TODO
            // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement

            System.out.println("Fin chemin : " + c + "-" + r + " -> " + lastC + "-" + lastR);
            setChanged();
            notifyObservers();
        }

        public void parcoursGrille(int r, int c) {
            lastC = c;
            lastR = r;
            System.out.println("Case que vous pointez : " + c + "-" + r);

            if (!(grille.plateau[c][r] instanceof CaseSymbole)) {
                grille.plateau[c][r] = new CaseLigne(new Position(c, r), Ligne.VERTICALE);
                setChanged();
                notifyObservers(new Position(c, r));
            }
        }


    }
