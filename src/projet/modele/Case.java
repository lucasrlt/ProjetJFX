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
public class Case {
    public Position position;
    public char caractere;

    public Case(Position _position) {
        this.position = position;
        this.caractere = '_';
    }

    public Case(Position position, char _caractere) {
        this.position = position;
        this.caractere = _caractere;
    }
}
