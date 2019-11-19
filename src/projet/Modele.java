package projet;

import java.util.Observable;

public class Modele extends Observable {
    Grille grille;
    int lastC, lastR;

    public Modele() {
        super();

        grille = new Grille(3, 3);
    }
        public void enfoncerClicGrille(int c, int r) {
            // TODO
            System.out.println("startDD : " + c + "-" + r);
            setChanged();
            notifyObservers();
        }

        public void relacherCliclGrille(int c, int r) {
            // TODO

            // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement

            System.out.println("stopDD : " + c + "-" + r + " -> " + lastC + "-" + lastR);
            setChanged();
            notifyObservers();
        }

        public void parcoursGrille(int c, int r) {
            lastC = c;
            lastR = r;
            System.out.println("parcoursDD : " + c + "-" + r);
            setChanged();
            notifyObservers();
        }


    }
