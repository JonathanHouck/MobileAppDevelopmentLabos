package be.howest.nmct.evaluationstudents.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import be.howest.nmct.evaluationstudents.admin.ModulePunt;
import be.howest.nmct.evaluationstudents.admin.Student;
import be.howest.nmct.evaluationstudents.data.StudentAdmin;

/**
 * Created by Jonathan on 9/04/2015.
 */
public class ModulePuntLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private String sEmailStudent;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.ModulePuntColumns.COLUMN_MODULE_NAAM,
            Contract.ModulePuntColumns.COLUMN_MODULE_SCORE,
            Contract.ModulePuntColumns.COLUMN_MODULE_STUDIEPUNTEN
    };

    private static Object lock = new Object();

    public ModulePuntLoader(Context context, String sEmailStudent) {
        super(context);
        this.sEmailStudent = sEmailStudent;
    }

    @Override
    protected void onStartLoading() {
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
            loadCursor();
        }

        return mCursor;
    }

    private void loadCursor() {

        synchronized (lock) {
            if (mCursor != null) return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;

            for (ModulePunt modulePunt : StudentAdmin.getStudent(sEmailStudent).getScoresStudent().values()) {
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(modulePunt.getModule());
                row.add(modulePunt.getScore());
                row.add(modulePunt.getAantalStudiePunten());
                id++;
            }
            mCursor = cursor;
        }
    }
}
