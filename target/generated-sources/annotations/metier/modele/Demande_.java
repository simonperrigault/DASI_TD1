package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-04-06T13:17:07")
@StaticMetamodel(Demande.class)
public class Demande_ { 

    public static volatile SingularAttribute<Demande, Integer> note;
    public static volatile SingularAttribute<Demande, Date> DateFin;
    public static volatile SingularAttribute<Demande, String> bilan;
    public static volatile SingularAttribute<Demande, Long> id;
    public static volatile SingularAttribute<Demande, Date> DateDebut;
    public static volatile SingularAttribute<Demande, String> detail;
    public static volatile SingularAttribute<Demande, Eleve> eleve;
    public static volatile SingularAttribute<Demande, Intervenant> intervenant;
    public static volatile SingularAttribute<Demande, Matiere> matiere;

}