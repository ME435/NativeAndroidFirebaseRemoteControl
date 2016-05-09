package edu.rosehulman.fisherds.firemote.models;

import android.util.Log;

import com.firebase.client.DataSnapshot;

import java.util.Map;

import edu.rosehulman.fisherds.firemote.MainActivity;

/**
 * Created by fisherds on 5/8/2016.
 */
public class Params {
    public int leftDutyCycle;
    public int rightDutyCycle;
    public int targetH;
    public int targetS;
    public int targetV;
    public int rangeH;
    public int rangeS;
    public int rangeV;

    public Params(DataSnapshot dataSnapshot) {
        if (!setParamsFromDataSnapshot(dataSnapshot)) {
            // This params object is worthless
            Log.e(MainActivity.TAG, "Fix your Firebase backend!");
            leftDutyCycle = 0;
            rightDutyCycle = 0;
            targetH = 0;
            targetS = 0;
            targetV = 0;
            rangeH = 0;
            rangeS = 0;
            rangeV = 0;
        }
    }

    public boolean setParamsFromDataSnapshot(DataSnapshot dataSnapshot) {
        try {
            Map<String, Object> paramsMap = (Map<String, Object>) dataSnapshot.getValue();
            Map<String, Long> driveMap = (Map<String, Long>) paramsMap.get("driveStraight");
            Map<String, Map<String, Long>> imgMap = (Map<String, Map<String, Long>>) paramsMap.get("imgRec");
            Map<String, Long> targetMap = imgMap.get("target");
            Map<String, Long> rangeMap = imgMap.get("range");
            leftDutyCycle = driveMap.get("leftDutyCycle").intValue();
            rightDutyCycle = driveMap.get("rightDutyCycle").intValue();
            targetH = targetMap.get("h").intValue();
            targetS = targetMap.get("s").intValue();
            targetV = targetMap.get("v").intValue();
            rangeH = rangeMap.get("h").intValue();
            rangeS = rangeMap.get("s").intValue();
            rangeV = rangeMap.get("v").intValue();
            Log.d(MainActivity.TAG, "Successfully received the params from Firebase");
            return true;
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Loading params failed.  No params were set.");
            return false;
        }
    }
}
