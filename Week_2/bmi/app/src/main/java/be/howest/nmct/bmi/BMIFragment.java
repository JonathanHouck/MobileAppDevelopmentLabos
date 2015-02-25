package be.howest.nmct.bmi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jonathan on 21/02/2015.
 */
public class BMIFragment extends Fragment {

    private EditText height;
    private EditText mass;
    private TextView index;
    private TextView category;
    private Button update;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);

        //int SDK_INT = android.os.Build.VERSION.SDK_INT; //testje: versie 21

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

        return v;
    }

    private void berekenBMI() {
        BMIInfo bmi = new BMIInfo(Float.parseFloat(height.getText().toString()), Integer.parseInt(mass.getText().toString()));
        bmi.recalculate();
        BMIInfo.Category categoryBMI = BMIInfo.Category.getCategory(bmi.bmiIndex);

        this.index.setText("" + bmi.bmiIndex);
        this.category.setText(categoryBMI.toString());

        int resourceId = getResourceId(categoryBMI);
        this.image.setImageResource(resourceId);
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
}
