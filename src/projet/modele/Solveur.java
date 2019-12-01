package projet.modele;

import java.util.ArrayList;

public class Solveur {
    private ArrayList<ArrayList<Integer>> cheminsValides;
    private ArrayList<Integer>[] noeuxAdjacents;

    private Grille grille;
    private int nbNoeux;
    private int largeur;

    public Solveur(Grille _grille) {
        this.grille = _grille;
        this.nbNoeux = grille.dimX * grille.dimY;
        this.largeur = grille.dimX;

        this.cheminsValides = new ArrayList<>();

        noeuxAdjacents = new ArrayList[nbNoeux];

        for (int i = 0; i < nbNoeux; i++) {
            noeuxAdjacents[i] = new ArrayList<>();
        }

        construireLiensPourGrille();
    }

    public void ajouterLien(int debut, int fin) {
        noeuxAdjacents[debut].add(fin);
    }

    private void construireLiensPourGrille() {
        for (int i = 0; i < nbNoeux; i++) {
            if (i % largeur < largeur - 1)
                ajouterLien(i, i + 1);
            if (i % largeur > 0)
                ajouterLien(i, i - 1);

            if (i / largeur >= 1)
                ajouterLien(i , i - largeur);
            if (i / largeur < (largeur - 1))
                ajouterLien(i, i + largeur);
        }
    }

    private boolean cheminsSeCroisent(ArrayList<ArrayList<Integer>> chemins) {
        for (int i = 0; i < chemins.size() - 1; i++) {
            for (int j = i + 1; j < chemins.size(); j++) {
                for (Integer noeu1 : chemins.get(i)) {
                    if (chemins.get(j).contains(noeu1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean cheminsParcourentTousNoeux(ArrayList<ArrayList<Integer>> chemins) {
        ArrayList<Integer> noeuxParcourus = new ArrayList<>();

        for (ArrayList<Integer> chemin : chemins) {
            noeuxParcourus.addAll(chemin);
        }

        for (int noeux = 0; noeux < nbNoeux; noeux++) {
            if (!noeuxParcourus.contains(noeux)) {
                return false;
            }
        }

        return true;
    }

    private void chercherCheminsProfondeur(Integer debut, Integer fin,
                                           boolean[] noeuxVisites,
                                           ArrayList<Integer> chemin) {

        noeuxVisites[debut] = true;

        if (debut.equals(fin))
        {
            noeuxVisites[debut] = false;

            this.cheminsValides.add(new ArrayList<>(chemin));
            return ;
        }

        for (Integer i : noeuxAdjacents[debut])
        {
            if (!noeuxVisites[i])
            {
                chemin.add(i);
                chercherCheminsProfondeur(i, fin, noeuxVisites, chemin);

                chemin.remove(i);
            }
        }

        // Mark the current node
        noeuxVisites[debut] = false;
    }


    public ArrayList<ArrayList<Integer>> trouverCheminsPossibles(int debut, int fin, ArrayList<Integer> noeuxIgnores) {
        boolean[] estVisite = new boolean[nbNoeux];
        for (Integer noeu : noeuxIgnores) {
            estVisite[noeu] = true;
        }

        ArrayList<Integer> chemin = new ArrayList<>();
        chemin.add(debut);

        this.cheminsValides.clear();
        chercherCheminsProfondeur(debut, fin, estVisite, chemin);



        return new ArrayList<>(this.cheminsValides);
    }

    private <Integer> ArrayList<ArrayList<ArrayList<Integer>>> calculate(ArrayList<ArrayList<ArrayList<Integer>>> input) {
        ArrayList<ArrayList<ArrayList<Integer>>> result= new ArrayList<>();

        if (input.isEmpty()) {  // If input a empty list
            result.add(new ArrayList<>());// then add empty list and return
            return result;
        } else {
            ArrayList<ArrayList<Integer>> head = input.get(0);//get the first list as a head
            ArrayList<ArrayList<ArrayList<Integer>>> tail = calculate(new ArrayList<ArrayList<ArrayList<Integer>>> (input.subList(1, input.size())));//recursion to calculate a tail list
            for (ArrayList<Integer> h : head) {//we merge every head element with every tail list.
                for (ArrayList<ArrayList<Integer>> t : tail) {
                    ArrayList<ArrayList<Integer>> resultElement = new ArrayList<>();
                    resultElement.add(h);
                    resultElement.addAll(t);
                    result.add(resultElement);
                }
            }
        }
        return result;
    }

    public int positionVersNoeu(Position p) {
        return p.y * largeur + p.x;
    }

    public Position noeuVersPosition(int noeu) {
        return new Position(
                noeu % largeur,
                noeu / largeur
        );
    }

    public boolean trouverSolution() {
        ArrayList<ArrayList<ArrayList<Integer>>> tousChemins = new ArrayList<>();
        ArrayList<Integer> ignores = new ArrayList<>();
        for (int i = 0; i < grille.pairesSymboles.size(); i++) {
            for (int j = 0; j < grille.pairesSymboles.size(); j++) {
                if (i != j) {
                    ignores.add(positionVersNoeu(grille.pairesSymboles.get(j).getKey().position));
                    ignores.add(positionVersNoeu(grille.pairesSymboles.get(j).getValue().position));
                }
            }

            int debut = positionVersNoeu(grille.pairesSymboles.get(i).getKey().position);
            int fin =  positionVersNoeu(grille.pairesSymboles.get(i).getValue().position);
            tousChemins.add(trouverCheminsPossibles(debut, fin, ignores));

            ignores.clear();
        }

        ArrayList<ArrayList<ArrayList<Integer>>> combinaisons = calculate(tousChemins);
        for (ArrayList<ArrayList<Integer>> combinaison : combinaisons) {
            if (!cheminsSeCroisent(combinaison) && cheminsParcourentTousNoeux(combinaison)) {
                grille.clearChemins();
                for (ArrayList<Integer> chemin : combinaison) {
                    Position casePos = noeuVersPosition(chemin.get(0));
                    grille.nouveauChemin(casePos.x, casePos.y);

                    for (int i = 1; i < chemin.size() - 1; i++) {
                        casePos = noeuVersPosition(chemin.get(i));
                        grille.creerLignePourCase(casePos.x, casePos.y);
                    }

                    casePos = noeuVersPosition(chemin.get(chemin.size() - 1));
                    grille.terminerChemin(casePos.x, casePos.y);
                }

                return true;
            }
        }

        return false;
    }
}
