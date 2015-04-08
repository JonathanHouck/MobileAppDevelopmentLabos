package be.howest.nmct.courses.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stijn on 23/03/2015.
 */
public class Module {

    private int semester;
    private String naam;
    private List<Docent> docentList = new ArrayList<>();

    public Module(String naam, int semester) {
        this.naam = naam;
        this.semester = semester;
    }

    public String getNaam() {
        return naam;
    }

    public int getSemester() {
        return semester;
    }

    public List<Docent> getDocentList() {
        return docentList;
    }

    public int getAantalDocenten(){
        return docentList.size();
    }

    @Override
    public String toString() {
        return "Cursus " + naam;
    }
}
