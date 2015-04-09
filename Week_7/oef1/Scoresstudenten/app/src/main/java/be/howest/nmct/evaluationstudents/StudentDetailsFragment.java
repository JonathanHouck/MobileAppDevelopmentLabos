package be.howest.nmct.evaluationstudents;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.howest.nmct.evaluationstudents.admin.Student;
import be.howest.nmct.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.evaluationstudents.loader.Contract;
import be.howest.nmct.evaluationstudents.loader.ModulePuntLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mModulePuntAdapter;

    private static final String EXTRA_EMAIL = "be.howest.nmct.week7oef1.EMAIL";

    private TextView textView_naam;
    private GridView gridView_modules;
    private TextView textView_diplomagraad;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }

    public static StudentDetailsFragment newInstance(String sEmailStudent) {
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_EMAIL, sEmailStudent);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_details, container, false);

        textView_naam = (TextView) v.findViewById(R.id.textView_naam);
        gridView_modules = (GridView) v.findViewById(R.id.gridView_modules);
        textView_diplomagraad = (TextView) v.findViewById(R.id.textView_diplomagraad);

        //email student ophalen en naam en voornaam in textView plaatsen
        String emailStudent = getArguments().getString(EXTRA_EMAIL);
        Student s = StudentAdmin.getStudent(emailStudent);

        textView_naam.setText(s.getNaamStudent() + " " + s.getVoornaamStudent());

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[] {
                Contract.ModulePuntColumns.COLUMN_MODULE_NAAM,
                Contract.ModulePuntColumns.COLUMN_MODULE_SCORE,
                Contract.ModulePuntColumns.COLUMN_MODULE_STUDIEPUNTEN
        };

        int[] textView_ids = new int[] {
                R.id.textView_modulenaam,
                R.id.textView_modulepunt,
                R.id.textView_diplomagraad
        };

        mModulePuntAdapter = new ModulePuntAdapter(getActivity(), R.layout.cel_modules, null, columns, textView_ids, 0);
        gridView_modules.setAdapter(mModulePuntAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ModulePuntLoader(getActivity(), getArguments().getString(EXTRA_EMAIL));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mModulePuntAdapter.swapCursor(data);
        printDiplomaGraad(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mModulePuntAdapter.swapCursor(null);
    }

    class ModulePuntAdapter extends SimpleCursorAdapter {

        public ModulePuntAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            TextView textView_modulenaam = (TextView) view.findViewById(R.id.textView_modulenaam);
            TextView textView_modulepunt = (TextView) view.findViewById(R.id.textView_modulepunt);

            int colNrModuleNaam = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_NAAM);
            int colNrModulePunt = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_SCORE);

            String sModuleNaam = cursor.getString(colNrModuleNaam);
            String sModuleScore = cursor.getString(colNrModulePunt);
            double dModuleScore = Double.parseDouble(sModuleScore);

            if (dModuleScore < 10) {
                textView_modulepunt.setTextColor(Color.RED);
            }

            textView_modulenaam.setText(sModuleNaam);
            textView_modulepunt.setText(sModuleScore);
        }
    }

    private void printDiplomaGraad(Cursor cursor) {
        ArrayList<Integer> studiepuntenModules = new ArrayList<>();
        ArrayList<Double> scoresModules = new ArrayList<>();

        int aantalRijen = cursor.getCount();
        for (int i = 0; i < aantalRijen; i++) {
            cursor.moveToPosition(i);
            int colnr1 = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_STUDIEPUNTEN);
            studiepuntenModules.add(cursor.getInt(colnr1));

            int colnr2 = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_SCORE);
            scoresModules.add(cursor.getDouble(colnr2));
        }

        int iTotaalStudiepunten = 0;
        for (int aantalSP : studiepuntenModules) iTotaalStudiepunten += aantalSP;

        double dTotaleScore = 0;
        for (int iTeller = 0; iTeller < scoresModules.size(); iTeller += 1) {
            double dGewicht = (double) studiepuntenModules.get(iTeller) / iTotaalStudiepunten;
            dTotaleScore += (scoresModules.get(iTeller) * dGewicht);
        }

        textView_diplomagraad.setText(Student.DIPLOMAGRAAD.getDiplomagraad((float) dTotaleScore).getOmschrijving());
    }
}
