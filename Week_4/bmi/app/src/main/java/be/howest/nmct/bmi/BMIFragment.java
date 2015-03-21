package be.howest.nmct.bmi;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMIFragment extends Fragment {

    private EditText height;
    private EditText mass;
    private TextView index;
    private TextView category;
    private Button update;
    private ImageView image;

    public BMIFragment() {
        // Required empty public constructor
    }

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);

        Log.d(getClass().getSimpleName(), "OnCreateView");

        this.height = (EditText) v.findViewById(R.id.height);
        this.mass = (EditText) v.findViewById(R.id.mass);

        this.index = (TextView) v.findViewById(R.id.index_value);
        this.category = (TextView) v.findViewById(R.id.category_value);
        this.update = (Button) v.findViewById(R.id.update);
        this.image = (ImageView) v.findViewById(R.id.image);

        this.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                berekenBMI();
            }
        });

        SharedPreferences setting = getActivity().getSharedPreferences(PREFS_NAME, 0);

        if (setting.contains("height") && setting.contains("mass")) {
            float tempHeight = setting.getFloat("height", 0);
            this.height.setText(Float.toString(tempHeight));

            int tempMass = setting.getInt("mass", 0);
            this.mass.setText(Integer.toString(tempMass));
        }

        return v;
    }

    private void berekenBMI() {
        //als de height of de massa leeg is, niets doen
        //if (!(this.height.getText().toString().equals("") || this.mass.getText().toString().equals(""))) {
        if (!(this.height.getText().toString().isEmpty() || this.mass.getText().toString().isEmpty())) {

            float tempHeight = Float.parseFloat(this.height.getText().toString());
            int tempMass = Integer.parseInt(this.mass.getText().toString());
            BMIInfo bmi = new BMIInfo(tempHeight, tempMass);

            bmi.recalculate();
            BMIInfo.Category categoryBMI = BMIInfo.Category.getCategory(bmi.bmiIndex);

            this.index.setText("" + bmi.bmiIndex);
            this.category.setText(categoryBMI.toString());

            int resourceId = getResourceId(categoryBMI);
            this.image.setImageResource(resourceId);
        }
    }

    private int getResourceId(BMIInfo.Category categoryBMI) {

        int resourceId;

        switch (categoryBMI) {

            case ERNSTIGONDERGEWICHT:
                resourceId = R.drawable.silhouette_1;
                break;
            case GROOTONDERGEWICHT:
                resourceId = R.drawable.silhouette_2;
                break;
            case ONDERGEWICHT:
                resourceId = R.drawable.silhouette_3;
                break;
            case NORMAAL:
                resourceId = R.drawable.silhouette_4;
                break;
            case OVERGEWICHT:
                resourceId = R.drawable.silhouette_5;
                break;
            case MATIGOVERGEWICHT:
                resourceId = R.drawable.silhouette_6;
                break;
            case ERNSTIGOVERGEWICHT:
                resourceId = R.drawable.silhouette_7;
                break;
            case ZEERGROOTOVERGEWICHT:
                resourceId = R.drawable.silhouette_8;
                break;
            default:
                resourceId = R.drawable.silhouette_4;
                break;
        }
        return resourceId;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(getClass().getSimpleName(), "onAttach");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //d staat voor debug
        Log.d(getClass().getSimpleName(), "OnCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getClass().getSimpleName(), "OnActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClass().getSimpleName(), "onResume");
        berekenBMI();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(), "onStop");

        //als lengte en gewicht niet leeg zijn, ze bewaren
        if (!(this.height.getText().toString().equals("") || this.mass.getText().toString().equals(""))) {
            SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();

            float tempHeight = Float.parseFloat(this.height.getText().toString());
            int tempMass = Integer.parseInt(this.mass.getText().toString());
            editor.putFloat("height", tempHeight);
            editor.putInt("mass", tempMass);

            editor.apply();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getClass().getSimpleName(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getClass().getSimpleName(), "onDetach");
    }
}