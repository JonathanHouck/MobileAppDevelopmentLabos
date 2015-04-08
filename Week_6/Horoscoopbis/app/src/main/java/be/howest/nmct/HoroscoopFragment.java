package be.howest.nmct;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import be.howest.nmct.data.Data;


public class HoroscoopFragment extends ListFragment {

    private OnHorscoopFragmentListener mListener;
    public HoroscoopAdapter myAdapter;

    public HoroscoopFragment() {
        // Required empty public constructor
    }

    public static HoroscoopFragment newInstance() {
        HoroscoopFragment fragment = new HoroscoopFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myAdapter = new HoroscoopAdapter();
        setListAdapter(myAdapter);
    }

    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop> {
        public HoroscoopAdapter() {
            super(getActivity(), R.layout.row_horoscoop,
                    R.id.textView_naam_horoscoop, Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewHoroscoop = holder.textViewNaamHoroscoop;
            textViewHoroscoop.setText(horoscoop.getNaamHoroscoop());

            ImageView imagesView_horoscoop = holder.imageviewHoroscoop;
            imagesView_horoscoop.setImageResource(HoroscoopFuncties.getResourceId(horoscoop));

            Button button_info = holder.btnToonInfo;
            button_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBeginAndEndData(horoscoop);
                }
            });

            return row;
        }

        private void showBeginAndEndData(Data.Horoscoop horoscoop) {
            Toast.makeText(getActivity(), horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.demandMain(HoroscoopFuncties.getResourceId(Data.Horoscoop.values()[position]));
    }

    class ViewHolder {
        public ImageView imageviewHoroscoop= null;
        public TextView textViewNaamHoroscoop = null;
        public Button btnToonInfo = null;

        public ViewHolder(View row) {
            this.imageviewHoroscoop = (ImageView) row.findViewById(R.id.imagesView_horoscoop);
            this.textViewNaamHoroscoop = (TextView) row.findViewById(R.id.textView_naam_horoscoop);
            this.btnToonInfo = (Button) row.findViewById(R.id.button_info);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHorscoopFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHorscoopFragmentListener");
        }
    }


    public interface OnHorscoopFragmentListener {
        public void demandMain(int resource_id_horoscoop);
    }
}
