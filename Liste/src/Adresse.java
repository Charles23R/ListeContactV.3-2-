import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Adresse implements Serializable {

    private String numeroPorte="0";
    private String rue="";
    private String appartement="";
    private String ville="";
    private String province="";
    private String pays="";

    //Getters
    public String getNumeroPorte() {
        return numeroPorte;
    }

    public String getRue() {
        return rue;
    }

    public String getAppartement() {
        return appartement;
    }

    public String getVille() {
        return ville;
    }

    public String getProvince() {
        return province;
    }

    public String getPays() {
        return pays;
    }

    //Setters
    public void setNumeroPorte(String numeroPorte) {
        this.numeroPorte = numeroPorte;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setAppartement(String appartement) {
        this.appartement = appartement;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }


    public Adresse ajouterAdresse(){
        Scanner sc=new Scanner(System.in);
        Adresse nouvelleAdresse=new Adresse();

        System.out.println("Adresse : ");
        System.out.println("  Numéro de porte : ");
        nouvelleAdresse.setNumeroPorte(sc.next());
        System.out.println("  Rue : ");
        nouvelleAdresse.setRue(sc.next());
        System.out.println("  Appartement (facultatif) : ");
        nouvelleAdresse.setAppartement(sc.next());
        System.out.println("  Ville : ");
        nouvelleAdresse.setVille(sc.next());
        System.out.println("  Province : ");
        nouvelleAdresse.setProvince(sc.next());
        System.out.println("  Pays : ");
        nouvelleAdresse.setPays(sc.next());

        return nouvelleAdresse;
    }

    public void afficherAdresse(){
        System.out.println("Adresse : ");
        System.out.println("  Numéro de porte : "+this.numeroPorte);
        System.out.println("  Rue : ");
        System.out.println("  Appartement (facultatif) : "+this.appartement);
        System.out.println("  Ville : "+this.ville);
        System.out.println("  Province : "+this.province);
        System.out.println("  Pays : "+this.pays);
    }
}
