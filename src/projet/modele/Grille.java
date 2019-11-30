/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.modele;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author p1710505
 */
public class Grille {
    public Case[][] plateau;
    public int dimX;
    public int dimY;
    public ArrayList<Chemin> chemins;
    public ArrayList<Pair<CaseSymbole, CaseSymbole>> pairesSymboles;
    public boolean ecranRegles = true;

    public Grille(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.plateau = new Case[this.dimX][this.dimY];
        this.chemins = new ArrayList<Chemin>();
        this.pairesSymboles = new ArrayList<Pair<CaseSymbole, CaseSymbole>>();

        this.initGrille();
    }

    public void initGrille() {
        if(!ecranRegles)
        {
        chemins.clear();
        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                plateau[x][y] = new Case(new Position(x, y));
            }
        }

        pairesSymboles.add(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(0, 0), Symbole.CARRE),
                new CaseSymbole(new Position(1, 2), Symbole.CARRE)
                ));
        plateau[0][0] = pairesSymboles.get(0).getKey();
        plateau[1][2] = pairesSymboles.get(0).getValue();

        pairesSymboles.add(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(2, 0), Symbole.ROND),
                new CaseSymbole(new Position(2, 2), Symbole.ROND)
        ));
        plateau[2][0] = pairesSymboles.get(1).getKey();
        plateau[2][2] = pairesSymboles.get(1).getValue();
    }}

    public void clearChemin(Chemin chemin) {
        for (Case ca : chemin.casesIntermediaires) {
            if (ca instanceof CaseLigne)
                plateau[ca.position.x][ca.position.y] = new Case(ca.position);
        }

        chemins.remove(chemin);
    }

    public boolean verifierVictoire() {
        Optional<Chemin> cheminPourSymbole;
        for (Pair<CaseSymbole, CaseSymbole> paireSymbole : pairesSymboles) {
            cheminPourSymbole = trouverCheminPourSymbole(paireSymbole.getKey().symbole);
            if (!cheminPourSymbole.isPresent() || !cheminPourSymbole.get().estValide())
                return false;
        }

        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                if (!((plateau[x][y] instanceof CaseSymbole) || (plateau[x][y] instanceof CaseLigne)))
                    return false;
            }
        }

        return true;
    }

    public Chemin dernierChemin() {
        return chemins.get(chemins.size() - 1);
    }

    private Optional<Chemin> trouverCheminPourSymbole(Symbole symbole) {
        Optional<Chemin> resultat = Optional.empty();

        for (Chemin chemin : chemins) {
            if (chemin.getPremiereCase().symbole == symbole) {
                resultat = Optional.of(chemin);
                break;
            }
        }

        return resultat;
    }

    public boolean nouveauChemin(int x, int y) {
        if (plateau[x][y] instanceof CaseSymbole) {
            CaseSymbole caseVisee = (CaseSymbole) plateau[x][y];
            int id = -1;

            Optional<Chemin> cheminExistant = trouverCheminPourSymbole(((CaseSymbole) plateau[x][y]).symbole);
            if (cheminExistant.isPresent())
                clearChemin(cheminExistant.get());

            Chemin nc = new Chemin(plateau[x][y]);
            chemins.add(nc);

            return true;
        } else {
            System.out.println("/!\\ Tout chemin doit démarrer à un symbôle !");
        }

        return false;
    }

    public boolean terminerChemin(int x, int y) {
        if (!(plateau[x][y] instanceof CaseSymbole)) {
            clearChemin(dernierChemin());
            return false;
        }

        Case caseFinale = dernierChemin().getDerniereCase();

        dernierChemin().ajouterCase(plateau[x][y]);
        if (!dernierChemin().estValide()) {
            clearChemin(dernierChemin());
            return false;
        }

        if (caseFinale instanceof CaseLigne) {
            ((CaseLigne) plateau[caseFinale.position.x][caseFinale.position.y]).ligne = dernierChemin().lignePourCase(dernierChemin().casesIntermediaires.size() - 2);
        }

        return true;
    }

    public void ajouterLigne(int x, int y) {
        chemins.get(chemins.size() - 1).ajouterCase(plateau[x][y]);
    }

    public void finChemin(int x, int y) {
        chemins.get(chemins.size() - 1).ajouterCase(plateau[x][y]);
    }

    public boolean creerLignePourCase(int x, int y) {
        if (!(plateau[x][y] instanceof CaseLigne)) {
            if (!(plateau[x][y] instanceof CaseSymbole)) {
                Case finChemin = dernierChemin().getDerniereCase();

                // On ne continue que si la case cliquée est une des cases adjacentes à la dernières case du chemin en cours.
                if ((((x == finChemin.position.x + 1 || x == finChemin.position.x - 1)) && y == finChemin.position.y)
                        || ((y == finChemin.position.y + 1 || y == finChemin.position.y - 1)
                                && x == finChemin.position.x))
                {
                    // Création de la nouvelle case
                    plateau[x][y] = new CaseLigne(new Position(x, y), Ligne.VERTICALE);
                    dernierChemin().ajouterCase(plateau[x][y]);

                    // Met à jour la ligne de la nouvelle case
                    ((CaseLigne) plateau[x][y]).ligne = dernierChemin().lignePourCase(dernierChemin().getLongueur() - 1);

                    // Met à jour la ligne de la case précédente
                    if (finChemin instanceof CaseLigne) {
                        ((CaseLigne) plateau[finChemin.position.x][finChemin.position.y]).ligne = dernierChemin()
                                .lignePourCase(dernierChemin().getLongueur() - 2);

                    }
                }
            }
        } else if (!(new Position(x, y) == dernierChemin().getDerniereCase().position)) {
            return false;
        }

        return true;
    }
}
