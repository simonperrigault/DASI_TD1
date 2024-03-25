package vue;

import dao.JpaUtil;
import java.util.List;
import metier.modele.Client;
import metier.service.ServiceClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sperrigaul
 */
public class Main {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        ServiceClient servclient = new ServiceClient();

//        Client c1 = new Client("Hugo", "Victor", "vhugo@paris.fr", "Paris");
//        c1.setMotDePasse("toto");
//        System.out.println(c1.toString());
//        Boolean res = servclient.inscrireClient(c1);
        Client c2 = new Client("Boloss", "Victor", "vhugo@paris.fr", "Paris");
        Boolean res = servclient.inscrireClient(c2);
        /*Client resAuth = servclient.authentifierClient("vhugo@paris.fr", "toto");
        if (resAuth != null) {
            System.out.println(resAuth.toString());
        }

        Client resAuth1 = servclient.authentifierClient("vhuo@paris.fr", "toto");
        if (resAuth1 != null) {
            System.out.println(resAuth1.toString());
        } else {
            System.out.println("Erreur d'auth");
        }

        Client resAuth2 = servclient.authentifierClient("vhugo@paris.fr", "tto");
        if (resAuth2 != null) {
            System.out.println(resAuth2.toString());
        } else {
            System.out.println("Erreur d'auth");
        }*/
        Long id1 = new Long(1);
        Client resID = servclient.findClient(id1);
        if (resID != null) {
            System.out.println(resID.toString());
        } else {
            System.out.println("Erreur d'auth");
        }

        resID = servclient.findClient(new Long(2));
        if (resID != null) {
            System.out.println(resID.toString());
        } else {
            System.out.println("Erreur d'auth");
        }
        
        
//        Client c3 = new Client("Simon", "Gabin", "SG@insa.fr", "Lyon");
//        
//        c3.setMotDePasse("BG");
//        Boolean res3 = servclient.inscrireClient(c3);

        Client c4 = new Client("Simon", "Gabin", "tollose@insa.fr", "TooLoose");      
        c4.setMotDePasse("BG");
        Boolean res4 = servclient.inscrireClient(c4);
        
        
        List<Client> clients = servclient.consulterListeClients();
        System.out.println("\n");
        for (Client c : clients)
        {
            System.out.println("Client #"+c.getId()+": "+c.getNom()+" "+c.getPrenom()+" ["+c.getLatitude()+", "+c.getLongitude()+"]");
        }
        System.out.println("\n");
        

        JpaUtil.fermerFabriquePersistance();

    }
}
