package be.howest.nmct;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import be.howest.nmct.data.Data;


public class MainActivity extends Activity implements MainFragment.OnMainFragmentListener, SelectGeboortejaarFragment.OnSelectGeboortejaarFragmentListener, HoroscoopFragment.OnHorscoopFragmentListener {

    public String geboortejaar = "";
    public int resource_id_horoscoop = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(this.geboortejaar, resource_id_horoscoop), "MainFragment")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSelectGeboortejaarFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        SelectGeboortejaarFragment fragment = SelectGeboortejaarFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "SelectGeboortejaarFragment");
        fragmentTransaction.addToBackStack("show_new_geboortedatum");
        fragmentTransaction.commit();
    }

    public void demandNewSelectGeboortejaar() {
        showSelectGeboortejaarFragment();
    }

    public void showHoroscoopFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        HoroscoopFragment fragment = HoroscoopFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "HoroscoopFragment");
        fragmentTransaction.addToBackStack("show_new_horoscoop");
        fragmentTransaction.commit();
    }

    public void demandNewHoroscoop() {
        showHoroscoopFragment();
    }

    public void showMainFragment(String jaar) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        MainFragment fragment = (MainFragment) getFragmentManager().findFragmentByTag("MainFragment");
        fragment.setGeboortedatum(jaar);

        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.container, fragment, "MainFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void demandMain(String jaar) {
        showMainFragment(jaar);
    }

    public void showMainFragment(int resource_id_horoscoop) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        MainFragment fragment = (MainFragment) getFragmentManager().findFragmentByTag("MainFragment");
        fragment.setHoroscoop(resource_id_horoscoop);

        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.container, fragment, "MainFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void demandMain(int resource_id_horoscoop) {
        showMainFragment(resource_id_horoscoop);
    }
}
