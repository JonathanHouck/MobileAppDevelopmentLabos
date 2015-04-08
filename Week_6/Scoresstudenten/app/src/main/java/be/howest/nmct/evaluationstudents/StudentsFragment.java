package be.howest.nmct.evaluationstudents;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import be.howest.nmct.evaluationstudents.loader.Contract;
import be.howest.nmct.evaluationstudents.loader.StudentsLoader;

public class StudentsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentAdaptar mStudentAdapter;

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

        mStudentAdapter = new StudentAdaptar(getActivity(), R.layout.row_student, null, columns, textView_ids, 0);
        setListAdapter(mStudentAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mStudentAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mStudentAdapter.swapCursor(null);
    }

    class StudentAdaptar extends SimpleCursorAdapter {
        public StudentAdaptar(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ImageView icon = (ImageView) view.findViewById(R.id.imageView_student);

            int colnr = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL);
            double score = cursor.getDouble(colnr);
            DecimalFormat df = new DecimalFormat("##.00");
            TextView textviewTtotaleScore = (TextView) view.findViewById(R.id.textView_score);
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
}
