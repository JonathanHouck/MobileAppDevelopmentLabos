package nmct.howest.be.launching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExplicitActivity extends Activity {

    public static final String EXTRA_INFO = "be.howest.nmct.android.launching.EXTRA_INFO";

    public static final int RESULT_CODE_NOIDEA = 5;

    private TextView textview;
    private Button btnOk;
    private Button btnCancel;
    private Button btnNoIdea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        String value = getIntent().getStringExtra(ExplicitActivity.EXTRA_INFO);
        textview = (TextView) findViewById(R.id.textView);
        textview.setText(value);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        btnNoIdea = (Button) findViewById(R.id.btnNoIdea);
        btnNoIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_CODE_NOIDEA);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_INFO_BACK_LASTNAME, "Walcarius");
                intent.putExtra(MainActivity.EXTRA_INFO_BACK_AGE, 40);
                setResult(ExplicitActivity.RESULT_CODE_NOIDEA, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explicit, menu);
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
