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
import metier.modele.Matiere;

/**
 *
 * @author sperrigaul
 */
public class MatiereDao {
    public List<Matiere> ListMatieres() {
        String jpql = "select a from Matiere a";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Matiere.class);
        return query.getResultList();

    }
}
