package be.howest.nmct;

import be.howest.nmct.data.Data;

/**
 * Created by Jonathan on 7/04/2015.
 */
public class HoroscoopFuncties {
    public static int getResourceId(Data.Horoscoop horoscoop) {
        switch(horoscoop)
        {
            case WATERMAN:
                return R.drawable.waterman;
            case MAAGD:
                return R.drawable.maagd;
            case BOOGSCHUTTER:
                return R.drawable.boogschutter;
            case KREEFT:
                return R.drawable.kreeft;
            case LEEUW:
                return R.drawable.leeuw;
            case RAM:
                return R.drawable.ram;
            case SCHORPIOEN:
                return R.drawable.schorpioen;
            case STEENBOK:
                return R.drawable.steenbok;
            case TWEELING:
                return R.drawable.tweeling;
            case STIER:
                return R.drawable.stier;
            case VISSEN:
                return R.drawable.vissen;
            case WEEGSCHAAL:
                return R.drawable.weegschaal;
            default:
                return R.drawable.weegschaal;
        }
    }
}
