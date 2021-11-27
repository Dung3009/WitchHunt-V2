import java.util.LinkedList;
import java.util.*;

public class JeuCarte {

    private LinkedList<CarteRumeur> deckCarteRumeurs;
    public static final int nbrCarte = 12;

    public JeuCarte(){
        deckCarteRumeurs = new LinkedList<CarteRumeur>();
        NomCarte[] nomCartes = NomCarte.values();
        for (int i = 0; i < nomCartes.length; i++){
            CarteRumeur carteRumeur = new CarteRumeur(nomCartes[i]);
            deckCarteRumeurs.add(carteRumeur);
        }

    }
    public void melanger(){
        Collections.shuffle(deckCarteRumeurs);
    }
    public boolean estVide(){
        return deckCarteRumeurs.isEmpty();
    }

    @Override
    public String toString() {
        return "JeuCarte{" +
                "deckCarteRumeurs=" + deckCarteRumeurs +
                '}';
    }

    public CarteRumeur distribuerUneCarte(){
        return deckCarteRumeurs.poll();
    }
    public LinkedList<CarteRumeur> getDeckCarteRumeurs(){
        return deckCarteRumeurs;
    }
}
