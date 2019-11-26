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

    public CaseLigne caseLigne;
    public ArrayList<Case> casesIntermediaires;
    public Chemin(Case caseDepart)
    {
        this.casesIntermediaires = new ArrayList();
        casesIntermediaires.add(caseDepart);
    }

    public void ajoutCaseChemin(Case caseActuelle){
        casesIntermediaires.add(caseActuelle);
    }

    public CaseLigne verifierChemin()
    {
        //casesIntermediaires.get(0);
        for(int i = 1; i < casesIntermediaires.size(); i++)
        {
            Case caseActuelle = casesIntermediaires.get(i);

            if((casesIntermediaires.get(i-1).position.x == caseActuelle.position.x-1) &&
                    (casesIntermediaires.get(i-1).position.y == caseActuelle.position.y))
            {
                caseActuelle.caractere = 'L';
                caseLigne.ligne = Ligne.HORIZONTALE;

                if((casesIntermediaires.get(i+1).position.x == caseActuelle.position.x) &&
                        (casesIntermediaires.get(i+1).position.y == caseActuelle.position.y+1))
                {
                    caseActuelle.caractere = 'L';
                    caseLigne.ligne = Ligne.BAS_GAUCHE;
                }
            }

        }
        return caseLigne;
    }
}
