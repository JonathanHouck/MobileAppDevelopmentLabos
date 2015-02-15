package nmct.howest.be.scoresstudenten;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class ModulePunt {

   //attributen//
    private String moduleNaam;
    private int aantalStudiepunten = 6;
    private double score;

    //getters en setters//
    public String getModuleNaam() {
        return moduleNaam;
    }

    public void setModuleNaam(String moduleNaam) {
        this.moduleNaam = moduleNaam;
    }

    public int getAantalStudiePunten() {
        return aantalStudiepunten;
    }

    public void setAantalStudiepunten(int aantalStudiepunten) {
        this.aantalStudiepunten = aantalStudiepunten;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    //constructoren//
    public ModulePunt(String moduleNaam, int aantalStudiepunten, double score) {
        this.moduleNaam = moduleNaam;
        this.aantalStudiepunten = aantalStudiepunten;
        this.score = score;
    }

    public ModulePunt(String moduleNaam, double score) {
        this.score = score;
        this.moduleNaam = moduleNaam;
    }

    //toString-methode
    @Override
    public String toString() {
        return "ModulePunt{" +
                "moduleNaam='" + moduleNaam + '\'' +
                ", aantalStudiepunten=" + aantalStudiepunten +
                ", score=" + score +
                '}';
    }


    //equals- & hashCode-methode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModulePunt that = (ModulePunt) o;

        if (!moduleNaam.equals(that.moduleNaam)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return moduleNaam.hashCode();
    }
}
