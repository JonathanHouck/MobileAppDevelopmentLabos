package be.howest.nmct;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.howest.nmct.data.Data;

public class SelectGeboortejaarFragment extends ListFragment {

    private OnSelectGeboortejaarFragmentListener mListener;

    public SelectGeboortejaarFragment() {
        // Required empty public constructor
    }

    public static SelectGeboortejaarFragment newInstance() {
        SelectGeboortejaarFragment fragment = new SelectGeboortejaarFragment();
        return fragment;
    }

    private final static List<String> GEBOORTEJAREN;
    private ListAdapter myListAdapter;

    static {
        GEBOORTEJAREN = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for (int jaar = 1900; jaar <= Calendar.getInstance().get(Calendar.YEAR); jaar++) {
            GEBOORTEJAREN.add("" + jaar);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, GEBOORTEJAREN);
        setListAdapter(myListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        String sGeboortejaar = GEBOORTEJAREN.get(position);
        mListener.demandMain(sGeboortejaar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSelectGeboortejaarFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSelectGeboortejaarFragmentListener");
        }
    }

    public interface OnSelectGeboortejaarFragmentListener {
        public void demandMain(String jaar);
    }
}
