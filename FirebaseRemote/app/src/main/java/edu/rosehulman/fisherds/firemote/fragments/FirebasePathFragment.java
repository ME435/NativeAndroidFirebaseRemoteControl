package edu.rosehulman.fisherds.firemote.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.rosehulman.fisherds.firemote.FirebaseState;
import edu.rosehulman.fisherds.firemote.MainActivity;
import edu.rosehulman.fisherds.firemote.R;


/**
 * Fragment to control the Firebase Path config screen.
 */
public class FirebasePathFragment extends BaseFragment {

    private OnFirebasePathSetListener mListener;
    private EditText mFirebaseUrlEditText;
    private EditText mRobotNameEditText;

    public FirebasePathFragment() {
        // Required empty public constructor
    }


    public static FirebasePathFragment newInstance(FirebaseState firebaseState) {
        FirebasePathFragment fragment = new FirebasePathFragment();

        // Note: The recommended patern is to use the arguments and recover them in onCreate
        //  I didn't want to do that because I wanted an object.
        fragment.setFirebaseState(firebaseState);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // Result is ignored intentionally.
        View view = inflater.inflate(R.layout.fragment_firebase_path, container, false);

        Log.d(MainActivity.TAG, "Creating the view for the Firebase path");
        mFirebaseUrlEditText = (EditText)view.findViewById(R.id.firebase_url_edittext);
        mRobotNameEditText = (EditText)view.findViewById(R.id.robot_name_edittext);
        Button setUrl = (Button)view.findViewById(R.id.set_firebase_url);

        setUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirebasePathFragment.this.getActivity(), "You clicked Set", Toast.LENGTH_SHORT).show();
                String urlBase = mFirebaseUrlEditText.getText().toString();
                String robotName = mRobotNameEditText.getText().toString();
                Log.d(MainActivity.TAG, "Check to make sure mFirebaseState exist (Firebase State) = " + mFirebaseState);
                mFirebaseState.initialize(urlBase, robotName);

                if (mListener != null) {
                    mListener.onFirebasePathSet();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirebasePathSetListener) {
            mListener = (OnFirebasePathSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     * See the Android Training lesson
     * http://developer.android.com/training/basics/fragments/communicating.html
     * Communicating with Other Fragments for more information.
     */
    public interface OnFirebasePathSetListener {
        void onFirebasePathSet();
    }
}
