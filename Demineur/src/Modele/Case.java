/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import javafx.scene.layout.StackPane;

/**
 *
 * @author p1212374
 */
public class Case 
{
    private boolean etat; // True-Case dévoilée False-Case non dévoilée
    private int valeurBouton; // 0-Rien 1-Drapeau 2-Point d'intérrogation 3-Bombe car mauvais Point d'intérrogation 
    private int valeur; // -1 si c'est une mine
    private int x, y;
    
    public Case(int x, int y, int val)
    {
        etat = false;
        valeurBouton = 0;
        valeur = val;
        this.x = x;
        this.y = y;
    }
    
    public void reinitialiser(){
        etat = false;
        valeurBouton = 0;
        valeur = 0;
    }


    /**
     * @return the etat
     */
    public boolean getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    /**
     * @return the valeur
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * @param valeur the valeur to set
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * @return the x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @param x to set
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * @return the y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param y the c to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the valeur_bouton
     */
    public int getValeur_bouton() {
        return valeurBouton;
    }

    /**
     * @param valeur_bouton the valeur_bouton to set
     */
    public void setValeur_bouton(int valeur_bouton) {
        this.valeurBouton = valeur_bouton;
    }
    
    
}
