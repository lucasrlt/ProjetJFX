/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.modele;

/**
 *
 * @author p1710505
 */
public class Position {
    public int x;
    public int y;

    public Position(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
