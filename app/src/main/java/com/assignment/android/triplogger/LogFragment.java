package com.assignment.android.triplogger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FilePermission;
import java.util.List;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class LogFragment extends Fragment {
    public static final String ARG_TRIP_ID = "com.assignment.android.triplogger.trip_id";
    private static final int REQUEST_PHOTO = 0;

    private File mPhotoFile;
    private Spinner mTripType;
    private EditText mTitle;
    private ImageView TripPhoto;
    private ImageButton mPhotoButton;
    private Button mDateButton;
    private EditText destination;
    private EditText comments;
    private TextView mlocation;
    private Trip mTrip;
    private Button mSaveButton;
    private Button mCancelButton;
    private Location l;
    private GoogleApiClient mclient;

    public static LogFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(RecordActivity.EXTRA_TRIP_ID, tripId);

        LogFragment fragment = new LogFragment();
        fragment.setArguments(args);
        return fragment;
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
        mclient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mclient.disconnect();
    }
    @SuppressLint("MissingPermission")

  */  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("New Log");

        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        mTrip = TripLibrary.get(getActivity()).getTrip(tripId);
        mPhotoFile = TripLibrary.get(getActivity()).getPhotoFile(mTrip);

        /*mclient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                getActivity().invalidateOptionsMenu();
                LocationRequest request = LocationRequest.create();
                request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                request.setNumUpdates(1);
                request.setInterval(0);
                FusedLocationProviderClient f = new LocationServices.getFusedLocationProviderClient(this.getClass());
            }

            @Override
            public void onConnectionSuspended(int i) {


            }
        }).build();
*/

    }


    @Override
    public void onPause(){
        super.onPause();

        TripLibrary.get(getActivity()).updateTrip(mTrip);
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
    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri =
                    FileProvider.getUriForFile(getActivity(),
                            "com.assignment.android.triplogger.fileprovider",
                            mPhotoFile);
            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log,container,false);


        PackageManager pM =getActivity().getPackageManager();
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
        mPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile !=null && captureImage.resolveActivity(pM)!= null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.assignment.android.triplogger.fileprovider",mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity:cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage,REQUEST_PHOTO);
                }


        });
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
        mlocation = (TextView) v.findViewById(R.id.gps);
        mSaveButton=(Button)v.findViewById(R.id.trip_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i =new Intent(getActivity(),MainActivity.class);
                Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        mCancelButton=(Button)v.findViewById(R.id.trip_delete);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TripLibrary.get(getActivity()).deleteTrip(mTrip);
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),MainActivity.class);
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
