package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Modes;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionFragment extends BaseFragment implements FirebaseState.ModesChangeListener {


    public CompetitionFragment() {
        // Required empty public constructor
    }

    public static CompetitionFragment newInstance() {
        CompetitionFragment fragment = new CompetitionFragment();
        // Custom initialization.
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFirebaseState().setModesDelegate(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition, container, false);
    }

    @Override
    public void onModesChanged(Modes modes) {
        Log.d(MainActivity.TAG, "Modes changed");
    }
}
