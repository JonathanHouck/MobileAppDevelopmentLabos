package be.howest.nmct.courses.loader;

import android.provider.BaseColumns;

/**
 * Created by Stijn on 23/03/2015.
 */
public class Contract {

    public interface ModuleColumns extends BaseColumns {
        public static final String COLUMN_MODULE_NAAM = "module_naam";
        public static final String COLUMN_SEMESTER = "module_semester";
        public static final String COLUMN_AANTAL_DOCENTEN = "aantal_docenten";
    }


    public interface DocentColumns extends BaseColumns {
        public static final String COLUMN_NAAM = "docent_naam";
        public static final String COLUMN_VOORNAAM = "docent_voornaam";
        public static final String COLUMN_EMAIL = "docent_email";
    }

}
