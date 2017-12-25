package com.mayank7319gmail.hospitallocator.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayank7319gmail.hospitallocator.activities.DetailActivity;
import com.mayank7319gmail.hospitallocator.R;
import com.mayank7319gmail.hospitallocator.models.SinglePlace;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 18-12-2017.
 */

public class HospitalListRecycler extends RecyclerView.Adapter<HospitalListRecycler.ListHolder>{

    private ArrayList<SinglePlace> itemList;
    private Context ctx;

    public HospitalListRecycler(ArrayList<SinglePlace> itemList, Context ctx) {
        this.itemList = itemList;
        this.ctx = ctx;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.layout_list_item ,parent , false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        final SinglePlace currentItem = itemList.get(position);

        String name = currentItem.getName();
        if(name.length()>25)
            name = name.substring(0,25);

        holder.tvName.setText(name);
        holder.tvIcon.setText(currentItem.getName().substring(0,1));

        String address = currentItem.getVicinity();
        if(address.length()>40)
            address = address.substring(0,40);

        holder.tvAddress.setText(address);
        holder.tvDistance.setText(currentItem.getDistanceString());
        holder.tvTime.setText(currentItem.getTimeString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx,DetailActivity.class);
                i.putExtra("placeId", currentItem.getPlaceId());
                i.putExtra("latitude",currentItem.getLoc().latitude);
                i.putExtra("longitude",currentItem.getLoc().longitude);
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvAddress, tvIcon, tvTime, tvDistance;
        View itemView;

        public ListHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvIcon = (TextView) itemView.findViewById(R.id.tvIcon);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
        }
    }
}
