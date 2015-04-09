package be.howest.nmct.courses;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import be.howest.nmct.courses.R;
import be.howest.nmct.courses.loader.Contract;
import be.howest.nmct.courses.loader.DocentenLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocentenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ARG_MODULE_NAAM = "module_naam";

    private TextView textViewDetailNaamModule;
    private GridView gridViewDocenten;

    private CursorAdapter mAdapter;

    public DocentenFragment() {
        // Required empty public constructor
    }

    public static DocentenFragment newInstance(String sNaamModule) {
        DocentenFragment fragment = new DocentenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODULE_NAAM, sNaamModule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_docenten, container, false);

        textViewDetailNaamModule = (TextView) v.findViewById(R.id.textViewDetailNaamModule);
        gridViewDocenten = (GridView) v.findViewById(R.id.gridViewDocenten);
        textViewDetailNaamModule.setText(getArguments().getString(ARG_MODULE_NAAM));

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] columns = new String[]{Contract.DocentColumns.COLUMN_NAAM, Contract.DocentColumns.COLUMN_VOORNAAM, Contract.DocentColumns.COLUMN_EMAIL};
        int[] viewIds = new int[]{R.id.textViewNaamDocent, R.id.textViewVoornaamDocent, R.id.textviewEmailDocent};

        mAdapter = new DocentenAdaptar(getActivity(), R.layout.cel_docent, null, columns, viewIds, 0);
        gridViewDocenten.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DocentenLoader(getActivity(), getArguments().getString(ARG_MODULE_NAAM));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    class DocentenAdaptar extends SimpleCursorAdapter {

        private int layout;

        public DocentenAdaptar(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            TextView textviewEmailDocent = (TextView) view.findViewById(R.id.textviewEmailDocent);

            int colnr1 = cursor.getColumnIndex(Contract.DocentColumns.COLUMN_EMAIL);
            String emailDocent = cursor.getString(colnr1);
            if (emailDocent.isEmpty()) {
                emailDocent = "Geen emailadres gekend";
                textviewEmailDocent.setTextColor(Color.RED);
            }
            textviewEmailDocent.setText(emailDocent);

        }
    }

}
