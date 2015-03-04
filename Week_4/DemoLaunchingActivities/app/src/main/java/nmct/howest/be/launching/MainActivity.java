package nmct.howest.be.launching;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button btnStartActivity2;
    private Button btnShowDialog;
    private static final int REQUEST_CODE_EXPLICIT = 1;

    public static final String EXTRA_INFO_BACK_LASTNAME = "Lastname";
    public static final String EXTRA_INFO_BACK_AGE = "Age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartActivity2 = (Button) findViewById(R.id.btnStartActivity2);
        btnStartActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSecondActivity(v);
            }
        });

        btnShowDialog = (Button) findViewById(R.id.btnShowDialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }

    private void ShowDialog() {
        FragmentManager fm = getFragmentManager();
        ShowDialogFragment dialog = new ShowDialogFragment();
        dialog.show(fm, "dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //controleren van welke vraag we antwoord hebben binnen gekregen
            case REQUEST_CODE_EXPLICIT:
                //wat was het antwoord?
                switch (resultCode) {
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "User selects OK", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        Toast.makeText(MainActivity.this, "User selects Canel", Toast.LENGTH_SHORT).show();
                        break;
                    case ExplicitActivity.RESULT_CODE_NOIDEA:
                        ///Toast.makeText(MainActivity.this, "User selects: Give own result", Toast.LENGTH_SHORT).show();

                        String value = data.getStringExtra(MainActivity.EXTRA_INFO_BACK_LASTNAME);
                        //0 is default value
                        int age = data.getIntExtra(MainActivity.EXTRA_INFO_BACK_AGE, 0);
                        Toast.makeText(MainActivity.this, "No Idea what's happening there " + value + " - " + age, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startSecondActivity(View v) {
        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
        intent.putExtra(ExplicitActivity.EXTRA_INFO, "2NMCT");
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_EXPLICIT);
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
