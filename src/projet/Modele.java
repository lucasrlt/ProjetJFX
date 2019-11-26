package projet;

import projet.modele.*;

import java.util.Observable;

public class Modele extends Observable {
    int lastC, lastR;
    public Grille grille;
    boolean allowDrag;

    public Modele() {
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
        }

        return cheminCree;
    }

    public boolean relacherClicGrille(int r, int c) {
        // TODO
        // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement

        if (allowDrag) {
            System.out.println("Fin chemin : " + c + "-" + r + " -> " + lastC + "-" + lastR);

            Case caseFinale = grille.dernierChemin().caseFinale();
            grille.finChemin(lastC, lastR);

            if (caseFinale instanceof CaseLigne) {
                ((CaseLigne) grille.plateau[caseFinale.position.x][caseFinale.position.y]).ligne = grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 2);
            }
            allowDrag = false;

            if (!(grille.dernierChemin().caseFinale() instanceof CaseSymbole)) {
                setChanged();
                notifyObservers(grille.dernierChemin());

                for (Case ca : grille.dernierChemin().casesIntermediaires) {
                    if (ca instanceof CaseLigne)
                        grille.plateau[ca.position.x][ca.position.y] = new Case(ca.position);
                }
                grille.chemins.remove(grille.chemins.size() - 1);
                return false;
            } else {
                setChanged();
                notifyObservers();
            }
        }

        return true;
    }

    public boolean parcoursGrille(int r, int c) {
        if (allowDrag) {
            lastC = c;
            lastR = r;
            System.out.println("Case que vous pointez : " + c + "-" + r);

            if (!(grille.plateau[c][r] instanceof CaseLigne)) {
                if(!(grille.plateau[c][r] instanceof CaseSymbole)) {
                    Case caseFinale = grille.dernierChemin().caseFinale();

                    if ((((c == caseFinale.position.x + 1 || c == caseFinale.position.x - 1)) && r == caseFinale.position.y) || ((r == caseFinale.position.y + 1 || r == caseFinale.position.y - 1) && c == caseFinale.position.x))
                    {
                        grille.plateau[c][r] = new CaseLigne(new Position(c, r), Ligne.VERTICALE);
                        grille.chemins.get(grille.chemins.size() - 1).ajoutCaseChemin(grille.plateau[c][r]);
                        ((CaseLigne) grille.plateau[c][r]).ligne = grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 1);

                        if (caseFinale instanceof CaseLigne) {
                            ((CaseLigne) grille.plateau[caseFinale.position.x][caseFinale.position.y]).ligne = grille.dernierChemin().lignePourCase(grille.dernierChemin().casesIntermediaires.size() - 2);

                        }


                        setChanged();
                        notifyObservers();
                    }
                }
            } else if (!(new Position(c, r) == grille.dernierChemin().caseFinale().position)){
                return false;
            }

            setChanged();
            notifyObservers();
        }

        return true;
    }


    }
