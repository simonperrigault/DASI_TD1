/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import metier.modele.Demande;

/**
 *
 * @author sperrigaul
 */
public class DemandeDao {
    public void create(Demande demande) {
        JpaUtil.obtenirContextePersistance().persist(demande);
    }
    
    public Demande date(Demande demande) {
        return JpaUtil.obtenirContextePersistance().merge(demande);
    }
    
    
}
