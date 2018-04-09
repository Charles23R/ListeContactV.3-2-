import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Entreprise implements Serializable {

    private String nom="";
    private Adresse adresse=null;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Entreprise ajouterEntreprise(){
        Scanner sc=new Scanner(System.in);
        Entreprise nouvelleEntreprise=new Entreprise();

        System.out.println("Entreprise : ");
        System.out.println("Nom :");
        nouvelleEntreprise.setNom(sc.next());

        nouvelleEntreprise.setAdresse(new Adresse());
        nouvelleEntreprise.setAdresse(getAdresse().ajouterAdresse());

        return nouvelleEntreprise;
    }

    public void afficherEntreprise(){
        System.out.println("Entreprise : ");
        System.out.println("Nom : "+nom);

        adresse.afficherAdresse();
    }
}
