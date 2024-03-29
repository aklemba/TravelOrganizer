package com.example.ekipaapp.ui.location;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.viewmodel.LocationViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private final Context context;
    private final LocationViewModel locationViewModel;
    private final String eventKey;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private List<DataSnapshot> locationList;

    void setAllowedToVote(boolean allowedToVote) {
        this.allowedToVote = allowedToVote;
    }

    private boolean allowedToVote = false;

    LocationAdapter(Context context, LocationViewModel locationViewModel, String eventKey) {
        this.context = context;
        this.locationViewModel = locationViewModel;
        this.eventKey = eventKey;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        if (locationList == null) {
            holder.nameTextView.setText("NULL");
            return;
        }
        DataSnapshot data = locationList.get(position);
        Location location = data.getValue(Location.class);
        holder.nameTextView.setText(location.getName());
        holder.urlTextView.setText(location.getUrl());
        holder.routeTextView.setText(String.valueOf(location.getRouteLength()));
        holder.rentPerPersonTextView.setText(String.valueOf(location.getRentalCostPerPerson()));
        holder.rentTextView.setText(String.valueOf(location.getRentalCostOverall()));
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditLocationActivity.class);
            intent.putExtra(EditLocationActivity.LOCATION_KEY, data.getKey());
            intent.putExtra(LocationsActivity.EVENT_KEY, eventKey);
            context.startActivity(intent);
        });
        holder.removeButton.setOnClickListener(v -> {
            locationViewModel.removeLocation(data.getKey(), eventKey);
        });
        String userEmail = auth.getCurrentUser().getEmail();
        HashMap<String, String> votes = location.getVotes();

        boolean votedOn;
        if (votes == null) {
            votedOn = false;
            holder.voteCountTextView.setText("0");
        } else {
            votedOn = location.getVotes().containsValue(userEmail);
            holder.voteCountTextView.setText(String.valueOf(votes.size()));
        }
        if (votedOn || allowedToVote) {
            holder.voteCheck.setVisibility(View.VISIBLE);
        } else {
            holder.voteCheck.setVisibility(View.GONE);
        }
        holder.voteCheck.setChecked(votedOn);
        holder.voteCheck.setOnClickListener(v -> {
            if (!((CheckBox) v).isChecked()) {
                String emailKey;
                for (Map.Entry<String, String> entry : location.getVotes().entrySet()) {
                    if (entry.getValue().equals(userEmail)) {
                        emailKey = entry.getKey();
                        locationViewModel.unvote(data.getKey(), eventKey, emailKey);
                        break;
                    }
                }
                return;
            }
            locationViewModel.vote(data.getKey(), eventKey, userEmail);
        });
    }

    void setLocationList(List<DataSnapshot> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (locationList == null) {
            return 0;
        }
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView rentTextView;
        private final TextView urlTextView;
        private final TextView routeTextView;
        private final TextView rentPerPersonTextView;
        private final Button editButton;
        private final Button removeButton;
        private final CheckBox voteCheck;
        private final TextView voteCountTextView;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            routeTextView = itemView.findViewById(R.id.routeLengthTextView);
            rentPerPersonTextView = itemView.findViewById(R.id.rentalCostPerPersonTextView);
            rentTextView = itemView.findViewById(R.id.rentalCostOverallTextView);
            editButton = itemView.findViewById(R.id.editButton);
            removeButton = itemView.findViewById(R.id.removeButton);
            voteCheck = itemView.findViewById(R.id.voteCheckBox);
            voteCountTextView = itemView.findViewById(R.id.voteCountTextView);
        }
    }
}
