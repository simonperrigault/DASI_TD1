/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.IntervenantDao;
import dao.JpaUtil;
import dao.MatiereDao;
import java.util.List;
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
public class UncalledService {

    public void InsertIntervenants() {
        IntervenantDao intervDao = new IntervenantDao();

        Intervenant interv = new Intervenant("Martin", "Camille", 6, 3, "0655447788", "cam.martin@sorbonne.fr", "azerty123");

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            intervDao.create(interv);
            interv = new Intervenant("Zola", "Anna", 6, 0, "0633221144", "anna.zola@sup.fr", "azerty12345");
            intervDao.create(interv);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }
    
    public void InsertMatiere()
    {
        MatiereDao matiereDao = new MatiereDao();

        

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
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
            
}
