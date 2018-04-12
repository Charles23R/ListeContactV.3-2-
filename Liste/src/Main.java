import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;

import java.io.*;
import java.util.*;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Main {

    public static String[] listePays= new String[]{"CANADA", "ÉTATS-UNIS", "MEXIQUE", "FRANCE", "ALLEMAGNE", "YOUGOSLAVIE", "ZIMBABWE"};
    public static String[] listeProvince= new String[]{"QUÉBEC", "ONTARIO", "MANITOBA", "COLOMBIE-BRITANNIQUE", "ALBERTA", "SASKATCHEWAN"};

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

    public static String verifyString(String chaine, Scanner sc){
        boolean fin =false;
        while (!fin){
            boolean ok=true;
            for (int i=0; i<chaine.length();i++){
                if (chaine.charAt(i)=='$' || chaine.charAt(i)=='%' || chaine.charAt(i)=='\\' || chaine.charAt(i)=='/' || chaine.charAt(i)=='*' || chaine.charAt(i)=='@' || chaine.charAt(i)=='?'){
                    ok=false;
                }
            }
            if (!ok){
                System.out.println("Réessayez svp (Veuillez ne pas entrer de caractères spéciaux)");
                chaine=sc.next();
            }
            else{
                return chaine;
        }
    }
        return chaine;
    }

    public static int verifyPays(String pays){
        pays = pays.toUpperCase();
        int ok=0;
        for (int i=0; i<listePays.length; i++){
            if (pays.equals(listePays[i])){
                ok=1;
            }
        }
        if (pays.equals("CANADA")){
            ok=2;
        }
        return ok;
    }

    public static boolean verifyProvince(String province){
        province = province.toUpperCase();
        boolean ok=false;
        for (int i=0; i<listeProvince.length; i++){
            if (province.equals(listeProvince[i])){
                ok=true;
            }
        }
        return ok;
    }

    public static boolean verifyTelephone(String telephone){
        boolean ok=true;
        if (telephone.length()!=12){
            ok=false;
        }
        for (int i=0; i<telephone.length()-1; i++){
            if (i==3 || i==7){
                if (telephone.charAt(i)!='-'){
                    ok=false;
                }
            }
            else{
                if ((int)telephone.charAt(i)<46 || (int)telephone.charAt(i)>57){
                    ok=false;
                }
            }
        }
        return ok;
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
                System.out.println("impossible de lire ce fichier");
            }
        }catch(Exception ex){
            System.out.println("impossible de lire ce fichier");
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
                System.out.println("impossible de lire ce fichier");
            }
        }catch(Exception ex){
            System.out.println("impossible de lire ce fichier");
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
        nouveauContact.setPrenom(verifyString(sc.next(), sc));
        System.out.println("Nom : ");
        nouveauContact.setNom(verifyString(sc.next(), sc));
        nouveauContact.setAdresse(nouveauContact.getAdresse().ajouterAdresse());
        nouveauContact.setOccupation(nouveauContact.getOccupation().ajouterOccupation());

        char decider='o';
        while (decider!='n'){
            System.out.println("Entrer un numéro de téléphone? (o/n)");
            decider=sc.next().charAt(0);
            if (decider=='o') {
                System.out.println("");
                telephoneHypothetique=nouveauContact.ajouterTelephone();
                if (verifyTelephone(telephoneHypothetique.getNumero())){
                    nouveauContact.getListeTelephone().add(nouveauContact.getNombreTelephones(), telephoneHypothetique);
                    nouveauContact.setNombreTelephones();
                }
                else{
                    System.out.println("Erreur dans le format de votre numéro de téléphone");
                }
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
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).setPrenom(chaine);
                liste.put(chaine, liste.get(cle));
                liste.remove(cle);
                cle=chaine;
            }
            System.out.println("Nom : (" + liste.get(cle).getNom() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).setNom(chaine);
            }
            System.out.println("Adresse : ");
            System.out.println("  Numéro de porte : (" + liste.get(cle).getAdresse().getNumeroPorte() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setNumeroPorte(chaine);
            }
            System.out.println("  Rue : (" + liste.get(cle).getAdresse().getRue() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setRue(chaine);
            }
            System.out.println("  Appartement (facultatif) : (" + liste.get(cle).getAdresse().getAppartement() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setAppartement(chaine);
            }
            System.out.println("  Ville : (" + liste.get(cle).getAdresse().getVille() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getAdresse().setVille(chaine);
            }
                boolean ok =false;
                while (!ok){
                    System.out.println("  Pays : (" + liste.get(cle).getAdresse().getPays() + ")");
                    chaine = verifyString(sc.nextLine().trim(), sc);
                    if (!chaine.equals("")) {
                        String pays=chaine;
                        int option = Main.verifyPays(pays);
                        if (option==0){
                            System.out.println("Erreur, ce pays n'existe pas");
                        }
                        else if (option==2){
                            liste.get(cle).getAdresse().setPays(pays);
                            ok=true;
                            boolean ok2=false;
                            while(!ok2){
                                System.out.println("  Province : (" + liste.get(cle).getAdresse().getProvince() + ")");
                                chaine = verifyString(sc.nextLine().trim(), sc);
                                if (!chaine.equals("")) {
                                    System.out.println("  Province : ");
                                    String province =chaine;
                                    if (Main.verifyProvince(province)){
                                        liste.get(cle).getAdresse().setProvince(province);
                                        ok2=true;
                                    }
                                    else{
                                        System.out.println("Cette province n'existe pas");
                                    }
                                    liste.get(cle).getAdresse().setProvince(chaine);
                                }
                            }
                        }
                        else
                        {
                            liste.get(cle).getAdresse().setPays(pays);
                            ok=true;
                        }
                    }
                    else {
                        ok=true;
                    }
                }
            System.out.println("Occupation : ");
            System.out.println("  Poste : (" + liste.get(cle).getOccupation().getPoste() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().setPoste(chaine);
            }
            System.out.println("  Entreprise : ");
            System.out.println("    Nom : (" + liste.get(cle).getOccupation().getEntreprise().getNom() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().setNom(chaine);
            }
            System.out.println("    Adresse : ");
            System.out.println("      Numéro de porte : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getNumeroPorte() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setNumeroPorte(chaine);
            }
            System.out.println("      Rue : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getRue() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setRue(chaine);
            }
            System.out.println("      Appartement : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getNumeroPorte() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setAppartement(chaine);
            }
            System.out.println("      Ville : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getVille() + ")");
            chaine = verifyString(sc.nextLine().trim(), sc);
            if (!chaine.equals("")) {
                liste.get(cle).getOccupation().getEntreprise().getAdresse().setVille(chaine);
            }
            boolean ok5 =false;
            while (!ok5){
                System.out.println("  Pays : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getPays() + ")");
                chaine = verifyString(sc.nextLine().trim(), sc);
                if (!chaine.equals("")) {
                    String pays=chaine;
                    int option = Main.verifyPays(pays);
                    if (option==0){
                        System.out.println("Erreur, ce pays n'existe pas");
                    }
                    else if (option==2){
                        liste.get(cle).getOccupation().getEntreprise().getAdresse().setPays(pays);
                        ok5=true;
                        boolean ok6=false;
                        while(!ok6){
                            System.out.println("  Province : (" + liste.get(cle).getOccupation().getEntreprise().getAdresse().getProvince() + ")");
                            chaine = verifyString(sc.nextLine().trim(), sc);
                            if (!chaine.equals("")) {
                                System.out.println("  Province : ");
                                String province =chaine;
                                if (Main.verifyProvince(province)){
                                    liste.get(cle).getOccupation().getEntreprise().getAdresse().setProvince(province);
                                    ok6=true;
                                }
                                else{
                                    System.out.println("Cette province n'existe pas");
                                }
                                liste.get(cle).getOccupation().getEntreprise().getAdresse().setProvince(chaine);
                            }
                        }
                    }
                    else
                    {
                        liste.get(cle).getAdresse().setPays(pays);
                        ok5=true;
                    }
                }
                else {
                    ok5=true;
                }
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
                    if (verifyTelephone(telephoneHypothetique.getNumero())){
                        liste.get(cle).getListeTelephone().add(liste.get(cle).getNombreTelephones(), telephoneHypothetique);
                        liste.get(cle).setNombreTelephones();
                    }
                    else{
                        System.out.println("Erreur dans le format de votre numéro de téléphone");
                    }
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
