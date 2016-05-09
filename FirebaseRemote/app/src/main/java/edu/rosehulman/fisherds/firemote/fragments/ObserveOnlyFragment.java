package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ObserveOnlyFragment extends BaseFragment {


    public ObserveOnlyFragment() {
    }

    public static ObserveOnlyFragment newInstance(FirebaseState firebaseState) {
        ObserveOnlyFragment fragment = new ObserveOnlyFragment();

        // Note: The recommended patern is to use the arguments and recover them in onCreate
        //  I didn't want to do that because I wanted an object.
        fragment.setFirebaseState(firebaseState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_observe_only, container, false);
    }

}
