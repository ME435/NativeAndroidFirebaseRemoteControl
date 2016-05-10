package edu.rosehulman.fisherds.firemote.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.R;
import edu.rosehulman.fisherds.firemote.models.Params;


public class ParamsFragment extends BaseFragment implements FirebaseState.ParamsChangeListener {

    private TextView mLeftDutyCycleTextView, mRightDutyCycleTextView;
    private TextView mTargetHTextView, mTargetSTextView, mTargetVTextView;
    private TextView mRangeHTextView, mRangeSTextView, mRangeVTextView;

    public ParamsFragment() {
        // Required empty public constructor
    }

    public static ParamsFragment newInstance() {
        ParamsFragment fragment = new ParamsFragment();
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
        getFirebaseState().setParamsDelegate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_params, container, false);

        mLeftDutyCycleTextView = (TextView)view.findViewById(R.id.drive_straight_left_textview);
        mRightDutyCycleTextView = (TextView)view.findViewById(R.id.drive_straight_right_textview);

        mTargetHTextView = (TextView)view.findViewById(R.id.imgrec_target_h_textview);
        mTargetSTextView = (TextView)view.findViewById(R.id.imgrec_target_s_textview);
        mTargetVTextView = (TextView)view.findViewById(R.id.imgrec_target_v_textview);

        mRangeHTextView = (TextView)view.findViewById(R.id.imgrec_range_h_textview);
        mRangeSTextView = (TextView)view.findViewById(R.id.imgrec_range_s_textview);
        mRangeVTextView = (TextView)view.findViewById(R.id.imgrec_range_v_textview);

        updateView(getFirebaseState().getParams());
        return view;
    }

    private void updateView(Params params) {
        if (params != null) {
            mLeftDutyCycleTextView.setText("" + params.leftDutyCycle);
            mRightDutyCycleTextView.setText("" + params.rightDutyCycle);
            mTargetHTextView.setText("" + params.targetH);
            mTargetSTextView.setText("" + params.targetS);
            mTargetVTextView.setText("" + params.targetV);
            mRangeHTextView.setText("" + params.rangeH);
            mRangeSTextView.setText("" + params.rangeS);
            mRangeVTextView.setText("" + params.rangeV);
        }
    }

    @Override
    public void onParamsChanged(Params params) {
        updateView(params);
    }
}
