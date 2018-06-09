package com.assignment.android.triplogger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import java.io.File;
import java.util.UUID;

public class TripFragment extends Fragment {
    public static final String ARG_TRIP_ID="com.assignment.android.triplogger.trip_id";


    private Spinner mTripType;

    private File mPhotoFile;
    private EditText mTitle;
    private ImageView TripPhoto;
    private Button mDateButton;
    private Button mLocation;
    private EditText destination;
    private EditText comments;
    private TextView location;
    private Trip mTrip;
    private Button mSaveButton;
    private Button mDeleteButton;
    private Location l;

    public static TripFragment newInstance(UUID tripId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID,tripId);
        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updatePhotoView() {
        if (mPhotoFile == null ||
                !mPhotoFile.exists()) {
            TripPhoto.setImageDrawable(null);
        } else {
            Bitmap bitmap =
                    PictureUtils.getScaledBitmap(
                            mPhotoFile.getPath(),
                            getActivity());
            TripPhoto.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID tripId = (UUID)getArguments().getSerializable(ARG_TRIP_ID);
        mTrip =  TripLibrary.get(getActivity()).getTrip(tripId);
        getActivity().setTitle(mTrip.getTitle());
        mPhotoFile = TripLibrary.get(getActivity()).getPhotoFile(mTrip);
    }


    @Override
    public void onPause(){
        super.onPause();

        TripLibrary.get(getActivity()).updateTrip(mTrip);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trip,container,false);

        mTitle=(EditText)v.findViewById(R.id.trip_title);
        mTitle.setText(mTrip.getTitle());
        mTitle.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                mTrip.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });
        TripPhoto = (ImageView)v.findViewById(R.id.trip_photo);
        mDateButton = (Button) v.findViewById(R.id.trip_date);
        mDateButton.setText(mTrip.getDate().toString());
        destination = (EditText) v.findViewById(R.id.trip_dest);
        destination.setText(mTrip.getDest());
        destination.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                mTrip.setmDest(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });
        comments = (EditText) v.findViewById(R.id.trip_comments);
        comments.setText(mTrip.getComment());
        comments.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                mTrip.setmComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });
        mLocation = (Button) v.findViewById(R.id.gps);
        mSaveButton=(Button)v.findViewById(R.id.trip_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(),MainActivity.class);
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }

        });
        mDeleteButton=(Button)v.findViewById(R.id.trip_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(),MainActivity.class);
                TripLibrary.get(getActivity()).deleteTrip(mTrip);
                Toast.makeText(getActivity(), "Trip deleted", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        mTripType=(Spinner)v.findViewById(R.id.trip_type);
        ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.trips));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTripType.setAdapter(myAdapter);
        mTripType.setSelection(mTrip.getTripType());
        mTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mTrip.setTripType(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updatePhotoView();

        return v;
    }
}
