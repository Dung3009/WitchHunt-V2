import java.util.Scanner;

public class JoueurVirtuel extends Joueur implements Strategie  {


    public JoueurVirtuel(int refJoueur){ //constructeur de JoueurVirtuel
        super(refJoueur);
        this.estVirtuel=true;
    }

    public void accuserVirtuel(){
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

    public void commencerTourVirtuel(){
        if (carteJoueurMain.size() == 0) {
            this.accuserVirtuel();
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

}
