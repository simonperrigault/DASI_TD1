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
import dao.MatiereDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Intervenant;
import metier.modele.Matiere;
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
    
    public Demande actualiserDemande(Demande demande)
    {
        DemandeDao demandeDao = new DemandeDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            demandeDao.update(demande);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return demande;
    }
    
    public Intervenant actualiserIntervenant(Intervenant interv)
    {
        IntervenantDao dao = new IntervenantDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.update(interv);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return interv;
    }
    
    public List<Matiere> getAllMatieresAlphabetique() {
        MatiereDao dao = new MatiereDao();
        List<Matiere> res = new ArrayList();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            res = dao.getListMatieres();
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return res;
    }
    
    public Demande selectionnerIntervenantDemande(Eleve eleve, Matiere matiere, String detail) {
        IntervenantDao intervDao = new IntervenantDao();
        EleveDao eleveDao = new EleveDao();
        DemandeDao demandeDao = new DemandeDao();
        
        Demande res = new Demande(eleve, matiere, detail);
        res.setDateDebut(new Date());

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            demandeDao.create(res);
            
            Intervenant intervenant = null;
            eleve.addDemande(res);
            eleveDao.update(eleve);
            
            List<Intervenant> allIntervenants = intervDao.getIntervenantsDispo(eleve.getClasse());
            if (allIntervenants.isEmpty()) {
                res = null;
            }
            else {
                intervenant = allIntervenants.get(0);
                intervenant.addDemande(res);
                intervenant.setDemandeEnCours(res);
                intervDao.update(intervenant);
                
                res.setIntervenant(intervenant);
                demandeDao.update(res);
                
                String classeEleve = Integer.toString(eleve.getClasse()) + "ème";
                if (eleve.getClasse() == 0) {
                    classeEleve = "Terminale";
                }
                else if (eleve.getClasse() == 1) {
                    classeEleve = "1ère";
                }
                else if (eleve.getClasse() == 2) {
                    classeEleve = "2nde";
                }
                Message.envoyerNotification(intervenant.getTel(), "Bonjour " + intervenant.getPrenom() + ". Merci de prendre en charge la demande de soutien en \""
                        + res.getMatiere().getNom() + "\" demandée à "+res.getDateDebut().getHours()+"h"+res.getDateDebut().getMinutes()+" par "+eleve.getPrenom()+" en classe de "+ classeEleve);
            }
            
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return res;
    }
    
    public int getDureeMoyenneSoutiens(Intervenant intervenant) {
        int res = 0;
        
        for (Demande dem : intervenant.getDemandes()) {
            res += 60*(dem.getDateFin().getHours() - dem.getDateDebut().getHours()) + dem.getDateFin().getMinutes()-dem.getDateFin().getMinutes();
        }
        
        res /= intervenant.getDemandes().size();
        
        return res;
    }
    
    public List<Demande> getHistoriqueDemandesIntervenant(Intervenant inter) {
        List<Demande> res = inter.getDemandes();
        
        res.sort((o1, o2) -> o1.getDateFin().compareTo(o2.getDateFin()));
        
        return res;
    }
    
    public List<Demande> getHistoriqueDemandesEleve(Eleve eleve) {
        List<Demande> res = eleve.getDemandes();
        
        res.sort((o1, o2) -> o1.getDateFin().compareTo(o2.getDateFin()));
        
        return res;
    }
    

}
