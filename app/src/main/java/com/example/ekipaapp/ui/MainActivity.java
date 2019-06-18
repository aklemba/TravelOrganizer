package com.example.ekipaapp.ui;

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
import com.example.ekipaapp.viewmodel.EventViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        if (!checkIfLoggedIn()) {
            startLoginActivity();
            finish();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean checkIfLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
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
        final EventAdapter eventAdapter = new EventAdapter(this, viewModel);
        eventRecyclerList.setAdapter(eventAdapter);
        eventRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.open_add_event).setOnClickListener(this::openAddEventActivity);
    }

    private void openAddEventActivity(View view) {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    void initializeListeners() {
        findViewById(R.id.logoutButton).setOnClickListener(this::logout);
        viewModel.getAllEvents().addValueEventListener(eventsListener);
    }

    private void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startLoginActivity();
    }

    private final ValueEventListener eventsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<DataSnapshot> eventList = new ArrayList<>();
            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                eventList.add(eventSnapshot);
            }
            EventAdapter eventAdapter = (EventAdapter) eventRecyclerList.getAdapter();
            eventAdapter.setEventList(eventList);
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
        viewModel.getAllEvents().removeEventListener(eventsListener);
    }
}
