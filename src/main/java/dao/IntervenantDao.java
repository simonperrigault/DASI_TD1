/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Intervenant;

/**
 *
 * @author sperrigaul
 */
public class IntervenantDao {
    public void create(Intervenant interv) {
        JpaUtil.obtenirContextePersistance().persist(interv);
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
    
}
