package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.fisherds.firemote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RobotTestingFragment extends BaseFragment {


    public RobotTestingFragment() {
        // Required empty public constructor
    }

    public static RobotTestingFragment newInstance() {
        RobotTestingFragment fragment = new RobotTestingFragment();
        // Spot for custom initialization.
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
        return inflater.inflate(R.layout.fragment_robot_testing, container, false);
    }

}
