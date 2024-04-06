/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;

/**
 *
 * @author sperrigaul
 */
public class IntervenantDao {

    public void create(Intervenant interv) {
        JpaUtil.obtenirContextePersistance().persist(interv);
    }
    
    public void update(Intervenant interv) {
        JpaUtil.obtenirContextePersistance().merge(interv);
    }

    public Intervenant rechercheParMail(String mail) {
        String jpql = "select a from Intervenant a where a.mail = :unMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        query.setParameter("unMail", mail);
        List<Intervenant> resultat;
        resultat = query.getResultList();
        Intervenant resultat_unique;
        if (resultat.isEmpty()) {
            resultat_unique = null;
        } else {
            resultat_unique = resultat.get(0);
        }
        return resultat_unique;
    }
    
    public List<Intervenant> getAllIntervenants() {
        String jpql = "select a from Intervenant a";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        return query.getResultList();
    }
    
    public List<Intervenant> getIntervenantsDispo(int niveau) {
        String jpql = "select i from Intervenant i where i.demandeEnCours = null and i.niveauMax <= :classe and i.niveauMin >= :classe order by size(i.demandes)";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        query.setParameter("classe", niveau);
        return query.getResultList();
    }
    
    public HashMap<Matiere, Double> getMinutesParMatiere(Long id) {
        String jpql = "select d from Demande d where d.intervenant.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("id", id);
        List<Demande> demandes = query.getResultList();
        
        HashMap<Matiere, Double> res = new HashMap();
        
        for (Demande d : demandes) {
            res.put(d.getMatiere(), res.getOrDefault(d.getMatiere(), 0.0) + (d.getDateFin().getTime()-d.getDateDebut().getTime())/1000/60);
        }
        
        return res;
    }
    
    public HashMap<Integer, Double> getMinutesParClasse(Long id) {
        String jpql = "select d from Demande d where d.intervenant.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("id", id);
        List<Demande> demandes = query.getResultList();
        
        HashMap<Integer, Double> res = new HashMap();
        
        for (Demande d : demandes) {
            res.put(d.getEleve().getClasse(), res.getOrDefault(d.getEleve().getClasse(), 0.0) + (d.getDateFin().getTime()-d.getDateDebut().getTime())/1000/60);
        }
        
        return res;
    }

}
