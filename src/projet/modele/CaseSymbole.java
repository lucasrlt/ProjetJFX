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
public class CaseSymbole extends Case{
    public Symbole symbole;

    public CaseSymbole(Position _p, Symbole _s) {
        super(_p, 'S');
        this.symbole = _s;
    }
}
