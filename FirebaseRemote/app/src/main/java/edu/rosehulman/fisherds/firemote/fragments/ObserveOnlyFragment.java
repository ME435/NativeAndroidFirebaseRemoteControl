package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Monitor;


/**
 * A simple {@link Fragment} subclass.
 */
public class ObserveOnlyFragment extends BaseFragment implements FirebaseState.MonitorChangeListener {


    /**
     * An array (of size 3) that keeps a reference to the 3 balls displayed on the UI.
     */
    private ImageButton[] mBallImageButtons;

    /**
     * An array constants (of size 7) that keeps a reference to the different ball color images resources.
     */
    // Note, the order is important and must be the same throughout the app.
    private static final int[] BALL_DRAWABLE_RESOURCES = new int[]{R.drawable.none_ball, R.drawable.blue_ball,
            R.drawable.red_ball, R.drawable.yellow_ball, R.drawable.green_ball, R.drawable.black_ball, R.drawable.white_ball};

    /**
     * TextViews that can change values.
     */
    private TextView mCurrentStateTextView, mStateTimeTextView, mGpsInfoTextView, mSensorOrientationTextView,
            mLeftDutyCycleTextView, mRightDutyCycleTextView, mMatchTimeTextView;
    private TextView mLeftRightLocationTextView, mTopBottomLocationTextView, mSizePercentageTextView;


    public ObserveOnlyFragment() {
    }

    public static ObserveOnlyFragment newInstance() {
        ObserveOnlyFragment fragment = new ObserveOnlyFragment();
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
        getFirebaseState().setMonitorDelegate(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getFirebaseState().setMonitorDelegate(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_observe_only, container, false);
        mBallImageButtons = new ImageButton[]{(ImageButton) view.findViewById(R.id.location_1_image_button),
                (ImageButton) view.findViewById(R.id.location_2_image_button),
                (ImageButton) view.findViewById(R.id.location_3_image_button)};
        mCurrentStateTextView = (TextView) view.findViewById(R.id.current_state_textview);
        mStateTimeTextView = (TextView) view.findViewById(R.id.state_time_textview);
        mGpsInfoTextView = (TextView) view.findViewById(R.id.gps_info_textview);
        mSensorOrientationTextView = (TextView) view.findViewById(R.id.orientation_textview);
        mLeftDutyCycleTextView = (TextView) view.findViewById(R.id.left_duty_cycle_textview);
        mRightDutyCycleTextView = (TextView) view.findViewById(R.id.right_duty_cycle_textview);
        mMatchTimeTextView = (TextView) view.findViewById(R.id.match_time_textview);
        mLeftRightLocationTextView = (TextView)view.findViewById(R.id.left_right_location_value);
        mTopBottomLocationTextView = (TextView)view.findViewById(R.id.top_bottom_location_value);
        mSizePercentageTextView = (TextView)view.findViewById(R.id.size_percentage_value);

        updateView(getFirebaseState().getMonitor());
        return view;
    }

    @Override
    public void onMonitorChanged(Monitor monitor) {
        updateView(monitor);

        // This fragment is often contained in another fragment.  Pass along the monitor updates.
        if (getParentFragment() instanceof FirebaseState.MonitorChangeListener) {
            ((FirebaseState.MonitorChangeListener) getParentFragment()).onMonitorChanged(monitor);
        }
    }

    private void updateView(Monitor monitor) {
        if (monitor != null) {
            for (int i = 0; i < 3; i++) {
                Monitor.BallColor ballColor = monitor.golfBallColors[i];
                mBallImageButtons[i].setImageResource(BALL_DRAWABLE_RESOURCES[ballColor.ordinal()]);
            }
            mCurrentStateTextView.setText(monitor.state);
            mStateTimeTextView.setText("" + (monitor.stateTimeMs / 1000));

            // GPS info as 1 line
            String gpsInfo = getString(R.string.xy_format, monitor.gpsX, monitor.gpsY);
            if (monitor.gpsHeading <= 180.0 && monitor.gpsHeading > -180.0) {
                gpsInfo += " " + getString(R.string.degrees_format, monitor.gpsHeading);
            } else {
                gpsInfo += " ?º";
            }
            gpsInfo += "    " + monitor.gpsHeadingCount + "/" + monitor.gpsTotalCount;
            mGpsInfoTextView.setText(gpsInfo);
            mSensorOrientationTextView.setText(getString(R.string.degrees_format, monitor.sensorHeading));
            if (monitor.coneFound) {
                mLeftRightLocationTextView.setText(String.format("%.3f", monitor.coneLeftRight));
                mTopBottomLocationTextView.setText(String.format("%.3f", monitor.coneTopBottom));
                mSizePercentageTextView.setText(String.format("%.5f", monitor.coneSizePercentage));
            } else {
                mLeftRightLocationTextView.setText("---");
                mTopBottomLocationTextView.setText("---");
                mSizePercentageTextView.setText("---");
            }
            mLeftDutyCycleTextView.setText("Left\n" + monitor.leftDutyCycle);
            mRightDutyCycleTextView.setText("Right\n" + monitor.rightDutyCycle);
            if (monitor.state.equalsIgnoreCase("READY_FOR_MISSION")) {
                mMatchTimeTextView.setText("5:00");
            } else {
                mMatchTimeTextView.setText(monitor.matchTime);
            }
        }
    }
}
