package edu.rosehulman.fisherds.firemote.fragments;

import android.support.v4.app.Fragment;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;

public class BaseFragment  extends Fragment {

    protected FirebaseState getFirebaseState() {
        return ((MainActivity)getActivity()).getFirebaseState();
    }
}
