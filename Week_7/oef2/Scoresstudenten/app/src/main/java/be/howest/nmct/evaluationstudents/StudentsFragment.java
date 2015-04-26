package be.howest.nmct.evaluationstudents;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import be.howest.nmct.evaluationstudents.admin.Student;
import be.howest.nmct.evaluationstudents.loader.Contract;
import be.howest.nmct.evaluationstudents.loader.StudentsLoader;

public class StudentsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private OnStudentFragmentListener mListener;
    private StudentAdapter mStudentAdapter;

    private Student.DIPLOMAGRAAD diplomagraadStudents;
    private String DIPLOMAGRAAD = "key_diplo";

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[] {
                Contract.StudentColumns.COLUMN_STUDENT_NAAM,
                Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
                Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
                Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL
        };

        int[] textView_ids = new int[] {
                R.id.textView_naam,
                R.id.textView_voornaam,
                R.id.textView_email,
                R.id.textView_score
        };

        mStudentAdapter = new StudentAdapter(getActivity(), R.layout.row_student, null, columns, textView_ids, 0);
        setListAdapter(mStudentAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (args == null) {
            //geen diplomagraad
            return new StudentsLoader(getActivity());
        } else {
            //wel met diplomagraad
            Student.DIPLOMAGRAAD graad = Student.DIPLOMAGRAAD.values()[args.getInt(DIPLOMAGRAAD)];
            return new StudentsLoader(getActivity(), graad);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mStudentAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mStudentAdapter.swapCursor(null);
    }

    class StudentAdapter extends SimpleCursorAdapter {
        public StudentAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            TextView textviewTtotaleScore = (TextView) view.findViewById(R.id.textView_score);
            ImageView icon = (ImageView) view.findViewById(R.id.imageView_student);

            int colNr = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL);
            double score = cursor.getDouble(colNr);
            DecimalFormat df = new DecimalFormat("##.00");

            textviewTtotaleScore.setText(df.format(score));

            if (score < 8) {
                icon.setImageResource(R.drawable.student_red);
            } else if (score < 10) {
                icon.setImageResource(R.drawable.student_orange);
            } else {
                icon.setImageResource(R.drawable.student_green);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor c = (Cursor) mStudentAdapter.getItem(position);
        String selectedStudentEmail = c.getString(c.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_EMAIL));
        mListener.demandStudentDetail(selectedStudentEmail);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStudentFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStudentFragmentListener");
        }
    }

    public interface OnStudentFragmentListener {
        public void demandStudentDetail(String sEmailStudent);
    }

    public void setDiplomagraadStudents(Student.DIPLOMAGRAAD diplomagraadStudents) {
        this.diplomagraadStudents = diplomagraadStudents;
        Bundle bundle = new Bundle();
        bundle.putInt(DIPLOMAGRAAD, diplomagraadStudents.ordinal());
        getLoaderManager().restartLoader(0, bundle, this);
    }
}
