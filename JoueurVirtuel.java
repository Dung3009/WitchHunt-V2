public class JoueurVirtuel extends Joueur implements Strategie  {

    private boolean estVirtuel;

    public JoueurVirtuel(int refJoueur){ //constructeur de JoueurVirtuelle
        super(refJoueur);
        this.estVirtuel=true;
    }

    public void aggressif(){
        if(this.estVirtuel) {
            while (tour == true) {
                this.accuser();
            }
        }
    }

    public void strategique(){
        if(this.estVirtuel) {
            int stratAlea = (int) (Math.random() * 1); //donne 0 ou 1 de manière aléatoire
            if (stratAlea == 0) {
                this.accuser();
            } else {
                int carteAlea = (int) (Math.random() * carteJoueurMain.size()); //nombre aléatoire entre 0 et le nombre total de carte présentes dans la main du joueur
                this.efffetHunt(carteJoueurMain.get(carteAlea));
            }
        }
    }

}
