package be.howest.nmct;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BitcoinRateFragment extends Fragment {

    private Float rate1BitcoinInEuros;
    private EditText editText_bitcoin_in_euro;
    private Button btnWijzigWisselkoers;

    static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";

    public static final String PREFS_RATE = "PrefRate";

    private OnBitcoinRateFragmentListener mListener;

    public static BitcoinRateFragment newInstance(Float bitcoinrate) {
        BitcoinRateFragment fragment = new BitcoinRateFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);
        return fragment;
    }

    public BitcoinRateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //parameters bijhouden
        if (getArguments() != null) {
            rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE, 100f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        editText_bitcoin_in_euro = (EditText) v.findViewById(R.id.editText_bitcoin_in_euro);
        editText_bitcoin_in_euro.setText(rate1BitcoinInEuros.toString());

        btnWijzigWisselkoers = (Button) v.findViewById(R.id.btnWijzigen);
        btnWijzigWisselkoers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_bitcoin_in_euro.getText().toString().isEmpty()) {
                    haalWisselkoersOp();
                    mListener.demandBitcoinConverter(rate1BitcoinInEuros);
                }
            }
        });

        return v;
    }

    private void haalWisselkoersOp() {
        rate1BitcoinInEuros = Float.parseFloat(editText_bitcoin_in_euro.getText().toString());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBitcoinRateFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnBitcoinRateFragmentListener {
        public void demandBitcoinConverter(Float rate1BitcoinInEuros);
    }
}
