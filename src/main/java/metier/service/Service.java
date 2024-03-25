/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.JpaUtil;
import java.io.IOException;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Eleve;
import metier.modele.Etablissement;
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
                // List<String> result = api.getInformationLycee("0690132U");
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

                } else {
                    throw new Exception("Code établissement inconnu");
                }
            }
            eleve.setEtablissement(etablissement);
            elevedao.create(eleve);
            JpaUtil.validerTransaction();
            // MAIL ---------------------------------
        }
        catch (Exception ex) {
            JpaUtil.annulerTransaction();
            // MAIL ---------------------------------
            eleve = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
    
    

}
