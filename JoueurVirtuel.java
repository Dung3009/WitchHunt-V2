import java.util.Scanner;

public class JoueurVirtuel extends Joueur implements Strategie  {


    public JoueurVirtuel(int refJoueur){ //constructeur de JoueurVirtuel
        super(refJoueur);
        this.estVirtuel=true;
    }

    @Override
    public Joueur choisirJoueur() {
        int numJoueur = (int) (Math.random() * Partie.getInstance().getTabjoueur().size()); //choisis un numéro de joueur au hasard parmis tous les joueurs de la partie en cours
        return Partie.getInstance().getTabjoueur().get(numJoueur);
    }

    @Override
    public void choisirRole() {
        int numRole = (int) (Math.random()*2);
        if (numRole == 0){
            this.setIdentite(Role.Witch);
        }
        else{
            this.setIdentite(Role.Villager);
        }
    }
    

    @Override
    public void choisirCarteRumour() {
        int numCarteRumour = (int) (Math.random()*this.carteJoueurMain.size());
        while (this.utiliserEffet == false) {
            this.utiliserEffet = true;
            this.jouerCarte(carteJoueurMain.get(numCarteRumour));
            if (!this.utiliserEffet){
                numCarteRumour = (int) (Math.random()*this.carteJoueurMain.size());
                this.jouerCarte(carteJoueurMain.get(numCarteRumour));
            }
        }
    }



    @Override
    public void accuser() {
        this.setAccuse(true);
        int numJoueur = (int) (Math.random() * Partie.getInstance().getTabjoueur().size());
        Joueur joueurAccuse = Partie.getInstance().getTabjoueur().get(numJoueur);
        while (joueurAccuse.isIdEstRevele() == true) {
            numJoueur = (int) (Math.random() * Partie.getInstance().getTabjoueur().size());
            joueurAccuse = Partie.getInstance().getTabjoueur().get(numJoueur);
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
    @Override
    public void commencerTour() {
        if (carteJoueurMain.size() == 0) {
            this.accuser();
        } else if (this.isEvilEye() == true) {
            System.out.println("Le joueur virtuel " +this.getNom()+" accuse un autre joueur.");
            this.aggressif();
        }
        else{
            int choixStrat = (int)(Math.random()*1);
            if(choixStrat==1){
                this.aggressif();
            }
            else{
                this.strategique();
            }
        }
    }
    public void aggressif(){
        if(this.estVirtuel) {
            while (tour == true) {
                System.out.println("Le joueur virtuel"+ this.refJoueur + " accuse un autre Joueur");
                this.accuser();
            }
        }
    }
    public void strategique(){
        if(this.estVirtuel) {
            int stratAlea = (int) (Math.random() * 1); //donne 0 ou 1 de manière aléatoire
            if (stratAlea == 0) {
                System.out.println("Le joueur virtuel"+ this.refJoueur + " accuse un autre Joueur");
                this.accuser();
            }
            else {
                System.out.println("Le joueur virtuel"+ this.refJoueur + " utilise l'effet Hunt");
                int carteAlea = (int) (Math.random() * carteJoueurMain.size()); //nombre aléatoire entre 0 et le nombre total de carte présentes dans la main du joueur
                this.efffetHunt(carteJoueurMain.get(carteAlea));
            }
        }
    }




}
