package com.assignment.android.triplogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TriplistFragment extends Fragment {
    private List<Trip> mTrips;
    private Button mLogButton;
    private Button mSettingsButton;
    private RecyclerView mTripRecyclerView;
    private TripAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mTrips = TripLibrary.get(getActivity()).getTrips();
    }

    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        TripLibrary TripLib = TripLibrary.get(getActivity());
        List<Trip> trips = TripLib.getTrips();

        if(mAdapter ==null){
            mAdapter = new TripAdapter(trips);
            mTripRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setTrips(trips);
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_triplist,container,false);

        mTripRecyclerView =(RecyclerView) v.findViewById(R.id.trip_recycler_view);
        mTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLogButton=(Button)v.findViewById(R.id.log_button);
        mLogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Trip t = new Trip();
                TripLibrary.get(getActivity()).addTrip(t);
                Intent i = new Intent(getActivity(),RecordActivity.class);
                i.putExtra(RecordActivity.EXTRA_FRAGMENT_ID,1);
                i.putExtra(RecordActivity.EXTRA_TRIP_ID,t.getId());
                startActivity(i);

            }
        });


        mSettingsButton=(Button) v.findViewById(R.id.settings);
        mSettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(),SettingsActivity.class);
                startActivity(i);

            }
        });
        updateUI();

        return v;
    }

    private class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Trip mTrip;
        public TextView mTitle;
        public TextView mTripDate;
        public TextView mDest;

        public TripHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle=(TextView)itemView.findViewById(R.id.trip_list_title);
            mTripDate=(TextView) itemView.findViewById(R.id.trip_list_date);
            mDest=(TextView) itemView.findViewById(R.id.trip_list_destination);
        }

        public void bindTrip(Trip trip){
            mTrip =trip;
            mTitle.setText(mTrip.getTitle());
            mTripDate.setText(mTrip.getDate().toString());
            mDest.setText(mTrip.getDest());
        }

        @Override
        public void onClick(View v){
            Intent i = new Intent(getActivity(),RecordActivity.class);
            i.putExtra(RecordActivity.EXTRA_FRAGMENT_ID,2);
            i.putExtra(RecordActivity.EXTRA_TRIP_ID,mTrip.getId());
            startActivity(i);
        }
    }

 public class TripAdapter extends RecyclerView.Adapter<TripHolder>{
        private List<Trip> mTrips;

        public TripAdapter(List<Trip> trips){
            mTrips=trips;
        }
        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.trip_list,parent,false);
            return new TripHolder(view);
        }

        @Override
     public void onBindViewHolder(TripHolder holder, int position){
            Trip trip =mTrips.get(position);
            holder.bindTrip(trip);
        }

        @Override
     public int getItemCount(){return mTrips.size();}

     public void setTrips(List<Trip> trips){
            mTrips = trips;
     }
 }

}


