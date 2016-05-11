package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Modes;
import edu.rosehulman.fisherds.firemote.models.Monitor;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionFragment extends BaseFragment implements FirebaseState.ModesChangeListener, FirebaseState.MonitorChangeListener {

    private Button mGoAutonomousButton;
    private Button mChangeTeamButton;
    private Button mPerformTestButton;
    private LinearLayout mCompetitionLinearLayout;
    private boolean mCurrentAutonomousState = false;
    private ValueEventListener mTeamListener;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        getFirebaseState().setModesDelegate(this);
//        getFirebaseState().setMonitorDelegate(this);  // Would do nothing since the embedded fragment would steal the delegate anyway.
        // Instead the ObserveOnlyFragment will call our onMonitorChanged method for us.

        // Hacky little listener to just listen for the team string.
        FirebaseState firebaseState = getFirebaseState();
        Firebase firebaseRef = firebaseState.getRobotFirebaseRef();
        mTeamListener = firebaseRef.child("monitor").child("team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String teamString = (String)dataSnapshot.getValue();
                    if (teamString.equalsIgnoreCase("red")) {
                        mChangeTeamButton.setBackgroundResource(R.drawable.red_button);
                    } else if (teamString.equalsIgnoreCase("blue")) {
                        mChangeTeamButton.setBackgroundResource(R.drawable.blue_button);
                    }
                } catch (Exception e) {
                    Log.e(MainActivity.TAG, "Invalid team string");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        getFirebaseState().setModesDelegate(null);

        FirebaseState firebaseState = getFirebaseState();
        Firebase firebaseRef = firebaseState.getRobotFirebaseRef();
        firebaseRef.removeEventListener(mTeamListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competition, container, false);

        mGoAutonomousButton = (Button)view.findViewById(R.id.go_autonomous_button);
        mChangeTeamButton = (Button) view.findViewById(R.id.change_team_button);
        mPerformTestButton = (Button) view.findViewById(R.id.perform_ball_test_button);
        mCompetitionLinearLayout = (LinearLayout)view.findViewById(R.id.competition_buttons_linearlayout);

        Fragment observeOnlyFragment = ObserveOnlyFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.observe_only_fragment_container, observeOnlyFragment).commit();

        mGoAutonomousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setPressButton("goStop");
                getFirebaseState().setAutonomousMode(true);
            }
        });

        mChangeTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setPressButton("teamChange");
            }
        });

        mPerformTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirebaseState().setPressButton("performBallTest");
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
            mCurrentAutonomousState = modes.isAutonomous;
            if (mCurrentAutonomousState) {
                mCompetitionLinearLayout.setVisibility(View.GONE);
            } else {
                mCompetitionLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMonitorChanged(Monitor monitor) {
        Log.d(MainActivity.TAG, "Testing fragment monitor updated");
        if (monitor != null) {
            Log.d(MainActivity.TAG, "state = " + monitor.state);
            if (monitor.state.equalsIgnoreCase("READY_FOR_MISSION")) {
                mGoAutonomousButton.setBackgroundResource(R.drawable.green_button);
                mGoAutonomousButton.setText("Go!");
            } else {
                mGoAutonomousButton.setBackgroundResource(R.drawable.red_button);
                mGoAutonomousButton.setText("Stop");
            }
        }
    }
}
