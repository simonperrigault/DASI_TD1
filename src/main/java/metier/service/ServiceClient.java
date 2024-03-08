/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDao;
import dao.JpaUtil;
import metier.modele.Client;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author sperrigaul
 */
public class ServiceClient {
    public Boolean inscrireClient(Client client) {
        ClientDao clientdao = new ClientDao();
        Boolean res = null;
        LatLng gps = GeoNetApi.getLatLng(client.getAdressePostale());
        if (gps == null) {
            Message.envoyerMail("simon.perrigault@insa-lyon.fr", client.getMail(), "Mauvaise adresse postale", "Erreur : votre adresse postale n'est pas bonne");
            res = false;
        }
        else {
            client.setLatitude(gps.lat);
            client.setLongitude(gps.lng);
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                clientdao.create(client);
                JpaUtil.validerTransaction();
                res = true;
                Message.envoyerMail("simon.perrigault@insa-lyon.fr", client.getMail(), "Validation", "Votre compte client a été enregistré dans la base de données");
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JpaUtil.annulerTransaction();

                Message.envoyerMail("simon.perrigault@insa-lyon.fr", client.getMail(), "Erreur", "Une erreur est survenue lors de l'enregistrement dans la base de données");

                res = false;
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        
        return res;
    }
}
