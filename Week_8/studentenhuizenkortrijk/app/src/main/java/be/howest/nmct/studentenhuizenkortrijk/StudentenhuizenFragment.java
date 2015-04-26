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

import java.util.List;

import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader.StudentenhuizenLoader;
import be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis;

public class StudentenhuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentenHuizenAdaptar mStudentenHuizenAdaptar;
    private RecyclerView mStudentenhuizenRecyclerView = null;
    private RecyclerView.LayoutManager mLayoutManager;

    public StudentenhuizenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_studentenhuizen, container, false);

        mStudentenhuizenRecyclerView = (RecyclerView) v.findViewById(R.id.studentenhuizen_recycler_view);

        return v;
    }

    class StudentenHuizenAdaptar extends RecyclerView.Adapter<StudentenHuizenAdaptar.KotViewHolder> {

        private List<Studentenhuis> mDataset;

        public StudentenHuizenAdaptar(List<Studentenhuis> myDataSet) {
            this.mDataset = myDataSet;
        }

        @Override
        public KotViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_studentenhuis, viewGroup, false);
            KotViewHolder holder = new KotViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(KotViewHolder viewHolder, int position) {

            Studentenhuis sh = mDataset.get(position);

            viewHolder.mTextView_straat.setText(sh.getAdres());
            viewHolder.mTextView_huisnr.setText(sh.getHuisnummer());
            viewHolder.mTextView_gemeente.setText(sh.getGemeente());
            viewHolder.mTextView_aantal_kamers.setText(sh.getAantalKamers());
        }

        //dataset is null --> activitycreated eerst en daarna wordt loader ingeladen, dit moet omgekeer zijn
        @Override
        public int getItemCount() {
            return mDataset.size();
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mStudentenHuizenAdaptar.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[] {
                Contract.Studentenhuis.COLUMN_STUDENTENHUIS_STRAAT,
                Contract.Studentenhuis.COLUMN_STUDENTENHUIS_HUISNUMMER,
                Contract.Studentenhuis.COLUMN_STUDENTENHUIS_GEMEENTE,
                Contract.Studentenhuis.COLUMN_STUDENTENHUIS_AANTALKAMERS
        };

        int[] textView_ids = new int[] {
                R.id.textView_straat,
                R.id.textView_huisnr,
                R.id.textView_gemeente,
                R.id.textView_aantal_kamers
        };

        mStudentenhuizenRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mStudentenhuizenRecyclerView.setLayoutManager(mLayoutManager);

        mStudentenHuizenAdaptar = new StudentenHuizenAdaptar(StudentenhuizenLoader.getLijstStudentenhuizen());
        mStudentenhuizenRecyclerView.setAdapter(mStudentenHuizenAdaptar);

        getLoaderManager().initLoader(0, null, this);
    }

    /*public List<Studentenhuis> getData() {
        List<Studentenhuis> studentenhuizen = new ArrayList<Studentenhuis>();

        InputStream input = null;
        JsonReader reader = null;

        try {
            synchronized (lock) {
                input = new URL(url).openStream();
                reader = new JsonReader(new InputStreamReader(input, "UTF-8"));

                int id = 1;
                reader.beginArray();
                while (reader.hasNext()) {

                    reader.beginObject();
                    Studentenhuis sh = new Studentenhuis();

                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("ADRES")) {
                            sh.setAdres(reader.nextString());
                        } else if (name.equals("HUISNR")) {
                            //opgelet zoawel numerieke waarden (nextInt) als string-waarden (nextString) komen voor!
                            //Voer controle uit via 'peek' -methode: Return the type of the next token without consuming it
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                sh.setHuisnummer(reader.nextString());

                            } else if (reader.peek().equals(JsonToken.NUMBER)) {
                                sh.setHuisnummer(Integer.toString(reader.nextInt()));
                            }
                        } else if (name.equals("GEMEENTE")) {
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                sh.setGemeente(reader.nextString());
                            }
                        } else if (name.equals("aantal kamers")) {
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                sh.setAantalKamers(Integer.parseInt(reader.nextString()));

                            } else if (reader.peek().equals(JsonToken.NUMBER)) {
                                sh.setAantalKamers(reader.nextInt());
                            }
                        } else {
                            reader.skipValue();
                        }
                    }

                    id++;
                    studentenhuizen.add(sh);
                    reader.endObject();
                }
                reader.endArray();
            }


        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
            try {
                input.close();
            } catch (IOException e) {
            }
        }

        return studentenhuizen;
    }*/
}