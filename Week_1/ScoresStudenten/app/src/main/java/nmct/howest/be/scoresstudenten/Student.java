package nmct.howest.be.scoresstudenten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class Student implements Comparable<Student> {

    //attributen//
    private String emailStudent;

    //scores van de student gaan bijhouden -> collectie van objecten van de klasse Modulepunt
    //de keys zijn string, de waarden van ModulePunt
    private HashMap<String, ModulePunt> scoresStudent = new HashMap<String, ModulePunt>();

    //getters en setters//
    public String getEmailStudent() {
        return emailStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    //contructor
    public Student(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    //functies om score toe te voegen
    //tweede functie maakt gebruik van overloading en heeft als extra paramters het aantal studiepunten
    public void voegScoreToe(String sModuleNaam, double score) {

        //maak een object van de klasse ModulePunt
        ModulePunt mp =  new ModulePunt(sModuleNaam, score);

        //voeg deze toe aan de collectie
        //controleeer eerst even of deze al aanwezig is
        if (!scoresStudent.containsKey(sModuleNaam)) {
            scoresStudent.put(sModuleNaam, mp);
        }
        scoresStudent.put(sModuleNaam, mp);
    }

    public void voegScoreToe(String sModuleNaam, double score, int aantalStudiePunten) {
        ModulePunt mp =  new ModulePunt(sModuleNaam, aantalStudiePunten, score);
        if (!scoresStudent.containsKey(sModuleNaam)) {
            scoresStudent.put(sModuleNaam, mp);
        }
        scoresStudent.put(sModuleNaam, mp);
    }

    //berekenen van de totale score van een student
    public double getTotaleScoreStudent() {

        int somStudiePunten = 0;

        //keySet itereert(herhaalt) over de sleutels van elke foreach lus
        for(String key : scoresStudent.keySet())
        {
            //van een bepaalde waarde in de collectie het aantal studiepunten verkrijgen
            //hier gebruiken we de getter, we kunnen niet .AantalStudiePunten doen omdat we niet in de klasse ModulePunt zitten
            somStudiePunten += scoresStudent.get(key).getAantalStudiePunten();
        }

        double totaalPercentage = 0.0f;
        for(String key : scoresStudent.keySet())
        {
            //gewicht van de module berekenen (aantal studiepunten gedeelt door alle studiepunten)
            double moduleGewicht = (double) scoresStudent.get(key).getAantalStudiePunten() / (double) somStudiePunten;

            //score vermenigvuldigen met gewicht van de module, alle verkregen producten optellen
            totaalPercentage += scoresStudent.get(key).getScore() * moduleGewicht;
        }

        return totaalPercentage;
    }

    //toString-methode
    @Override
    public String toString() {
        return emailStudent + " - Totale score: " + getTotaleScoreStudent();
    }

    //totale score van studenten vergelijken
    @Override
    public int compareTo(Student another) {
        //indien zelfde totale score --> 0 terug geven
        //indien kleinere totale score dan andere student --> negatief getal teruggeven
        //indien grotere totale score dan andere student --> positief getal teruggeven

        if (this.getTotaleScoreStudent() == another.getTotaleScoreStudent()) {
            return 0;
        } else if (this.getTotaleScoreStudent() < another.getTotaleScoreStudent()) {
            return -1;
        } else {
            return 1;
        }
    }

    //3 static methodes

    //van elke student, voor een bepaalde module, de score in een lijst plaatsen
    public static List<Double> getScoresModule(List<Student> studenten, String moduleNaam) {

        //doordat List een interface is, kan het niet direct geïnititaliseerd worden,
        //hierdoor gebruiken we ArrayList die een implementatie van de interface is
        //door ArrayList te upcasten, maken we gebruik van een List (upcasten: bv. van Student naar Object)
        //http://stackoverflow.com/questions/6810691/instantiating-a-list-in-java
        List<Double> alleResultaten = new ArrayList<Double>();

        //for (int i = 0 ; i < studenten.size() ; i++) {}
        for (Student student : studenten) {

            //hier hebben we geen getters geschreven om de totale score van de student op te halen
            //omdat we in de klasse van de student zelf zitten kunnen we scoresStudent aanspreken
            ModulePunt modulePunt = student.scoresStudent.get(moduleNaam);

            alleResultaten.add(modulePunt.getScore());

        }
        return alleResultaten;
    }

    //van elke student, voor een bepaalde module, de score optellen en hiervan het gemiddelde nemen
    public static double getGemiddeldeScoreModule(List<Student> studenten, String moduleNaam) {
        double totaal = 0;

        for (Student student : studenten) {
            ModulePunt modulePunt = student.scoresStudent.get(moduleNaam);
            totaal += modulePunt.getScore();
        }

        return totaal / studenten.size();
    }

    static final Comparator<Student> TOTALE_SCORE_ORDER = new Comparator<Student>() {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.compareTo(s2);
        }
    };

    public static void sorteerStudenten(List<Student> studenten) {
        Collections.sort(studenten, TOTALE_SCORE_ORDER);
    }
}
