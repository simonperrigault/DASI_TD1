/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Client;

/**
 *
 * @author sperrigaul
 */
public class ClientDao {

    public void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    public Client rechercheParMail(String mail) {
        String jpql = "select a from Client a where a.mail = :unMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Client.class);
        query.setParameter("unMail", mail);
        List<Client> resultat;
        resultat = query.getResultList();
        Client resultat_unique;
        if (resultat.isEmpty()) {
            resultat_unique = null;
        } else {
            resultat_unique = resultat.get(0);
        }
        return resultat_unique;
    }

    public Client rechercheParID(Long ID) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, ID);
    }

    public List<Client> findAllClients() {
        String jpql = "select a from Client a order by a.nom, a.prenom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Client.class);
        return query.getResultList();

    }
}
