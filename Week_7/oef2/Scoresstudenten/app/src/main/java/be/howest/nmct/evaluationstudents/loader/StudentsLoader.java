package be.howest.nmct.evaluationstudents.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.List;

import be.howest.nmct.evaluationstudents.admin.Student;
import be.howest.nmct.evaluationstudents.data.StudentAdmin;

/**
 * Created by Jonathan on 8/04/2015.
 */
public class StudentsLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private Student.DIPLOMAGRAAD diplomagraad;

    private final String[] mColumnNames = new String[] {
            BaseColumns._ID,
            Contract.StudentColumns.COLUMN_STUDENT_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL };

    private static Object lock = new Object();

    public StudentsLoader(Context context) {
        super(context);
    }

    public StudentsLoader(Context context, Student.DIPLOMAGRAAD diplomagraad) {
        super(context);
        this.diplomagraad = diplomagraad;
    }

    @Override
    protected void onStartLoading() {
        //super.onStartLoading(); //deze lijn zorgt ervoor dat enkel de lijst wordt ingeladen, textview boven de lijst niet meer
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (mCursor == null) {
            if (diplomagraad == null) {
                loadCursor(StudentAdmin.getStudenten());
            } else {
                loadCursor(StudentAdmin.getStudenten(this.diplomagraad));
            }
        }

        return mCursor;
    }

    private void loadCursor(List<Student> studenten) {

        synchronized (lock) {
            if (mCursor != null) return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;

            for (Student student : studenten) {
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(student.getNaamStudent());
                row.add(student.getVoornaamStudent());
                row.add(student.getEmailStudent());
                row.add(student.getTotaleScoreStudent());
                id++;
            }
            mCursor = cursor;
        }
    }
}
