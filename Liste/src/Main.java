import java.io.*;
import java.util.*;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Main {
    public static void main(String[] args) {

        boolean programme=true;
        int choixMain=0;
        HashMap<String, Contact> listeContact=new HashMap<String, Contact>();
        String cle;
        Queue<String> listeRappels = new LinkedList<String>();

        //Initialisation du scanner
        Scanner sc= new Scanner(System.in);

        while (programme){
            System.out.println("Bienvenue dans l'application Contacts!");
            System.out.println("1- Ajouter un contact");
            System.out.println("2- Modifier un contact");
            System.out.println("3- Afficher les contacts");
            System.out.println("4- Supprimer un contact");
            System.out.println("5- Ajouter un contact à la liste de rappel");
            System.out.println("6- Afficher la liste de rappels");
            System.out.println("7- Charger une liste");
            System.out.println("8- Quitter");
            try{
                String choixString=sc.nextLine();
                choixMain=Integer.parseInt(choixString);
            }catch (Exception ex){
                System.out.println("Exception trouvée : "+ex);
                choixMain=0;
            }

            switch (choixMain){
                case 1: //Ajouter un contact
                    Contact hypothetique=ajouterContact();
                    listeContact.put(hypothetique.getPrenom().toUpperCase(),hypothetique);
                    break;

                case 2: //Modifier un contact
                    System.out.println("Entrez le prénom du contact à modifier");
                    cle=sc.next().toUpperCase();
                    modifierContact(cle, listeContact);
                    break;

                case 3: //Afficher les contacts
                    afficherContacts(listeContact);
                    break;

                case 4: //Supprimer un contact
                    String supp;
                        System.out.println("Entrez le prénom du contact à supprimer (AUCUN RETOUR EN ARRIÈRE)");
                        supp=sc.next();
                        if (listeContact.containsKey(supp.toUpperCase())){
                            listeContact.remove(supp.toUpperCase());
                            System.out.println("Contact supprimé");
                            System.out.println("");
                        }
                        else{
                            System.out.println("erreur");
                        }
                        break;

                case 5: System.out.println("Entrez le prénom du contact à ajouter à la liste de rappels");
                    String cleRappel=sc.next().toUpperCase();
                    if (listeContact.containsKey(cleRappel)){
                        listeRappels.add(listeContact.get(cleRappel).getPrenom());
                        System.out.println("Contact ajouté à la liste avec succès");
                        System.out.println("");
                    }
                    else{
                        System.out.println("Ce contact n'existe pas");
                        System.out.println("");
                    }

                    break;

                case 6 :
                    char decisionRappel='o';
                    int i=0;
                    System.out.println("Bienvenue dans la liste de rappels");
                    if (!listeRappels.isEmpty()) {
                        System.out.println("Les contacts qui ont été entrés en premiers seront les premiers de la liste");
                        System.out.println("Le premier contact est " + listeRappels.peek());
                        System.out.println("Voulez-vous rappeler ce contact? (il sera alors supprimé de la liste) (o/n)");
                        decisionRappel = sc.next().charAt(0);
                        if (decisionRappel == 'o') {
                            System.out.println(listeRappels.remove() + " a été retiré");
                        }
                    }
                    else{
                        System.out.println("La liste est vide");
                    }
                    System.out.println("");
                        break;

                case 7: listeContact=chargerMap();
                    listeRappels=chargerRappels();
                    break;

                case 8: //Quitter

                    programme=false;
                    System.out.println("Merci d'avoir utilisé l'application À la prochaine!");
                    sauvegarde(listeContact, listeRappels);
                    break;

                default: //Erreur
                    System.out.println("Erreur");
                System.out.println("");
            }
        }
    }



    public static HashMap<String, Contact> chargerMap(){
        HashMap<String, Contact> list=null;
        try {

            ObjectInputStream entree = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("list.dat")));
            try {
                list = (HashMap<String, Contact>) entree.readObject();
                entree.close();
            }catch (Exception ex){
                System.out.println("impossible de lire ce fichier 1");
            }
        }catch(Exception ex){
            System.out.println("impossible de lire ce fichier 2");
        }
        return list;
    }

    public static Queue chargerRappels(){
        Queue<String> queue = null;
        try {
            ObjectInputStream entree = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("list.dat")));
            try {
                entree.readObject();
                queue = (Queue<String>) entree.readObject();
                entree.close();
            }catch (Exception ex){
                System.out.println("impossible de lire ce fichier 3");
            }
        }catch(Exception ex){
            System.out.println("impossible de lire ce fichier 4");
        }
        return queue;
    }

    public static void sauvegarde(HashMap<String, Contact> list, Queue<String> queue){
        try {
        ObjectOutputStream sortie = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("list.dat")));
        sortie.writeObject(list);
        sortie.writeObject(queue);
        sortie.close();
        }catch (Exception ex){
            System.out.println("Impossible de sauvegarder. DÉSOLÉ!");
        }
    }



    public static Contact ajouterContact(){
        Scanner sc= new Scanner(System.in);

        Contact nouveauContact=new Contact();
        nouveauContact.setOccupation(new Occupation());
        nouveauContact.getOccupation().setEntreprise(new Entreprise());
        nouveauContact.getOccupation().getEntreprise().setAdresse(new Adresse());
        nouveauContact.setAdresse(new Adresse());
        Telephone telephoneHypothetique=new Telephone();


        System.out.println("Veuillez entrer les informations suivantes");
        System.out.println("Prénom : ");
        nouveauContact.setPrenom(sc.next());
        System.out.println("Nom : ");
        nouveauContact.setNom(sc.next());
        nouveauContact.setAdresse(nouveauContact.getAdresse().ajouterAdresse());
        nouveauContact.setOccupation(nouveauContact.getOccupation().ajouterOccupation());

        char decider='o';
        while (decider!='n'){
            System.out.println("Entrer un numéro de téléphone? (o/n)");
            decider=sc.next().charAt(0);
            if (decider=='o') {
                System.out.println("");
                telephoneHypothetique=nouveauContact.ajouterTelephone();
                nouveauContact.getListeTelephone().add(nouveauContact.getNombreTelephones(), telephoneHypothetique);
                nouveauContact.setNombreTelephones();
            }
            else if (decider!='o' && decider!='n'){
                System.out.println("erreur");
            }
        }

        System.out.println("Votre contact a été entré avec succès !");
        return nouveauContact;
    }



    public static void afficherContacts(HashMap<String, Contact> liste){

        int i=0;
        for (Contact contact : liste.values()){
            i++;
            System.out.println("-----------------------------------------------------");
            System.out.println("");
            System.out.println("Prénom : "+contact.getPrenom());
            System.out.println("Nom : "+contact.getNom());
            System.out.println("  ");
            contact.getAdresse().afficherAdresse();
            System.out.println("");
            contact.getOccupation().afficherOccupation();
            System.out.println("");
            contact.afficherTelephones();
            System.out.println("-----------------------------------------------------");
        }

        if (i==0){
            System.out.println("Auncun contact à afficher");
            System.out.println("");
        }
    }



    public static void modifierContact(String cle, HashMap<String, Contact> liste) {
        Scanner sc = new Scanner(System.in);
        char decider;
        liste.get(cle);
        String chaine;
        Telephone telephoneHypothetique=new Telephone();

        if (liste.containsKey(cle)) {


            System.out.println("Veuillez entrer les informations suivantes");
            System.out.println("Prénom : (" + liste.get(cle).getPrenom() + ")");
            chaine = sc.nextLine().trim();
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).setPrenom(chaine);
                liste.put(chaine, liste.get(cle));
                liste.remove(cle);
                cle=chaine;
            }
            System.out.println("Nom : (" + liste.get(cle).getNom() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).setNom(chaine);
            }
            System.out.println("Adresse : ");
            System.out.println("  Numéro de porte : (" + liste.get(cle).getAdresse().getNumeroPorte() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setNumeroPorte(chaine);
            }
            System.out.println("  Rue : (" + liste.get(cle).getAdresse().getRue() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setRue(chaine);
            }
            System.out.println("  Appartement (facultatif) : (" + liste.get(cle).getAdresse().getAppartement() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setAppartement(chaine);
            }
            System.out.println("  Ville : (" + liste.get(cle).getAdresse().getVille() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setVille(chaine);
            }
            System.out.println("  Province : (" + liste.get(cle).getAdresse().getProvince() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setProvince(chaine);
            }
            System.out.println("  Pays : (" + liste.get(cle).getAdresse().getPays() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setPays(chaine);
            }
            System.out.println("Occupation : ");
            System.out.println("  Poste : (" + liste.get(cle).getOccupation().getPoste() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().setPoste(chaine);
            }
            System.out.println("  Entreprise : ");
            System.out.println("    Nom : (" + liste.get(cle).getOccupation().getEntreprise().getNom() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().setNom(chaine);
            }
            System.out.println("    Adresse : ");
            System.out.println("      Numéro de porte : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getNumeroPorte() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setNumeroPorte(chaine);
            }
            System.out.println("      Rue : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getRue() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setRue(chaine);
            }
            System.out.println("      Appartement : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getNumeroPorte() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setAppartement(chaine);
            }
            System.out.println("      Ville : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getVille() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setVille(chaine);
            }
            System.out.println("      Province : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getProvince() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setProvince(chaine);
            }
            System.out.println("      Pays : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getPays() + ")");
            chaine = sc.nextLine().trim();
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setPays(chaine);
            }
            System.out.println("");
            System.out.println("Téléphones");

            for (int j = 0; j < liste.get(cle).getNombreTelephones(); j++) {
                if (liste.get(cle).getNombreTelephones()!=0){

                    System.out.println("  " + liste.get(cle).getListeTelephone().get(j).getInfo() + " : " + liste.get(cle).getListeTelephone().get(j).getNumero());
                    chaine = sc.nextLine().trim();
                    if (!chaine.equals("")) {
                        liste.get(cle).getListeTelephone().get(j).setNumero(chaine);
                    }
                }
            }

            decider = 'o';
            while (decider != 'n') {
                System.out.println("Entrer un numéro de téléphone? (o/n)");
                decider = sc.next().charAt(0);
                if (decider == 'o') {
                    System.out.println("");
                    telephoneHypothetique = liste.get(cle).ajouterTelephone();
                    liste.get(cle).getListeTelephone().add(liste.get(cle).getNombreTelephones(), telephoneHypothetique);
                    liste.get(cle).setNombreTelephones();
                } else if (decider != 'o' && decider != 'n') {
                    System.out.println("erreur");
                }
            }
        }
        else {
            System.out.println("Ce contact n'existe pas");
            System.out.println("");
        }
    }
}
