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
    private Float IPS;
    private Double lat;
    private Double lon;
    
    protected Etablissement() {
        
    }

    public Etablissement(String code, String nom, String secteur, String code_commune, String commune, String code_departement, String departement, String academie, Float IPS) {
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

    @Override
    public String toString() {
        return "Etablissement{" + "code=" + code + ", nom=" + nom + ", secteur=" + secteur + ", code_commune=" + code_commune + ", commune=" + commune + ", code_departement=" + code_departement + ", departement=" + departement + ", academie=" + academie + ", IPS=" + IPS + ", lat=" + lat + ", lon=" + lon + '}';
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

    public Float getIPS() {
        return IPS;
    }

    public void setIPS(Float IPS) {
        this.IPS = IPS;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
/*
    public void addListe_eleves(Eleve eleves) {
        this.liste_eleves.add(eleves);
    }
*/

   

    
    
    
}
