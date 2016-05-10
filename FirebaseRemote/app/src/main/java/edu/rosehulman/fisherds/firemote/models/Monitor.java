package edu.rosehulman.fisherds.firemote.models;

import android.util.Log;

import com.firebase.client.DataSnapshot;

import java.util.Map;

import edu.rosehulman.fisherds.firemote.MainActivity;

/**
 * Created by fisherds on 5/8/2016.
 */
public class Monitor {

    /**
     * An enum used for variables when a ball color needs to be referenced.
     */
    public enum BallColor {
        NONE, BLUE, RED, YELLOW, GREEN, BLACK, WHITE
    }

    public String state;
    public double gpsHeading;
    public double gpsX;
    public double gpsY;
    public int gpsHeadingCount;
    public int gpsTotalCount;
    public boolean coneFound;
    public double coneLeftRight;
    public double coneTopBottom;
    public double coneSizePercentage;
    public double sensorHeading;
    public long stateTimeMs;
    public String matchTime;
    public int leftDutyCycle;
    public int rightDutyCycle;
    public BallColor[] golfBallColors = new BallColor[]{BallColor.NONE, BallColor.NONE, BallColor.NONE};

    public Monitor(DataSnapshot dataSnapshot) {
        if (!setMonitorFromDataSnapshot(dataSnapshot)) {
            // This monitor object is worthless
            Log.e(MainActivity.TAG, "Fix your Firebase backend!");
            state = "Unknown";
            gpsHeading = 0.0;
            gpsX = 0.0;
            gpsY = 0.0;
            gpsHeadingCount = 0;
            gpsTotalCount = 0;
            coneFound = false;
            coneLeftRight = 0.0;
            coneTopBottom = 0.0;
            coneSizePercentage = 0.0;
            sensorHeading = 0.0;
            stateTimeMs = 0;
            matchTime = "5:00";
            leftDutyCycle = 0;
            rightDutyCycle = 0;
            golfBallColors = new BallColor[]{BallColor.NONE, BallColor.NONE, BallColor.NONE};
        }
    }

    public boolean setMonitorFromDataSnapshot(DataSnapshot dataSnapshot) {
//        try {
        Map<String, Object> monitorMap = (Map<String, Object>) dataSnapshot.getValue();
        state = (String) monitorMap.get("state");
        Map<String, String> golfBallMap = (Map<String, String>) monitorMap.get("golfBallColors");
        Map<String, Object> gpsMap = (Map<String, Object>) monitorMap.get("gps");
        Map<String, Object> imgRecMap = (Map<String, Object>) monitorMap.get("imgRec");
        Map<String, Double> orientationMap = (Map<String, Double>) monitorMap.get("orientation");
        Map<String, Object> timeMap = (Map<String, Object>) monitorMap.get("time");
        Map<String, Long> wheelSpeedMap = (Map<String, Long>) monitorMap.get("wheelSpeed");

        gpsHeading = ((Double)gpsMap.get("gpsHeading")).doubleValue();
        gpsX = ((Double)gpsMap.get("x")).doubleValue();
        gpsY = ((Double)gpsMap.get("y")).doubleValue();
        gpsHeadingCount = ((Long)gpsMap.get("headingCount")).intValue();
        gpsTotalCount = ((Long)gpsMap.get("totalCount")).intValue();
        coneFound =  ((Boolean)imgRecMap.get("found")).booleanValue();
        coneLeftRight = ((Double)imgRecMap.get("leftRight")).doubleValue();
        coneTopBottom = ((Double)imgRecMap.get("topBottom")).doubleValue();
        coneSizePercentage = ((Double)imgRecMap.get("size")).doubleValue();
        sensorHeading = ((Double)orientationMap.get("sensorHeading")).doubleValue();
        stateTimeMs = ((Long)timeMap.get("stateTimeMs")).longValue();
        matchTime = ((String)timeMap.get("matchTime"));
        leftDutyCycle = ((Long)wheelSpeedMap.get("leftDutyCycle")).intValue();
        rightDutyCycle = ((Long)wheelSpeedMap.get("rightDutyCycle")).intValue();
        String location1Color = golfBallMap.get("location1");
        String location2Color = golfBallMap.get("location2");
        String location3Color = golfBallMap.get("location3");
        golfBallColors[0] = BallColor.valueOf(location1Color);
        golfBallColors[1] = BallColor.valueOf(location2Color);
        golfBallColors[2] = BallColor.valueOf(location3Color);
        Log.d(MainActivity.TAG, "Successfully received the monitor values from Firebase");
        return true;
//        } catch (Exception e) {
//            Log.e(MainActivity.TAG, "Loading monitor failed.  No monitor values were set.");
//            return false;
//        }
    }
}
