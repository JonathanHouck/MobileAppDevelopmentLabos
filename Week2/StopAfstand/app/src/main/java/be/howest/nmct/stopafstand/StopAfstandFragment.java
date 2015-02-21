package be.howest.nmct.stopafstand;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jonathan on 19/02/2015.
 */
public class StopAfstandFragment extends Fragment
{
    private EditText snelheid;
    private EditText reactietijd;

    //http://www.mkyong.com/android/android-radio-buttons-example/
    private RadioGroup radio_buttons;

    private Button button_stopafstand;
    private TextView stopafstand;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand, container, false);

        snelheid = (EditText) v.findViewById(R.id.snelheid);
        reactietijd = (EditText) v.findViewById(R.id.reactietijd);
        button_stopafstand = (Button) v.findViewById(R.id.button_stopafstand);
        radio_buttons = (RadioGroup) v.findViewById(R.id.wegtype);
        stopafstand = (TextView) v.findViewById(R.id.stopafstand);

        button_stopafstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berekenStopAfstand(v);
            }
        });

        return v;
    }

    private void berekenStopAfstand(View v) {
        int selectedId = radio_buttons.getCheckedRadioButtonId();
        RadioButton wegtype = (RadioButton) radio_buttons.findViewById(selectedId);
        int wegtype_nr = radio_buttons.indexOfChild(wegtype);

        StopAfstandInfo sai = new StopAfstandInfo();
        sai.setSnelheid(Integer.parseInt(snelheid.getText().toString()));
        sai.setReactietijd(Float.parseFloat(reactietijd.getText().toString()));

        if (wegtype_nr == 0) {
            sai.setWegtype(StopAfstandInfo.WegType.WEGDEK_DROOG);
        } else {
            sai.setWegtype(StopAfstandInfo.WegType.WEGDEK_NAT);
        }

        int stopafstand_resultaat = Math.round(StopAfstandInfo.GetStopAfstand(sai));
        String stopafstand_output = Integer.toString(stopafstand_resultaat) + " meter";
        stopafstand.setText(stopafstand_output);

        //Context context = v.getContext().getApplicationContext();
        //Toast toast = Toast.makeText(context, stopafstand_output, Toast.LENGTH_SHORT);
        Toast toast = Toast.makeText(getActivity(), stopafstand_output, Toast.LENGTH_SHORT);
        toast.show();
    }
}
