/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
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
    
    public Intervenant update(Intervenant interv) {
        return JpaUtil.obtenirContextePersistance().merge(interv);
    }

    public Intervenant rechercheParMail(String mail) {
        String jpql = "select a from Intervenant a where a.mail = :unMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        query.setParameter("unMail", mail);
        List<Intervenant> resultat;
        resultat = query.getResultList();

        // on ne veut en retourner qu'un seul mais on a une liste
        Intervenant resultat_unique;
        if (resultat.isEmpty()) { // on regarde si la liste est vide = pas de résultat
            resultat_unique = null;
        } else { // si elle n'est pas vide on prend le premier résultat
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
        // un intervenant est disponible s'il n'a pas de demande en cours
        // on regarde aussi si le niveau de l'intervenant est compatible avec le niveau de l'élève
        // et on les trie par nombre de demandes croissantes (= ceux qui en ont le moins en premier)
        String jpql = "select i from Intervenant i where i.demandeEnCours = null and i.niveauMax <= :classe and i.niveauMin >= :classe order by size(i.demandes)";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        query.setParameter("classe", niveau);
        return query.getResultList();
    }
    
    public HashMap<Matiere, Double> getMinutesParMatiere(Intervenant inter) {
        String jpql = "select d from Demande d where d.intervenant = :inter";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("inter", inter);
        List<Demande> demandes = query.getResultList();
        
        HashMap<Matiere, Double> res = new HashMap();
        
        for (Demande d : demandes) {
            // pour chaque demande, on ajoute son temps en minutes (on divise les millisecondes par 1000 et 60 pour avoir les minutes)
            // si la matière n'est pas dans la map, on utilise la valeur par défaut 0.0 pour initialiser le temps
            res.put(d.getMatiere(), res.getOrDefault(d.getMatiere(), 0.0) + (d.getDateFin().getTime()-d.getDateDebut().getTime())/1000/60);
        }
        
        return res;
    }
    
    public HashMap<Integer, Double> getMinutesParClasse(Intervenant inter) {
        String jpql = "select d from Demande d where d.intervenant = :inter";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("inter", inter);
        List<Demande> demandes = query.getResultList();
        
        HashMap<Integer, Double> res = new HashMap();
        
        for (Demande d : demandes) {
            // pour chaque demande, on ajoute son temps en minutes (on divise les millisecondes par 1000 et 60 pour avoir les minutes)
            // si la classe n'est pas dans la map, on utilise la valeur par défaut 0.0 pour initialiser le temps
            res.put(d.getEleve().getClasse(), res.getOrDefault(d.getEleve().getClasse(), 0.0) + (d.getDateFin().getTime()-d.getDateDebut().getTime())/1000/60);
        }
        
        return res;
    }

}
