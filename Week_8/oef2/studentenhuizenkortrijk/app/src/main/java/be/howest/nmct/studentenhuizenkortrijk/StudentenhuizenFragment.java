package be.howest.nmct.studentenhuizenkortrijk;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.StudentenhuizenLoader;

public class StudentenhuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mStudentenHuizenAdaptar;

    public StudentenhuizenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_studentenhuizen, container, false);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mStudentenHuizenAdaptar.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mStudentenHuizenAdaptar.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[] {
                Contract.StudentenHuis.COLUMN_STUDENTENHUIS_STRAAT,
                Contract.StudentenHuis.COLUMN_STUDENTENHUIS_GEMEENTE,
                Contract.StudentenHuis.COLUMN_STUDENTENHUIS_HUISNUMMER,
                Contract.StudentenHuis.COLUMN_STUDENTENHUIS_AANTALKAMERS
        };

        int[] textView_ids = new int[] {
                R.id.textView_straat,
                R.id.textView_gemeente,
                R.id.textView_huisnr,
                R.id.textView_aantal_kamers
        };

        mStudentenHuizenAdaptar = new StudentenHuizenAdaptar(getActivity(), R.layout.row_studentenhuis, null, columns, textView_ids, 0);
        getListView().setAdapter(mStudentenHuizenAdaptar);
        getLoaderManager().initLoader(0, null, this);

    }

    class StudentenHuizenAdaptar extends SimpleCursorAdapter {

        public StudentenHuizenAdaptar(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            //ImageView imageView_house = (ImageView) view.findViewById(R.id.imageView_house);
            TextView textView_straat = (TextView) view.findViewById(R.id.textView_straat);
            TextView textView_huisnr = (TextView) view.findViewById(R.id.textView_huisnr);
            TextView textView_gemeente = (TextView) view.findViewById(R.id.textView_gemeente);
            TextView textView_aantal_kamers = (TextView) view.findViewById(R.id.textView_aantal_kamers);

            textView_straat.setText(cursor.getString(cursor.getColumnIndex(Contract.StudentenHuis.COLUMN_STUDENTENHUIS_STRAAT)));
            textView_huisnr.setText(cursor.getString(cursor.getColumnIndex(Contract.StudentenHuis.COLUMN_STUDENTENHUIS_HUISNUMMER)));
            textView_gemeente.setText(cursor.getString(cursor.getColumnIndex(Contract.StudentenHuis.COLUMN_STUDENTENHUIS_GEMEENTE)));
            textView_aantal_kamers.setText("Aantal kamers: " + cursor.getString(cursor.getColumnIndex(Contract.StudentenHuis.COLUMN_STUDENTENHUIS_AANTALKAMERS)));
        }
    }
}