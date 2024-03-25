/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
}
