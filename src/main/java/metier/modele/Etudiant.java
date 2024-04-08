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
public class Etudiant extends Intervenant {
    private String universite;
    private String specialite;

    public Etudiant() {
    }

    public Etudiant(String universite, String specialite, String nom, String prenom, Integer niveauMin, Integer niveauMax, String tel, String mail, String motDePasse) {
        super(nom, prenom, niveauMin, niveauMax, tel, mail, motDePasse);
        this.universite = universite;
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "nom=" + nom + ", prenom=" + prenom + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", tel=" + tel + ", mail=" + mail + "universite=" + universite + ", specialite=" + specialite + '}';
    }    
    

    public String getUniversite() {
        return universite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
}
