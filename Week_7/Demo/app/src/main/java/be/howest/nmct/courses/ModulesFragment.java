package be.howest.nmct.courses;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.howest.nmct.courses.loader.Contract;
import be.howest.nmct.courses.loader.ModulesLoader;
import be.howest.nmct.courses.model.Docent;
import be.howest.nmct.courses.provider.ModuleProvider;

/**
 * Created by Stijn on 23/03/2015.
 */
public class ModulesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ModuleAdaptar mAdapter;

    private OnModulesFragmentListener mListener;

    public ModulesFragment(){}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnModulesFragmentListener) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnModulesFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modules, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] columns = new String[]{Contract.ModuleColumns.COLUMN_MODULE_NAAM,
                Contract.ModuleColumns.COLUMN_SEMESTER, Contract.ModuleColumns.COLUMN_AANTAL_DOCENTEN};
        int[] viewIds = new int[]{R.id.textViewModulenaam, R.id.textviewSemester, R.id.textViewAantalDocenten};

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new ModuleAdaptar(getActivity(),R.layout.row_module, null,columns, viewIds, 0);
        setListAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new ModulesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = (Cursor)mAdapter.getItem(position);
        String selectedModule = c.getString(c.getColumnIndex(Contract.ModuleColumns.COLUMN_MODULE_NAAM));
        if (mListener!=null) mListener.onSelectModule(selectedModule);

    }

    class ModuleAdaptar extends SimpleCursorAdapter {

        private int layout;

        public ModuleAdaptar(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent, false);
            TextView textViewNaamModule = (TextView) row.findViewById(R.id.textViewModulenaam);


            int colnr = cursor.getColumnIndex(Contract.ModuleColumns.COLUMN_SEMESTER);
            int semester = cursor.getInt(colnr);


            if (semester == 3){
                textViewNaamModule.setTextColor(Color.parseColor("#FFBB33"));
            } else if (semester == 4){
                textViewNaamModule.setTextColor(Color.parseColor("#33B5E5"));
            }


            return row;
        }
    }

    public interface OnModulesFragmentListener {
        public void onSelectModule(String sNaamModule);
    }


}
