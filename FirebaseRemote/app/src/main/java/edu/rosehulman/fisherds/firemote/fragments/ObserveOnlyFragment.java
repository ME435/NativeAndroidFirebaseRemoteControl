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
            mGuessXYTextView, mLeftDutyCycleTextView, mRightDutyCycleTextView, mMatchTimeTextView;
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

        getFirebaseState().setMonitorDelegate(this);
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
        mGuessXYTextView = (TextView) view.findViewById(R.id.guess_location_textview);
        mLeftDutyCycleTextView = (TextView) view.findViewById(R.id.left_duty_cycle_textview);
        mRightDutyCycleTextView = (TextView) view.findViewById(R.id.right_duty_cycle_textview);
        mMatchTimeTextView = (TextView) view.findViewById(R.id.match_time_textview);

        return view;
    }

    @Override
    public void onMonitorChanged(Monitor monitor) {
        updateView(monitor);
    }

    private void updateView(Monitor monitor) {
//        mCurrentStateTextView.setText(monitor.state);
    }
}
