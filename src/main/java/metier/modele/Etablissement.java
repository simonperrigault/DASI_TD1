/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.ArrayList;
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
public class Etablissement {
    @Id
    private String code;
    private String nom;
    private String secteur;
    private String code_commune;
    private String commune;
    private String code_departement;
    private String departement;
    private String academie;
    private float IPS;
    private double lat;
    private double lon;
    @OneToMany(mappedBy="etablissement")
    private List<Eleve> liste_eleves = new ArrayList();
    
    protected Etablissement() {
        
    }

    public Etablissement(String code, String nom, String secteur, String code_commune, String commune, String code_departement, String departement, String academie, float IPS) {
        this.code = code;
        this.nom = nom;
        this.secteur = secteur;
        this.code_commune = code_commune;
        this.commune = commune;
        this.code_departement = code_departement;
        this.departement = departement;
        this.academie = academie;
        this.IPS = IPS;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getCode_commune() {
        return code_commune;
    }

    public void setCode_commune(String code_commune) {
        this.code_commune = code_commune;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getCode_departement() {
        return code_departement;
    }

    public void setCode_departement(String code_departement) {
        this.code_departement = code_departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getAcademie() {
        return academie;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public float getIPS() {
        return IPS;
    }

    public void setIPS(float IPS) {
        this.IPS = IPS;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<Eleve> getListe_eleves() {
        return liste_eleves;
    }

    
    
    
}
