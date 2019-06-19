package com.example.ekipaapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.R;
import com.example.ekipaapp.viewmodel.LocationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends AppCompatActivity {

    private RecyclerView locationRecyclerList;
    private LocationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_activity);
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
        locationRecyclerList = findViewById(R.id.location_list);
        final LocationAdapter locationAdapter = new LocationAdapter(this, viewModel);
        locationRecyclerList.setAdapter(locationAdapter);
        locationRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.open_add_location).setOnClickListener(this::openAddLocationActivity);
    }

    private void openAddLocationActivity(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }

    void initializeListeners() {
        viewModel.getAllLocations().addValueEventListener(locationsListener);
    }

    private final ValueEventListener locationsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<DataSnapshot> locationList = new ArrayList<>();
            for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                locationList.add(locationSnapshot);
            }
            LocationAdapter locationAdapter = (LocationAdapter) locationRecyclerList.getAdapter();
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
}
