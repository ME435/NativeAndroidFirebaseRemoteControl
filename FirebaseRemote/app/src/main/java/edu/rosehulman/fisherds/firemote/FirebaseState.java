package edu.rosehulman.fisherds.firemote;

import android.app.Activity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.fisherds.firemote.models.Commands;
import edu.rosehulman.fisherds.firemote.models.Modes;
import edu.rosehulman.fisherds.firemote.models.Monitor;
import edu.rosehulman.fisherds.firemote.models.Params;

/**
 * Created by fisherds on 5/8/2016.
 */
public class FirebaseState {

    private final Firebase mRobotFirebaseRef;
    private ParamsChangeListener mParamsChangeListener;
    private MonitorChangeListener mMonitorChangeListener;
    private ModesChangeListener mModesChangeListener;
    private CommandsChangeListener mCommandsChangeListener;


    public FirebaseState(Activity activity, String firebasePath, String robotName) {
        // Creates the one and only FirebaseStateListener
        // Setup Firebase as the very first thing.
        Firebase.setAndroidContext(activity);
        if (!Firebase.getDefaultConfig().isPersistenceEnabled()) {
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }
        Firebase topLevelFirebaseRef = new Firebase(firebasePath);
        mRobotFirebaseRef = topLevelFirebaseRef.child(robotName);

        addListeners();
    }

    private void addListeners() {

        // Params
        mRobotFirebaseRef.child("params").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mParamsChangeListener != null) {
                    mParamsChangeListener.onParamsChanged(new Params(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Monitor
        mRobotFirebaseRef.child("monitor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mMonitorChangeListener != null) {
                    mMonitorChangeListener.onMonitorChanged(new Monitor(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Commands
        mRobotFirebaseRef.child("commands").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mCommandsChangeListener != null) {
                    mCommandsChangeListener.onCommandsChanged(new Commands(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Modes
        mRobotFirebaseRef.child("modes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mModesChangeListener != null) {
                    mModesChangeListener.onModesChanged(new Modes(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }


    public interface ParamsChangeListener {
        void onParamsChanged(Params params);
    }

    public interface MonitorChangeListener {
        void onMonitorChanged(Monitor monitor);
    }

    public interface ModesChangeListener {
        void onModesChanged(Modes modes);
    }

    public interface CommandsChangeListener {
        void onCommandsChanged(Commands commands);
    }

    public void setParamsChangeListener(ParamsChangeListener paramsChangeListener) {
        mParamsChangeListener = paramsChangeListener;
    }

    public void setMonitorChangeListener(MonitorChangeListener monitorChangeListener) {
        mMonitorChangeListener = monitorChangeListener;
    }

    public void setModesChangeListener(ModesChangeListener modesChangeListener) {
        mModesChangeListener = modesChangeListener;
    }

    public void setCommandsChangeListener(CommandsChangeListener commandsChangeListener) {
        mCommandsChangeListener = commandsChangeListener;
    }
}
