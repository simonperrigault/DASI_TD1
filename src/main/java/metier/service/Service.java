/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.DemandeDao;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import java.io.IOException;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Intervenant;
import util.EducNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author sperrigaul
 */
public class Service {

    public Eleve inscrireEleve(Eleve eleve, String code_etablissement) throws IOException, Exception {
        EleveDao elevedao = new EleveDao();
        EtablissementDao etablidao = new EtablissementDao();


        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            Etablissement etablissement = etablidao.rechercheParCode(code_etablissement);
            if (etablissement == null) {
                EducNetApi api = new EducNetApi();

                List<String> result = api.getInformationCollege(code_etablissement);
                if (result == null)
                {
                 result = api.getInformationLycee(code_etablissement);   
                }

                if (result != null) {
                    String uai = result.get(0);
                    String nom_etabli = result.get(1);
                    String secteur = result.get(2);
                    String codeCommune = result.get(3);
                    String nomCommune = result.get(4);
                    String codeDepartement = result.get(5);
                    String nomDepartement = result.get(6);
                    String academie = result.get(7);
                    float ips = Float.parseFloat(result.get(8));
                    etablissement = new Etablissement(code_etablissement, nom_etabli, secteur, codeCommune, nomCommune, codeDepartement, nomDepartement, academie, ips);
                    
                    String adresseEtablissement = nom_etabli + nomCommune;
                    LatLng coordsEtablissement = GeoNetApi.getLatLng(adresseEtablissement);
                    etablissement.setLat(coordsEtablissement.lat);
                    etablissement.setLon(coordsEtablissement.lng);

                    etablidao.create(etablissement);

                } 
                
                else {
                    throw new Exception("Code établissement inconnu");
                }
            }
            eleve.setEtablissement(etablissement);
            elevedao.create(eleve);
            JpaUtil.validerTransaction();
            Message.envoyerMail("contact@instruct.if", eleve.getMail(), "Bienvenue sur le réseau INSTRUCT'IF", "Bonjour "+eleve.getPrenom()+", nous te confirmons ton inscription sur le réseau INSTRUCT' IF. Si tu as besoin d'un soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
        }
        catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("contact@instruct.if", eleve.getMail(), "Echec de l'inscription sur le réseau INSTRUCT'IF", "Bonjour "+eleve.getPrenom()+", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
            eleve = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
    
    public Eleve authentifierEleve(String mail, String motDePasse) {
        EleveDao elevedao = new EleveDao();
        Eleve eleve;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            eleve = elevedao.rechercheParMail(mail);
            JpaUtil.validerTransaction();
            if (eleve != null && !eleve.getMotDePasse().equals(motDePasse)) {
                eleve = null;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            eleve = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
    
    public Intervenant authentifierIntervenant(String mail, String motDePasse) {
        IntervenantDao intervenantDao = new IntervenantDao();
        Intervenant inter;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            inter = intervenantDao.rechercheParMail(mail);
            JpaUtil.validerTransaction();
            if (inter != null && !inter.getMotDePasse().equals(motDePasse)) {
                inter = null;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            inter = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return inter;
    }
    
    public Demande CreerDemande(Demande demande)
    {
        DemandeDao demandeDao = new DemandeDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            demandeDao.create(demande);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return demande;
    }

}
