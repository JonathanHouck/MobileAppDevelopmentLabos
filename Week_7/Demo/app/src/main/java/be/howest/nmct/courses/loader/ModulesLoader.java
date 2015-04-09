package be.howest.nmct.courses.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import be.howest.nmct.courses.model.Module;
import be.howest.nmct.courses.provider.ModuleProvider;

/**
 * Created by Stijn on 23/03/2015.
 */
public class ModulesLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.ModuleColumns.COLUMN_MODULE_NAAM,
            Contract.ModuleColumns.COLUMN_SEMESTER,
            Contract.ModuleColumns.COLUMN_AANTAL_DOCENTEN};


    private static Object lock = new Object();

    public ModulesLoader(Context context) {
        super(context);
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
            for(Module module : ModuleProvider.getModules2NMCT()){
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(module.getNaam());
                row.add(module.getSemester());
                row.add(module.getAantalDocenten());
                id++;
            }
            mCursor = cursor;
        }
    }

}

