package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Demande;
import metier.modele.Etablissement;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-04-08T15:34:31")
@StaticMetamodel(Eleve.class)
public class Eleve_ { 

    public static volatile SingularAttribute<Eleve, String> motDePasse;
    public static volatile SingularAttribute<Eleve, Integer> classe;
    public static volatile SingularAttribute<Eleve, String> mail;
    public static volatile SingularAttribute<Eleve, Date> dateNaissance;
    public static volatile SingularAttribute<Eleve, Etablissement> etablissement;
    public static volatile SingularAttribute<Eleve, Long> id;
    public static volatile ListAttribute<Eleve, Demande> demandes;
    public static volatile SingularAttribute<Eleve, String> nom;
    public static volatile SingularAttribute<Eleve, String> prenom;

}