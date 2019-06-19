package com.example.ekipaapp.ui.event;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.R;
import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.ui.location.LocationsActivity;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

import static com.example.ekipaapp.ui.location.LocationsActivity.EVENT_KEY;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private List<DataSnapshot> eventList;
    private Context context;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        DataSnapshot data = eventList.get(position);
        holder.eventNameTextView.setText(data.getValue(Event.class).getName());
        holder.itemView.setOnClickListener(v -> startLocationsActivity(data.getKey()));
    }

    private void startLocationsActivity(String key) {
        Intent intent = new Intent(context, LocationsActivity.class);
        intent.putExtra(EVENT_KEY, key);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();
    }

    void setEventList(List<DataSnapshot> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{
        private final TextView eventNameTextView;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);

        }
    }
}
