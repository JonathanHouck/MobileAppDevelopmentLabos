package be.howest.nmct.demoimplicit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LocationActivity extends ActionBarActivity {

    private EditText txtLattitude;
    private EditText txtLongitude;
    private Button btnToonLocatie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        txtLattitude = (EditText) findViewById(R.id.txtLattitude);
        txtLongitude = (EditText) findViewById(R.id.txtLongitude);
        btnToonLocatie = (Button) findViewById(R.id.btnToonLocatie);
        btnToonLocatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMap();
            }
        });
    }

    private void ShowMap() {

        if (!txtLattitude.getText().toString().isEmpty() && !txtLongitude.getText().toString().isEmpty()) {

            float lattitude = Float.parseFloat(txtLattitude.getText().toString());
            float longitude = Float.parseFloat(txtLongitude.getText().toString());
            String geoLocation = "geo:" + lattitude + "," + longitude;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(geoLocation));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Intent niet gevonden", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Gelive de tekstvelden in te vullen", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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
