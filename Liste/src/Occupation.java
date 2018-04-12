import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Occupation implements Serializable {
    private String poste="";
    private Entreprise entreprise=null;

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Occupation ajouterOccupation(){
        Scanner sc = new Scanner(System.in);
        Occupation nouvelleOccupation = new Occupation();

        System.out.println("Occupation");
        System.out.println("  Poste : ");
        nouvelleOccupation.setPoste(Main.verifyString(sc.next(), sc));

        nouvelleOccupation.setEntreprise(new Entreprise());
        nouvelleOccupation.setEntreprise(getEntreprise().ajouterEntreprise());

        return nouvelleOccupation;
    }

    public void afficherOccupation(){
        System.out.println("Occupation : ");
        System.out.println("Poste : "+poste);

        entreprise.afficherEntreprise();
    }

}
