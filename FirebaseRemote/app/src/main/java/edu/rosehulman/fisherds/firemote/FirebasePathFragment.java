package edu.rosehulman.fisherds.firemote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Fragment to control the Firebase Path config screen.
 */
public class FirebasePathFragment extends Fragment {

    private EditText mFirebaseUrlEditText;
    private EditText mRobotNameEditText;

    public FirebasePathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firebase_path, container, false);

        mFirebaseUrlEditText = (EditText)view.findViewById(R.id.firebase_url);
        mRobotNameEditText = (EditText)view.findViewById(R.id.robot_name);
        Button setUrl = (Button)view.findViewById(R.id.set_firebase_url);

        setUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Setup the Firebase ref and toggle into observe only mode.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
