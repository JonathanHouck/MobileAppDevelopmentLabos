package be.howest.nmct.demoimplicit;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button btnWebbrowser;
    private Button btnMakeCall;
    private Button btnDialNumber;
    private Button btnGeoLoc;
    private Button btnContacts;
    private Button btnEditContact;
    private Button btnCalcBMI;

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

        btnDialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialNumber();
            }
        });

        btnGeoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMap();
            }
        });

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowContacts();
            }
        });

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditContact();
            }
        });

        btnCalcBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("be.howest.nmct.bmi");
                startActivity(intent);
            }
        });
    }

    private void EditContact() {
        Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void ShowContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    private void ShowMap() {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    private void DialNumber() {
        //http://developer.android.com/reference/android/content/Intent.html
        Uri number = Uri.parse("tel:5551234");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
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
        btnCalcBMI = (Button) findViewById(R.id.btnCalcBMI);
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
