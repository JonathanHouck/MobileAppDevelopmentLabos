package be.howest.nmct.stopafstand;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class StopAfstandActivity extends ActionBarActivity {

    //mogelijke oplossingen voor seekbar probleem
    //http://stackoverflow.com/questions/24123094/null-pointer-exception-while-implementing-seekbar-listener-interface-in-android
    //http://stackoverflow.com/questions/23070905/nullpointerexception-pointing-to-setonseekbarchangelistener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_afstand);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    //.add(R.id.container, new StopAfstandFragment())
                    .add(R.id.container, new StopAfstandFragmentSeekBar())
                    .commit();
        }
    }
}
