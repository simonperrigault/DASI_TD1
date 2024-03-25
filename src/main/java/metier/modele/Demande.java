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
    
    private String etat;

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

    public Demande(Eleve eleve, Intervenant intervenant, Matiere matiere, String detail) {
        this.eleve = eleve;
        this.intervenant = intervenant;
        this.matiere = matiere;
        this.detail = detail;
    }
    
    
    
             
    
}
