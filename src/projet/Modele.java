package projet;

import projet.modele.Grille;

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

        public void parcoursGrille(int c, int r) {
            lastC = c;
            lastR = r;
            System.out.println("Case que vous pointez : " + c + "-" + r);
            setChanged();
            notifyObservers();
        }


    }
