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
    private int x;
    private int y;

    public Position(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    public int getPositionX()
    {
        return this.x;
    }
    public int getPositionY()
    {
        return this.y;
    }
    public void setPositionX(int x)
    {
        this.x = x;
    }
    public void setPositionY(int y)
    {
        this.y = y;
    }
}
