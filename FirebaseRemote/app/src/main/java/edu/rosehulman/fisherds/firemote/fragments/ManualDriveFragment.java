package edu.rosehulman.fisherds.firemote.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.rosehulman.fisherds.firemote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManualDriveFragment extends BaseFragment {

    private int mSpeedPercentage = 100;
    public static final int MAX_DUTY_CYCLE = 255;
    public static final int PARTIAL_DUTY_CYCLE = 155;
    private TextView mLeftWheelSpeedTextView, mRightWheelSpeedTextView, mSpeedPercentageTextView;

    public ManualDriveFragment() {
        // Required empty public constructor
    }

    public static ManualDriveFragment newInstance() {
        ManualDriveFragment fragment = new ManualDriveFragment();
        // Spot for custom initialization.
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manual_drive, container, false);

        mLeftWheelSpeedTextView = (TextView) view.findViewById(R.id.current_speed_left_duty_cycle_textview);
        mRightWheelSpeedTextView = (TextView) view.findViewById(R.id.current_speed_right_duty_cycle_textview);
        mSpeedPercentageTextView = (TextView) view.findViewById(R.id.speed_percentage_textview);

        SeekBar speedSeekbar = (SeekBar)view.findViewById(R.id.speed_seekbar);
        speedSeekbar.setMax(100);
        speedSeekbar.setProgress(mSpeedPercentage);
        speedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSpeedPercentage = progress;
                mSpeedPercentageTextView.setText(mSpeedPercentage + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Button click listeners...
        (view.findViewById(R.id.wheel_speed_stop_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleStopClick(v);
                    }
                });
        (view.findViewById(R.id.wheel_speed_forward_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleForwardClick(v);
                    }
                });
        (view.findViewById(R.id.wheel_speed_reverse_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleBackClick(v);
                    }
                });
        (view.findViewById(R.id.wheel_speed_left_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleLeftClick(v);
                    }
                });
        (view.findViewById(R.id.wheel_speed_right_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleRightClick(v);
                    }
                });
        (view.findViewById(R.id.position_home_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleHomeClick(v);
                    }
                });
        (view.findViewById(R.id.position1_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handlePosition1Click(v);
                    }
                });
        (view.findViewById(R.id.position2_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handlePosition2Click(v);
                    }
                });
        (view.findViewById(R.id.script1_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleScript1Click(v);
                    }
                });
        (view.findViewById(R.id.script2_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleScript2Click(v);
                    }
                });
        (view.findViewById(R.id.script3_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleScript3Click(v);
                    }
                });
        (view.findViewById(R.id.set_origin_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleSetOrigin(v);
                    }
                });
        (view.findViewById(R.id.set_x_axis_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleSetXAxis(v);
                    }
                });
        (view.findViewById(R.id.reset_heading_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleZeroHeading(v);
                    }
                });
//        (view.findViewById(R.id._button)).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        handle(v);
//                    }
//                });

        return view;
    }

    private void sendSpeed(int unscaledLeftDutyCycle, int unscaledRightDutyCycle) {
        int scaledLeftDC = unscaledLeftDutyCycle * mSpeedPercentage / 100;
        int scaledRightDC = unscaledRightDutyCycle * mSpeedPercentage / 100;
        mLeftWheelSpeedTextView.setText("" + scaledLeftDC);
        mRightWheelSpeedTextView.setText("" + scaledRightDC);
        String speedStr = String.format("%1$d, %2$d", scaledLeftDC, scaledRightDC);
        getFirebaseState().setSendWheelSpeed(speedStr);
    }

    public void handleStopClick(View view) {
        sendSpeed(0, 0);
    }

    public void handleForwardClick(View view) {
        sendSpeed(MAX_DUTY_CYCLE, MAX_DUTY_CYCLE);
    }

    public void handleBackClick(View view) {
        sendSpeed(-PARTIAL_DUTY_CYCLE, -PARTIAL_DUTY_CYCLE);
    }

    public void handleLeftClick(View view) {
        sendSpeed(PARTIAL_DUTY_CYCLE, MAX_DUTY_CYCLE);
    }

    public void handleRightClick(View view) {
        sendSpeed(MAX_DUTY_CYCLE, PARTIAL_DUTY_CYCLE);
    }

    private void handleHomeClick(View v) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "handleHomeClick", Toast.LENGTH_SHORT).show();
        getFirebaseState().setSendCommand("ATTACH 111111");
        Handler commandHandler = new Handler();
        commandHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFirebaseState().setSendCommand("POSITION 0 90 0 -90 90");
            }
        }, 1000);
    }

    public void handlePosition1Click(View view) {
        getFirebaseState().setSendCommand("0 45 0 0 0");
    }

    public void handlePosition2Click(View view) {
        getFirebaseState().setSendCommand("-5 135 20 -45 90");
    }

    public void handleScript1Click(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set custom to script1", Toast.LENGTH_SHORT).show();
        getFirebaseState().setCustom("script1");
    }

    public void handleScript2Click(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set custom to script2", Toast.LENGTH_SHORT).show();
        getFirebaseState().setCustom("script2");
    }

    public void handleScript3Click(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set custom to script3", Toast.LENGTH_SHORT).show();
        getFirebaseState().setCustom("script3");
    }

    public void handleSetOrigin(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set pressButton to setOrigin", Toast.LENGTH_SHORT).show();
        getFirebaseState().setPressButton("setOrigin");
    }

    public void handleSetXAxis(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set pressButton to setOrigin", Toast.LENGTH_SHORT).show();
        getFirebaseState().setPressButton("setXAxis");
    }

    public void handleZeroHeading(View view) {
//        Toast.makeText(ManualDriveFragment.this.getActivity(), "Set pressButton to setOrigin", Toast.LENGTH_SHORT).show();
        getFirebaseState().setPressButton("zeroHeading");
    }
}
