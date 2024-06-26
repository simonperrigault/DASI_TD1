package vue;

import dao.JpaUtil;
import java.util.Date;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;
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
        JpaUtil.desactiverLog(); // pour ne pas avoir tous les logs de JpaUtil qui sont seulement utiles pour le debug
        JpaUtil.creerFabriquePersistance();
        Service service = new Service();
        
        //Ajout des intervenants
        System.out.println("\nAjout des intervenants");
        service.InsertIntervenants();

        //Ajout des matières dans la base
        System.out.println("\nAjout des matières");
        service.InsertMatieres();
        List<Matiere> matieres = service.getAllMatieresAlphabetique();
        System.out.println("Liste des matières : ");
        for (Matiere m : matieres) {
            System.out.println(m);
        }
        
        Intervenant intervenant;
        intervenant = service.authentifierIntervenant("cam.martin@sorbonne.fr", "azerty123");
        System.out.println("auth i1 : "+intervenant);
        System.out.println("auth i2 : "+service.authentifierIntervenant("cam.martin@sorbonne.fr", "azerty1235"));
        System.out.println("auth i3 : "+service.authentifierIntervenant("cam.martin@rbonne.fr", "azerty123"));
        
        System.out.println("\nAjout des élèves");
        Eleve eleve1 = new Eleve("Perrigault", "Simon", new Date(2004,03,31), 1, "simon.perrigault@insa-lyon.fr", "Vulcania");
        Eleve eleve2 = new Eleve("Joussot", "Gabin", new Date(2004,03,31), 0, "simon.errigault@insa-lyon.fr", "Vulcania");
        Eleve eleve3 = new Eleve("Gouineaud", "Romane", new Date(2004,03,31), 6, "sn.errigault@insa-lyon.fr", "Vulcania");
        Eleve eleve4 = new Eleve("Ben Bouzid", "Selim", new Date(2004,03,31), 6, "sn@insa-lyon.fr", "Vulcania");
        Eleve eleve5 = new Eleve("Griguer", "Mehdi", new Date(2004,03,31), 6, "@insa-lyon.fr", "Vulcania");
        
        eleve1 = service.inscrireEleve(eleve1, "0220057T");
        eleve2 = service.inscrireEleve(eleve2, "0691664J");
        eleve3 = service.inscrireEleve(eleve3, "0691664J");
        eleve4 = service.inscrireEleve(eleve4, "0691664J");
        eleve5 = service.inscrireEleve(eleve5, "0");
        
        eleve1 = service.authentifierEleve("simon.perrigault@insa-lyon.fr", "Vulcania");
        System.out.println("auth e1 : "+eleve1); // on teste le bon login puis 2 mauvais
        System.out.println("auth e2 : "+service.authentifierEleve("simon.perrigault@insa-lyor", "Vulcania"));
        System.out.println("auth e3 : "+service.authentifierEleve("simon.perrigault@insa-lyon.fr", "Vulcan"));
        
        // 4 demandes pour 3 intervenants, la deuxième n'est pas prise en compte car plus d'intervenant pour lycée (renvoie null)
        System.out.println("\nCréation des demandes");
        Demande demande1 = service.selectionnerIntervenantDemande(eleve1, service.getAllMatieresAlphabetique().get(0), "Je suis en galère");
        System.out.println(demande1);
        Demande demande2 = service.selectionnerIntervenantDemande(eleve2, service.getAllMatieresAlphabetique().get(1), "Je suis en galère");
        System.out.println(demande2);
        Demande demande3 = service.selectionnerIntervenantDemande(eleve3, service.getAllMatieresAlphabetique().get(5), "Je suis en galère");
        System.out.println(demande3);
        Demande demande4 = service.selectionnerIntervenantDemande(eleve4, service.getAllMatieresAlphabetique().get(5), "Je suis en galère");
        System.out.println(demande4);
        
        // on imagine qu'une visio vient de se finir
        service.finirVisioEleve(demande1, 4);
        service.finirVisioIntervenant(demande1, "Super ! Continue comme ça !");
        
        // page historique eleve
        System.out.println("\nHistorique élève : ");
        System.out.println(service.getHistoriqueDemandesEleve(eleve1));
        
        // page historique intervenant
        System.out.println("\nHistorique intervenant : ");
        System.out.println(service.getHistoriqueDemandesIntervenant(demande1.getIntervenant()));
        
        /// page tableau de bord intervenant
        System.out.println("\nTableau de bord intervenant : ");
        System.out.println(service.getAllEtablissementsAlphabetique());
        System.out.println(service.getAllElevesFromEtablissement(eleve2.getEtablissement()));
        System.out.println(service.getDureeMoyenneSoutiens(demande1.getIntervenant()));
        System.out.println(service.getMinutesParClasse(demande1.getIntervenant()));
        System.out.println(service.getMinutesParMatiere(demande1.getIntervenant()));

        JpaUtil.fermerFabriquePersistance();

    }
}
