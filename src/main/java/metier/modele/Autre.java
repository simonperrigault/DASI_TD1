/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author sperrigaul
 */
@Entity
public class Autre extends Intervenant{
    private String activite;

    public Autre() {
    }

    public Autre(String activite, String nom, String prenom, Integer niveauMin, Integer niveauMax, String tel, String mail, String motDePasse) {
        super(nom, prenom, niveauMin, niveauMax, tel, mail, motDePasse);
        this.activite = activite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "Autre{" + "nom=" + nom + ", prenom=" + prenom + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", tel=" + tel + ", mail=" + mail + "activite=" + activite + '}';
    }
    
    
}
