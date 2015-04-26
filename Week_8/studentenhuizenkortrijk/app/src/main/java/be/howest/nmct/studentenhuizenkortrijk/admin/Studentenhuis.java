package be.howest.nmct.studentenhuizenkortrijk.admin;

import java.util.List;

/**
 * Created by Jonathan on 26/04/2015.
 */
public class Studentenhuis {

    private String adres;
    private String huisnummer;
    private String gemeente;
    private int aantalKamers;

    public Studentenhuis(String adres, String huisnummer, String gemeente, int aantalKamers) {
        this.adres = adres;
        this.huisnummer = huisnummer;
        this.gemeente = gemeente;
        this.aantalKamers = aantalKamers;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public int getAantalKamers() {
        return aantalKamers;
    }

    public void setAantalKamers(int aantalKamers) {
        this.aantalKamers = aantalKamers;
    }
}