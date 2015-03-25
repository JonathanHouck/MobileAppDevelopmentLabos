package be.howest.nmct;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeFragment extends Fragment {

    private static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";
    private Float rate1BitcoinInEuros;

    public static final String PREFS_RATE = "PrefRate";

    private OnChangeFragmentListener mListener;

    private EditText editText_bedrag_in_€;
    private EditText editText_bedrag_in_bitcoin;

    private Button btnNaarEuro;
    private Button btnNaarBitcoin;
    private Button btnWijzigKoers;

    private TextView textview_wisselkoers;

    public ChangeFragment() {
        // Required empty public constructor
    }

    public static ChangeFragment newInstance(float bitcoinrate) {
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRate1BitcoinInEuros(Float rate) {
        rate1BitcoinInEuros = rate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE, 100f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_change, container, false);

        editText_bedrag_in_€ = (EditText) v.findViewById(R.id.editText_bedrag_in_€);
        editText_bedrag_in_bitcoin = (EditText) v.findViewById(R.id.editText_bedrag_in_bitcoin);

        btnNaarEuro = (Button) v.findViewById(R.id.btnNaarEuro);
        btnNaarBitcoin = (Button) v.findViewById(R.id.btnNaarBitcoin);
        btnWijzigKoers = (Button) v.findViewById(R.id.btnWijzigWisselkoers);

        textview_wisselkoers = (TextView) v.findViewById(R.id.textView_wisselkoers);

        if (savedInstanceState != null) {
            Float bedrag_in_€ = savedInstanceState.getFloat("bedrag_in_€", 0);
            Float bedrag_in_bitcoin = savedInstanceState.getFloat("bedrag_in_bitcoin", 0);

            if (bedrag_in_€ != 0) editText_bedrag_in_€.setText(bedrag_in_€.toString());
            if (bedrag_in_bitcoin != 0) editText_bedrag_in_bitcoin.setText((bedrag_in_bitcoin.toString()));
        }

        toonWisselkoers();

        btnNaarEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToEuro();
            }
        });

        btnNaarBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBitcoin();
            }
        });

        btnWijzigKoers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.demandNewBitcoinRate(rate1BitcoinInEuros);
            }
        });

        return v;
    }

    private void toonWisselkoers() {
        String wisselkoers = Float.toString(rate1BitcoinInEuros);
        textview_wisselkoers.setText("1 bitcoin = " + wisselkoers);
    }

    private void changeToEuro() {

        if (!editText_bedrag_in_bitcoin.getText().toString().isEmpty()) {
            Float bedragInBitcoin = Float.parseFloat(editText_bedrag_in_bitcoin.getText().toString());
            Float bedragInEuro = bedragInBitcoin * rate1BitcoinInEuros;
            editText_bedrag_in_€.setText(bedragInEuro.toString());
        }
    }

    private void changeToBitcoin() {

        if (!editText_bedrag_in_€.getText().toString().isEmpty()) {
            Float bedragInEuro = Float.parseFloat(editText_bedrag_in_€.getText().toString());
            Float bedragInBitcoin = bedragInEuro / rate1BitcoinInEuros;
            editText_bedrag_in_bitcoin.setText(bedragInBitcoin.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnChangeFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnChangeFragmentListener {
        public void demandNewBitcoinRate(Float rate1BitcoinInEuros);
    }

    @Override
    public void onStart() {
        super.onStart();

        //als je het scherm roteert staat de rate terug op 100 en wordt hij opgehaald uit SharedPreferences
        //als je het bedrag wijzigt en op de button klikt mag BITCOIN_RATE niet opgehaald worden

        //--> de onStop methode wordt hier niet meer uitgevoert (logish) en de onStart() methode zal dus nog de oude waarde krijgen
        //--> niet de ingevoerde waarde uit het tekstvak
        //--> dit is niet de bedoeling, daarom enkel maar uitvoeren als de rate op 100 staat
        if (rate1BitcoinInEuros == 100) {
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_RATE, 0);
            rate1BitcoinInEuros = settings.getFloat(BITCOIN_RATE, 100f);
        }

        toonWisselkoers();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_RATE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(BITCOIN_RATE, rate1BitcoinInEuros);

        // Commit the edits!5
        editor.commit();
    }

    //oefening 2
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!editText_bedrag_in_€.getText().toString().isEmpty()) outState.putFloat("bedrag_in_€",  Float.parseFloat(editText_bedrag_in_€.getText().toString()));
        if (!editText_bedrag_in_bitcoin.getText().toString().isEmpty()) outState.putFloat("bedrag_in_bitcoin",  Float.parseFloat(editText_bedrag_in_bitcoin.getText().toString()));
    }
}
