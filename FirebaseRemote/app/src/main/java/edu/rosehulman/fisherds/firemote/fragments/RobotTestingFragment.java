package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Modes;
import edu.rosehulman.fisherds.firemote.models.Monitor;


/**
 * A simple {@link Fragment} subclass.
 */
public class RobotTestingFragment extends BaseFragment implements FirebaseState.ModesChangeListener, FirebaseState.MonitorChangeListener {

    private Button mFreezeResumeButton;
    private Button mGoStopButton;
    private boolean mCurrentFrozenState = false;

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
    public void onStart() {
        super.onStart();
        getFirebaseState().setModesDelegate(this);
//        getFirebaseState().setMonitorDelegate(this);  // Would do nothing since the embedded fragment would steal the delegate anyway.
        // Instead the ObserveOnlyFragment will call our onMonitorChanged method for us.

    }

    @Override
    public void onStop() {
        super.onStop();
        getFirebaseState().setModesDelegate(null);
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
                getFirebaseState().setFrozenMode(!mCurrentFrozenState);
            }
        });

        mGoStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setPressButton("goStop");
            }
        });

        // Load the initial state.
        onModesChanged(getFirebaseState().getModes());
        onMonitorChanged(getFirebaseState().getMonitor());
        return view;
    }

    @Override
    public void onModesChanged(Modes modes) {
        if (modes != null) {
            mCurrentFrozenState = modes.isFrozen;
            if (modes.isFrozen) {
                mFreezeResumeButton.setBackgroundResource(R.drawable.red_button);
                mFreezeResumeButton.setText("Resume");
            } else {
                mFreezeResumeButton.setBackgroundResource(R.drawable.blue_button);
                mFreezeResumeButton.setText("Freeze!");
            }
        }
    }

    @Override
    public void onMonitorChanged(Monitor monitor) {
        Log.d(MainActivity.TAG, "Testing fragment monitor updated");
        if (monitor != null) {
            Log.d(MainActivity.TAG, "state = " + monitor.state);
            if (monitor.state.equalsIgnoreCase("READY_FOR_MISSION")) {
                mGoStopButton.setBackgroundResource(R.drawable.green_button);
                mGoStopButton.setText("Go!");
            } else {
                mGoStopButton.setBackgroundResource(R.drawable.red_button);
                mGoStopButton.setText("Stop");
            }
        }
    }
}
