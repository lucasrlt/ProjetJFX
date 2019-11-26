/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.modele;

import java.util.ArrayList;

/**
 *
 * @author p1710505
 */
public class Grille {
    public Case[][] plateau;
    public int dimX;
    public int dimY;
    public ArrayList<Chemin> chemins;
    
    public Grille(int dimX, int dimY)
    {
        this.dimX = dimX;
        this.dimY = dimY;
        this.plateau = new Case[this.dimX][this.dimY];
        this.chemins = new ArrayList();

        this.initGrille();
    }

    public void initGrille() {
        for (int y = 0; y < dimY; y++) {
            for (int x = 0; x < dimX; x++) {
                plateau[x][y] = new Case(new Position(x, y));
            }
        }

        plateau[0][0] = new CaseSymbole(new Position(0, 0), Symbole.CARRE);
        plateau[1][2] = new CaseSymbole(new Position(1, 2), Symbole.CARRE);

        plateau[2][0] = new CaseSymbole(new Position(2, 0), Symbole.ROND);
        plateau[2][2] = new CaseSymbole(new Position(2, 2), Symbole.ROND);
    }

    public Chemin dernierChemin() { return chemins.get(chemins.size() - 1); }

    public boolean nouveauChemin(int x, int y) {
        if (plateau[x][y] instanceof CaseSymbole) {
            Chemin nc = new Chemin(plateau[x][y]);
            chemins.add(nc);

            return true;
        } else {
            System.out.println("/!\\ Tout chemin doit démarrer à un symbôle !");
        }

        return false;
    }

    public void ajouterLigne(int x, int y) {
        chemins.get(chemins.size() - 1).ajoutCaseChemin(plateau[x][y]);
    }

    public void finChemin(int x, int y) {
        chemins.get(chemins.size() - 1).ajoutCaseChemin(plateau[x][y]);
    }
}
