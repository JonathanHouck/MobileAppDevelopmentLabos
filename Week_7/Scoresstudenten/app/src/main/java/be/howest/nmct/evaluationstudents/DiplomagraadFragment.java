package be.howest.nmct.evaluationstudents;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import be.howest.nmct.evaluationstudents.admin.ModulePunt;
import be.howest.nmct.evaluationstudents.admin.Student;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiplomagraadFragment extends ListFragment {

    public OnDiplomagraadFragmentListener mListener;
    public DiplomgagraadAdapter myAdapter;

    private ListView lv;

    public DiplomagraadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diplomagraad, container, false);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAdapter = new DiplomgagraadAdapter();
        setListAdapter(myAdapter);
    }

    class DiplomgagraadAdapter extends ArrayAdapter<Student.DIPLOMAGRAAD> {

        public DiplomgagraadAdapter() {
            super(getActivity(), R.layout.row_diplomagraad, R.id.textView_diplomagraad_drawer, Student.DIPLOMAGRAAD.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Student.DIPLOMAGRAAD diplomagraad = Student.DIPLOMAGRAAD.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textView_select_diplomagraad = holder.textView_diplomagraad_drawer;
            textView_select_diplomagraad.setText(diplomagraad.getNaam());

            return row;
        }
    }

    class ViewHolder {
        public TextView textView_diplomagraad_drawer = null;

        public ViewHolder(View row) {
            this.textView_diplomagraad_drawer = (TextView) row.findViewById(R.id.textView_diplomagraad_drawer);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Student.DIPLOMAGRAAD diplomagraad = Student.DIPLOMAGRAAD.values()[position];
        mListener.demandStudentsDiplomgraad(diplomagraad);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDiplomagraadFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStudentFragmentListener");
        }
    }

    public interface OnDiplomagraadFragmentListener {
        public void demandStudentsDiplomgraad(Student.DIPLOMAGRAAD diplomagraad);
    }
}
