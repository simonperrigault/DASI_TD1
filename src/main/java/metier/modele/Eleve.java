package metier.modele;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sperrigaul
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Eleve implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private int classe;
    
    @Column(unique = true)
    private String mail;
    
    private String motDePasse;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    @ManyToOne
    private Etablissement etablissement;
    
    @OneToMany(mappedBy="eleve")
    private List<Demande> demandes;

    protected Eleve() {
    }

    public Eleve(String nom, String prenom, Date datenaissance, int classe, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.dateNaissance = datenaissance;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }    

    @Override
    public String toString() {
        return "Eleve{" + "nom=" + nom + ", prenom=" + prenom + ", classe=" + classe + ", mail=" + mail + ", dateNaissance=" + dateNaissance + ", etablissement=" + etablissement + '}';
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }
    
    
}
