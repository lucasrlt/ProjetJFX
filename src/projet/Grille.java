/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

/**
 *
 * @author p1710505
 */
public class Grille {
    public Case[][] plateau;
    public int dimX;
    public int dimY;
    //public ArrayList<Chemin> chemin (Tableau dynamique de chemins)
    
    public Grille(int dimX, int dimY)
    {
        this.dimX = dimX;
        this.dimY = dimY;
        this.plateau = new Case[this.dimX][this.dimY];
        //this.chemin = new ArrayList();
    }
}
