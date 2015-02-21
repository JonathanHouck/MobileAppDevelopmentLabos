package be.howest.nmct.stopafstand;

import java.util.Scanner;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class StopAfstandInfo {
    private int snelheid;
    private float reactietijd;
    private float stopafstand;
    private WegType wegtype;

    public StopAfstandInfo(int snelheid, float reactietijd, float stopafstand, WegType wegtype) {
        this.snelheid = snelheid;
        this.reactietijd = reactietijd;
        this.stopafstand = stopafstand;
        this.wegtype = wegtype;
    }

    public StopAfstandInfo() {}

    public static enum WegType {
        WEGDEK_DROOG, WEGDEK_NAT
    }

    public int getSnelheid() {
        return snelheid;
    }

    public void setSnelheid(int snelheid) {
        this.snelheid = snelheid;
    }

    public float getReactietijd() {
        return reactietijd;
    }

    public void setReactietijd(float reactietijd) {
        this.reactietijd = reactietijd;
    }

    public float getStopafstand() {
        return stopafstand;
    }

    public void setStopafstand(float stopafstand) {
        this.stopafstand = stopafstand;
    }

    public WegType getWegtype() {
        return wegtype;
    }

    public void setWegtype(WegType wegtype) {
        this.wegtype = wegtype;
    }

    public static float GetStopAfstand(StopAfstandInfo saf) {
        //float snelheidInMS = snelheid * 1000/3600;
        float reactieweg = GetSnelheidMS(saf.snelheid) * saf.reactietijd;
        float remweg = (GetSnelheidMS(saf.snelheid) * GetSnelheidMS(saf.snelheid)) / (2 * GetRemvertraging(saf.wegtype));

        return reactieweg + remweg;
    }

    public static float GetRemvertraging(WegType wt) {
        if (wt == WegType.WEGDEK_DROOG) {
            return  8;
        } else {
            return  5;
        }
    }

    public static float GetSnelheidMS (int snelheidInput) {
        return snelheidInput / (float) 3.6;
    }

    public static void main(String[] args) {
        System.out.println("Geef snelheid op");
        Scanner scanner = new Scanner(System.in);
        int snelheidInput = scanner.nextInt();

        System.out.println("Geef reactietijd op");
        float reactietijdInput = scanner.nextFloat();

        System.out.println("Geef wegtype op");
        String wegtypeInput = scanner.next();

        StopAfstandInfo saf = new StopAfstandInfo();
        saf.snelheid = snelheidInput;
        saf.reactietijd = reactietijdInput;

        if (wegtypeInput == "DROOG") {
            saf.wegtype = WegType.WEGDEK_DROOG;
        } else {
            saf.wegtype = WegType.WEGDEK_NAT;
        }

        float stopAfstand = StopAfstandInfo.GetStopAfstand(saf);
        System.out.println(stopAfstand);
    }
}