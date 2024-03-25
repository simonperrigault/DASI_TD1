package vue;

import dao.JpaUtil;
import java.util.Date;
import java.util.List;
import metier.modele.Eleve;
import metier.service.Service;
import metier.service.UncalledService;

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
        Service service = new Service();

        
        Eleve eleve = new Eleve("Joussot", "Gabin", new Date(2004,03,31), 0, "simon.perrigault@insa-lyon.fr", "Vulcania");
        Eleve eleve2 = new Eleve("Joussot", "Gabin", new Date(2004,03,31), 0, "simon.errigault@insa-lyon.fr", "Vulcania");
        
        try {
            eleve = service.inscrireEleve(eleve, "0691664J");
            eleve2 = service.inscrireEleve(eleve2, "0691664J");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("auth e1 : "+service.authentifierEleve("simon.perrigault@insa-lyon.fr", "Vulcania"));
        System.out.println("auth e2 : "+service.authentifierEleve("simon.perrigault@insa-lyor", "Vulcania"));
        System.out.println("auth e3 : "+service.authentifierEleve("simon.perrigault@insa-lyon.fr", "Vulcan"));
        
        
        //Ajout des intervenants
        UncalledService uncalledService = new UncalledService();
        uncalledService.InsertIntervenants();
        
        System.out.println("auth i1 : "+service.authentifierIntervenant("cam.martin@sorbonne.fr", "azerty123"));
        System.out.println("auth i2 : "+service.authentifierIntervenant("cam.martin@sorbonne.fr", "azerty1235"));
        System.out.println("auth i3 : "+service.authentifierIntervenant("cam.martin@rbonne.fr", "azerty123"));
        
        //Ajout des matières dans la base
        uncalledService.InsertMatiere();
        
        service.
        

        JpaUtil.fermerFabriquePersistance();

    }
}
