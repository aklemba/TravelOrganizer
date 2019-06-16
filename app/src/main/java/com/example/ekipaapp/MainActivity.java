package com.example.ekipaapp;

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

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.viewmodel.EventViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eventRecyclerList;
    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initialize();
    }

    private void initialize() {
        initializeDatabaseAccess();
        initializeViews();
    }

    private void initializeDatabaseAccess() {
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
    }

    private void initializeViews() {
        eventRecyclerList = findViewById(R.id.event_list);
        final EventAdapter eventAdapter = new EventAdapter();
        eventRecyclerList.setAdapter(eventAdapter);
        eventRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.open_add_event).setOnClickListener(this::openAddEventActivity);
        initializeListeners();
    }

    private void openAddEventActivity(View view) {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private void clearDatabase(View view) {
        viewModel.removeAllEvents();
    }

    void initializeListeners() {

        findViewById(R.id.removeAllEventsButton).setOnClickListener(this::clearDatabase);

        viewModel.getAllEvents().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Event> eventList = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    eventList.add(eventSnapshot.getValue(Event.class));
                }
                EventAdapter eventAdapter = (EventAdapter) eventRecyclerList.getAdapter();
                eventAdapter.setEventList(eventList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
