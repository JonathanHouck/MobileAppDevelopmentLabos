package nmct.howest.be.colorpicker;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {


    public ColorFragment() {
        // Required empty public constructor
    }

    public ColorView myColorView;

    private static final String SS_COLOR = "color";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_color_view, container, false);

        /*myColorView = (ColorView) v.findViewById(R.id.myColorView1);

        if (savedInstanceState != null) {
            Log.d("ColorView", "onRestore: " + savedInstanceState.getString(SS_COLOR));
            myColorView.setColor(savedInstanceState.getString(SS_COLOR));
        }*/

        return v;
    }

    //http://stackoverflow.com/questions/12793069/android-onsaveinstancestate-not-being-called-from-activity

    //wordt onthouden wanneer je op home duwt en app start,
    //ook wanneer de developper optie (destroy every activity as soon as the user leaves it) aanstaat
    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SS_COLOR, myColorView.getColor());
        Log.d("ColorView", "onSaveInstance: " + myColorView.getColor());
    }*/
}
