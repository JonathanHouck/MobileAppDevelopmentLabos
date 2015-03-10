package be.howest.nmct;


import android.content.Intent;
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


    public ChangeFragment() {
        // Required empty public constructor
    }

    private float currentRateBitcoinInEuro = 101;

    private EditText editText_bedrag_in_€;
    private EditText editText_bedrag_in_bitcoin;

    private Button btnNaarEuro;
    private Button btnNaarBitcoin;
    private Button btnWijzigKoers;

    private TextView textview_wisselkoers;

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
                /*Intent naarWijzigKoers = new Intent(getActivity(), BitcoinRateFragment.class);
                startActivity(naarWijzigKoers);*/
            }
        });

        return v;
    }

    private void toonWisselkoers() {
        String wisselkoers = Float.toString(currentRateBitcoinInEuro);
        textview_wisselkoers.setText("1 bitcoin = " + wisselkoers);
    }

    private void changeToEuro() {

        if (!editText_bedrag_in_bitcoin.getText().toString().isEmpty()) {
            Float bedragInBitcoin = Float.parseFloat(editText_bedrag_in_bitcoin.getText().toString());
            Float bedragInEuro = bedragInBitcoin * currentRateBitcoinInEuro;
            editText_bedrag_in_€.setText(bedragInEuro.toString());
        }
    }

    private void changeToBitcoin() {

        if (!editText_bedrag_in_€.getText().toString().isEmpty()) {
            Float bedragInEuro = Float.parseFloat(editText_bedrag_in_€.getText().toString());
            Float bedragInBitcoin = bedragInEuro / currentRateBitcoinInEuro;
            editText_bedrag_in_bitcoin.setText(bedragInBitcoin.toString());
        }
    }
}
