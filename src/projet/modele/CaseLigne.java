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
public class CaseLigne extends Case {
    Ligne ligne;

    public CaseLigne(Position _position, Ligne _ligne) {
        super(_position, 'L');
        this.ligne = _ligne;
    }
}
