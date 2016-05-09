package edu.rosehulman.fisherds.firemote.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Params;


public class ParamsFragment extends BaseFragment implements FirebaseState.ParamsChangeListener {

    public ParamsFragment() {
        // Required empty public constructor
    }

    public static ParamsFragment newInstance(FirebaseState firebaseState) {
        ParamsFragment fragment = new ParamsFragment();

        // Note: The recommended patern is to use the arguments and recover them in onCreate
        //  I didn't want to do that because I wanted an object.
        fragment.setFirebaseState(firebaseState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseState.setParamsDelegate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_params, container, false);
    }

    @Override
    public void onParamsChanged(Params params) {
        Toast.makeText(ParamsFragment.this.getActivity(), "Params are updated", Toast.LENGTH_SHORT).show();
        Log.d(MainActivity.TAG, "Params = " + params);
    }
}
