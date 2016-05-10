package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Modes;
import edu.rosehulman.fisherds.firemote.models.Monitor;


/**
 * A simple {@link Fragment} subclass.
 */
public class RobotTestingFragment extends BaseFragment implements FirebaseState.ModesChangeListener, FirebaseState.MonitorChangeListener {


    private Button mFreezeResumeButton;
    private Button mGoStopButton;

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
        getFirebaseState().setMonitorDelegate(this);
        getFirebaseState().setModesDelegate(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_robot_testing, container, false);

        mFreezeResumeButton = (Button)view.findViewById(R.id.freeze_resume_button);
        mGoStopButton = (Button) view.findViewById(R.id.go_stop_button);

        Fragment observeOnlyFragment = ObserveOnlyFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.observe_only_fragment_container, observeOnlyFragment).commit();

        mFreezeResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setFrozenMode(true);
            }
        });

        mGoStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setPressButton("goStop");
            }
        });

        return view;
    }

    @Override
    public void onModesChanged(Modes modes) {
        // TODO: Update the Freeze/Resume button as appropriate.
    }

    @Override
    public void onMonitorChanged(Monitor monitor) {
        // TODO: Update the Go/Stop button as appropriate.
        // Only check to see if the state changed from READY_FOR_MISSION or not.
    }
}
