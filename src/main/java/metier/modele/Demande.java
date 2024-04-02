/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sperrigaul
 */
@Entity
public class Demande {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Eleve eleve;
    
    @ManyToOne
    private Intervenant intervenant;

    @Temporal(TemporalType.DATE)
    private Date DateDebut;
    
    @Temporal(TemporalType.DATE)
    private Date DateFin;
    
    @ManyToOne
    private Matiere matiere;
    
    
    private int note;
    private String detail;
    private String bilan;

    public Demande() {
    }

    public Demande(Eleve eleve, Matiere matiere, String detail) {
        this.eleve = eleve;
        this.matiere = matiere;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Demande{" + "eleve=" + eleve + ", intervenant=" + intervenant + ", DateDebut=" + DateDebut + ", DateFin=" + DateFin + ", matiere=" + matiere + ", note=" + note + ", detail=" + detail + ", bilan=" + bilan + '}';
    }
    
    

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date DateDebut) {
        this.DateDebut = DateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date DateFin) {
        this.DateFin = DateFin;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }
    
    
    
             
    
}
