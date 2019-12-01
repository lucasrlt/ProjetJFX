package projet.modele;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Dictionary;

public class Niveau {
    public static int nbNiveaux = 2;

    public int dimension;
    public ArrayList<Pair<CaseSymbole, CaseSymbole>> pairesSymboles;

    private Niveau(int _dimension) {
        this.dimension = _dimension;
        this.pairesSymboles = new ArrayList<>();
    }

    private void ajouterPaireSymbole(Pair<CaseSymbole, CaseSymbole> paire) {
        this.pairesSymboles.add(paire);
    }

    static Niveau getNiveau(int niveau) {
        switch(niveau) {
            case 0:
                return getNiveau1();
            case 1:
                return getNiveau2();
            default:
                return getNiveau1();
        }
    }

    static Niveau getNiveau1() {
        Niveau niveau1 = new Niveau(3);

        niveau1.ajouterPaireSymbole(new Pair<>(
            new CaseSymbole(new Position(0, 0), Symbole.CARRE),
            new CaseSymbole(new Position(1, 2), Symbole.CARRE)
        ));
        niveau1.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(2, 0), Symbole.ROND),
                new CaseSymbole(new Position(2, 2), Symbole.ROND)
        ));

        return niveau1;
    }

    static Niveau getNiveau2() {
        Niveau niveau2 = new Niveau(4);

        niveau2.ajouterPaireSymbole(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(0, 0), Symbole.CARRE),
                new CaseSymbole(new Position(0, 3), Symbole.CARRE)
        ));
        niveau2.ajouterPaireSymbole(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(3, 0), Symbole.ROND),
                new CaseSymbole(new Position(3, 3), Symbole.ROND)
        ));

        return niveau2;

    }
}