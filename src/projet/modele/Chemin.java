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
public class Chemin {

    public ArrayList<Case> casesIntermediaires;

    public Chemin(Case caseDepart)
    {
        this.casesIntermediaires = new ArrayList();
        casesIntermediaires.add(caseDepart);
    }

    public Case caseFinale() { return casesIntermediaires.get(casesIntermediaires.size() - 1); }

    public void ajoutCaseChemin(Case caseActuelle) {
        casesIntermediaires.add(caseActuelle);
    }

    public Ligne lignePourCase(int chId)
    {
        Ligne ligne = Ligne.BAS_DROITE;

        Case caseActuelle = casesIntermediaires.get(chId);

        Case prevCase = casesIntermediaires.get(chId - 1);

        Case nextCase = chId < casesIntermediaires.size() - 1 ? casesIntermediaires.get(chId + 1) : null;

        Position currCasePos = caseActuelle.position;
        Position prevCasePos = prevCase.position;
        Position nextCasePos = nextCase != null ? nextCase.position : null;


        boolean isLastCell = nextCase == null;

        if (prevCasePos.x == currCasePos.x) {
            if (prevCasePos.y == currCasePos.y - 1 && !isLastCell && (nextCasePos.x == currCasePos.x - 1 && nextCasePos.y == currCasePos.y)) {
                return Ligne.HAUT_GAUCHE;
            }

            if (prevCasePos.y == currCasePos.y - 1 && !isLastCell && (nextCasePos.x == currCasePos.x + 1 && nextCasePos.y == currCasePos.y)) {
                return Ligne.HAUT_DROITE;
            }

            if (prevCasePos.y == currCasePos.y + 1 && !isLastCell && (nextCasePos.x == currCasePos.x + 1 && nextCasePos.y == currCasePos.y)) {
                return Ligne.BAS_DROITE;
            }

            if (prevCasePos.y == currCasePos.y + 1 && !isLastCell && (nextCasePos.x == currCasePos.x - 1 && nextCasePos.y == currCasePos.y)) {
                return Ligne.BAS_GAUCHE;
            }

            return Ligne.VERTICALE;
        }

        if (prevCasePos.y == currCasePos.y) {
            if (prevCasePos.x == currCasePos.x - 1 && !isLastCell && (nextCasePos.x == currCasePos.x && nextCasePos.y == currCasePos.y + 1)) {
                return Ligne.BAS_GAUCHE;
            }

            if (prevCasePos.x == currCasePos.x + 1 && !isLastCell && (nextCasePos.x == currCasePos.x && nextCasePos.y == currCasePos.y + 1)) {
                return Ligne.BAS_DROITE;
            }

            if (prevCasePos.x == currCasePos.x + 1 && !isLastCell && (nextCasePos.x == currCasePos.x && nextCasePos.y == currCasePos.y - 1)) {
                return Ligne.HAUT_DROITE;
            }

            if (prevCasePos.x == currCasePos.x - 1 && !isLastCell && (nextCasePos.x == currCasePos.x && nextCasePos.y == currCasePos.y - 1)) {
                return Ligne.HAUT_GAUCHE;
            }

            return Ligne.HORIZONTALE;
        }

        return ligne;
    }
}
