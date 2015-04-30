package be.howest.nmct.studentenhuizenkortrijk;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;

import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.StudentenhuizenLoader;

public class StudentenhuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mStudentenhuizenRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Cursor mCursorFilter;

    private final String[] mColumnNames = new String[] {
            BaseColumns._ID,
            Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_STRAAT,
            Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_HUISNUMMER,
            Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_GEMEENTE,
            Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_AANTALKAMERS
    };

    public StudentenhuizenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_studentenhuizen, container, false);

        mStudentenhuizenRecyclerView = (RecyclerView) v.findViewById(R.id.studentenhuizen_recycler_view);
        mStudentenhuizenRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mStudentenhuizenRecyclerView.setLayoutManager(mLayoutManager);

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Inflate the options menu from XML
        inflater.inflate(R.menu.menu_studentenhuizen, menu);

        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(getActivity().searchManager.getSearchableInfo(getActivity().getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    mAdapter = new StudentenHuizenAdaptar(mCursorFilter);
                    mAdapter.notifyDataSetChanged();
                    mStudentenhuizenRecyclerView.setAdapter(mAdapter);
                    return true;
                } else {
                    Cursor filterCursor = filterCursorOnStreet(newText);

                    //probleem: hij stelt de mCursorStudentenhuizen in op de filtercursor
                    mAdapter = new StudentenHuizenAdaptar(filterCursor);
                    mAdapter.notifyDataSetChanged();
                    mStudentenhuizenRecyclerView.setAdapter(mAdapter);
                    return true;
                }
            }
        });
    }

    /*public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mAdapter.swapCursor(mCursor);
            setListAdapter(mAdapter);
            return true;
        } else {
            Cursor filterCursor = filterCursorOnStreet(newText);
            mAdapter.swapCursor(filterCursor);
            setListAdapter(mAdapter);
            return true;
        }
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorFilter = data;
        mAdapter = new StudentenHuizenAdaptar(data);
        mStudentenhuizenRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter = new StudentenHuizenAdaptar(null);
    }

    public class StudentenHuizenAdaptar extends RecyclerView.Adapter<StudentenHuizenAdaptar.KotViewHolder> {

        Cursor cursorStudentenHuizen;

        public StudentenHuizenAdaptar(Cursor cursor) {
            cursorStudentenHuizen = cursor;
        }

        @Override
        public KotViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_studentenhuis, viewGroup, false);
            KotViewHolder holder = new KotViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(KotViewHolder viewHolder, int position) {

            cursorStudentenHuizen.moveToPosition(position);

            int colnr1 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_STRAAT);
            int colnr2 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_HUISNUMMER);
            int colnr3 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_GEMEENTE);
            int colnr4 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_AANTALKAMERS);

            viewHolder.mTextView_straat.setText(cursorStudentenHuizen.getString(colnr1));
            viewHolder.mTextView_huisnr.setText(cursorStudentenHuizen.getString(colnr2));
            viewHolder.mTextView_gemeente.setText(cursorStudentenHuizen.getString(colnr3));
            viewHolder.mTextView_aantal_kamers.setText("Aantal kamers: " + cursorStudentenHuizen.getString(colnr4));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int colnr1 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_STRAAT);
                    int colnr2 = cursorStudentenHuizen.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_HUISNUMMER);

                    String adres = cursorStudentenHuizen.getString(colnr1) + " " + cursorStudentenHuizen.getString(colnr2);
                    Uri uri = Uri.parse("geo:0,0?q=" + adres);
                    showMap(uri);
                }
            });
        }

        public void showMap(Uri geoLocation) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }

        //dataset is null --> activitycreated eerst en daarna wordt loader ingeladen, dit moet omgekeer zijn
        @Override
        public int getItemCount() {
            return cursorStudentenHuizen.getCount();
        }

        class KotViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView_straat = null;
            public TextView mTextView_huisnr = null;
            public TextView mTextView_gemeente = null;
            public TextView mTextView_aantal_kamers = null;

            public KotViewHolder(View itemView) {
                super(itemView);

                mTextView_straat = (TextView) itemView.findViewById(R.id.textView_straat);
                mTextView_huisnr = (TextView) itemView.findViewById(R.id.textView_huisnr);
                mTextView_gemeente = (TextView) itemView.findViewById(R.id.textView_gemeente);
                mTextView_aantal_kamers = (TextView) itemView.findViewById(R.id.textView_aantal_kamers);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private Cursor filterCursorOnStreet(String straat)
    {
        MatrixCursor newCursor = new MatrixCursor(mColumnNames);
        int colnr1 = mCursorFilter.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_STRAAT);
        int colnr2 = mCursorFilter.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_HUISNUMMER);
        int colnr3 = mCursorFilter.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_GEMEENTE);
        int colnr4 = mCursorFilter.getColumnIndex(Contract.StudentenhuisColumns.COLUMN_STUDENTENHUIS_AANTALKAMERS);

        int id = 1;

        if(mCursorFilter.moveToFirst())
        {
            do{
                if(mCursorFilter.getString(colnr1).toLowerCase().contains(straat.toLowerCase().trim()))
                {
                    MatrixCursor.RowBuilder row = newCursor.newRow();
                    row.add(id++);
                    row.add(mCursorFilter.getString(colnr1));
                    row.add(mCursorFilter.getString(colnr2));
                    row.add(mCursorFilter.getString(colnr3));
                    row.add(mCursorFilter.getString(colnr4));
                }
            } while(mCursorFilter.moveToNext());
        }
        return newCursor;
    }
}