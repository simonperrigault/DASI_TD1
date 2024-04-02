/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Matiere;

/**
 *
 * @author sperrigaul
 */
public class DemandeDao {
    public void create(Demande demande) {
        JpaUtil.obtenirContextePersistance().persist(demande);
    }
    
    public void update(Demande demande) {
        JpaUtil.obtenirContextePersistance().merge(demande);
    }
    
    
}
