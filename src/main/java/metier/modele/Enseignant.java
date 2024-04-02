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
public class Enseignant extends Intervenant {
    private String type_etablissement;

    public Enseignant() {
    }

    public Enseignant(String type_etablissement, String nom, String prenom, int niveauMin, int niveauMax, String tel, String mail, String motDePasse) {
        super(nom, prenom, niveauMin, niveauMax, tel, mail, motDePasse);
        this.type_etablissement = type_etablissement;
    }

    @Override
    public String toString() {
        return "Enseignant{" + "nom=" + nom + ", prenom=" + prenom + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", tel=" + tel + ", mail=" + mail + "type_etablissement=" + type_etablissement + '}';
    }
    
    

    public String getType_etablissement() {
        return type_etablissement;
    }

    public void setType_etablissement(String type_etablissement) {
        this.type_etablissement = type_etablissement;
    }
    
    
}
