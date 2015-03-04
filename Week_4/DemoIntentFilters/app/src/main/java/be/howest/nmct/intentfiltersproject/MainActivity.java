package be.howest.nmct.intentfiltersproject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {

    private Button btnLaunchWithAction;
    private Button btnOnderzoek4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        btnLaunchWithAction = (Button) findViewById(R.id.btnLaunchWithAction);
        btnLaunchWithAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWithAction();
            }
        });

        btnOnderzoek4 = (Button) findViewById(R.id.btnOnderzoek4);
        btnOnderzoek4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onderzoek4();
            }
        });
    }

    private void onderzoek4() {
        Intent intent = new Intent(Constants.ACTION_IMPLY, Uri.parse("xtp:///somedata"));
        if (checkIntentExists(intent)) startActivity(intent);
        else Toast.makeText(this, "Intent bestaat niet", Toast.LENGTH_LONG).show();

        /*//test zonder data
        Intent intent = new Intent(Constants.ACTION_IMPLY);
        if (checkIntentExists(intent)) startActivity(intent);
        else Toast.makeText(this, "Intent bestaat niet", Toast.LENGTH_LONG).show();*/
    }

    private void launchWithAction() {
        Intent intent = new Intent(Constants.ACTION_IMPLY);
        //Intent intent = new Intent("android.intent.action.VIEW");
        //intent.addCategory(intent.CATEGORY_CAR_DOCK);

        //niet korter: theorie 3 slide 16
        if (checkIntentExists(intent)) startActivity(intent);
        else Toast.makeText(this, "Intent bestaat niet", Toast.LENGTH_LONG).show();
    }

    private boolean checkIntentExists(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        //intent is save (groter dan 0) --> true
        return activities.size() > 0;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
