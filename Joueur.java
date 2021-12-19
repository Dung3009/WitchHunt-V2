import java.util.*;

public class Joueur {

    protected String nom;
    protected static int nbCarte,  refJoueur;
    protected boolean  estAccuse, idEstRevele, tour, evilEye, accuse, utiliserEffet, personneUtiliseEvilEye;
    protected ArrayList<CarteRumeur> carteJoueurMain, carteJoueurPlateau;
    private Role identite;
    private int points;
    protected boolean estVirtuel;

    public Joueur(int refJoueur) { //constructeur de Joueur
        this.refJoueur=refJoueur;
        this.carteJoueurMain=new ArrayList<CarteRumeur>();
        this.carteJoueurPlateau=new ArrayList<CarteRumeur>();
        this.estVirtuel=false;
        this.setPoints(0);
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
    public Joueur choisirJoueur(){
        if(this.estVirtuel==false) {
            Scanner inputNom = new Scanner(System.in);
            System.out.println("Entrer le nom du joueur que vous voulez choisir ");
            String nomJoueur = inputNom.nextLine();
            Joueur joueurChoisi = Partie.getInstance().chercherJoueur(nomJoueur);
            return joueurChoisi;
        }
        else{
            int numJoueur = (int) (Math.random() * Partie.getInstance().getTabjoueur().size()); //choisis un numéro de joueur au hasard parmis tous les joueurs de la partie en cours
            String nomJoueur= Partie.getInstance().getTabjoueur().get(numJoueur).getNom();
            JoueurVirtuel joueurChoisi = (JoueurVirtuel) Partie.getInstance().chercherJoueur(nomJoueur);
            return joueurChoisi;
        }
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
            this.idEstRevele = true;
            System.out.println("Le joueur "+ getNom() + " est une WITCH");
            Partie.getInstance().getTabjoueurSup().add(this);
            Partie.getInstance().getTabjoueur().remove(this);
            return true;
        }
        else{
            this.idEstRevele = true;
            System.out.println("Le joueur "+ getNom() + " est un Villageois");
            return false;
        }
    }

    public void jouerCarte(CarteRumeur carteRumeur){
        /*if(this.estVirtuel==true){
            JoueurVirtuel joueurV=new JoueurVirtuel(this.refJoueur);
            joueurV =(JoueurVirtuel) this;
            int choixStrat=(int)(Math.random()*1);
            if(choixStrat==1){
                joueurV.aggressif();
            }
            else if(choixStrat==0){
                joueurV.strategique();
            }
        }
        else {*/
            this.carteJoueurPlateau.add(carteRumeur);
            this.carteJoueurMain.remove(carteRumeur);
            if (this.estAccuse == true) {
                System.out.println("Le joueur " + getNom() + " a utilisé l'effet Witch de la carte " + carteRumeur.getNomCarte());
                this.effetWitch(carteRumeur);
                if (this.utiliserEffet) {
                    this.estAccuse = false;
                } else {
                    this.carteJoueurMain.add(carteRumeur);
                    this.carteJoueurPlateau.remove(carteRumeur);
                    System.out.println("Vous pouvez pas utiliser cette carte ");
                }
            } else {
                System.out.println("Le joueur " + getNom() + " a utilisé l'effet Hunt de la carte " + carteRumeur.getNomCarte());
                this.efffetHunt(carteRumeur);

                if (this.utiliserEffet == false) {
                    this.carteJoueurMain.add(carteRumeur);
                    this.carteJoueurPlateau.remove(carteRumeur);
                    System.out.println("Vous pouvez pas utiliser cette carte ");
                }
            }
        }

    //}

    public void accuser() { //Pour une reférence de joueur donné, la variable estAccusé de l'objet de type Joueur prend la valeur "true".
        if (this.estVirtuel == false) {
            this.setAccuse(true);
            Scanner inputAction = new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
            System.out.println("Quel Joueur voulez vous accuser ? :");
            String accuser = inputAction.nextLine();
            Joueur joueurAccuse = Partie.getInstance().chercherJoueur(accuser);
            while (joueurAccuse.isIdEstRevele() == true) {
                System.out.println("Ce joueur a deja révélé son identité. Veuillez choisir autre joueur.");
                joueurAccuse = this.choisirJoueur();

            }
            joueurAccuse.setEstAccuse(true);
            while (this.Accuse()) {
                joueurAccuse.repondreAccusation();
                this.setAccuse(false);
            }

            if (joueurAccuse.getIdentite().equals(Role.Witch)) {
                this.setPoints(this.getPoints() + 1);
            }
        } else {
            this.setAccuse(true);
            int numJoueur = (int) (Math.random() * Partie.getInstance().getTabjoueur().size());
            String nomJoueur = Partie.getInstance().getTabjoueur().get(numJoueur).getNom();
            Joueur joueurAccuse = Partie.getInstance().chercherJoueur(nomJoueur);
            while (joueurAccuse.isIdEstRevele() == true) {
                System.out.println("Ce joueur a deja révélé son identité.");
                joueurAccuse = this.choisirJoueur();
            }

            joueurAccuse.setEstAccuse(true);
            while (this.Accuse()) {
                joueurAccuse.repondreAccusation();
                this.setAccuse(false);
            }

            if (joueurAccuse.getIdentite().equals(Role.Witch)) {
                this.setPoints(this.getPoints() + 1);
            }
        }
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
            System.out.println("Entrez le numéro de la carte que vous voulez jeter");
            int numCarte = inputNumeroCarte.nextInt();
            Partie.getInstance().getCarteDiscarte().add(carteJoueurMain.get(numCarte));
            this.carteJoueurMain.remove(carteJoueurMain.get(numCarte));
            this.setTour(true);


        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PointedHat)){
            if (this.carteJoueurPlateau.size() == 0){
                System.out.println("Vous ne pouvez pas utiliser cette carte ");
                this.utiliserEffet = false;
            }
            else{
                System.out.println("Ce sont vos carte sur le plateau ");
                for(int i = 0; i< this.carteJoueurPlateau.size(); i++){
                    System.out.println(this.carteJoueurPlateau.get(i).getNomCarte());
                }
                Scanner inputNumeroCarte = new Scanner(System.in);
                System.out.println("Entrez le numéro de la carte que vous voulez récupérer ");
                int numcarte = inputNumeroCarte.nextInt();
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
            boolean check = false;
            Joueur joueurChoisi = this.choisirJoueur();
            while(check == false){
                boolean checkCarte = true;
                for (int i =0; i < joueurChoisi.getCarteJoueurPlateau().size(); i++ ){
                    if(joueurChoisi.getCarteJoueurPlateau().get(i).getNomCarte() == NomCarte.Wart){
                        checkCarte = false;
                    }
                }
                if (checkCarte == false){
                    System.out.println("Mauvaise saisie. Veuillez choisir un autre joueur ");
                    joueurChoisi = this.choisirJoueur();
                }
                else{
                    check = true;
                }

            }
            joueurChoisi.setTour(true);

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.EvilEye)){
            Joueur joueurChoisi  = this.choisirJoueur();
            joueurChoisi.setTour(true);
            joueurChoisi.setEvilEye(true);
            this.personneUtiliseEvilEye = true;
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
            Joueur joueurAccuse = Partie.getInstance().getTabjoueur().get(indicePersonneAccuse);
            System.out.println("Veuillez choisir un numéro de carte ");
            for (int j = 0; j < joueurAccuse.getCarteJoueurMain().size(); j++){

                System.out.println(j+1);
            }
            Scanner inputNumcarte = new Scanner(System.in);
            int numCarte = inputNumcarte.nextInt();
            while (check == false) {//Condition d'erreur
                if (numCarte >= 0 && numCarte <= this.carteJoueurMain.size()) {
                    check = true;
                } else if (numCarte < 0 && numCarte > this.carteJoueurMain.size()) {
                    System.out.println("Mauvaise saisie. Veuillez saisir un autre numéro de carte ");
                    numCarte = inputNumcarte.nextInt();

                }
            }
            this.carteJoueurMain.add(joueurAccuse.getCarteJoueurMain().get(numCarte-1));//ajouter une carte dans le jeu de la personne accusée
            joueurAccuse.getCarteJoueurMain().remove(joueurAccuse.getCarteJoueurMain().get(numCarte-1));// retire la carte du jeu de la personne qui accuse

        }
        if ( carteRumeur.getNomCarte().equals(NomCarte.Cauldron)){
            this.setTour(true);
            int indicePersonneAccuse = 0;
            boolean check = false;
            while (Partie.getInstance().getTabjoueur().get(indicePersonneAccuse).accuse == false){
                indicePersonneAccuse++;
            }
            Joueur joueurAccuse = Partie.getInstance().getTabjoueur().get(indicePersonneAccuse);
            int numCarte =(int) (Math.random()* joueurAccuse.getCarteJoueurMain().size());
            Partie.getInstance().getCarteDiscarte().add(joueurAccuse.getCarteJoueurMain().get(numCarte));
            joueurAccuse.getCarteJoueurMain().remove(joueurAccuse.getCarteJoueurMain().get(numCarte));// retire la carte du jeu de la personne qui accuse

        }

    }

    public int repondreEffetHuntDuckingStool(){
        int choice;
        Scanner inputChoice = new Scanner(System.in);
        System.out.println("Vous voulez reveler votre ID ou jeter une carte. Tapper '1' pour reveler, '2' pour jeter une carte ");
        choice = inputChoice.nextInt();
        while (choice != 1 && choice !=2){
            System.out.println("Mauvais saisir. Veuillez resaisir votre choice ");
            choice = inputChoice.nextInt();
        }
        if (choice == 2){
            Scanner inputNumeroCarte = new Scanner(System.in);
            System.out.println("Ce sont vos cartes ");
            for (int i=0; i < this.getCarteJoueurMain().size(); i++){
                System.out.println(this.getCarteJoueurMain().get(i).getNomCarte());
            }
            System.out.println("Tapper le numero de la carte que vous voulez jeter ");
            int numeroCarte = inputNumeroCarte.nextInt();
            while (numeroCarte > this.getCarteJoueurMain().size() || numeroCarte < 1){
                System.out.println("Mauvais saisir. Veuillez resaisir le numero de la carte");
                numeroCarte = inputNumeroCarte.nextInt();
            }
            Partie.getInstance().getCarteDiscarte().add(this.carteJoueurMain.get(numeroCarte));
            this.carteJoueurMain.remove(this.carteJoueurMain.get(numeroCarte));
            this.setTour(true);


        }
        return choice;

    }

    public void efffetHunt( CarteRumeur carteRumeur){
        if (carteRumeur.getNomCarte().equals(NomCarte.AngryMob)){
            boolean check = false;
            if (this.idEstRevele == false){
                System.out.println("Vous ne pouvez pas utiliser cette carte ");
                this.utiliserEffet = false;
            }
            else{
                Joueur joueurChoisi = this.choisirJoueur();
                boolean verifierID;
                while(check == false){
                    boolean checkCarte = true;
                    for (int i =0; i < joueurChoisi.getCarteJoueurPlateau().size(); i++ ){
                        if(joueurChoisi.getCarteJoueurPlateau().get(i).getNomCarte() == NomCarte.BroomStick){
                            checkCarte = false;
                        }
                    }
                    if (checkCarte == false){
                        System.out.println("Mauvaise saisie. Veuillez choisir un autre joueur ");
                        joueurChoisi = this.choisirJoueur();
                    }
                    else{
                        check = true;
                    }

                }
                verifierID = joueurChoisi.revelerId();
                if (verifierID){
                    this.setPoints(this.getPoints()+2);
                    this.setTour(true);

                }
                else{
                    this.setPoints(this.getPoints()-2);
                    joueurChoisi.setTour(true);
                }




            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.TheInquisition)){
            if (this.isIdEstRevele() == false){
                System.out.println("Vous ne pouvez pas utiliser  cette carte ");
                this.utiliserEffet = false;
            }

            Joueur joueurChoisi = this.choisirJoueur();
            joueurChoisi.setTour(true);
            System.out.println("Son ID est "+ joueurChoisi.getIdentite() );

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PointedHat)){
            if (this.carteJoueurPlateau.size() == 0){
                System.out.println("Vous ne pouvez pas utiliser cette carte ");
                this.utiliserEffet = false;
            }
            else{
                System.out.println("Ce sont vos carte sur le plateau ");
                for(int i = 0; i< this.carteJoueurPlateau.size(); i++){
                    System.out.println(this.carteJoueurPlateau.get(i).getNomCarte());
                }
                Scanner inputNumeroCarte = new Scanner(System.in);
                System.out.println("Entrez le numero de la carte que vous voulez récupérer ");
                int numcarte = inputNumeroCarte.nextInt();
                this.getCarteJoueurMain().add(this.getCarteJoueurPlateau().get(numcarte));
                this.getCarteJoueurPlateau().remove(this.getCarteJoueurPlateau().get(numcarte));
                Joueur joueurChoisi = this.choisirJoueur();
                joueurChoisi.setTour(true);


            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Wart)){
            Joueur joueurChoisi = this.choisirJoueur();
            joueurChoisi.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.BroomStick)){
            Joueur joueurChoisi = this.choisirJoueur();
            joueurChoisi.setTour(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.HookedNose)){
            Joueur joueurChoisi = this.choisirJoueur();
            joueurChoisi.setTour(true);
            int numCarte =(int) (Math.random()* joueurChoisi.getCarteJoueurMain().size());
            this.carteJoueurMain.add(joueurChoisi.getCarteJoueurMain().get(numCarte));
            joueurChoisi.getCarteJoueurMain().remove(joueurChoisi.getCarteJoueurMain().get(numCarte));// retire la carte du jeu de la personne qui accuse

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.EvilEye)){
            Joueur joueurChoisi = this.choisirJoueur();
            joueurChoisi.setTour(true);
            joueurChoisi.setEvilEye(true);
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.PetNewt)){

            boolean check = false;
            Joueur joueurChoisi = this.choisirJoueur();
            if (joueurChoisi.carteJoueurPlateau.size() == 0){
                System.out.println("Vous ne pouvez pas choisir ce joueur car il n'a pas encore révéler de cartes rumeurs ");
                this.utiliserEffet = false;
            }
            System.out.println(" Veuillez choisir le numero de la carte");
            for (int i=0; i < joueurChoisi.getCarteJoueurPlateau().size();i++){
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
            this.carteJoueurMain.add(joueurChoisi.getCarteJoueurPlateau().get(numCarte-1));
            joueurChoisi.getCarteJoueurPlateau().remove(joueurChoisi.getCarteJoueurPlateau().get(numCarte-1));
            joueurChoisi.setTour(true);

        }
        if (carteRumeur.getNomCarte().equals(NomCarte.DuckingStool)){
            boolean check = false;
            Joueur joueurChoisi = this.choisirJoueur();

            while(check == false){
                boolean checkCarte = true;
                for (int i =0; i < joueurChoisi.getCarteJoueurPlateau().size(); i++ ){
                    if(joueurChoisi.getCarteJoueurPlateau().get(i).getNomCarte() == NomCarte.Wart){
                        checkCarte = false;
                    }
                }
                if (checkCarte == false){
                    System.out.println("Mauvais saisir. Veuillez choisir autre joueur ");
                    joueurChoisi = this.choisirJoueur();
                }
                else{
                    check = true;
                }

            }
            int choice = joueurChoisi.repondreEffetHuntDuckingStool();
            boolean verifierID;
            if (choice == 1){
                verifierID = joueurChoisi.revelerId();
                if (verifierID){
                    this.setPoints(this.getPoints()+1);
                    this.setTour(true);
                }
                else{
                    joueurChoisi.setTour(true);

                    if(this.getPoints()>0){
                        this.setPoints(this.getPoints()-1);
                    }

                }
            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Cauldron)){
            boolean verifierID;
            verifierID = this.revelerId();
            if (!verifierID){
                Joueur joueurChoisi = this.choisirJoueur();
                joueurChoisi.setTour(true);
            }
            else{
                int indice = Partie.getInstance().getTabjoueur().indexOf(this);
                if (indice ==0){
                    int size = Partie.getInstance().getTabjoueur().size();
                    Partie.getInstance().getTabjoueur().get(size-1).setTour(true);
                }
                else{
                    Partie.getInstance().getTabjoueur().get(indice-1).setTour(true);

                }
            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.Toad)){
            boolean verifierID;
            verifierID = this.revelerId();
            if (verifierID){
                Joueur joueurChoisi = this.choisirJoueur();
                joueurChoisi.setTour(true);
            }
            else{
                int indice = Partie.getInstance().getTabjoueur().indexOf(this);
                if (indice ==0){
                    int size = Partie.getInstance().getTabjoueur().size();
                    Partie.getInstance().getTabjoueur().get(size-1).setTour(true);
                }
                else{
                    Partie.getInstance().getTabjoueur().get(indice-1).setTour(true);

                }
            }
        }
        if (carteRumeur.getNomCarte().equals(NomCarte.BlackCat)){
            if (Partie.getInstance().getCarteDiscarte().size() == 0){
                System.out.println("Vous ne pouvez pas utiliser cette carte ");
                this.utiliserEffet = false;
            }
            else{
                System.out.println("Ce sont des cartes jetés");
                ArrayList<CarteRumeur> carteDiscarte = Partie.getInstance().getCarteDiscarte();
                for (int i=0; i <carteDiscarte.size(); i++){
                    System.out.println(carteDiscarte.get(i).getNomCarte());
                }
                Scanner inputNumeroCarte = new Scanner(System.in);

                System.out.println("Tapper le numero de la carte que vous voulez ajouter a la main ");
                int numeroCarte = inputNumeroCarte.nextInt();
                while (numeroCarte > carteDiscarte.size() || numeroCarte < 1){
                    System.out.println("Mauvais saisir. Veuillez resaisir le numero de la carte");
                    numeroCarte = inputNumeroCarte.nextInt();
                }
                this.getCarteJoueurMain().add(carteDiscarte.get(numeroCarte));
                carteDiscarte.remove(carteDiscarte.get(numeroCarte));

                carteDiscarte.add(carteRumeur);
                this.getCarteJoueurPlateau().remove(carteRumeur);





            }
        }



    }

    public void repondreAccusation() { //Méthode décrivant une des actions que peut faire le joueur si ce dernier est accusé : Si le joueur est accusé il peut révéler son identité
        if(this.estVirtuel==false) {
            if (this.estAccuse == true) {

                System.out.println("Joueur " + getNom());

                if (carteJoueurMain.size() == 0) {
                    this.revelerId();
                } else {
                    Scanner inputAction = new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
                    System.out.println("Quelle action voulez vous faire ? :");
                    System.out.println("- R pour réveler votre identité `\n - W pour utiliser l'effet Witch");
                    char action = inputAction.nextLine().charAt(0); //Lis la valeur saisie par l'utilisateur

                    while (action != 'R' && action != 'W') { //condition d'erreur
                        System.out.println("Mauvaise saisie, veuillez saisir R ou W\n");
                        action = inputAction.nextLine().charAt(0);
                    }

                    if (action == 'R') {
                        this.revelerId();
                    } else if (action == 'W') {
                        System.out.println("Ce sont vos cartes ");
                        for (int i = 0; i < this.carteJoueurMain.size(); i++) {
                            System.out.println(this.carteJoueurMain.get(i).getNomCarte());
                        }
                        Scanner inputCarteR = new Scanner(System.in);
                        while (this.utiliserEffet == false) {
                            this.utiliserEffet = true;
                            System.out.println("Quelle carte voulez vous jouer ? : Veuillez saisir le numéro de la carte");
                            int numCarteR = inputCarteR.nextInt();
                            while (numCarteR > carteJoueurMain.size() || numCarteR < 1) { //tant qu'on ne trouve pas le nom de la carte saisie, on incrémente j
                                System.out.println("Erreur, veuillez ressasir le nom de la carte");
                                numCarteR = inputCarteR.nextInt();

                            }
                            this.jouerCarte(carteJoueurMain.get(numCarteR - 1));
                        }
                    }
                }

            }
            this.setEstAccuse(false);
        }

        else{
            int choixAction = (int) (Math.random() * 1);
            if(choixAction==1){
                System.out.println("Le joueur virtuel " + this.getNom() + "révèle son identité.");
                this.revelerId();
            }
            else{
                System.out.println("Le joueur virtuel " + this.getNom() + " utilise un effet Witch.");
                int choixCarte=(int) (Math.random() * this.getCarteJoueurMain().size());
                this.effetWitch(this.carteJoueurMain.get(choixCarte));
            }
        }

    }

    public void commencerTour() {
        if (this.estVirtuel == false) {
            if (carteJoueurMain.size() == 0) {
                this.accuser();
            } else if (this.isEvilEye() == true) {
                System.out.println(" Vous êtes obligé d'accuser ");
                Joueur joueurChoisi = this.choisirJoueur();
                if (Partie.getInstance().getTabjoueur().size() >= 3) {
                    while (joueurChoisi.personneUtiliseEvilEye == true) {
                        System.out.println("Mauvais saisir. Vous ne pouvez pas accuser cette personne");
                        joueurChoisi = this.choisirJoueur();
                    }
                    joueurChoisi.setEstAccuse(true);
                    joueurChoisi.repondreAccusation();
                } else {
                    joueurChoisi.setEstAccuse(true);

                    joueurChoisi.repondreAccusation();

                }
                this.setEvilEye(false);
                for (int i = 1; i < Partie.getInstance().getTabjoueur().size(); i++) {
                    Partie.getInstance().getTabjoueur().get(i).personneUtiliseEvilEye = false;
                }
            } else {
                Scanner inputAction = new Scanner(System.in); //création d'un objet de type scanner pour récupérer les inputs de l'utilisateur
                System.out.println("Quelle action voulez vous faire ? : \n - A pour accuser un joueur \n - H pour utiliser le Hunt effect");
                char action = inputAction.nextLine().charAt(0);

                while (action != 'A' && action != 'H') { //condition d'erreur
                    System.out.println("Mauvaise saisie : Quelle action voulez vous faire ? : \n - A pour accuser un joueur \n - H pour utiliser le Hunt effect");
                    action = inputAction.nextLine().charAt(0);

                }

                if (action == 'A') {
                    this.accuser();
                } else if (action == 'H') {
                    System.out.println("Ce sont vos cartes ");
                    for (int i = 0; i < this.carteJoueurMain.size(); i++) {
                        System.out.println(this.carteJoueurMain.get(i).getNomCarte());
                    }
                    boolean check = false;
                    Scanner inputNumeroCarte = new Scanner(System.in);
                    this.utiliserEffet = false;
                    while (this.utiliserEffet == false) {
                        this.utiliserEffet = true;
                        System.out.println("Entrer le numero de la carte que vous voulez utiliser");
                        int numCarte = inputNumeroCarte.nextInt();
                        while (numCarte < 0 || numCarte > this.carteJoueurMain.size()) {//Condition d'erreur
                            System.out.println("Mauvaise saisie Veuillez saisir un autre numero de carte ");
                            numCarte = inputNumeroCarte.nextInt();
                        }
                        this.jouerCarte(this.getCarteJoueurMain().get(numCarte - 1));
                    }

                }

            }
            this.setTour(false);
            boolean check = false;
            for (int i = 0; i < Partie.getInstance().getTabjoueur().size(); i++) {
                if (Partie.getInstance().getTabjoueur().get(i).isTour()) {
                    check = true;
                }
            }
            if (check == false) {
                int indice = Partie.getInstance().getTabjoueur().indexOf(this);
                if (indice == Partie.getInstance().getTabjoueur().size() - 1) {
                    Partie.getInstance().getTabjoueur().get(0).setTour(true);
                } else {
                    Partie.getInstance().getTabjoueur().get(indice + 1).setTour(true);
                }
            }
        }
        else {
            if (carteJoueurMain.size() == 0) {
                this.accuser();
            } else if (this.isEvilEye() == true) {
                System.out.println("Le joueur virtuel " + this.getNom() + " est obligé d'accuser un autre joueur.");
                this.accuser();
            }
        }
    }


        public static void main (String[] args){

        }

    }