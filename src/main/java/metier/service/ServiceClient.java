/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDao;
import dao.JpaUtil;
import java.util.List;
import javax.persistence.TypedQuery;
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
        } else {
            client.setLatitude(gps.lat);
            client.setLongitude(gps.lng);
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                clientdao.create(client);
                JpaUtil.validerTransaction();
                res = true;
                Message.envoyerMail("simon.perrigault@insa-lyon.fr", client.getMail(), "Validation", "Votre compte client a été enregistré dans la base de données");
            } catch (Exception ex) {
                ex.printStackTrace();
                JpaUtil.annulerTransaction();

                Message.envoyerMail("simon.perrigault@insa-lyon.fr", client.getMail(), "Erreur", "Une erreur est survenue lors de l'enregistrement dans la base de données");

                res = false;
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }

        return res;
    }

    public Client authentifierClient(String mail, String motDePasse) {
        ClientDao clientdao = new ClientDao();
        Client client;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientdao.rechercheParMail(mail);
            JpaUtil.validerTransaction();
            if (client != null && !client.getMotDePasse().equals(motDePasse)) {
                client = null;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;

        //return 
    }
    
    public Client findClient(Long id) {
        ClientDao clientdao = new ClientDao();
        Client client;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientdao.rechercheParID(id);
            JpaUtil.validerTransaction();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;

        //return 
    }
    
    public List<Client> consulterListeClients()
    {
       
        
        ClientDao clientdao = new ClientDao();
        List<Client> clients;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clients = clientdao.findAllClients();
            JpaUtil.validerTransaction();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            clients = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return clients; 
    }
           
}
