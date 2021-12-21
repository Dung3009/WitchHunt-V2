import java.util.*;


public class CarteRumeur  {
    private NomCarte nomCarte;
    private int refCarte;
    public CarteRumeur(NomCarte nomCarte, int refCarte){
        this.nomCarte = nomCarte;
        this.refCarte = refCarte;

    }

    public int getRefCarte() {
        return refCarte;
    }

    public void setRefCarte(int refCarte) {
        this.refCarte = refCarte;
    }

    public NomCarte getNomCarte() {
        return nomCarte;
    }

    public void setNomCarte(NomCarte nomCarte) {
        this.nomCarte = nomCarte;
    }
}