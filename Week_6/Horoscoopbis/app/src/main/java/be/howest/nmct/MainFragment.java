package be.howest.nmct;


import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import be.howest.nmct.data.Data;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private OnMainFragmentListener mListener;

    private Button button_selecteer_geboortejaar;
    private Button button_selecteer_horoscoop;
    private TextView textView_Geboortejaar;
    private ImageView imageView_keuze_horoscoop;

    private String geboortejaar;
    private int resource_id_horoscoop;

    public static final String EXTRA_BIRTHYEAR = "be.howest.nmct.week5oef1.BIRTHYEAR";
    public static final String EXTRA_HOROSCOOP = "be.howest.nmct.week5oef1.HOROSCOOP";

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String GEBOORTEJAAR = "be.howest.nmct.NEW_GEBOORTEJAAR";
    private static final String HOROSCOOPID = "be.howest.nmct.NEW_HOROSCOOPID";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String jaar, int resource_id_horoscoop) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_BIRTHYEAR, jaar);
        bundle.putInt(EXTRA_HOROSCOOP, resource_id_horoscoop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            geboortejaar = getArguments().getString(EXTRA_BIRTHYEAR, "");
            resource_id_horoscoop = getArguments().getInt(EXTRA_HOROSCOOP, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        button_selecteer_geboortejaar = (Button) v.findViewById(R.id.button_selecteer_geboortejaar);
        button_selecteer_horoscoop = (Button) v.findViewById(R.id.button_selecteer_horoscoop);
        textView_Geboortejaar = (TextView) v.findViewById(R.id.textView_Geboortejaar);
        imageView_keuze_horoscoop = (ImageView) v.findViewById(R.id.imageView_keuze_horoscoop);

        showGeboortedatumEnHoroschoop();

        button_selecteer_geboortejaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.demandNewSelectGeboortejaar();
            }
        });

        button_selecteer_horoscoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.demandNewHoroscoop();
            }
        });

        return v;
    }

    private void showGeboortedatumEnHoroschoop() {
        textView_Geboortejaar.setText(geboortejaar);
        imageView_keuze_horoscoop.setImageResource(resource_id_horoscoop);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMainFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnMainFragmentListener {
        public void demandNewSelectGeboortejaar();
        public void demandNewHoroscoop();
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (geboortejaar.equals("") && resource_id_horoscoop == 0) {
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            geboortejaar = settings.getString(GEBOORTEJAAR, "");
            resource_id_horoscoop = settings.getInt(HOROSCOOPID, 0);
            showGeboortedatumEnHoroschoop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(GEBOORTEJAAR, geboortejaar);
        editor.putInt(HOROSCOOPID, resource_id_horoscoop);

        editor.commit();
    }

    public void setGeboortedatum(String geboortejaar) {
         this.geboortejaar = geboortejaar;
    }

    public void setHoroscoop(int resource_id_horoscoop) {
        this.resource_id_horoscoop = resource_id_horoscoop;
    }
}
