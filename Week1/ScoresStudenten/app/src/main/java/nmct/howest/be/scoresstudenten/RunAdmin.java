package nmct.howest.be.scoresstudenten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jonathan on 14/02/2015.
 */
public class RunAdmin {
    public static void main(String[] args) {

        //HashMap<String, ModulePunt> mapperJonathan = new HashMap<String, ModulePunt>();
        Student Jonathan = new Student("jonathan.houck@student.howest.be");
        Jonathan.VoegScoreToe("Mobile App Development", 15);
        Jonathan.VoegScoreToe("New Media", 14);

        Student Nick = new Student("nick.spriet@student.howest.be");
        Nick.VoegScoreToe("Mobile App Development", 17);
        Nick.VoegScoreToe("New Media", 11);

        Student Stijn = new Student("stijn.deryckere@student.howest.be");
        Stijn.VoegScoreToe("Mobile App Development", 20);
        Stijn.VoegScoreToe("New Media", 10);

        /*List<Student> studenten = new ArrayList<Student>();
        studenten.add(Jonathan);
        studenten.add(Nick);
        studenten.add(Stijn);*/

        //korter
        List<Student> studenten = Arrays.asList(Jonathan, Nick, Stijn);

        String gekozenModule = "New Media";

        double gemiddelde = Student.getGemiddeldeScoreModule(studenten, gekozenModule);
        System.out.println("Gemiddelde van module " + gekozenModule + " zijn: " + gemiddelde);

        List<Double> scoresModule = Student.getScoresModule(studenten, gekozenModule);
        System.out.println("De scores voor de module " + gekozenModule + " zijn:");
        for (double score : scoresModule) {
            System.out.println(score);
        }

        System.out.println("Sorteer de studenten volgens hun totale score");
        Student.sorteerStudenten(studenten);
        for (Student student : studenten) {
            System.out.println(student.toString());
        }
    }
}
