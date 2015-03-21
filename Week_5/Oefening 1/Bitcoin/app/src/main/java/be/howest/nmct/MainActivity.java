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

    //private float rate1BitcoinInEuros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ChangeFragment())
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
        ChangeFragment newFragment = ChangeFragment.newInstance(rate1BitcoinInEuros);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void showFragmentBitcoinRate(Float rate1BitcoinInEuros) {
        //beter nieuwe instance maken
        BitcoinRateFragment newFragment =  BitcoinRateFragment.newInstance(rate1BitcoinInEuros);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

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
}