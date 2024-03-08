package vue;


import dao.JpaUtil;
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
    public static void main(String[] args){
        JpaUtil.creerFabriquePersistance();
        ServiceClient servclient = new ServiceClient();
        
        Client c1 = new Client("Hugo", "Victor", "vhugo@paris.fr", "Paris");
        c1.setMotDePasse("toto");
        System.out.println(c1.toString());
        
        Boolean res = servclient.inscrireClient(c1);
        
        Client c2 = new Client("Boloss", "Victor", "vhugo@paris.fr", "Paris");
        res = servclient.inscrireClient(c2);
        
        JpaUtil.fermerFabriquePersistance();
        
    }
}
