package com.example.ekipaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekipaapp.entity.Event;

import java.util.List;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

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
        Event event = eventList.get(position);
        holder.nameTextView.setText(event.getName());
        holder.urlTextView.setText(event.getUrl());
        holder.routeTextView.setText(String.valueOf(event.getRouteLength()));
        holder.rentPerPersonTextView.setText(String.valueOf(event.getRentalCostPerPerson()));
        holder.rentTextView.setText(String.valueOf(event.getRentalCostOverall()));
    }

    void setEventList(List<Event> eventList) {
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

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            routeTextView = itemView.findViewById(R.id.routeLengthTextView);
            rentPerPersonTextView = itemView.findViewById(R.id.rentalCostPerPersonTextView);
            rentTextView = itemView.findViewById(R.id.rentalCostOverallTextView);
        }
    }
}
