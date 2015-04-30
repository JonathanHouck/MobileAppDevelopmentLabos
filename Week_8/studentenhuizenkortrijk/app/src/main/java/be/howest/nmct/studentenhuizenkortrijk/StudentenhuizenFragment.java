package be.howest.nmct.studentenhuizenkortrijk;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.StudentenhuizenLoader;

public class StudentenhuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mStudentenhuizenRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

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

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter = new StudentenHuizenAdaptar(data);
        mStudentenhuizenRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter = new StudentenHuizenAdaptar(null);
    }

    public class StudentenHuizenAdaptar extends RecyclerView.Adapter<StudentenHuizenAdaptar.KotViewHolder> {

        Cursor cursorStudentenHuizen;

        public StudentenHuizenAdaptar(Cursor cursorStudentenHuizen) {
            this.cursorStudentenHuizen = cursorStudentenHuizen;
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
}