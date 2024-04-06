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
import metier.modele.Etablissement;

/**
 *
 * @author sperrigaul
 */
public class EleveDao {

    public void create(Eleve eleve) {
        JpaUtil.obtenirContextePersistance().persist(eleve);
    }
    
    public void update(Eleve eleve) {
        JpaUtil.obtenirContextePersistance().merge(eleve);
    }

    public Eleve rechercheParID(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, id);
    }
    
    public Eleve rechercheParMail(String mail) {
        String jpql = "select a from Eleve a where a.mail = :unMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Eleve.class);
        query.setParameter("unMail", mail);
        List<Eleve> resultat;
        resultat = query.getResultList();
        Eleve resultat_unique;
        if (resultat.isEmpty()) {
            resultat_unique = null;
        } else {
            resultat_unique = resultat.get(0);
        }
        return resultat_unique;
    }
    
    public List<Eleve> getAllElevesFromEtablissement(String code) {
        String jpql = "select e from Eleve e where e.etablissement.code = :code order by e.nom, e.prenom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("code", code);
        return query.getResultList();

    }

}
