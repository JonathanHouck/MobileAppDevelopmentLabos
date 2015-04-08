package nmct.howest.be.horoscoop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HoroscoopActivity extends ListActivity {

    public HoroscoopAdapter myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_horoscoop);

        myAdapter = new HoroscoopAdapter();
        setListAdapter(myAdapter);
    }

    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop> {
        public HoroscoopAdapter() {
            super(HoroscoopActivity.this, R.layout.row_horoscoop,
                    R.id.textView_naam_horoscoop, Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            Log.d("HOROSCOOP", "findViewById");

            TextView textViewHoroscoop = holder.textViewNaamHoroscoop;
            textViewHoroscoop.setText(horoscoop.getNaamHoroscoop());

            ImageView imagesView_horoscoop = holder.imageviewHoroscoop;
            imagesView_horoscoop.setImageResource(HoroscoopFuncties.getResourceId(horoscoop));

            Button button_info = holder.btnToonInfo;
            button_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBeginAndEndData(horoscoop);
                }
            });

            return row;
        }

        private void showBeginAndEndData(Data.Horoscoop horoscoop) {

            Toast.makeText(HoroscoopActivity.this, horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent();

        Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];
        int resource_id_horoscoop = HoroscoopFuncties.getResourceId(horoscoop);
        intent.putExtra(MainActivity.EXTRA_HOROSCOOP, resource_id_horoscoop);

        setResult(RESULT_OK, intent);
        finish();
    }

    class ViewHolder {
        public ImageView imageviewHoroscoop= null;
        public TextView textViewNaamHoroscoop = null;
        public Button btnToonInfo = null;

        public ViewHolder(View row) {
            this.imageviewHoroscoop = (ImageView) row.findViewById(R.id.imagesView_horoscoop);
            this.textViewNaamHoroscoop = (TextView) row.findViewById(R.id.textView_naam_horoscoop);
            this.btnToonInfo = (Button) row.findViewById(R.id.button_info);
        }
    }
}
