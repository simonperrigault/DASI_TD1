package vue;

import dao.JpaUtil;
import java.util.List;
import metier.modele.Eleve;
import metier.service.Service;

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
        
        Eleve eleve = new Eleve("Joussot", "Gabin", "31/03/2004", 0, "simon.perrigault@insa-lyon.fr", "Vulcania");
        Eleve eleve2 = new Eleve("Joussot", "Gabin", "31/03/2004", 0, "simon.errigault@insa-lyon.fr", "Vulcania");
        
        try {
            eleve = service.inscrireEleve(eleve, "0691664J");
            eleve2 = service.inscrireEleve(eleve2, "0691664J");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        

        JpaUtil.fermerFabriquePersistance();

    }
}
