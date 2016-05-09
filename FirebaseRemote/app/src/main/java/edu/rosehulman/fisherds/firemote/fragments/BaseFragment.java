package edu.rosehulman.fisherds.firemote.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;

public class BaseFragment  extends Fragment {

    protected FirebaseState mFirebaseState;

    public void setFirebaseState(FirebaseState firebaseState) {
        mFirebaseState = firebaseState;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.TAG, "Manually set the firebase state.");
        mFirebaseState = ((MainActivity)getActivity()).getFirebaseState();
    }
}
