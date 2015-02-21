package be.howest.nmct.stopafstand;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */

public class StopAfstandFragmentSeekBar extends Fragment {

    //private EditText snelheid;
    //private EditText reactietijd;

    //http://www.mkyong.com/android/android-radio-buttons-example/
    private RadioGroup radio_buttons;

    private Button button_stopafstand;
    private TextView stopafstand;

    private SeekBar seek_bar_snelheid;
    private TextView seek_bar_snelheid_waarde;

    //private SeekBar seek_bar_reactietijd;
    //private TextView seek_bar_reactietijd_waarde;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand_seek_bar, container, false);

        this.button_stopafstand = (Button) v.findViewById(R.id.button_stopafstand);
        this.radio_buttons = (RadioGroup) v.findViewById(R.id.wegtype);
        this.stopafstand = (TextView) v.findViewById(R.id.stopafstand);

        this.seek_bar_snelheid_waarde = (TextView) v.findViewById(R.id.seek_bar_snelheid_waarde);
        this.seek_bar_snelheid = (SeekBar) v.findViewById(R.id.seek_bar_snelheid);

        this.seek_bar_snelheid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //probleem: waarden seekBar en progress zijn niet ingesteld dus kan de tekst niet ingesteld worden
                seek_bar_snelheid_waarde.setText(progress);
                //setProgress(seekBar, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*this.seek_bar_reactietijd_waarde = (TextView) v.findViewById(R.id.seek_bar_reactietijd_waarde);
        this.seek_bar_reactietijd = (SeekBar) v.findViewById(R.id.seek_bar_reactietijd);
        this.seek_bar_reactietijd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setProgress(seekBar, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        this.button_stopafstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berekenStopAfstand(v);
            }
        });

        return v;
    }

    private void setProgress(SeekBar seekBar, int progress) {
        /*switch (seekBar.getId()) {
            case R.id.seek_bar_snelheid:
                this.seek_bar_snelheid_waarde.setText(progress);
            case R.id.seek_bar_reactietijd:
                this.seek_bar_reactietijd_waarde.setText(progress);
        }*/
    }

    private void berekenStopAfstand(View v) {
        int selectedId = this.radio_buttons.getCheckedRadioButtonId();
        RadioButton wegtype = (RadioButton) this.radio_buttons.findViewById(selectedId);
        int wegtype_nr = this.radio_buttons.indexOfChild(wegtype);

        StopAfstandInfo sai = new StopAfstandInfo();
        sai.setSnelheid(this.seek_bar_snelheid.getProgress());
        //sai.setReactietijd(this.seek_bar_reactietijd.getProgress());

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
