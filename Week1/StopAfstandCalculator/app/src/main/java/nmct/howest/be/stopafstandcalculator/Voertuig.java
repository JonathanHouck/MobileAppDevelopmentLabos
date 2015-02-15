package nmct.howest.be.stopafstandcalculator;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class Voertuig {
    public static enum MerkVoertuig {OPEL, VOLKSWAGEN, PEUGOT, MERCEDES}

    private MerkVoertuig merk;
    private int bouwjaar;

    public Voertuig(MerkVoertuig merk, int bouwjaar) {
        this.merk = merk;
        this.bouwjaar = bouwjaar;
    }

    public MerkVoertuig getMerk() {
        return merk;
    }

    public void setMerk(MerkVoertuig merk) {
        this.merk = merk;
    }

    public int getBouwjaar() {
        return bouwjaar;
    }

    public void setBouwjaar(int bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    @Override
    public String toString() {
        return "Voertuig{" +
                "merk=" + merk +
                ", bouwjaar=" + bouwjaar +
                '}';
    }
}
