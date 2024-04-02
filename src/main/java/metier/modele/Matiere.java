/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author sperrigaul
 */
@Entity 
public class Matiere {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
        
    @Column(unique = true)
    private String nom;

    public Matiere() {
    }

    public Matiere(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Matiere{" + "nom=" + nom + '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
        
}
