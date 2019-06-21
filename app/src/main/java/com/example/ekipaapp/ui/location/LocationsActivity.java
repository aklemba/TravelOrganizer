package com.example.ekipaapp.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.firebase.FirebaseDatabaseConsts;
import com.example.ekipaapp.viewmodel.LocationViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LocationsActivity extends AppCompatActivity {

    public static final String EVENT_KEY = "eventKey";

    private LocationViewModel viewModel;
    private String eventKey;
    private String email;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_activity);
        eventKey = getIntent().getStringExtra(EVENT_KEY);
        initialize();
    }

    private void initialize() {
        initializeDatabaseAccess();
        initializeViews();
    }

    private void initializeDatabaseAccess() {
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    private void initializeViews() {
        RecyclerView locationRecyclerList = findViewById(R.id.location_list);
        locationAdapter = new LocationAdapter(this, viewModel, eventKey);
        locationRecyclerList.setAdapter(locationAdapter);
        locationRecyclerList.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.open_add_location).setOnClickListener(this::openAddLocationActivity);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    private void openAddLocationActivity(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        intent.putExtra(EVENT_KEY, eventKey);
        startActivity(intent);
    }

    void initializeListeners() {
        viewModel.getAllLocationsForEvent(eventKey).addValueEventListener(locationsListener);
    }

    private final ValueEventListener locationsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<DataSnapshot> locationList = new ArrayList<>();
            int counter = 0;
            for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                locationList.add(locationSnapshot);

                // Count the votes to check if there are any available
                Location location = locationSnapshot.getValue(Location.class);
                if (location == null) {
                    return;
                }
                HashMap<String, String> votes = location.getVotes();
                if (votes == null || !votes.containsValue(email)) {
                    continue;
                }
                counter++;
            }
            locationAdapter.setAllowedToVote(counter < FirebaseDatabaseConsts.MAX_VOTES);
            locationAdapter.setLocationList(locationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        initializeListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.getAllLocations().removeEventListener(locationsListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
