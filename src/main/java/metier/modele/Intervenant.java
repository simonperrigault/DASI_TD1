/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author sperrigaul
 */
@Entity
public class Intervenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private int niveauMin;
    private int niveauMax;
    private String tel;

    private String mail;

    @Column(unique = true)
    private String motDePasse;

    @OneToMany(mappedBy = "intervenant")
    private List<Demande> demandes;

    public Intervenant() {
    }

    public Intervenant(String nom, String prenom, int niveauMin, int niveauMax, String tel, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
        this.tel = tel;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Intervenant{" + "nom=" + nom + ", prenom=" + prenom + ", niveauMin=" + niveauMin + ", niveauMax=" + niveauMax + ", tel=" + tel + ", mail=" + mail + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNiveauMin(int niveauMin) {
        this.niveauMin = niveauMin;
    }

    public void setNiveauMax(int niveauMax) {
        this.niveauMax = niveauMax;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }



    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNiveauMin() {
        return niveauMin;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

}
