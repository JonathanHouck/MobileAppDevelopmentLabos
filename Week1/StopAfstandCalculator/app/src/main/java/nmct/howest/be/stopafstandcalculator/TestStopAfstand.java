package nmct.howest.be.stopafstandcalculator;

import java.util.Scanner;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class TestStopAfstand {
    public static void main(String[] args) {

        System.out.println("Demo oefening 1");
        Voertuig v1 = new Voertuig(Voertuig.MerkVoertuig.OPEL, 2014);
        System.out.println(v1);

        System.out.println("Geef een getal op: ");

        //opvragen van data via de klasse Scanner
        Scanner scanner = new Scanner(System.in);
        int getal = scanner.nextInt();
        System.out.println("Getal is:" + getal);

        System.out.println("Geef een decimaal getal op: ");
        float decimaalgetal = scanner.nextFloat();
        System.out.println("Decimaal getal is: " + decimaalgetal);
    }
}
