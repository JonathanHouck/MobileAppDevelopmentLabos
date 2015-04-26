package nmct.howest.be.horoscoop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectGeboortejaarActivity extends ListActivity {

    private final static List<String> GEBOORTEJAREN;
    private ListAdapter myListAdapter;

    static {
        GEBOORTEJAREN = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for (int jaar = 1900; jaar <= Calendar.getInstance().get(Calendar.YEAR); jaar++) {
            GEBOORTEJAREN.add("" + jaar);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, GEBOORTEJAREN);
        setListAdapter(myListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);

        String sGeboortejaar = GEBOORTEJAREN.get(position);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_BIRTHYEAR, sGeboortejaar);
        setResult(RESULT_OK, intent);
        finish();
    }
}
