package be.nmct.howest.be.demoimplicit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button btnWebbrowser;
    private Button btnMakeCall;
    private Button btnDialNumber;
    private Button btnGeoLoc;
    private Button btnContacts;
    private Button btnEditContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findButtons();

        btnWebbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBrowser();
            }
        });

        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCall();
            }
        });
    }

    private void MakeCall() {
        Uri number = Uri.parse("tel:5551234");
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);
    }

    private void OpenBrowser() {
        Uri webpage = Uri.parse("http://www.nmct.be");
        Intent browser = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(browser);
    }

    private void findButtons() {
        btnWebbrowser = (Button) findViewById(R.id.btnWebbrowser);
        btnMakeCall = (Button) findViewById(R.id.btnMakeCall);
        btnDialNumber = (Button) findViewById(R.id.btnDialNumber);
        btnGeoLoc = (Button) findViewById(R.id.btnGeoLoc);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        btnEditContact = (Button) findViewById(R.id.btnEditContact);
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
}
