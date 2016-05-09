package edu.rosehulman.fisherds.firemote;

import android.app.Activity;
import android.util.Log;

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

    private Firebase mRobotFirebaseRef;

    // Connection to which fragment is setup to listen for updates.
    private ParamsChangeListener mParamsDelegate;
    private MonitorChangeListener mMonitorDelegate;
    private ModesChangeListener mModesDelegate;
    private CommandsChangeListener mCommandsDelegate;

    // References to the internal Firebase listeners that are setup for this path.
    private ValueEventListener mParamsValueEventListener;
    private ValueEventListener mMonitorValueEventListener;
    private ValueEventListener mCommandsValueEventListener;
    private ValueEventListener mModesValueEventListener;

    // Last value received for each object.
    private Params mParams;
    private Monitor mMonitor;
    private Modes mModes;
    private Commands mCommands;


    public FirebaseState(Activity activity) {
        // Creates the one and only FirebaseStateListener
        // Setup Firebase as the very first thing.
        Firebase.setAndroidContext(activity);
        if (!Firebase.getDefaultConfig().isPersistenceEnabled()) {
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }
    }

    /**
     * Resets the all related listeners and starts a new Firebase ref from scratch using the
     * provided path.
     *
     * @param firebasePath
     * @param robotName
     */
    public void initialize(String firebasePath, String robotName) {
        reset();

        Firebase topLevelFirebaseRef = new Firebase("https://" + firebasePath + ".firebaseio.com");
        mRobotFirebaseRef = topLevelFirebaseRef.child(robotName);

        // Params
        mParamsValueEventListener = mRobotFirebaseRef.child("params")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mParams = new Params(dataSnapshot);
                Log.d(MainActivity.TAG, "Params changed:  " + mParams);
                if (mParamsDelegate != null) {
                    mParamsDelegate.onParamsChanged(mParams);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Monitor
        mMonitorValueEventListener = mRobotFirebaseRef.child("monitor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMonitor = new Monitor(dataSnapshot);
                Log.d(MainActivity.TAG, "Monitor values changed:  " + mMonitor);
                if (mMonitorDelegate != null) {
                    mMonitorDelegate.onMonitorChanged(mMonitor);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Commands
        mCommandsValueEventListener = mRobotFirebaseRef.child("commands").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCommands = new Commands(dataSnapshot);
                Log.d(MainActivity.TAG, "Commands changed:  " + mCommands);
                if (mCommandsDelegate != null) {
                    mCommandsDelegate.onCommandsChanged(mCommands);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

        // Modes
        mModesValueEventListener = mRobotFirebaseRef.child("modes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mModes = new Modes(dataSnapshot);
                Log.d(MainActivity.TAG, "Modes changed:  " + mModes);
                if (mModesDelegate != null) {
                    mModesDelegate.onModesChanged(mModes);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }

    /**
     * Completely clears the state of the FirebaseState
     */
    private void reset() {
        Log.d(MainActivity.TAG, "Clear the old Firebase ref listeners and delegates");
        // Remove all of the internal listeners.
        if (mParamsValueEventListener != null && mCommandsValueEventListener != null &&
                mMonitorValueEventListener != null && mModesValueEventListener != null &&
                mRobotFirebaseRef != null) {
            mRobotFirebaseRef.removeEventListener(mParamsValueEventListener);
            mRobotFirebaseRef.removeEventListener(mMonitorValueEventListener);
            mRobotFirebaseRef.removeEventListener(mCommandsValueEventListener);
            mRobotFirebaseRef.removeEventListener(mModesValueEventListener);
        }

        // Remove all of the external Fragment listener connections.
        mParamsDelegate = null;
        mMonitorDelegate = null;
        mModesDelegate = null;
        mCommandsDelegate = null;

        mRobotFirebaseRef = null;
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

    public void setParamsDelegate(ParamsChangeListener paramsChangeListener) {
        mParamsDelegate = paramsChangeListener;
    }

    public void setMonitorDelegate(MonitorChangeListener monitorChangeListener) {
        mMonitorDelegate = monitorChangeListener;
    }

    public void setModesDelegate(ModesChangeListener modesChangeListener) {
        mModesDelegate = modesChangeListener;
    }

    public void setCommandsDelegate(CommandsChangeListener commandsChangeListener) {
        mCommandsDelegate = commandsChangeListener;
    }

    @Override
    public String toString() {
        if (mRobotFirebaseRef == null) {
            return "Firebase State is not initialized yet.";
        } else {
            return "Firebase State is pointing to " + mRobotFirebaseRef.toString();
        }
    }
}
