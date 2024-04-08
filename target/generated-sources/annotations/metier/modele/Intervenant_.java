package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Demande;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-04-08T15:31:46")
@StaticMetamodel(Intervenant.class)
public class Intervenant_ { 

    public static volatile SingularAttribute<Intervenant, Demande> demandeEnCours;
    public static volatile SingularAttribute<Intervenant, String> motDePasse;
    public static volatile SingularAttribute<Intervenant, String> mail;
    public static volatile SingularAttribute<Intervenant, String> tel;
    public static volatile SingularAttribute<Intervenant, Long> id;
    public static volatile ListAttribute<Intervenant, Demande> demandes;
    public static volatile SingularAttribute<Intervenant, String> nom;
    public static volatile SingularAttribute<Intervenant, String> prenom;
    public static volatile SingularAttribute<Intervenant, Integer> niveauMin;
    public static volatile SingularAttribute<Intervenant, Integer> niveauMax;

}