import java.util.*;

public class Partie {

    private int nbrJoueur, round;
    private static ArrayList<Joueur> tabjoueur, tabjoueurSup;
    private JeuCarte jeuCarte;
    private ArrayList<CarteRumeur> carteDiscarte;
    private static Partie instance;

    public static void ajouterJoueurReels(int refJoueur) {
        tabjoueur.add(new Joueur(refJoueur));
    }

    public static void ajouterJoueurVirtuels(int refJoueur) {
        tabjoueur.add(new JoueurVirtuel(refJoueur));
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Joueur> getTabjoueurSup() {
        return tabjoueurSup;
    }

    public void setTabjoueurSup(ArrayList<Joueur> tabjoueurSup) {
        this.tabjoueurSup = tabjoueurSup;
    }

    public void supprimerJoueur(Joueur joueur) {
        tabjoueur.remove(joueur);
    }

    public static Partie getInstance() { //Méthode pour le patron de conception Singleton
        if (instance == null) {
            instance = new Partie();
        }
        return instance;
    }

    private Partie() { //constructeur de la classe Partie
        this.tabjoueur = new ArrayList<Joueur>(); //initialisation de la collection de Joueur
        jeuCarte = new JeuCarte(); //initialisation de la collection de Carte -> toutes les cartes présentes dans une partie
        carteDiscarte = new ArrayList<CarteRumeur>();
        tabjoueurSup = new ArrayList<Joueur>();

    }

    public ArrayList<CarteRumeur> getCarteDiscarte() {
        return carteDiscarte;
    }

    public void setCarteDiscarte(ArrayList<CarteRumeur> carteDiscarte) {
        this.carteDiscarte = carteDiscarte;
    }

    public void distribuerCartesRumours() {
        jeuCarte.melanger();
        while (jeuCarte.estVide() == false) {
            Iterator<Joueur> iterator = tabjoueur.iterator();
            if (tabjoueur.size() == 5 && jeuCarte.getDeckCarteRumeurs().size() == 2) {
                CarteRumeur carteRumeur = jeuCarte.distribuerUneCarte();
                carteDiscarte.add(carteRumeur);
            } else {
                while (iterator.hasNext()) {
                    CarteRumeur carteRumeur = jeuCarte.distribuerUneCarte();
                    iterator.next().ramasserCarte(carteRumeur);
                }
            }
        }
    }

    public Joueur chercherJoueur(String nom) { //méthode pour rechercher le nom d'un joueur parmis ceux déjà existants
        int indiceJoueur = 0;
        boolean check = false; // la variable check permet de voir si le nom est présent dans le tableau de joueur.
        while (check == false) {
            indiceJoueur = 0;
            while (indiceJoueur < tabjoueur.size() && !tabjoueur.get(indiceJoueur).getNom().equals(nom)) {
                indiceJoueur++;
            }
            if (indiceJoueur == tabjoueur.size()) {
                check = false;
            } else {
                check = true;
            }
            if (!check) { //Si le prénom n'est pas présent dans le tableau de Joueur, on demnde à l'utilisateur de ressaisir le nom.
                Scanner scanner = new Scanner(System.in);
                System.out.println("  nom est faux. Veuillez resaisir le nom ");
                nom = scanner.nextLine();

            }
        }

        return tabjoueur.get(indiceJoueur);
    }


    public ArrayList<Joueur> getTabjoueur() {
        return tabjoueur;
    }

    public void commencerJeu() {
        int i = 0;
        while (this.getTabjoueur().get(i).isTour() == false) {
            i += 1;
        }
        System.out.println("C'est le tour du joueur " + this.getTabjoueur().get(i).getNom());
        this.getTabjoueur().get(i).commencerTour();
    }

    public boolean terminerTour() {
        int compterPersonneRevelePasID = 0;
        for (int i = 0; i < this.getTabjoueur().size(); i++) {
            if (this.getTabjoueur().get(i).isIdEstRevele() == false) {
                compterPersonneRevelePasID += 1;
            }
        }
        if (compterPersonneRevelePasID == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Joueur joueurGagnantTour() {
        int indiceJoueurGagnant = 0;
        while (this.getTabjoueur().get(indiceJoueurGagnant).isIdEstRevele() == true) {
            indiceJoueurGagnant = indiceJoueurGagnant + 1;
        }
        Joueur joueurGagnant = this.getTabjoueur().get(indiceJoueurGagnant);
        if (joueurGagnant.getIdentite().equals(Role.Villager)) {
            joueurGagnant.setPoints(joueurGagnant.getPoints() + 1);
        } else {
            joueurGagnant.setPoints(joueurGagnant.getPoints() + 2);
        }

        return this.getTabjoueur().get(indiceJoueurGagnant);
    }

    public void annoncerLeGagnantDuTour() {
        Joueur joueurGagnant = this.joueurGagnantTour();
        System.out.println(" Le joueur " + joueurGagnant.getNom() + " a gagné ce tour ");
    }

    public void preparerNouveauTour() {
        int i = 0;
        while (this.getTabjoueurSup().size() != 0) {
            this.getTabjoueur().add(this.getTabjoueurSup().get(i));
            this.getTabjoueurSup().remove(this.getTabjoueurSup().get(i));
        }
        for (int j = 0; j < this.getTabjoueur().size(); i++) {
            this.getTabjoueur().get(j).setTour(false);
            this.getTabjoueur().get(j).getCarteJoueurMain().clear();
            this.getTabjoueur().get(j).getCarteJoueurPlateau().clear();
        }

        Joueur joueurGagnant = this.joueurGagnantTour();
        joueurGagnant.setTour(true);
        JeuCarte jeuCarte = new JeuCarte();
    }

    public boolean terminerJeu() {
        boolean check = true;
        for (int i = 0; i < this.getTabjoueur().size(); i++) {
            if (this.getTabjoueur().get(i).getPoints() < 5) {
                check = false;
            }
        }
        if (check) {
            return true;
        } else {
            return false;
        }
    }

    public Joueur joueurGagnantJeu() {
        int pointJoueurGagnant = this.getTabjoueur().get(0).getPoints();
        int indiceJoueurGagnant = 0;
        for (int i = 1; i < this.getTabjoueur().size(); i++) {
            if (this.getTabjoueur().get(i).getPoints() > pointJoueurGagnant) {
                pointJoueurGagnant = this.getTabjoueur().get(i).getPoints();
                indiceJoueurGagnant = i;
            }
        }
        return this.getTabjoueur().get(indiceJoueurGagnant);
    }

    public void annonceLeGagnantJeu() {
        Joueur joueurGagnantJeu = this.joueurGagnantJeu();
        System.out.println(" Le joueur " + joueurGagnantJeu.getNom() + " a gagné  ");
    }


    public static void main(String[] args) {

        Partie witchHunt = Partie.getInstance(); //crée une partie
        int nbJoueurR = 0;
        int nbJoueurV = 0;
        while(nbJoueurR+nbJoueurV<3) {
            //Etape pour ajouter des joueurs réels
            Scanner inputNbJR = new Scanner(System.in);
            System.out.println("Nombre de joueurs réels");
            nbJoueurR = inputNbJR.nextInt();


            for (int i = 0; i < nbJoueurR; i++) {
                ajouterJoueurReels(i);
                Scanner inputNom = new Scanner(System.in);
                System.out.println("Saisir un nom pour le joueur " + i);
                String nomJoueurReel = inputNom.nextLine();
                tabjoueur.get(i).nom = nomJoueurReel;

            }
            //fin ajout joueurs réels

            //Etape pour ajouter des joueurs virtuels
            Scanner inputNbJV = new Scanner(System.in);
            System.out.println("Nombre de joueurs virtuels");
            nbJoueurV = inputNbJV.nextInt();

            int joueurRestant = 6 - nbJoueurR;

            while (nbJoueurV + nbJoueurR > 6) { //condition d'erreur pour le nb de joueur
                System.out.println("Nombre de Joueur trop grands. Veuillez saisir un nombre égal à " + joueurRestant);
                nbJoueurV = inputNbJV.nextInt();
            }

            for (int j = nbJoueurR; j < nbJoueurV + nbJoueurR; j++) {
                ajouterJoueurVirtuels(j);
                Scanner inputNomV = new Scanner(System.in);
                System.out.println("Saisir un nom pour le joueur " + j);
                String nomJoueurVirtuel = inputNomV.nextLine();
                tabjoueur.get(j).nom = nomJoueurVirtuel;
            }
            if(nbJoueurR + nbJoueurV < 3){
                System.out.println("Veuillez resaisir le nombre de joueur ");
            }
            //fin ajout joueurs virtuels
        }

        int refJoueur = 0;
        witchHunt.setRound(1);

        while (witchHunt.terminerJeu() == false) { //tant qu'un joueur n'a pas obtenu au moins 5 points

            refJoueur = 0;
            System.out.println("Round " + witchHunt.round);

            //choix du rôle pour chaque joueur
            for (int k = 0; k < nbJoueurV + nbJoueurR; k++) {
                System.out.println("Joueur " + tabjoueur.get(k).getNom());
                tabjoueur.get(k).choisirRole();
            }

            witchHunt.jeuCarte = new JeuCarte(); //on crée un nouveau jeu de carte


            witchHunt.distribuerCartesRumours();//distribuer les cartes

            witchHunt.getTabjoueur().get(refJoueur).setTour(true);

            while (witchHunt.terminerTour() == false) {
                witchHunt.commencerJeu();
            }
            int z = 0;
            while (witchHunt.getTabjoueurSup().size() != 0) {
                witchHunt.getTabjoueur().add(witchHunt.getTabjoueurSup().get(z));
                witchHunt.getTabjoueurSup().remove(witchHunt.getTabjoueurSup().get(z));
            }
            for (int j = 0; j < witchHunt.getTabjoueur().size(); j++) {
                tabjoueur.get(j).setTour(false);
            }
            for (int i = 0; i < tabjoueur.size(); i++) {
                if (tabjoueur.get(i).isIdEstRevele() == false) {
                    System.out.println("Joueur " + tabjoueur.get(i).getNom() + " a gagné ce tour");
                    if (tabjoueur.get(i).getIdentite().equals(Role.Villager)) {
                        tabjoueur.get(i).setPoints(tabjoueur.get(i).getPoints() + 1);
                    } else {
                        tabjoueur.get(i).setPoints(tabjoueur.get(i).getPoints() + 2);
                    }
                    tabjoueur.get(i).setTour(true);
                }
            }
            for (int j = 0; j < witchHunt.getTabjoueur().size(); j++) {
                witchHunt.getTabjoueur().get(j).setIdEstRevele(false);
                witchHunt.getTabjoueur().get(j).getCarteJoueurMain().clear();
                witchHunt.getTabjoueur().get(j).getCarteJoueurPlateau().clear();

            }

            for (int i = 0; i < tabjoueur.size(); i++) { //affichage des points de chaque joueur à la fin d'un round
                System.out.println(tabjoueur.get(i).getNom() + " vous avez : " + tabjoueur.get(i).getPoints() + " points.");
            }
            System.out.println("Fin du round " + witchHunt.getRound());
            witchHunt.setRound(witchHunt.getRound() + 1);

        }
        witchHunt.annonceLeGagnantJeu();
    }
}
