package be.howest.nmct;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends Activity implements ChangeFragment.OnChangeFragmentListener, BitcoinRateFragment.OnBitcoinRateFragmentListener {

    private float rate1BitcoinInEuros = 100f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChangeFragment())
                    .commit();
        }*/

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ChangeFragment.newInstance(rate1BitcoinInEuros), "changeFragment")
                    .addToBackStack("start_ChangeFragment")
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


    public void showFragmentChange(Float rate1BitcoinInEuros) {
        //ChangeFragment newFragment = ChangeFragment.newInstance(rate1BitcoinInEuros);
        ChangeFragment fragment = (ChangeFragment) getFragmentManager().findFragmentByTag("changeFragment");
        fragment.setRate1BitcoinInEuros(rate1BitcoinInEuros);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void showFragmentBitcoinRate(Float rate1BitcoinInEuros) {
        BitcoinRateFragment newFragment =  BitcoinRateFragment.newInstance(rate1BitcoinInEuros);

        FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack("start_BitcoinRateFragment");

        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void demandBitcoinConverter(Float rate1BitcoinInEuros) {
        showFragmentChange(rate1BitcoinInEuros);
    }

    @Override
    public void demandNewBitcoinRate(Float rate1BitcoinInEuros) {
        showFragmentBitcoinRate(rate1BitcoinInEuros);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}