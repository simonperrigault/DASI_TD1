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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.modele.Autre;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Enseignant;
import metier.modele.Etablissement;
import metier.modele.Etudiant;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import util.EducNetApi;
import util.GeoNetApi;
import util.Message;
import java.util.HashMap;

/**
 *
 * @author sperrigaul
 */
public class Service {
    public void InsertIntervenants() {
        IntervenantDao intervDao = new IntervenantDao();


        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            // on crée 3 intervenants de catégories différentes et les persiste
            Intervenant interv = new Etudiant("Sorbonne", "Langues Orientales","Martin", "Camille", 2, 0, "0655447788", "cam.martin@sorbonne.fr", "azerty123");
            intervDao.create(interv);
            interv = new Enseignant("Supérieur", "Zola", "Anna", 6, 3, "0633221144", "anna.zola@sup.fr", "azerty12345");
            intervDao.create(interv);
            interv = new Autre("Retraité", "Wirane", "Hamza", 6, 3, "0758693652", "hamza.wirane@sup.fr", "azerty12345");
            intervDao.create(interv);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }
    
    public void InsertMatieres()
    {
        MatiereDao matiereDao = new MatiereDao();

        

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // on crée 8 matières et les persiste
            Matiere matiere = new Matiere("Histoire-Géo");
            matiereDao.create(matiere);
            matiere = new Matiere("Maths");
            matiereDao.create(matiere);
            matiere = new Matiere("Français");
            matiereDao.create(matiere);
            matiere = new Matiere("Espagnol");
            matiereDao.create(matiere);
            matiere = new Matiere("Anglais");
            matiereDao.create(matiere);
            matiere = new Matiere("Chimie");
            matiereDao.create(matiere);
            matiere = new Matiere("Physique");
            matiereDao.create(matiere);
            matiere = new Matiere("SVT");
            matiereDao.create(matiere);
            
            
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    public Eleve inscrireEleve(Eleve eleve, String code_etablissement) {
        EleveDao elevedao = new EleveDao();
        EtablissementDao etablidao = new EtablissementDao();


        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            Etablissement etablissement = etablidao.rechercheParCode(code_etablissement);
            if (etablissement == null) { // l'établissement n'est pas présent dans notre base de données
                EducNetApi api = new EducNetApi(); // on lance l'api pour le trouver

                // on cherche d'abord dans les collèges
                List<String> result = api.getInformationCollege(code_etablissement);
                if (result == null) // la recherche dans les collèges n'a pas donné de résultat
                {
                    // on cherche dans les lycées
                    result = api.getInformationLycee(code_etablissement);   
                }

                if (result != null) { // on a trouvé l'établissement
                    String uai = result.get(0);
                    String nom_etabli = result.get(1);
                    String secteur = result.get(2);
                    String codeCommune = result.get(3);
                    String nomCommune = result.get(4);
                    String codeDepartement = result.get(5);
                    String nomDepartement = result.get(6);
                    String academie = result.get(7);
                    float ips = Float.parseFloat(result.get(8)); // ips est un string dans le json donc on le convertit en float
                    etablissement = new Etablissement(code_etablissement, nom_etabli, secteur, codeCommune, nomCommune, codeDepartement, nomDepartement, academie, ips);
                    
                    // on recherche l'adresse de l'établissement pour avoir ses coordonnées
                    String adresseEtablissement = nom_etabli + ", " + nomCommune;
                    LatLng coordsEtablissement = GeoNetApi.getLatLng(adresseEtablissement);
                    etablissement.setLat(coordsEtablissement.lat);
                    etablissement.setLon(coordsEtablissement.lng);

                    etablidao.create(etablissement); // et enfin on le persiste

                } 
                
                else { // on n'a pas trouvé l'établissement
                    throw new Exception("Code établissement inconnu");
                }
            }
            eleve.setEtablissement(etablissement); // après avoir trouvé l'établissement, on l'ajoute à l'élève
            elevedao.create(eleve); // on persiste l'élève
            JpaUtil.validerTransaction();
            Message.envoyerMail("contact@instruct.if", eleve.getMail(), "Bienvenue sur le réseau INSTRUCT'IF", "Bonjour "+eleve.getPrenom()+", nous te confirmons ton inscription sur le réseau INSTRUCT' IF. Si tu as besoin d'un soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
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

        JpaUtil.creerContextePersistance();
        eleve = elevedao.rechercheParMail(mail);
        // soit l'élève vaut null car son mail n'existe pas
        // soit le mot de passe est incorrect
        if (eleve != null && !eleve.getMotDePasse().equals(motDePasse)) {
            eleve = null;
        }
            
        JpaUtil.fermerContextePersistance();
        return eleve;
    }
    
    public Intervenant authentifierIntervenant(String mail, String motDePasse) {
        IntervenantDao intervenantDao = new IntervenantDao();
        Intervenant inter;

        JpaUtil.creerContextePersistance();
        inter = intervenantDao.rechercheParMail(mail);
        // soit l'intervenant vaut null car son mail n'existe pas
        // soit le mot de passe est incorrect
        if (inter != null && !inter.getMotDePasse().equals(motDePasse)) {
            inter = null;
        }
        
        JpaUtil.fermerContextePersistance();

        return inter;
    }
    
    public Demande finirVisioEleve(Demande demande, Integer note)
    {
        DemandeDao demandeDao = new DemandeDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            demande.setDateFin(new Date());
            demande.setNote(note);
            demandeDao.update(demande);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return demande;
    }
    
    public Demande finirVisioIntervenant(Demande demande, String bilan)
    {
        IntervenantDao intervDao = new IntervenantDao();
        DemandeDao demandeDao = new DemandeDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            demande.setDateFin(new Date());
            demande.setBilan(bilan);
            demande.getIntervenant().setDemandeEnCours(null);
            demandeDao.update(demande);
            intervDao.update(demande.getIntervenant());

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return demande;
    }
    
    public List<Matiere> getAllMatieresAlphabetique() {
        MatiereDao dao = new MatiereDao();
        List<Matiere> res = new ArrayList();

        JpaUtil.creerContextePersistance();
        res = dao.getListMatieres();
        JpaUtil.fermerContextePersistance();
            
        return res;
    }
    
    public List<Etablissement> getAllEtablissementsAlphabetique() {
        EtablissementDao dao = new EtablissementDao();
        List<Etablissement> res = new ArrayList();

        JpaUtil.creerContextePersistance();
        res = dao.getAllEtablissements();
        JpaUtil.fermerContextePersistance();
            
        return res;
    }
    
    public List<Eleve> getAllElevesFromEtablissement(Etablissement e) {
        EleveDao dao = new EleveDao();
        List<Eleve> res = new ArrayList();

        JpaUtil.creerContextePersistance();
        res = dao.getAllElevesFromEtablissement(e);
        JpaUtil.fermerContextePersistance();
            
        return res;
    }
    
    public HashMap<Matiere, Double> getMinutesParMatiere(Intervenant intervenant) {
        IntervenantDao dao = new IntervenantDao();
        HashMap<Matiere, Double> res = new HashMap();

        JpaUtil.creerContextePersistance();
        res = dao.getMinutesParMatiere(intervenant);
        JpaUtil.fermerContextePersistance();
            
        return res;
    }
    
    public HashMap<Integer, Double> getMinutesParClasse(Intervenant intervenant) {
        IntervenantDao dao = new IntervenantDao();
        HashMap<Integer, Double> res = new HashMap();

        JpaUtil.creerContextePersistance();
        res = dao.getMinutesParClasse(intervenant);
        JpaUtil.fermerContextePersistance();
            
        return res;
    }
    
    public Demande selectionnerIntervenantDemande(Eleve eleve, Matiere matiere, String detail) {
        IntervenantDao intervDao = new IntervenantDao();
        EleveDao eleveDao = new EleveDao();
        DemandeDao demandeDao = new DemandeDao();
        
        Demande res = new Demande(eleve, matiere, detail);
        res.setDateDebut(new Date()); // on met la date de début à la date système actuelle

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            demandeDao.create(res);
            
            Intervenant intervenant = null;
            // on ajoute la demande à l'historique de l'éleve et on le met à jour
            eleve.addDemande(res);
            eleveDao.update(eleve);
            
            // on regarde les intervenants disponibles pour la classe de l'élève
            List<Intervenant> allIntervenants = intervDao.getIntervenantsDispo(eleve.getClasse());
            if (allIntervenants.isEmpty()) { // si aucun n'est disponible, on renvoie null
                res = null;
            }
            else { // sinon, on prend le premier de la liste (ils sont déjà triés par ordre croissant de nombre de demandes)
                intervenant = allIntervenants.get(0);

                // on ajoute la demande à l'historique de l'intervenant, on le rend indisponible et on le met à jour
                intervenant.addDemande(res);
                intervenant.setDemandeEnCours(res);
                intervDao.update(intervenant);
                
                // on met à jour la demande avec l'intervenant
                res.setIntervenant(intervenant);
                demandeDao.update(res);
                
                // on envoie une notification à l'intervenant
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
    
    public Integer getDureeMoyenneSoutiens(Intervenant intervenant) {
        Integer res = 0;
        
        for (Demande dem : intervenant.getDemandes()) {
            if (dem.getDateFin() == null) {
                continue;
            }
            // on convertit les millisecondes en minutes
            res += (int) ((dem.getDateFin().getTime()-dem.getDateDebut().getTime())/1000/60);
        }
        
        res /= intervenant.getDemandes().size();
        
        return res;
    }
    
    public List<Demande> getHistoriqueDemandesIntervenant(Intervenant inter) {
        List<Demande> res = inter.getDemandes();
        
        // on trie les demandes par date de début (au cas où elles n'ont pas de date de fin)
        res.sort((o1, o2) -> o1.getDateDebut().compareTo(o2.getDateDebut()));
        
        return res;
    }
    
    public List<Demande> getHistoriqueDemandesEleve(Eleve eleve) {
        List<Demande> res = eleve.getDemandes();
        
        // on trie les demandes par date de début (au cas où elles n'ont pas de date de fin)
        res.sort((o1, o2) -> o1.getDateDebut().compareTo(o2.getDateDebut()));
        
        return res;
    }
    

}
