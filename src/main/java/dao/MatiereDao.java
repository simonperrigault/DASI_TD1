/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Matiere;

/**
 *
 * @author sperrigaul
 */
public class MatiereDao {
    public List<Matiere> getListMatieres() {
        String jpql = "select a from Matiere a order by a.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Matiere.class);
        return query.getResultList();

    }
    public void create(Matiere matiere) {
        JpaUtil.obtenirContextePersistance().persist(matiere);
    }
    
    public Matiere find(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Matiere.class, id);
    }
    
    
}
