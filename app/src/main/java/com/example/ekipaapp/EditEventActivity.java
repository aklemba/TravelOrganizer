package com.example.ekipaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.viewmodel.EventViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EditEventActivity extends AppCompatActivity {

    public static final String EVENT_ID = "eventKey";
    private EventViewModel eventViewModel;
    private DatabaseReference eventData;
    private TextView eventNameEditText;
    private TextView urlEditText;
    private TextView personCostEditText;
    private TextView routeLengthEditText;
    private DataSnapshot eventSnapshot;
    private Button editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_layout);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        init();
    }

    private void init() {
        bindViews();
        setUpListener();
        getAndShowEvent();
    }

    private void getAndShowEvent() {
        String eventKey = getIntent().getStringExtra(EVENT_ID);
        if (eventKey == null) {
            Toast.makeText(this, "No event passed", Toast.LENGTH_SHORT).show();
            finish();
        }
        eventData = eventViewModel.getEventById(eventKey);
        eventData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventSnapshot = dataSnapshot;
                updateUI(dataSnapshot.getValue(Event.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindViews() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        urlEditText = findViewById(R.id.urlEditText);
        personCostEditText = findViewById(R.id.personCostEditText);
        routeLengthEditText = findViewById(R.id.routeLengthEditText);
        editButton = findViewById(R.id.confirmEventButton);
        editButton.setText(getString(R.string.edit_confirm));
    }

    private void setUpListener() {
        editButton.setOnClickListener((view) -> updateEventSnapshot());
    }

    private void updateEventSnapshot() {
        String name = eventNameEditText.getText().toString();
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
        Event event = eventSnapshot.getValue(Event.class);
        if (event == null) {
            Toast.makeText(this, "No event snapshot cached!", Toast.LENGTH_SHORT).show();
            return;
        }
        event.setName(name);
        event.setUrl(url);
        event.setRentalCostPerPerson(personCost);
        event.setRouteLength(routeLength);

        eventViewModel.updateEvent(eventSnapshot.getKey(), event);
        finish();
    }

    void updateUI(Event event) {
        eventNameEditText.setText(event.getName());
        urlEditText.setText(event.getUrl());
        personCostEditText.setText(String.valueOf(event.getRentalCostPerPerson()));
        routeLengthEditText.setText(String.valueOf(event.getRouteLength()));
    }

}
