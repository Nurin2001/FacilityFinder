package com.assignment.facilityfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.RecyclerViewHolder> {

    public List<ImageAndText> locationList;
    private Context context;

    public LocationAdapter(List<ImageAndText> locationList, Context context) {
        this.locationList = locationList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View facRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_row, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(facRow);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.locImg.setImageResource(locationList.get(position).getItemImg());
        holder.locName.setText(locationList.get(position).getItemName());
    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView locName;
        public ImageView locImg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            locName = itemView.findViewById(R.id.loc_name);
            locImg = itemView.findViewById(R.id.loc_img);

        }
    }
}
