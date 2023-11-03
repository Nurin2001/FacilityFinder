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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.RecyclerViewHolder> {

    public List<ImageAndText> facilityItemList;
    private Context context;

    public HomeAdapter(List<ImageAndText> facilityItemList, Context context) {
        this.facilityItemList = facilityItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View facRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_row, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(facRow);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.facImg.setImageResource(facilityItemList.get(position).getItemImg());
        holder.facName.setText(facilityItemList.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return facilityItemList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView facName;
        public ImageView facImg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            facName = itemView.findViewById(R.id.fac_name_txt);
            facImg = itemView.findViewById(R.id.facilityitem_img);

        }
    }
}
