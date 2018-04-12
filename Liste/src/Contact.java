import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Contact implements Serializable{

    private String prenom="";
    private String nom="";
    private Adresse adresse=null;
    private Occupation occupation=null;
    private ArrayList<Telephone> listeTelephone=new ArrayList<Telephone>();
    private int nombreTelephones=0;

    //Getters
    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public ArrayList<Telephone> getListeTelephone() {
        return listeTelephone;
    }

    public int getNombreTelephones() {
        return nombreTelephones;
    }

    //Setters
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public void setListeTelephone(ArrayList<Telephone> listeTelephone) {
        this.listeTelephone = listeTelephone;
    }

    public void setNombreTelephones() {
        this.nombreTelephones=this.nombreTelephones+1;
    }

    public Telephone ajouterTelephone(){
        Scanner sc=new Scanner(System.in);
        Telephone telephone=new Telephone();
        System.out.println("À quoi correspond ce numéro ? (cellulaire, maison, travail...)");
        telephone.setInfo(Main.verifyString(sc.next(), sc));
        System.out.println("Quel est le numéro?");
        telephone.setNumero(sc.next());
        return telephone;
    }

    public void afficherTelephones(){
        for (int i=0; i<nombreTelephones; i++){
            System.out.println("Téléphone #"+(i+1));
            System.out.println("Information : "+listeTelephone.get(i).getInfo());
            System.out.println("Numéro : "+listeTelephone.get(i).getNumero());
        }
    }
}
