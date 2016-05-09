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
public class ManualDriveFragment extends BaseFragment {


    public ManualDriveFragment() {
        // Required empty public constructor
    }

    public static ManualDriveFragment newInstance(FirebaseState firebaseState) {
        ManualDriveFragment fragment = new ManualDriveFragment();

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manual_drive, container, false);
    }

}
