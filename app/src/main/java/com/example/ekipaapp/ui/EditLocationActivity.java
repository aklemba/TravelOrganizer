package com.example.ekipaapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.viewmodel.LocationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EditLocationActivity extends AppCompatActivity {

    public static final String EVENT_ID = "locationKey";
    private LocationViewModel locationViewModel;
    private DatabaseReference locationData;
    private TextView locationNameEditText;
    private TextView urlEditText;
    private TextView personCostEditText;
    private TextView routeLengthEditText;
    private DataSnapshot locationSnapshot;
    private Button editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_layout);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        init();
    }

    private void init() {
        bindViews();
        setUpListener();
        getAndShowLocation();
    }

    private void getAndShowLocation() {
        String locationKey = getIntent().getStringExtra(EVENT_ID);
        if (locationKey == null) {
            Toast.makeText(this, "No location passed", Toast.LENGTH_SHORT).show();
            finish();
        }
        locationData = locationViewModel.getLocationById(locationKey);
        locationData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationSnapshot = dataSnapshot;
                updateUI(dataSnapshot.getValue(Location.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindViews() {
        locationNameEditText = findViewById(R.id.locationNameEditText);
        urlEditText = findViewById(R.id.urlEditText);
        personCostEditText = findViewById(R.id.personCostEditText);
        routeLengthEditText = findViewById(R.id.routeLengthEditText);
        editButton = findViewById(R.id.confirmLocationButton);
        editButton.setText(getString(R.string.edit_confirm));
    }

    private void setUpListener() {
        editButton.setOnClickListener((view) -> updateLocationSnapshot());
    }

    private void updateLocationSnapshot() {
        String name = locationNameEditText.getText().toString();
        String url = urlEditText.getText().toString();
        String personCostText = personCostEditText.getText().toString();
        int personCost = 0;
        if (!personCostText.isEmpty()) {
            personCost = Integer.valueOf(personCostText);
        }
        String routeLengthText = routeLengthEditText.getText().toString();
        int routeLength = 0;
        if (!routeLengthText.isEmpty()) {
            routeLength = Integer.valueOf(routeLengthText);
        }
        Location location = locationSnapshot.getValue(Location.class);
        if (location == null) {
            Toast.makeText(this, "No location snapshot cached!", Toast.LENGTH_SHORT).show();
            return;
        }
        location.setName(name);
        location.setUrl(url);
        location.setRentalCostPerPerson(personCost);
        location.setRouteLength(routeLength);

        locationViewModel.updateLocation(locationSnapshot.getKey(), location);
        finish();
    }

    void updateUI(Location location) {
        locationNameEditText.setText(location.getName());
        urlEditText.setText(location.getUrl());
        personCostEditText.setText(String.valueOf(location.getRentalCostPerPerson()));
        routeLengthEditText.setText(String.valueOf(location.getRouteLength()));
    }

}
