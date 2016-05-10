package edu.rosehulman.fisherds.firemote.models;

import android.util.Log;

import com.firebase.client.DataSnapshot;

import java.util.Map;

import edu.rosehulman.fisherds.firemote.MainActivity;

/**
 * Created by fisherds on 5/8/2016.
 */
public class Modes {

    public boolean isFrozen = false;
    public boolean isAutonomous = false;


    public Modes(DataSnapshot dataSnapshot) {
        try {
            Map<String, Boolean> modesMap = (Map<String, Boolean>) dataSnapshot.getValue();
            isFrozen = modesMap.get("frozen").booleanValue();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Loading frozen mode failed.  Value not set.");
        }
        try {
            Map<String, Boolean> modesMap = (Map<String, Boolean>) dataSnapshot.getValue();
            isAutonomous = modesMap.get("autonomous").booleanValue();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Loading autonomous mode failed.  Value not set.");
        }
    }
}
