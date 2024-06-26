/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Eleve;
import metier.modele.Etablissement;

/**
 *
 * @author sperrigaul
 */
public class EtablissementDao {
    public void create(Etablissement etabli) {
        JpaUtil.obtenirContextePersistance().persist(etabli);
    }
   

    public Etablissement rechercheParCode(String code) {
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class, code);
    }
    
    public List<Etablissement> getAllEtablissements() {
        String jpql = "select e from Etablissement e order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        return query.getResultList();

    }
    
    public Long getAllElevesFromEtablissement(Etablissement etabli) {
        String jpql = "select count(e) from Eleve e where e.etablissement = :etabli";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Long.class);
        query.setParameter("etabli", etabli);
        return (Long) query.getResultList().get(0);

    }
    
    
}
