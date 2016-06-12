/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;

public class Jeu extends Observable {

    private Grille g;
    private boolean partiePerdu;
    int lPersonalise, hPersonalise, nbMinePersonalise; // Si partie personalise
    

    public Jeu(int longueur, int hauteur, int nbMines) {
        this.partiePerdu = false;
        g = new Grille(longueur, hauteur, nbMines);
    }
    
    public Jeu jeuPersonalise(){
        return new Jeu(this.lPersonalise, this.hPersonalise, this.nbMinePersonalise);
    }
    
    public void restartJeu(){
        this.partiePerdu = false;
        g.reinitialiserGrille();
    }

    public boolean est_une_bombe(int x, int y) {
        if (g.getTabCases()[x][y].getValeur() == -1) {
            return true;
        } else {
            return false;
        }
    }

    public void MettreDrapeau(int x, int y) {
            g.getTabCases()[x][y].setValeur_bouton(1);
    }

    public void MettrePointInterrogation(int x, int y) {
        g.getTabCases()[x][y].setValeur_bouton(2);
    }

    public void MettreCaseVide(int x, int y) {
        g.getTabCases()[x][y].setValeur_bouton(0);
    }

    public void ClicCase(int x, int y) {
        if (g.getTabCases()[x][y].getValeur() == -1) {
            //Il a perdu donc on devoile tout
            g.devoiler();
            this.partiePerdu = true;
        } else {
            g.getTabCases()[x][y].setEtat(true);
            g.algoDevoilement(x, y);
        }

    }

    /**
     * @return the g
     */
    public Grille getG() {
        return g;
    }

    public boolean getPartiePerdu() {
        return this.partiePerdu;
    }

    public void setPartieGagne(boolean b) {
        this.partiePerdu = b;
    }
    
    

}
