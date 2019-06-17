package com.example.ekipaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.viewmodel.EventViewModel;

public class AddEventActivity extends AppCompatActivity {

    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_layout);

        initialize();
    }

    private void initialize() {
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        findViewById(R.id.confirmEventButton).setOnClickListener(this::addEvent);

    }

    private void addEvent(View view) {
        EditText eventNameEditText = findViewById(R.id.eventNameEditText);
        EditText urlEditText = findViewById(R.id.urlEditText);
        EditText personCostEditText = findViewById(R.id.personCostEditText);
        EditText routeLengthEditText = findViewById(R.id.routeLengthEditText);

        Event newEvent = new Event(eventNameEditText.getText().toString());
        newEvent.setUrl(urlEditText.getText().toString());
        String personCostText = personCostEditText.getText().toString();
        if (!personCostText.isEmpty()) {
            newEvent.setRentalCostPerPerson(Integer.parseInt(personCostText));
        }
        String routeLengthText = routeLengthEditText.getText().toString();
        if (!routeLengthText.isEmpty()) {
            newEvent.setRouteLength(Integer.parseInt(routeLengthText));
        }
        viewModel.insertEvent(newEvent);
        finish();
    }

}
