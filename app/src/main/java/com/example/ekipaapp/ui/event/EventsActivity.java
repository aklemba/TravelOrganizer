package com.example.ekipaapp.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.R;
import com.example.ekipaapp.viewmodel.EventViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private EventsAdapter eventsAdapter;
    private EventViewModel eventViewModel;
    private FrameLayout loadingSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
        init();
    }

    private void init() {
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        RecyclerView eventRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsAdapter = new EventsAdapter();
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventRecyclerView.setAdapter(eventsAdapter);
        findViewById(R.id.addEventButton).setOnClickListener(v -> startAddEventActivity());
        loadingSpinner = findViewById(R.id.loadingSpinner);
    }

    private void startAddEventActivity() {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventViewModel.getAllEvents().addValueEventListener(listener);
        enableLoadingSpinner(true);
    }

    private void enableLoadingSpinner(boolean enable) {
        if (!enable) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loadingSpinner.setVisibility(View.GONE);
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventViewModel.getAllEvents().removeEventListener(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            enableLoadingSpinner(true);
            List<DataSnapshot> eventList = new ArrayList<>();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                eventList.add(data);
            }
            eventsAdapter.setEventList(eventList);
            enableLoadingSpinner(false);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
