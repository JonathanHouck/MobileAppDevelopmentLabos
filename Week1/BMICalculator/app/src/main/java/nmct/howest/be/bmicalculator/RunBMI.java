package nmct.howest.be.bmicalculator;

import java.util.Scanner;

/**
 * Created by Jonathan on 14/02/2015.
 */
public class RunBMI {

    public static void main(String[] args) {
        /*BMIInfo info = new BMIInfo(1.7f, 70);
        System.out.println(info);*/

        System.out.println("Voer uw lengte in (in meter, voorbeeld: 1.72)");
        Scanner scanner = new Scanner(System.in);
        float height = scanner.nextFloat();

        System.out.println("Voer uw gewicht in (in kg, voorbeeld: 70)");
        int mass = scanner.nextInt();

        BMIInfo info2 = new BMIInfo(height, mass);
        System.out.println(info2);
    }
}
