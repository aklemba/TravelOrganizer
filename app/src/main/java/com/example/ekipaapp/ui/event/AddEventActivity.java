package com.example.ekipaapp.ui.event;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.viewmodel.EventViewModel;

public class AddEventActivity extends AppCompatActivity {
    private EditText eventNameTextView;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);
        init();
    }

    private void init() {
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        Button addEventButton = findViewById(R.id.confirmAddingEventButton);
        eventNameTextView = findViewById(R.id.setEvetNameEditText);
        addEventButton.setOnClickListener(v -> {
            String eventName = eventNameTextView.getText().toString();
            if (eventName.equals("")) {
                Toast.makeText(this, "No name given!", Toast.LENGTH_SHORT).show();
                return;
            }
            Event event = new Event();
            event.setName(eventName);
            eventViewModel.addEvent(event);
            finish();
        });
    }
}
