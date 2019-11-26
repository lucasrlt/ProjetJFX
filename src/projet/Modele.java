package projet;

import projet.modele.*;

import java.util.Observable;

public class Modele extends Observable {
    int lastC, lastR;
    public Grille grille;
    //public Chemin chemin;

    public Modele() {
        super();

        grille = new Grille(3, 3);
    }
        public void enfoncerClicGrille(int r, int c) {
            System.out.println("Début du chemin : " + c + "-" + r);
            grille.nouveauChemin(c, r);
            setChanged();
            notifyObservers();
        }

        public void relacherClicGrille(int r, int c) {
            // TODO
            // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement
            System.out.println("Fin chemin : " + c + "-" + r + " -> " + lastC + "-" + lastR);

            Case caseFinale = grille.dernierChemin().caseFinale();
            System.out.println(lastC + " " + lastR);
            grille.finChemin(lastC, lastR);

            System.out.println(grille.dernierChemin().caseFinale().position);

            if (caseFinale instanceof CaseLigne) {
                System.out.println("IOIEG");
                ((CaseLigne) grille.plateau[caseFinale.position.x][caseFinale.position.y]).ligne = grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 2);
            }

            setChanged();
            notifyObservers(new Position(lastC, lastR));
        }

        public void parcoursGrille(int r, int c) {
            lastC = c;
            lastR = r;
            System.out.println("Case que vous pointez : " + c + "-" + r);

            if (!(grille.plateau[c][r] instanceof CaseSymbole) && !(grille.plateau[c][r] instanceof CaseLigne)) {
                Case caseFinale = grille.dernierChemin().caseFinale();



                grille.plateau[c][r] = new CaseLigne(new Position(c, r), Ligne.VERTICALE);
                grille.chemins.get(grille.chemins.size() - 1).ajoutCaseChemin(grille.plateau[c][r]);
                ((CaseLigne) grille.plateau[c][r]).ligne  =  grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 1);

                if (caseFinale instanceof CaseLigne) {
                    System.out.println("OUI");
                    ((CaseLigne) grille.plateau[caseFinale.position.x][caseFinale.position.y]).ligne = grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 2);

                }


                setChanged();
                notifyObservers(new Position(c, r));
            }

            setChanged();
            notifyObservers();

        }


    }
