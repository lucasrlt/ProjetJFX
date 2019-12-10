package projet.modele;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Dictionary;

public class Niveau {
    public static int nbNiveaux = 6;

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
                return getNiveau1Bis();
            case 2:
                return getNiveau2();
            case 3:
                return getNiveau2Bis();
            case 4:
                return getNiveau3();
            case 5:
                return getNiveau3Bis();
            default:
                return getNiveau1();
        }
    }

    private static Niveau getNiveau1() {
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

    private static Niveau getNiveau2() {
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

    private static Niveau getNiveau3() {
        Niveau niveau3 = new Niveau(5);

        niveau3.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(0, 0), Symbole.CARRE),
                new CaseSymbole(new Position(0, 4), Symbole.CARRE)
        ));
        niveau3.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(4, 0), Symbole.ROND),
                new CaseSymbole(new Position(1, 4), Symbole.ROND)
        ));
        niveau3.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(1, 0), Symbole.TRIANGLE),
                new CaseSymbole(new Position(3, 3), Symbole.TRIANGLE)
        ));

        return niveau3;
    }

    private static Niveau getNiveau1Bis() {
        Niveau niveau1Bis = new Niveau(3);

        niveau1Bis.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(0, 0), Symbole.CARRE),
                new CaseSymbole(new Position(2, 0), Symbole.CARRE)
        ));

        return niveau1Bis;
    }

    private static Niveau getNiveau2Bis() {
        Niveau niveau2Bis = new Niveau(4);

        niveau2Bis.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(1, 2), Symbole.CARRE),
                new CaseSymbole(new Position(2, 1), Symbole.CARRE)
        ));

        niveau2Bis.ajouterPaireSymbole(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(1, 1), Symbole.ROND),
                new CaseSymbole(new Position(3, 3), Symbole.ROND)
        ));
        return niveau2Bis;
    }

    private static Niveau getNiveau3Bis() {
        Niveau niveau3Bis = new Niveau(5);

        niveau3Bis.ajouterPaireSymbole(new Pair<>(
                new CaseSymbole(new Position(1, 0), Symbole.CARRE),
                new CaseSymbole(new Position(4, 0), Symbole.CARRE)
        ));

        niveau3Bis.ajouterPaireSymbole(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(1, 3), Symbole.TRIANGLE),
                new CaseSymbole(new Position(4, 3), Symbole.TRIANGLE)
        ));
        niveau3Bis.ajouterPaireSymbole(new Pair<CaseSymbole, CaseSymbole>(
                new CaseSymbole(new Position(0, 0), Symbole.ROND),
                new CaseSymbole(new Position(4, 4), Symbole.ROND)
        ));
        return niveau3Bis;
    }
}
