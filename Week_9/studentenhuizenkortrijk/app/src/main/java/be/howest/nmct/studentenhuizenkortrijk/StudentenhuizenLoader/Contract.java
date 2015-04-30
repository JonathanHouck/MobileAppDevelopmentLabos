package be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader;

import android.provider.BaseColumns;

/**
 * Created by Jonathan on 23/04/2015.
 */
public final class Contract {
    public interface StudentenhuisColumns extends BaseColumns {
        public static final String COLUMN_STUDENTENHUIS_STRAAT = "studentenhuis_straat";
        public static final String COLUMN_STUDENTENHUIS_HUISNUMMER = "studentenhuis_huisnummer";
        public static final String COLUMN_STUDENTENHUIS_GEMEENTE = "studentenhuis_gemeente";
        public static final String COLUMN_STUDENTENHUIS_AANTALKAMERS = "studentenhuis_aantalkamers";
    }
}
