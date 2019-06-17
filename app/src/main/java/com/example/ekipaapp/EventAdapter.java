package com.example.ekipaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.entity.Event;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private List<DataSnapshot> eventList;

    EventAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (eventList == null) {
            holder.nameTextView.setText("NULL");
            return;
        }
        DataSnapshot data = eventList.get(position);
        Event event = data.getValue(Event.class);
        holder.nameTextView.setText(event.getName());
        holder.urlTextView.setText(event.getUrl());
        holder.routeTextView.setText(String.valueOf(event.getRouteLength()));
        holder.rentPerPersonTextView.setText(String.valueOf(event.getRentalCostPerPerson()));
        holder.rentTextView.setText(String.valueOf(event.getRentalCostOverall()));
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditEventActivity.class);
            intent.putExtra(EditEventActivity.EVENT_ID, data.getKey());
            context.startActivity(intent);
        });
    }

    void setEventList(List<DataSnapshot> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView rentTextView;
        private final TextView urlTextView;
        private final TextView routeTextView;
        private final TextView rentPerPersonTextView;
        private final Button editButton;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            routeTextView = itemView.findViewById(R.id.routeLengthTextView);
            rentPerPersonTextView = itemView.findViewById(R.id.rentalCostPerPersonTextView);
            rentTextView = itemView.findViewById(R.id.rentalCostOverallTextView);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
