package nmct.howest.be.horoscoop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button button_selecteer_geboortejaar;
    private Button button_selecteer_horoscoop;
    private TextView textView_Geboortejaar;
    private ImageView imageView_keuze_horoscoop;

    private String geboortejaar;
    private int position;
    private Data.Horoscoop horoscoop;

    public static final String EXTRA_BIRTHYEAR = "be.howest.nmct.week5oef1.BIRTHYEAR";
    public static final String EXTRA_HOROSCOOP = "be.howest.nmct.week5oef1.HOROSCOOP";

    private static final int REQUEST_BIRTHDAY = 1;
    private static final int REQUEST_HOROSCOOP = 2;

    public static final String PREFS_NAME = "MyPrefsFile";

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_selecteer_geboortejaar = (Button) findViewById(R.id.button_selecteer_geboortejaar);
        button_selecteer_horoscoop = (Button) findViewById(R.id.button_selecteer_horoscoop);
        textView_Geboortejaar = (TextView) findViewById(R.id.textView_Geboortejaar);
        imageView_keuze_horoscoop = (ImageView) findViewById(R.id.imageView_keuze_horoscoop);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        geboortejaar = settings.getString("geboortejaar", "");
        textView_Geboortejaar.setText(geboortejaar);

        position = settings.getInt("position_horoscoop", 0);
        imageView_keuze_horoscoop.setImageResource(getResourceIdFromPosition(position));

        button_selecteer_geboortejaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteerGeboortejaar(v);
            }
        });

        button_selecteer_horoscoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteerHoroscoop(v);
            }
        });
    }

    private void selecteerGeboortejaar(View v) {
        Intent intent = new Intent(MainActivity.this, SelectGeboortejaarActivity.class);
        startActivityForResult(intent, REQUEST_BIRTHDAY);
    }

    private void selecteerHoroscoop(View v) {
        Intent intent = new Intent(MainActivity.this, HoroscoopActivity.class);
        startActivityForResult(intent, REQUEST_HOROSCOOP);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_BIRTHDAY:
                switch (resultCode) {
                    case RESULT_OK:
                        geboortejaar = data.getStringExtra(MainActivity.EXTRA_BIRTHYEAR);
                        textView_Geboortejaar.setText("Geboortejaar: " + geboortejaar);
                        updateShareIntent();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "Geen geboortedatum gekozen", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case REQUEST_HOROSCOOP:
                switch (resultCode) {
                    case RESULT_OK:
                        int position = data.getIntExtra(MainActivity.EXTRA_HOROSCOOP, 0);
                        imageView_keuze_horoscoop.setImageResource(getResourceIdFromPosition(position));
                        updateShareIntent();

                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "Geen horoscoop gekozen", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("geboortejaar", textView_Geboortejaar.getText().toString());
        editor.putInt("position_horoscoop", position);

        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Set up ShareActionProvider's default share intent
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultIntent());

        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getDataForShare());
        return intent;
    }

    private String getDataForShare() {
        return "Mijn horoscoop: " + horoscoop.getNaamHoroscoop() + "\n" + geboortejaar;
    }

    private void updateShareIntent() {
        if (mShareActionProvider != null) {
            if (horoscoop != null && geboortejaar != "") {
                mShareActionProvider.setShareIntent(getDefaultIntent());
            }
        }
    }

    private int getResourceIdFromPosition(int position) {
        if (position == 2130837557) {
            horoscoop = Data.Horoscoop.BOOGSCHUTTER;
        } else {
            horoscoop = Data.Horoscoop.values()[position];
        }
        return HoroscoopFuncties.getResourceId(horoscoop);
    }
}