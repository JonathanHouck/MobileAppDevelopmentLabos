package be.howest.nmct.courses.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import be.howest.nmct.courses.model.Docent;
import be.howest.nmct.courses.provider.ModuleProvider;

/**
 * Created by Stijn on 30/03/2015.
 */
public class DocentenLoader  extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private String naamModule;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.DocentColumns.COLUMN_NAAM,
            Contract.DocentColumns.COLUMN_VOORNAAM,
            Contract.DocentColumns.COLUMN_EMAIL
    };

    private static Object lock = new Object();


    public DocentenLoader(Context context, String naamModule) {
        super(context);
        this.naamModule = naamModule;
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
            for (Docent docent : ModuleProvider.getDocentenModule(naamModule)) {
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(docent.getLastName());
                row.add(docent.getFirstname());
                row.add(docent.getEmail());
                id++;
            }
            mCursor = cursor;
        }
    }

}
