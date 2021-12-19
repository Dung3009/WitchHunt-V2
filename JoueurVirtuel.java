import java.util.Scanner;

public class JoueurVirtuel extends Joueur implements Strategie  {


    public JoueurVirtuel(int refJoueur){ //constructeur de JoueurVirtuel
        super(refJoueur);
        this.estVirtuel=true;
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

    public void commencerTour(){
        if (carteJoueurMain.size() == 0) {
            this.accuser();
        } else if (this.isEvilEye() == true) {
            System.out.println("Le joueur virtuel " +this.getNom()+" accuse un autre joueur.");
            this.aggressif();
        }

    }

}
