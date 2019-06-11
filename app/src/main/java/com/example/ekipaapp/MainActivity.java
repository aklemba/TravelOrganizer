package com.example.ekipaapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.viewmodel.EventViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        eventList = findViewById(R.id.event_list);
        final EventAdapter eventAdapter = new EventAdapter();
        eventList.setAdapter(eventAdapter);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                if (events.size() != 0) {
                    eventAdapter.setEventList(events);
                }
            }
        });
    }
}
