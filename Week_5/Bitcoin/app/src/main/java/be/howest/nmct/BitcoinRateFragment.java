package be.howest.nmct;


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


    public BitcoinRateFragment() {
        // Required empty public constructor
    }

    private Float rate1BitcoinInEuros;
    private EditText editText_bitcoin_in_euro;
    private Button btnWijzigWisselkoers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        editText_bitcoin_in_euro = (EditText) v.findViewById(R.id.editText_bitcoin_in_euro);
        btnWijzigWisselkoers = (Button) v.findViewById(R.id.btnWijzigWisselkoers);

        return v;
    }

    private void haalWisselkoersOp() {
        if (!editText_bitcoin_in_euro.getText().toString().isEmpty()) {
            rate1BitcoinInEuros = Float.parseFloat(editText_bitcoin_in_euro.getText().toString());
        }
    }
}
