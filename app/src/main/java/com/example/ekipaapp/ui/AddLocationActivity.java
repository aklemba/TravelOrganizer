package com.example.ekipaapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.viewmodel.LocationViewModel;

public class AddLocationActivity extends AppCompatActivity {

    private LocationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_layout);

        initialize();
    }

    private void initialize() {
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);

        findViewById(R.id.confirmLocationButton).setOnClickListener(this::addLocation);

    }

    private void addLocation(View view) {
        EditText locationNameEditText = findViewById(R.id.locationNameEditText);
        EditText urlEditText = findViewById(R.id.urlEditText);
        EditText personCostEditText = findViewById(R.id.personCostEditText);
        EditText routeLengthEditText = findViewById(R.id.routeLengthEditText);

        Location newLocation = new Location(locationNameEditText.getText().toString());
        newLocation.setUrl(urlEditText.getText().toString());
        String personCostText = personCostEditText.getText().toString();
        if (!personCostText.isEmpty()) {
            newLocation.setRentalCostPerPerson(Integer.parseInt(personCostText));
        }
        String routeLengthText = routeLengthEditText.getText().toString();
        if (!routeLengthText.isEmpty()) {
            newLocation.setRouteLength(Integer.parseInt(routeLengthText));
        }
        viewModel.insertLocation(newLocation);
        finish();
    }

}
