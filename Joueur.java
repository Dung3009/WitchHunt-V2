import java.util.*;

public class Joueur {

    protected String nom;
    protected static int nbCarte, points, refJoueur;
    protected boolean  estAccuse, idEstRevele, tour, evilEye, accuse;
    protected ArrayList<CarteRumeur> carteJoueurMain, carteJoueurPlateau;
    private Role identite;

    public Joueur(int refJoueur) { //constructeur de Joueur
        this.refJoueur=refJoueur;
        this.carteJoueurMain=new ArrayList<CarteRumeur>();
        this.carteJoueurPlateau=new ArrayList<CarteRumeur>();
    }

    public Role getIdentite() {
        return identite;
    }

    public void setIdentite(Role identite) {
        this.identite = identite;
    }

    public boolean isIdEstRevele() {
        return idEstRevele;
    }

    public void setIdEstRevele(boolean idEstRevele) {
        this.idEstRevele = idEstRevele;
    }

    public boolean isTour() {
        return tour;
    }

    public void setTour(boolean tour) {
        this.tour = tour;
    }

    public boolean isEstAccuse() {
        return estAccuse;
    }

    public void setEstAccuse(boolean estAccuse) {
        this.estAccuse = estAccuse;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }


    public void setPoints(int points){ //Ajout d'un certain nombre de points pour un joueur donné
        this.points= points;
    }

    public int getPoints() { //getter pour récupérer le nombre de points d'un joueur
        return this.points;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<CarteRumeur> getCarteJoueurPlateau() {
        return carteJoueurPlateau;
    }

    public void setCarteJoueurPlateau(ArrayList<CarteRumeur> carteJoueurPlateau) {
        this.carteJoueurPlateau = carteJoueurPlateau;
    }

    public ArrayList<CarteRumeur> getCarteJoueurMain() {
        return carteJoueurMain;
    }

    public void setCarteJoueurMain(ArrayList<CarteRumeur> carteJoueurMain) {
        this.carteJoueurMain = carteJoueurMain;
    }

    public boolean Accuse() {
        return accuse;
    }

    public void setAccuse(boolean accuse) {
        this.accuse = accuse;
    }

    public boolean isEvilEye() {
        return evilEye;
    }

    public void setEvilEye(boolean evilEye) {
        this.evilEye = evilEye;
    }
    public int choisirJoueur(){
        Scanner inputNom = new Scanner(System.in);
        System.out.println("Entrer nom de la joueur que vous voulez jouer suivant ");
        String nomJoueur = inputNom.nextLine();
        inputNom.close();
        int indiceJoueur = Partie.getInstance().chercherJoueur(nomJoueur);
        return indiceJoueur;
    }

    public void choisirRole(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous joueur être 'Witch' ou 'Villager'? Tapper 'W' pour 'Witch' et 'V' pour 'Villager' ");
        char role = scanner.nextLine().charAt(0);
        while(role!='W' && role!='V'){
            System.out.println("Mauvaise saisie. Veuillez saisir 'W' ou 'V' :");
            role = scanner.nextLine().charAt(0);
        }
        if ( role == 'W'){
            this.setIdentite(Role.Witch);
        }
        else{
            this.setIdentite(Role.Villager);
        }
    }

    public void ramasserCarte(CarteRumeur carteRumeur){
        this.carteJoueurMain.add(carteRumeur);
    }



    public boolean revelerId(){
        if(this.getIdentite().equals(Role.Witch)){
            Partie.getInstance().getTabjoueurSup().add(this);
            Partie.getInstance().getTabjoueur().remove(this);
            return true;
        }
        else{
            this.idEstRevele = true;
            return false;
        }
    }

    public void jouerCarte(CarteRumeur carteRumeur){
        this.carteJoueurPlateau.add(carteRumeur);
        this.carteJoueurMain.remove(carteRumeur);
        if (this.estAccuse == true){
            this.effetWitch(carteRumeur);
            this.estAccuse = false;
        }
        else{
            this.efffetHunt(carteRumeur);
        }
    }

    public void accuser() { //Pour une reférence de joueur donné, la variable estAccusé de l'objet de type Joueur prend la valeur "true".
        Scanner inputAction=new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
        System.out.println("Quel Joueur voulez vous accuser ? :");
        String accuser=inputAction.nextLine();
        this.setAccuse(true);
        int indice = Partie.getInstance().chercherJoueur(accuser);
        Partie.getInstance().getTabjoueur().get(indice).setEstAccuse(true);
        while (this.Accuse()){
            Partie.getInstance().getTabjoueur().get(indice).repondreAccusation();
            this.setAccuse(false);
        }
        this.tour=false;

    }

    public void effetWitch(CarteRumeur carteRumeur){
        if ( carteRumeur.getNomCarte().equals(NomCarte.AngryMob)){
            this.setTour(true);

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.TheInquisition)){
            System.out.println("Ce sont vos cartes ");
            for( int i = 0; i< this.carteJoueurMain.size(); i++){
                System.out.println(this.carteJoueurMain.get(i).getNomCarte());
            }
            Scanner inputNumeroCarte = new Scanner(System.in);
            System.out.println("Entrer le numero de la carte que vous voulez jeter");
            int numCarte = inputNumeroCarte.nextInt();
            inputNumeroCarte.close();
            Partie.getInstance().getCarteDiscarte().add(carteJoueurMain.get(numCarte));
            this.carteJoueurMain.remove(carteJoueurMain.get(numCarte));
            this.setTour(true);


        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PointedHat)){
            if (this.carteJoueurPlateau.size() == 0){
                System.out.println("Vous pouvez pas utiliser cette carte ");
            }
            else{
                System.out.println("Ce sont vos carte sur plateau ");
                for(int i = 0; i< this.carteJoueurPlateau.size(); i++){
                    System.out.println(this.carteJoueurPlateau.get(i).getNomCarte());
                }
                Scanner inputNumeroCarte = new Scanner(System.in);
                System.out.println("Entrer le numero de la carte que vous voulez recuperer ");
                int numcarte = inputNumeroCarte.nextInt();
                inputNumeroCarte.close();
                this.getCarteJoueurMain().add(this.getCarteJoueurPlateau().get(numcarte));
                this.getCarteJoueurPlateau().remove(this.getCarteJoueurPlateau().get(numcarte));
                this.setTour(true);
            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.BroomStick)){
            this.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Wart)){
            this.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.DuckingStool)) {
            int indiceJoueur = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.EvilEye)){
            int indiceJoueur  = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setEvilEye(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Toad)){
            this.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.BlackCat)){
            this.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PetNewt)){
            this.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.HookedNose)){//Permet de trouver le joueur qui accuse
            this.setTour(true);
            int indicePersonneAccuse = 0;
            boolean check = false;
            while (Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).accuse == false){
                indicePersonneAccuse++;
            }
            System.out.println("Veuillez choisir numero de la carte ");
            for (int j = 0; j < Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().size(); j++){

                System.out.println(j+1);
            }
            Scanner inputNumcarte = new Scanner(System.in);
            int numCarte = inputNumcarte.nextInt();
            while (check == false) {//Condition d'erreur
                if (numCarte >= 0 && numCarte <= this.carteJoueurMain.size()) {
                    check = true;
                } else if (numCarte < 0 && numCarte > this.carteJoueurMain.size()) {
                    System.out.println("Mauvais saisir. Veuillez saisir un autre numero de la carte ");
                    numCarte = inputNumcarte.nextInt();

                }
            }
            inputNumcarte.close();
            this.carteJoueurMain.add(Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().get(numCarte-1));//ajouter une carte dans le jeu de la personne accusée
            Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().remove(Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().get(numCarte-1));// retire la carte du jeu de la personne qui accuse

        }
        if ( carteRumeur.getNomCarte().equals(NomCarte.Cauldron)){
            this.setTour(true);
            int indicePersonneAccuse = 0;
            boolean check = false;
            while (Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).accuse == false){
                indicePersonneAccuse++;
            }
            int numCarte =(int) (Math.random()* Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().size());
            Partie.getInstance().getCarteDiscarte().add(Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().get(numCarte));
            Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().remove(Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).getCarteJoueurMain().get(numCarte));// retire la carte du jeu de la personne qui accuse

        }

    }

    public void efffetHunt( CarteRumeur carteRumeur){
        if (carteRumeur.getNomCarte().equals(NomCarte.AngryMob)){
            int indiceJoueur = this.choisirJoueur();
            boolean check = Partie.getInstance().getTabjoueur().get(indiceJoueur).revelerId();
            if (check){
                this.setPoints(this.getPoints()+2);
                this.setTour(true);
            }
            else {
                this.setPoints(this.getPoints()-2);
                Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);

            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.TheInquisition)){
            Scanner inputNom = new Scanner(System.in);
            System.out.println("Entrer nom de la joueur que vous voulez jouer suvant ");
            String nomJoueur = inputNom.nextLine();
            inputNom.close();
            int indiceJoueur = Partie.getInstance().chercherJoueur(nomJoueur);
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
            System.out.println("Son ID est "+ Partie.getInstance().getTabjoueur().get(indiceJoueur).getIdentite() );

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PointedHat)){
            if (this.carteJoueurPlateau.size() == 0){
                System.out.println("Vous pouvez pas utiliser cette carte ");
            }
            else{
                System.out.println("Ce sont vos carte sur plateau ");
                for(int i = 0; i< this.carteJoueurPlateau.size(); i++){
                    System.out.println(this.carteJoueurPlateau.get(i).getNomCarte());
                }
                Scanner inputNumeroCarte = new Scanner(System.in);
                System.out.println("Entrer le numero de la carte que vous voulez recuperer ");
                int numcarte = inputNumeroCarte.nextInt();
                inputNumeroCarte.close();
                this.getCarteJoueurMain().add(this.getCarteJoueurPlateau().get(numcarte));
                this.getCarteJoueurPlateau().remove(this.getCarteJoueurPlateau().get(numcarte));
                int indiceJoueur = this.choisirJoueur();
                Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);


            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Wart)){
            int indiceJoueur = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.BroomStick)){
            int indiceJoueur = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.HookedNose)){
            int indiceJoueur = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
            int numCarte =(int) (Math.random()* Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurMain().size());
            this.carteJoueurMain.add(Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurMain().get(numCarte));
            Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurMain().remove(Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurMain().get(numCarte));// retire la carte du jeu de la personne qui accuse

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.EvilEye)){
            int indiceJoueur  = this.choisirJoueur();
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setEvilEye(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PetNewt)){
            boolean check = false;
            int indiceJoueur = this.choisirJoueur();
            System.out.println(" Veuillez choisir la numero de la carte");
            for (int i=0; i < Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurPlateau().size();i++){
                System.out.println(i+1);
            }
            Scanner inputNumcarte = new Scanner(System.in);
            int numCarte = inputNumcarte.nextInt();
            while (check == false) {//Condition d'erreur
                if (numCarte >= 0 && numCarte <= this.carteJoueurMain.size()) {
                    check = true;
                } else if (numCarte < 0 && numCarte > this.carteJoueurMain.size()) {
                    System.out.println("Mauvais saisir. Veuillez saisir un autre numero de la carte ");
                    numCarte = inputNumcarte.nextInt();

                }
            }
            inputNumcarte.close();
            this.carteJoueurMain.add(Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurPlateau().get(numCarte-1));
            Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurPlateau().remove(Partie.getInstance().getTabjoueur().get(indiceJoueur).getCarteJoueurPlateau().get(numCarte-1));
            Partie.getInstance().getTabjoueur().get(indiceJoueur).setTour(true);

        }




    }


    public void repondreAccusation() { //Méthode décrivant une des actions que peut faire le joueur si ce dernier est accusé : Si le joueur est accusé il peut révéler son identité

        if (this.estAccuse==true) {

            System.out.println("Joueur "+getNom());

            if(carteJoueurMain.size()==0){
                this.revelerId();

            }
            else{
                Scanner inputAction=new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
                System.out.println("Quelle action voulez vous faire ? :");
                System.out.println("- R pour réveler votre identité `\n - W pour utiliser l'effet Witch");
                char action=inputAction.nextLine().charAt(0); //Lis la valeur saisie par l'utilisateur

                while (action!='R' && action!='W') { //condition d'erreur
                    System.out.println("Mauvaise saisie, veuillez saisir R ou W\n");
                    action=inputAction.nextLine().charAt(0);
                }

                if (action=='R'){
                    this.revelerId();
                }
                else if(action=='W'){
                    System.out.println("Ce sont vos cartes ");
                    for( int i = 0; i< this.carteJoueurMain.size(); i++){
                        System.out.println(this.carteJoueurMain.get(i).getNomCarte());
                    }
                    Scanner inputCarteR=new Scanner(System.in);
                    System.out.println("Quelle carte voulez vous jouer ? : Veuillez saisir le numéro de la carte");
                    int numCarteR=inputCarteR.nextInt();
                    while(numCarteR>carteJoueurMain.size()){ //tant qu'on ne trouve pas le nom de la carte saisie, on incrémente j
                        System.out.println("Erreur, veuillez ressasir le nom de la carte");
                        numCarteR=inputCarteR.nextInt();

                        }
                    this.jouerCarte(carteJoueurMain.get(numCarteR));
                    }

                }
            }





        }

    public void commencerTour(){

            if (carteJoueurMain.size()==0){
                this.accuser();
            }
            else{
                Scanner inputAction=new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
                System.out.println("Quelle action voulez vous faire ? : \n - A pour accuser un joueur \n - H pour utiliser le Hunt effect");
                char action=inputAction.nextLine().charAt(0);

                while(action!='A' && action!='H'){ //condition d'erreur
                    System.out.println("Mauvaise saisie : Quelle action voulez vous faire ? : \n - A pour accuser un joueur \n - H pour utiliser le Hunt effect");
                    action=inputAction.nextLine().charAt(0);

                }

                if(action=='A'){
                    this.accuser();
                }
                else if(action=='H'){
                    System.out.println("Ce sont vos cartes ");
                    for( int i = 0; i< this.carteJoueurMain.size(); i++){
                        System.out.println(this.carteJoueurMain.get(i).getNomCarte());
                    }
                    boolean check = false;
                    Scanner inputNumeroCarte = new Scanner(System.in);
                    System.out.println("Entrer le numero de la carte que vous voulez utiliser");
                    int numCarte = inputNumeroCarte.nextInt();
                    while (check == false){//Condition d'erreur
                        if(numCarte >=0 && numCarte <= this.carteJoueurMain.size()){
                            check = true;
                        }
                        else if (numCarte < 0 && numCarte> this.carteJoueurMain.size()){
                            System.out.println("Mauvais saisir. Veuillez saisir un autre numero de la carte ");
                            numCarte = inputNumeroCarte.nextInt();

                        }
                    }
                    this.jouerCarte(this.getCarteJoueurMain().get(numCarte-1));

                }
            }
        this.setTour(false);
    }




    public static void main (String[] args){

    }
}
